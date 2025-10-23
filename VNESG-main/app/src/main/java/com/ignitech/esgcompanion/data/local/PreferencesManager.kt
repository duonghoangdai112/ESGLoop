package com.ignitech.esgcompanion.data.local

import android.content.Context
import android.content.SharedPreferences
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PreferencesManager @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val prefs: SharedPreferences = context.getSharedPreferences(
        "esg_companion_prefs", 
        Context.MODE_PRIVATE
    )
    
    companion object {
        private const val KEY_REMEMBER_ME = "remember_me"
        private const val KEY_SAVED_EMAIL = "saved_email"
        private const val KEY_SAVED_PASSWORD = "saved_password"
        private const val KEY_CURRENT_USER_ID = "current_user_id"
        private const val KEY_LANGUAGE = "app_language"
    }
    
    fun setRememberMe(rememberMe: Boolean) {
        prefs.edit().putBoolean(KEY_REMEMBER_ME, rememberMe).apply()
    }
    
    fun getRememberMe(): Boolean {
        return prefs.getBoolean(KEY_REMEMBER_ME, false)
    }
    
    fun setSavedCredentials(email: String, password: String) {
        prefs.edit()
            .putString(KEY_SAVED_EMAIL, email)
            .putString(KEY_SAVED_PASSWORD, password)
            .apply()
    }
    
    fun getSavedEmail(): String? {
        return prefs.getString(KEY_SAVED_EMAIL, null)
    }
    
    fun getSavedPassword(): String? {
        return prefs.getString(KEY_SAVED_PASSWORD, null)
    }
    
    fun clearSavedCredentials() {
        prefs.edit()
            .remove(KEY_SAVED_EMAIL)
            .remove(KEY_SAVED_PASSWORD)
            .apply()
    }
    
    fun setCurrentUserId(userId: String) {
        prefs.edit().putString(KEY_CURRENT_USER_ID, userId).apply()
    }
    
    fun getCurrentUserId(): String? {
        return prefs.getString(KEY_CURRENT_USER_ID, null)
    }
    
    fun clearCurrentUserId() {
        prefs.edit().remove(KEY_CURRENT_USER_ID).apply()
    }
    
    fun setLanguage(languageCode: String) {
        prefs.edit().putString(KEY_LANGUAGE, languageCode).apply()
    }
    
    fun getLanguage(): String {
        return prefs.getString(KEY_LANGUAGE, "") ?: ""
    }
}
