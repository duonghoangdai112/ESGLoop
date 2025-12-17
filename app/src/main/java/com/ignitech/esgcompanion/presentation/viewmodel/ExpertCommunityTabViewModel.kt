package com.ignitech.esgcompanion.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ignitech.esgcompanion.presentation.screen.expert.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*

@HiltViewModel
class ExpertCommunityTabViewModel @Inject constructor() : ViewModel() {
    
    private val _uiState = MutableStateFlow(ExpertCommunityTabUiState())
    val uiState: StateFlow<ExpertCommunityTabUiState> = _uiState.asStateFlow()
    
    fun loadCommunityData() {
        viewModelScope.launch {
            // Load mock data for now
            _uiState.value = _uiState.value.copy(
                totalMembers = 1250,
                activeDiscussions = 45,
                expertContributions = 23,
                myPosts = 8,
                quickActions = listOf(
                    CommunityQuickAction(
                        id = "create_post",
                        title = "Create Post",
                        icon = Icons.Default.Create,
                        color = androidx.compose.ui.graphics.Color(0xFF4CAF50)
                    ),
                    CommunityQuickAction(
                        id = "ask_question",
                        title = "Ask Question",
                        icon = Icons.Default.QuestionAnswer,
                        color = androidx.compose.ui.graphics.Color(0xFF2196F3)
                    ),
                    CommunityQuickAction(
                        id = "share_insight",
                        title = "Share",
                        icon = Icons.Default.Share,
                        color = androidx.compose.ui.graphics.Color(0xFFFF9800)
                    ),
                    CommunityQuickAction(
                        id = "join_event",
                        title = "Join Event",
                        icon = Icons.Default.Event,
                        color = androidx.compose.ui.graphics.Color(0xFF9C27B0)
                    )
                ),
                featuredDiscussions = listOf(
                    CommunityDiscussion(
                        id = "disc_001",
                        title = "Latest ESG Trends 2024",
                        content = "Sharing about ESG trends that are developing strongly in 2024 and their impact on businesses...",
                        authorName = "Nguyễn Văn A",
                        authorId = "user_001",
                        pillar = com.ignitech.esgcompanion.domain.entity.ESGPillar.ENVIRONMENTAL,
                        likeCount = 45,
                        commentCount = 12,
                        isLiked = true,
                        createdAt = System.currentTimeMillis() - 3600000,
                        isFeatured = true
                    ),
                    CommunityDiscussion(
                        id = "disc_002",
                        title = "Experience in Implementing ESG at Enterprises",
                        content = "Sharing practical experience on implementing ESG programs at enterprises...",
                        authorName = "Trần Thị B",
                        authorId = "user_002",
                        pillar = com.ignitech.esgcompanion.domain.entity.ESGPillar.SOCIAL,
                        likeCount = 32,
                        commentCount = 8,
                        isLiked = false,
                        createdAt = System.currentTimeMillis() - 7200000,
                        isFeatured = true
                    )
                ),
                recentDiscussions = listOf(
                    CommunityDiscussion(
                        id = "disc_003",
                        title = "Challenges in ESG Reporting",
                        content = "Discussion about challenges that enterprises face when implementing ESG reporting...",
                        authorName = "Lê Văn C",
                        authorId = "user_003",
                        pillar = com.ignitech.esgcompanion.domain.entity.ESGPillar.GOVERNANCE,
                        likeCount = 18,
                        commentCount = 5,
                        isLiked = false,
                        createdAt = System.currentTimeMillis() - 10800000
                    ),
                    CommunityDiscussion(
                        id = "disc_004",
                        title = "Green Technology in Manufacturing",
                        content = "Application of green technology in manufacturing processes to reduce environmental impact...",
                        authorName = "Phạm Thị D",
                        authorId = "user_004",
                        pillar = com.ignitech.esgcompanion.domain.entity.ESGPillar.ENVIRONMENTAL,
                        likeCount = 25,
                        commentCount = 7,
                        isLiked = true,
                        createdAt = System.currentTimeMillis() - 14400000
                    )
                ),
                expertSpotlight = listOf(
                    ExpertProfile(
                        id = "expert_001",
                        name = "Dr. Nguyễn Minh",
                        specialization = "Environmental Expert",
                        rating = 4.8f,
                        contributionCount = 156
                    ),
                    ExpertProfile(
                        id = "expert_002",
                        name = "Ms. Trần Lan",
                        specialization = "Social Expert",
                        rating = 4.6f,
                        contributionCount = 134
                    ),
                    ExpertProfile(
                        id = "expert_003",
                        name = "Mr. Lê Hùng",
                        specialization = "Governance Expert",
                        rating = 4.9f,
                        contributionCount = 189
                    )
                ),
                upcomingEvents = listOf(
                    CommunityEvent(
                        id = "event_001",
                        title = "Webinar: ESG Reporting 2024",
                        description = "Detailed guidance on ESG reporting according to the latest standards",
                        type = EventType.WEBINAR,
                        startTime = System.currentTimeMillis() + 86400000,
                        duration = 90,
                        isRegistered = true
                    ),
                    CommunityEvent(
                        id = "event_002",
                        title = "Workshop: Sustainable Finance",
                        description = "Sustainable finance and its impact on ESG",
                        type = EventType.WORKSHOP,
                        startTime = System.currentTimeMillis() + 172800000,
                        duration = 120,
                        isRegistered = false
                    )
                )
            )
        }
    }
    
    fun performQuickAction(actionId: String) {
        viewModelScope.launch {
            // TODO: Handle quick actions
        }
    }
    
    fun openDiscussion(discussionId: String) {
        viewModelScope.launch {
            // TODO: Navigate to discussion detail
        }
    }
    
    fun likeDiscussion(discussionId: String) {
        viewModelScope.launch {
            // TODO: Toggle like for discussion
        }
    }
    
    fun commentOnDiscussion(discussionId: String) {
        viewModelScope.launch {
            // TODO: Navigate to comment screen
        }
    }
    
    fun viewAllDiscussions() {
        viewModelScope.launch {
            // TODO: Navigate to all discussions screen
        }
    }
    
    fun viewExpertProfile(expertId: String) {
        viewModelScope.launch {
            // TODO: Navigate to expert profile
        }
    }
    
    fun openEvent(eventId: String) {
        viewModelScope.launch {
            // TODO: Navigate to event detail
        }
    }
    
    fun registerForEvent(eventId: String) {
        viewModelScope.launch {
            // TODO: Register for event
        }
    }
}

data class ExpertCommunityTabUiState(
    val totalMembers: Int = 0,
    val activeDiscussions: Int = 0,
    val expertContributions: Int = 0,
    val myPosts: Int = 0,
    val quickActions: List<CommunityQuickAction> = emptyList(),
    val featuredDiscussions: List<CommunityDiscussion> = emptyList(),
    val recentDiscussions: List<CommunityDiscussion> = emptyList(),
    val expertSpotlight: List<ExpertProfile> = emptyList(),
    val upcomingEvents: List<CommunityEvent> = emptyList()
)
