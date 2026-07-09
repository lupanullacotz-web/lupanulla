package com.example.repository

import com.example.db.*
import com.example.model.*
import kotlinx.coroutines.flow.Flow

class AcademicRepository(private val dao: AcademicDao) {

    // --- In-Memory Academic Core ---
    fun getSubjectsByLevel(level: String): List<Subject> {
        return AcademicDatabase.subjects.filter { it.level.equals(level, ignoreCase = true) }
    }

    fun getAllSubjects(): List<Subject> {
        return AcademicDatabase.subjects
    }

    fun getSubjectById(id: String): Subject? {
        return AcademicDatabase.subjects.find { it.id == id }
    }

    fun getTopicById(subjectId: String, topicId: String): Topic? {
        return getSubjectById(subjectId)?.topics?.find { it.id == topicId }
    }

    fun getAllPastPapers(): List<PastPaper> {
        return AcademicDatabase.pastPapers
    }

    // --- Bookmarks (DAO) ---
    val allBookmarks: Flow<List<Bookmark>> = dao.getAllBookmarks()

    fun isBookmarked(topicId: String): Flow<Boolean> = dao.isBookmarked(topicId)

    suspend fun addBookmark(topicId: String, subjectId: String) {
        dao.insertBookmark(Bookmark(topicId = topicId, subjectId = subjectId))
    }

    suspend fun removeBookmark(topicId: String) {
        dao.deleteBookmark(topicId)
    }

    // --- Study Progress (DAO) ---
    val allProgress: Flow<List<StudyProgress>> = dao.getAllProgress()

    fun isCompleted(topicId: String): Flow<Boolean> = dao.isCompleted(topicId)

    suspend fun markTopicAsRead(topicId: String, completed: Boolean) {
        dao.saveProgress(StudyProgress(topicId = topicId, isCompleted = completed))
    }

    // --- Quiz History (DAO) ---
    val quizHistory: Flow<List<QuizHistory>> = dao.getQuizHistory()

    suspend fun recordQuizScore(subjectId: String, score: Int, total: Int) {
        dao.insertQuizHistory(QuizHistory(subjectId = subjectId, score = score, totalQuestions = total))
    }

    // --- Study Chat Assistant (DAO) ---
    val chatMessages: Flow<List<ChatMessageEntity>> = dao.getChatMessages()

    suspend fun saveChatMessage(sender: String, messageText: String) {
        dao.insertChatMessage(ChatMessageEntity(sender = sender, text = messageText))
    }

    suspend fun clearChatHistory() {
        dao.clearChatMessages()
    }
}
