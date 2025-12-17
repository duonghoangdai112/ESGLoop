package com.ignitech.esgcompanion.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ignitech.esgcompanion.data.local.entity.QuestionEntity
import com.ignitech.esgcompanion.data.repository.QuestionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(kotlinx.coroutines.ExperimentalCoroutinesApi::class)
@HiltViewModel
class QuestionLibraryViewModel @Inject constructor(
    private val questionRepository: QuestionRepository
) : ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _selectedType = MutableStateFlow("Tất cả")
    val selectedType: StateFlow<String> = _selectedType.asStateFlow()

    private val _selectedDifficulty = MutableStateFlow("Tất cả")
    val selectedDifficulty: StateFlow<String> = _selectedDifficulty.asStateFlow()

    private val _selectedQuestions = MutableStateFlow(setOf<String>())
    val selectedQuestions: StateFlow<Set<String>> = _selectedQuestions.asStateFlow()

    val questions: StateFlow<List<QuestionEntity>> = combine(
        searchQuery,
        selectedType,
        selectedDifficulty
    ) { search, type, difficulty ->
        when {
            search.isNotEmpty() && type != "Tất cả" && difficulty != "Tất cả" -> {
                questionRepository.searchQuestionsByTypeAndDifficulty(search, type, difficulty)
            }
            search.isNotEmpty() && type != "Tất cả" -> {
                questionRepository.searchQuestionsByType(search, type)
            }
            search.isNotEmpty() && difficulty != "Tất cả" -> {
                questionRepository.searchQuestionsByDifficulty(search, difficulty)
            }
            search.isNotEmpty() -> {
                questionRepository.searchQuestions(search)
            }
            type != "Tất cả" && difficulty != "Tất cả" -> {
                questionRepository.getQuestionsByTypeAndDifficulty(type, difficulty)
            }
            type != "Tất cả" -> {
                questionRepository.getQuestionsByType(type)
            }
            difficulty != "Tất cả" -> {
                questionRepository.getQuestionsByDifficulty(difficulty)
            }
            else -> {
                questionRepository.getAllQuestions()
            }
        }
    }.flatMapLatest { it }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    val types: StateFlow<List<String>> = questionRepository.getAllTypes()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = listOf("Tất cả")
        )

    val difficulties: StateFlow<List<String>> = questionRepository.getAllDifficulties()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = listOf("Tất cả")
        )

    val categories: StateFlow<List<String>> = questionRepository.getAllCategories()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = listOf("Tất cả")
        )

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun updateSelectedType(type: String) {
        _selectedType.value = type
    }

    fun updateSelectedDifficulty(difficulty: String) {
        _selectedDifficulty.value = difficulty
    }

    fun toggleQuestionSelection(questionId: String) {
        _selectedQuestions.value = if (_selectedQuestions.value.contains(questionId)) {
            _selectedQuestions.value - questionId
        } else {
            _selectedQuestions.value + questionId
        }
    }

    fun clearSelection() {
        _selectedQuestions.value = emptySet()
    }

    fun selectAllVisible(visibleQuestionIds: List<String>) {
        _selectedQuestions.value = _selectedQuestions.value + visibleQuestionIds.toSet()
    }

    fun deselectAllVisible(visibleQuestionIds: List<String>) {
        _selectedQuestions.value = _selectedQuestions.value - visibleQuestionIds.toSet()
    }
}
