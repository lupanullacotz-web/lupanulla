package com.example.db

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface AcademicDao {
    // --- Bookmarks ---
    @Query("SELECT * FROM bookmarks ORDER BY timestamp DESC")
    fun getAllBookmarks(): Flow<List<Bookmark>>

    @Query("SELECT EXISTS(SELECT 1 FROM bookmarks WHERE topicId = :topicId)")
    fun isBookmarked(topicId: String): Flow<Boolean>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBookmark(bookmark: Bookmark)

    @Query("DELETE FROM bookmarks WHERE topicId = :topicId")
    suspend fun deleteBookmark(topicId: String)

    // --- Study Progress ---
    @Query("SELECT * FROM study_progress")
    fun getAllProgress(): Flow<List<StudyProgress>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveProgress(progress: StudyProgress)

    @Query("SELECT EXISTS(SELECT 1 FROM study_progress WHERE topicId = :topicId AND isCompleted = 1)")
    fun isCompleted(topicId: String): Flow<Boolean>

    // --- Quiz History ---
    @Query("SELECT * FROM quiz_history ORDER BY timestamp DESC")
    fun getQuizHistory(): Flow<List<QuizHistory>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertQuizHistory(history: QuizHistory)

    // --- Chat Messages ---
    @Query("SELECT * FROM chat_messages ORDER BY timestamp ASC")
    fun getChatMessages(): Flow<List<ChatMessageEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertChatMessage(message: ChatMessageEntity)

    @Query("DELETE FROM chat_messages")
    suspend fun clearChatMessages()
}
