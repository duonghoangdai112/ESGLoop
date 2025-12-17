package com.ignitech.esgcompanion.data.repository

import com.ignitech.esgcompanion.data.local.dao.QuestionDao
import com.ignitech.esgcompanion.data.local.entity.QuestionEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class QuestionRepository @Inject constructor(
    private val questionDao: com.ignitech.esgcompanion.data.local.dao.QuestionDao
) {
    fun getAllQuestions(): Flow<List<QuestionEntity>> = questionDao.getAllQuestions()

    fun searchQuestions(searchQuery: String): Flow<List<QuestionEntity>> = 
        questionDao.searchQuestions(searchQuery)

    fun getQuestionsByType(type: String): Flow<List<QuestionEntity>> = 
        questionDao.getQuestionsByType(type)

    fun getQuestionsByDifficulty(difficulty: String): Flow<List<QuestionEntity>> = 
        questionDao.getQuestionsByDifficulty(difficulty)

    fun getQuestionsByCategory(category: String): Flow<List<QuestionEntity>> = 
        questionDao.getQuestionsByCategory(category)

    fun getQuestionsByTypeAndDifficulty(type: String, difficulty: String): Flow<List<QuestionEntity>> = 
        questionDao.getQuestionsByTypeAndDifficulty(type, difficulty)

    fun searchQuestionsByType(searchQuery: String, type: String): Flow<List<QuestionEntity>> = 
        questionDao.searchQuestionsByType(searchQuery, type)

    fun searchQuestionsByDifficulty(searchQuery: String, difficulty: String): Flow<List<QuestionEntity>> = 
        questionDao.searchQuestionsByDifficulty(searchQuery, difficulty)

    fun searchQuestionsByTypeAndDifficulty(searchQuery: String, type: String, difficulty: String): Flow<List<QuestionEntity>> = 
        questionDao.searchQuestionsByTypeAndDifficulty(searchQuery, type, difficulty)

    suspend fun getQuestionById(id: String): QuestionEntity? = questionDao.getQuestionById(id)

    suspend fun insertQuestion(question: QuestionEntity) = questionDao.insertQuestion(question)

    suspend fun insertQuestions(questions: List<QuestionEntity>) = questionDao.insertQuestions(questions)

    suspend fun updateQuestion(question: QuestionEntity) = questionDao.updateQuestion(question)

    suspend fun deleteQuestion(question: QuestionEntity) = questionDao.deleteQuestion(question)

    suspend fun deleteAllQuestions() = questionDao.deleteAllQuestions()

    fun getAllTypes(): Flow<List<String>> = questionDao.getAllTypes()

    fun getAllDifficulties(): Flow<List<String>> = questionDao.getAllDifficulties()

    fun getAllCategories(): Flow<List<String>> = questionDao.getAllCategories()
}
