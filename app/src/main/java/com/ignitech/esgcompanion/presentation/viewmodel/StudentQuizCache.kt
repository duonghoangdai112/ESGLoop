package com.ignitech.esgcompanion.presentation.viewmodel

object StudentQuizCache {
    private var cachedQuiz: StudentQuiz? = null
    
    fun saveQuiz(quiz: StudentQuiz) {
        cachedQuiz = quiz
    }
    
    fun getQuiz(quizId: String): StudentQuiz? {
        return if (cachedQuiz?.id == quizId) {
            cachedQuiz
        } else {
            null
        }
    }
    
    fun clear() {
        cachedQuiz = null
    }
}

