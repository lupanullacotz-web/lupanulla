package com.example.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "bookmarks")
data class Bookmark(
    @PrimaryKey val topicId: String,
    val subjectId: String,
    val timestamp: Long = System.currentTimeMillis()
)

@Entity(tableName = "study_progress")
data class StudyProgress(
    @PrimaryKey val topicId: String,
    val isCompleted: Boolean,
    val lastReadTime: Long = System.currentTimeMillis()
)

@Entity(tableName = "quiz_history")
data class QuizHistory(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val subjectId: String,
    val score: Int,
    val totalQuestions: Int,
    val timestamp: Long = System.currentTimeMillis()
)

@Entity(tableName = "chat_messages")
data class ChatMessageEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val sender: String, // "user" or "model" ("ai")
    val text: String,
    val timestamp: Long = System.currentTimeMillis()
)
