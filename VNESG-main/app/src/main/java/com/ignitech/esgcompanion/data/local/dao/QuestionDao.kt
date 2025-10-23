package com.ignitech.esgcompanion.data.local.dao

import androidx.room.*
import com.ignitech.esgcompanion.data.local.entity.QuestionEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface QuestionDao {
    @Query("SELECT * FROM questions ORDER BY createdAt DESC")
    fun getAllQuestions(): Flow<List<QuestionEntity>>

    @Query("SELECT * FROM questions WHERE title LIKE '%' || :searchQuery || '%' OR category LIKE '%' || :searchQuery || '%' ORDER BY createdAt DESC")
    fun searchQuestions(searchQuery: String): Flow<List<QuestionEntity>>

    @Query("SELECT * FROM questions WHERE type = :type ORDER BY createdAt DESC")
    fun getQuestionsByType(type: String): Flow<List<QuestionEntity>>

    @Query("SELECT * FROM questions WHERE difficulty = :difficulty ORDER BY createdAt DESC")
    fun getQuestionsByDifficulty(difficulty: String): Flow<List<QuestionEntity>>

    @Query("SELECT * FROM questions WHERE category = :category ORDER BY createdAt DESC")
    fun getQuestionsByCategory(category: String): Flow<List<QuestionEntity>>

    @Query("SELECT * FROM questions WHERE type = :type AND difficulty = :difficulty ORDER BY createdAt DESC")
    fun getQuestionsByTypeAndDifficulty(type: String, difficulty: String): Flow<List<QuestionEntity>>

    @Query("SELECT * FROM questions WHERE title LIKE '%' || :searchQuery || '%' AND type = :type ORDER BY createdAt DESC")
    fun searchQuestionsByType(searchQuery: String, type: String): Flow<List<QuestionEntity>>

    @Query("SELECT * FROM questions WHERE title LIKE '%' || :searchQuery || '%' AND difficulty = :difficulty ORDER BY createdAt DESC")
    fun searchQuestionsByDifficulty(searchQuery: String, difficulty: String): Flow<List<QuestionEntity>>

    @Query("SELECT * FROM questions WHERE title LIKE '%' || :searchQuery || '%' AND type = :type AND difficulty = :difficulty ORDER BY createdAt DESC")
    fun searchQuestionsByTypeAndDifficulty(searchQuery: String, type: String, difficulty: String): Flow<List<QuestionEntity>>

    @Query("SELECT * FROM questions WHERE id = :id")
    suspend fun getQuestionById(id: String): QuestionEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertQuestion(question: QuestionEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertQuestions(questions: List<QuestionEntity>)

    @Update
    suspend fun updateQuestion(question: QuestionEntity)

    @Delete
    suspend fun deleteQuestion(question: QuestionEntity)

    @Query("DELETE FROM questions")
    suspend fun deleteAllQuestions()

    @Query("SELECT DISTINCT type FROM questions ORDER BY type")
    fun getAllTypes(): Flow<List<String>>

    @Query("SELECT DISTINCT difficulty FROM questions ORDER BY difficulty")
    fun getAllDifficulties(): Flow<List<String>>

    @Query("SELECT DISTINCT category FROM questions ORDER BY category")
    fun getAllCategories(): Flow<List<String>>
}
