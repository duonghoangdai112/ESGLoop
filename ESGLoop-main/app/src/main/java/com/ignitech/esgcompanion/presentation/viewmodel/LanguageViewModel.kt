package com.ignitech.esgcompanion.presentation.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ignitech.esgcompanion.data.local.PreferencesManager
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class LanguageViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val preferencesManager: PreferencesManager
) : ViewModel() {
    
    private val _currentLanguage = MutableStateFlow(getCurrentLanguage())
    val currentLanguage: StateFlow<String> = _currentLanguage.asStateFlow()
    
    companion object {
        private const val KEY_LANGUAGE = "app_language"
    }
    
    private fun getCurrentLanguage(): String {
        val savedLanguage = preferencesManager.getLanguage()
        if (savedLanguage.isNotEmpty()) {
            return savedLanguage
        }
        
        // Default to system language or Vietnamese
        val systemLanguage = Locale.getDefault().language
        return if (systemLanguage in listOf("vi", "en")) {
            systemLanguage
        } else {
            "vi" // Default to Vietnamese
        }
    }
    
    fun setLanguage(languageCode: String) {
        viewModelScope.launch {
            preferencesManager.setLanguage(languageCode)
            _currentLanguage.value = languageCode
            
            // Update app locale
            updateAppLocale(languageCode)
        }
    }
    
    private fun updateAppLocale(languageCode: String) {
        val locale = when (languageCode) {
            "en" -> Locale.ENGLISH
            "vi" -> Locale("vi")
            else -> Locale("vi")
        }
        
        Locale.setDefault(locale)
        
        val configuration = context.resources.configuration
        configuration.setLocale(locale)
        context.resources.updateConfiguration(configuration, context.resources.displayMetrics)
    }
}
