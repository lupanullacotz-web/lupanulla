package com.example.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.api.GeminiNetwork
import com.example.db.AppDatabase
import com.example.db.ChatMessageEntity
import com.example.db.QuizHistory
import com.example.model.*
import com.example.repository.AcademicRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class AcademicViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: AcademicRepository

    init {
        val database = AppDatabase.getDatabase(application)
        repository = AcademicRepository(database.academicDao())
    }

    // --- Tab Navigation ---
    private val _currentTab = MutableStateFlow("DASHBOARD") // "DASHBOARD", "NOTES", "PAST_PAPERS", "QUIZ", "AI_CHAT"
    val currentTab: StateFlow<String> = _currentTab.asStateFlow()

    fun setTab(tab: String) {
        _currentTab.value = tab
    }

    // --- Dashboard & Study Progress Flows ---
    val bookmarks: StateFlow<List<com.example.db.Bookmark>> = repository.allBookmarks
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val progress: StateFlow<List<com.example.db.StudyProgress>> = repository.allProgress
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val quizHistory: StateFlow<List<QuizHistory>> = repository.quizHistory
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // --- View State Holders ---
    private val _selectedLevel = MutableStateFlow("Form 1") // "Form 1", ..., "Form 6"
    val selectedLevel: StateFlow<String> = _selectedLevel.asStateFlow()

    fun setLevel(level: String) {
        _selectedLevel.value = level
    }

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    fun setSearchQuery(query: String) {
        _searchQuery.value = query
    }

    // Selected Subject for detail viewing
    private val _selectedSubject = MutableStateFlow<Subject?>(null)
    val selectedSubject: StateFlow<Subject?> = _selectedSubject.asStateFlow()

    fun selectSubject(subject: Subject?) {
        _selectedSubject.value = subject
        _selectedTopic.value = null // reset topic
    }

    // Selected Topic for detailed note reading
    private val _selectedTopic = MutableStateFlow<Topic?>(null)
    val selectedTopic: StateFlow<Topic?> = _selectedTopic.asStateFlow()

    fun selectTopic(topic: Topic?) {
        _selectedTopic.value = topic
    }

    // Selected Past Paper for review
    private val _selectedPaper = MutableStateFlow<PastPaper?>(null)
    val selectedPaper: StateFlow<PastPaper?> = _selectedPaper.asStateFlow()

    fun selectPaper(paper: PastPaper?) {
        _selectedPaper.value = paper
    }

    // --- Bookmarking & Progress Actions ---
    fun toggleBookmark(topicId: String, subjectId: String, isBookmarked: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            if (isBookmarked) {
                repository.removeBookmark(topicId)
            } else {
                repository.addBookmark(topicId, subjectId)
            }
        }
    }

    fun toggleTopicCompleted(topicId: String, isCompleted: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.markTopicAsRead(topicId, isCompleted)
        }
    }

    // --- Interactive Multi-choice Quiz State ---
    private val _activeQuizSubject = MutableStateFlow<Subject?>(null)
    val activeQuizSubject: StateFlow<Subject?> = _activeQuizSubject.asStateFlow()

    private val _activeQuizQuestions = MutableStateFlow<List<QuizQuestion>>(emptyList())
    val activeQuizQuestions: StateFlow<List<QuizQuestion>> = _activeQuizQuestions.asStateFlow()

    private val _currentQuestionIndex = MutableStateFlow(0)
    val currentQuestionIndex: StateFlow<Int> = _currentQuestionIndex.asStateFlow()

    private val _selectedAnswerIndex = MutableStateFlow<Int?>(null)
    val selectedAnswerIndex: StateFlow<Int?> = _selectedAnswerIndex.asStateFlow()

    private val _quizIsSubmitted = MutableStateFlow(false)
    val quizIsSubmitted: StateFlow<Boolean> = _quizIsSubmitted.asStateFlow()

    private val _quizScore = MutableStateFlow(0)
    val quizScore: StateFlow<Int> = _quizScore.asStateFlow()

    private val _quizFinished = MutableStateFlow(false)
    val quizFinished: StateFlow<Boolean> = _quizFinished.asStateFlow()

    fun startQuiz(subject: Subject) {
        // Collect all questions across this subject's topics
        val questions = subject.topics.flatMap { it.selfTestQuestions }
        if (questions.isNotEmpty()) {
            _activeQuizSubject.value = subject
            _activeQuizQuestions.value = questions
            _currentQuestionIndex.value = 0
            _selectedAnswerIndex.value = null
            _quizIsSubmitted.value = false
            _quizScore.value = 0
            _quizFinished.value = false
            setTab("QUIZ") // Navigate to quiz interface
        }
    }

    fun selectQuizAnswer(index: Int) {
        if (!_quizIsSubmitted.value) {
            _selectedAnswerIndex.value = index
        }
    }

    fun submitQuizAnswer() {
        val selectedIdx = _selectedAnswerIndex.value ?: return
        val currentQ = _activeQuizQuestions.value.getOrNull(_currentQuestionIndex.value) ?: return

        _quizIsSubmitted.value = true
        if (selectedIdx == currentQ.correctAnswerIndex) {
            _quizScore.value += 1
        }
    }

    fun nextQuizQuestion() {
        val currentIndex = _currentQuestionIndex.value
        val questionsSize = _activeQuizQuestions.value.size

        if (currentIndex < questionsSize - 1) {
            _currentQuestionIndex.value = currentIndex + 1
            _selectedAnswerIndex.value = null
            _quizIsSubmitted.value = false
        } else {
            // Quiz completed! Save history
            _quizFinished.value = true
            val finalScore = _quizScore.value
            val total = questionsSize
            val currentSubject = _activeQuizSubject.value
            if (currentSubject != null) {
                viewModelScope.launch(Dispatchers.IO) {
                    repository.recordQuizScore(currentSubject.id, finalScore, total)
                }
            }
        }
    }

    // --- Study Chat Assistant (Gemini API) ---
    val chatMessages: StateFlow<List<ChatMessageEntity>> = repository.chatMessages
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    private val _isChatLoading = MutableStateFlow(false)
    val isChatLoading: StateFlow<Boolean> = _isChatLoading.asStateFlow()

    fun sendChatMessage(prompt: String) {
        if (prompt.trim().isEmpty()) return

        viewModelScope.launch(Dispatchers.IO) {
            // 1. Save and display user message immediately
            repository.saveChatMessage(sender = "user", messageText = prompt)
            
            // 2. Set loading status
            _isChatLoading.value = true

            // Gather recent context (e.g., last 10 turns to avoid loading huge volumes)
            val currentContextList = chatMessages.value
            val contextForApi = if (currentContextList.size > 10) {
                currentContextList.takeLast(10)
            } else {
                currentContextList
            }

            // 3. Ask Gemini
            val responseText = GeminiNetwork.askGemini(prompt, contextForApi)

            // 4. Save and display model's response
            repository.saveChatMessage(sender = "model", messageText = responseText)
            
            // 5. Unset loading status
            _isChatLoading.value = false
        }
    }

    fun clearChatHistory() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.clearChatHistory()
        }
    }

    // Raw static properties accessor
    fun getAllSubjects(): List<Subject> = repository.getAllSubjects()
    fun getAllPastPapers(): List<PastPaper> = repository.getAllPastPapers()
}
