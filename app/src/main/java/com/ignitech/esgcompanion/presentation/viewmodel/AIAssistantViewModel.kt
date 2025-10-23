package com.ignitech.esgcompanion.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ignitech.esgcompanion.data.dao.ESGAssessmentDao
import com.ignitech.esgcompanion.data.dao.ESGTrackerDao
import com.ignitech.esgcompanion.data.remote.Message
import com.ignitech.esgcompanion.data.repository.AIAssistantRepository
import com.ignitech.esgcompanion.presentation.screen.enterprise.AIRecommendation
import com.ignitech.esgcompanion.presentation.screen.enterprise.ChatMessage
import com.ignitech.esgcompanion.presentation.screen.enterprise.RecommendationType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@HiltViewModel
class AIAssistantViewModel @Inject constructor(
    private val aiRepository: AIAssistantRepository,
    private val assessmentDao: ESGAssessmentDao,
    private val trackerDao: ESGTrackerDao
) : ViewModel() {

    private val _uiState = MutableStateFlow(AIAssistantUiState())
    val uiState: StateFlow<AIAssistantUiState> = _uiState.asStateFlow()
    
    private val userId = "user_3" // Demo enterprise user
    private val conversationHistory = mutableListOf<Message>()

    fun loadInitialInsights() {
        viewModelScope.launch {
            delay(500) // Simulate loading
            
            // Load sample conversation for banking enterprise demo
            val sampleConversation = generateSampleBankingConversation()
            
            _uiState.update { it.copy(messages = sampleConversation) }
        }
    }
    
    private suspend fun generateSampleBankingConversation(): List<ChatMessage> {
        val messages = mutableListOf<ChatMessage>()
        
        // Message 1: AI Welcome & Initial Analysis
        messages.add(ChatMessage(
            id = UUID.randomUUID().toString(),
            content = """Hello! I'm your AI ESG Assistant for Banking Sector.

I've analyzed your latest ESG assessment data and identified key areas:

Current ESG Score: 68/100 (FAIR)
- Environmental: 62/100 (Warning)
- Social: 71/100 (Good)
- Governance: 72/100 (Good)

Top Priority Areas:
1. Financed emissions disclosure incomplete
2. MSME lending below 10% threshold
3. Limited coal phase-out strategy

How can I help you today?""",
            isFromAI = true,
            timestamp = "09:15",
            recommendations = generateInitialInsights()
        ))
        
        // Message 2: User asks about climate strategy
        messages.add(ChatMessage(
            id = UUID.randomUUID().toString(),
            content = "Can you analyze our current climate and emissions performance? We need to understand our gaps.",
            isFromAI = false,
            timestamp = "09:17"
        ))
        
        // Message 3: AI responds with climate analysis
        messages.add(ChatMessage(
            id = UUID.randomUUID().toString(),
            content = """Climate & Emissions Assessment:

Based on your portfolio analysis:

Current Status:
• Scope 1&2 emissions: Disclosed (Yes)
• Scope 3 financed emissions: Not disclosed (No)
• Climate scenario alignment: Partial

Action Items:
1. Disclose financed emissions (Scope 1, 2, 3)
   - Follow PCAF methodology
   - Expected impact: +12 Environmental points

2. Set 1.5°C aligned reduction targets
   - Short-term: 2030 target
   - Medium-term: 2040 target  
   - Long-term: 2050 net-zero

3. Establish sector-specific targets
   - Priority: Oil & Gas, Power Generation, Real Estate
   - Expected impact: +15 Environmental points

Timeline: Implement within 6-12 months for best results""",
            isFromAI = true,
            timestamp = "09:18"
        ))
        
        // Message 4: User asks about financial inclusion
        messages.add(ChatMessage(
            id = UUID.randomUUID().toString(),
            content = "What about our financial inclusion performance? We want to improve MSME lending.",
            isFromAI = false,
            timestamp = "09:22"
        ))
        
        // Message 5: AI responds with financial inclusion plan
        messages.add(ChatMessage(
            id = UUID.randomUUID().toString(),
            content = """Financial Inclusion Enhancement Plan:

Current Status:
• MSME loan portfolio: 7.5% (Target: >10%)
• Rural branches: Limited coverage
• Digital banking: Basic services available

Recommended Actions:

1. Increase MSME Lending
   • Set 10% portfolio target by 2025
   • Offer collateral-free loans <$50K
   • Create specialized MSME desk
   - Expected impact: +8 Social points

2. Expand Rural Access
   • Open 5 rural branches per year
   • Deploy mobile banking agents
   • Partner with microfinance institutions
   - Expected impact: +6 Social points

3. Digital Financial Services
   • Enhance mobile banking features
   • Offer e-money/cashless solutions
   • Financial literacy programs
   - Expected impact: +4 Social points

Investment: $500K-$1M
ROI: 18-24 months (new customer acquisition + ESG score)""",
            isFromAI = true,
            timestamp = "09:23"
        ))
        
        // Message 6: User asks about coal portfolio
        messages.add(ChatMessage(
            id = UUID.randomUUID().toString(),
            content = "We need a strategy to phase out our coal portfolio. Can you help?",
            isFromAI = false,
            timestamp = "09:28"
        ))
        
        // Message 7: AI responds with fossil fuel transition strategy
        messages.add(ChatMessage(
            id = UUID.randomUUID().toString(),
            content = """Fossil Fuel Transition Strategy:

Current Exposure:
• Coal mining portfolio: 12% of energy lending
• Coal power portfolio: 18% of energy lending
• Oil & gas exploration: 15% of energy lending

Risk Assessment: HIGH
- Regulatory pressure increasing
- Carbon price impact on loan quality
- Reputational risk

Phase-Out Strategy:

Year 2024-2025: Policy & Exclusions
• Exclude new coal mine development
• Exclude new coal power plants
• Set 20% activity threshold
- Impact: -5% coal exposure

Year 2026-2030: Active Phase-Out
• Exit coal mining clients >20% activity
• Exit coal power clients >20% activity  
• Offer transition financing
- Impact: -40% coal exposure

Year 2031-2040: Net-Zero Alignment
• Complete coal exit
• Reduce oil & gas by 60%
• 50% renewable energy portfolio
- Impact: Portfolio aligned with 1.5°C

Transition Support:
• $100M green refinancing facility
• Client transition advisory services
• Employee retraining programs

Expected ESG Score: +25 Environmental points""",
            isFromAI = true,
            timestamp = "09:30"
        ))
        
        // Message 8: User asks about quick wins
        messages.add(ChatMessage(
            id = UUID.randomUUID().toString(),
            content = "What are some quick wins we can implement this quarter?",
            isFromAI = false,
            timestamp = "09:35"
        ))
        
        // Message 9: AI responds with priority activities
        messages.add(ChatMessage(
            id = UUID.randomUUID().toString(),
            content = """Priority ESG Activities for Banking:

Environmental (Next Quarter):
• Conduct portfolio carbon footprint assessment
• Set up green lending team
• Launch sustainable finance products
• Implement paperless operations
- Expected: +12 Environmental points

Social (Next Quarter):
• Financial literacy program for 1,000 MSMEs
• Open 2 rural branches
• Launch collateral-free MSME loans
• Establish employee grievance mechanism
- Expected: +10 Social points

Governance (Next Quarter):
• Publish TCFD-aligned climate report
• Disclose portfolio breakdown
• External ESG report verification
• Board ESG training
- Expected: +15 Governance points

Quick Win Activities (This Month):
- Update ESG policy on website
- Set up ESG Committee
- Subscribe to ESG data provider
- Join industry ESG initiative (e.g., PCAF)""",
            isFromAI = true,
            timestamp = "09:36"
        ))
        
        return messages
    }
    
    private suspend fun generateInitialInsights(): List<AIRecommendation> {
        val insights = mutableListOf<AIRecommendation>()
        
        // Analyze assessment data
        try {
            assessmentDao.getAssessmentsByUser(userId).firstOrNull()?.let { assessments ->
                if (assessments.isNotEmpty()) {
                    val latestAssessment = assessments.first()
                    val score = latestAssessment.totalScore
                    
                    when {
                        score >= 80 -> insights.add(
                            AIRecommendation(
                                type = RecommendationType.STRENGTH,
                                title = "Excellent ESG Score!",
                                description = "You're maintaining an ESG score of $score/100. Keep up the good work!"
                            )
                        )
                        score >= 60 -> insights.add(
                            AIRecommendation(
                                type = RecommendationType.WARNING,
                                title = "ESG Score Needs Improvement",
                                description = "Current score is $score/100. Focus on weak pillars to improve."
                            )
                        )
                        else -> insights.add(
                            AIRecommendation(
                                type = RecommendationType.WARNING,
                                title = "Low ESG Score",
                                description = "Score is only $score/100. Action needed to improve."
                            )
                        )
                    }
                    
                    // Compare with previous
                    if (assessments.size > 1) {
                        val previous = assessments[1]
                        val change = latestAssessment.totalScore - previous.totalScore
                        when {
                            change > 0 -> insights.add(
                                AIRecommendation(
                                    type = RecommendationType.STRENGTH,
                                    title = "Positive Progress!",
                                    description = "Score increased by $change from last period. Great job!"
                                )
                            )
                            change < 0 -> insights.add(
                                AIRecommendation(
                                    type = RecommendationType.WARNING,
                                    title = "Score Decreased",
                                    description = "Score decreased by ${-change} from last period. Needs review."
                                )
                            )
                            else -> {
                                // No change, no recommendation needed
                            }
                        }
                    } else {
                        // Only one assessment, no comparison possible
                    }
                } else {
                    insights.add(
                        AIRecommendation(
                            type = RecommendationType.SUGGESTION,
                            title = "No ESG Assessment Yet",
                            description = "Complete your first ESG assessment to receive detailed AI analysis."
                        )
                    )
                }
            }
        } catch (e: Exception) {
            println("DEBUG: Error loading assessment data: ${e.message}")
        }
        
        // Analyze activity data
        try {
            trackerDao.getActivitiesByUser(userId).firstOrNull()?.let { activities ->
                when {
                    activities.isEmpty() -> insights.add(
                        AIRecommendation(
                            type = RecommendationType.SUGGESTION,
                            title = "No ESG Activities Yet",
                            description = "Start tracking ESG activities to monitor progress."
                        )
                    )
                    activities.size < 5 -> insights.add(
                        AIRecommendation(
                            type = RecommendationType.SUGGESTION,
                            title = "Increase ESG Activities",
                            description = "You have ${activities.size} activities. Aim for at least 10-15 activities per quarter for significant improvement."
                        )
                    )
                    else -> insights.add(
                        AIRecommendation(
                            type = RecommendationType.STRENGTH,
                            title = "Active ESG Engagement",
                            description = "Recorded ${activities.size} ESG activities. Great job! Keep it up."
                        )
                    )
                }
                
                // Check pillar balance
                val envCount = activities.count { it.pillar == com.ignitech.esgcompanion.domain.entity.ESGPillar.ENVIRONMENTAL }
                val socialCount = activities.count { it.pillar == com.ignitech.esgcompanion.domain.entity.ESGPillar.SOCIAL }
                val govCount = activities.count { it.pillar == com.ignitech.esgcompanion.domain.entity.ESGPillar.GOVERNANCE }
                
                val minCount = minOf(envCount, socialCount, govCount)
                val maxCount = maxOf(envCount, socialCount, govCount)
                
                if (maxCount - minCount > 3) {
                    val weakPillar = when (minCount) {
                        envCount -> "Environmental"
                        socialCount -> "Social"
                        else -> "Governance"
                    }
                    insights.add(
                        AIRecommendation(
                            type = RecommendationType.SUGGESTION,
                            title = "Balance ESG Pillars",
                            description = "$weakPillar pillar has fewer activities ($minCount). Consider increasing focus to balance."
                        )
                    )
                } else {
                    // Activities are well balanced, no recommendation needed
                }
            }
        } catch (e: Exception) {
            println("DEBUG: Error loading activity data: ${e.message}")
        }
        
        // Default suggestion if no insights
        if (insights.isEmpty()) {
            insights.add(
                AIRecommendation(
                    type = RecommendationType.SUGGESTION,
                    title = "Start Your ESG Journey",
                    description = "Complete ESG assessments and track activities to receive detailed AI analysis."
                )
            )
        }
        
        return insights
    }

    fun updateInputText(text: String) {
        _uiState.update { it.copy(inputText = text) }
    }

    fun sendMessage() {
        val input = _uiState.value.inputText.trim()
        if (input.isBlank()) return
        
        viewModelScope.launch {
            // Add user message
            val userMessage = ChatMessage(
                id = UUID.randomUUID().toString(),
                content = input,
                isFromAI = false,
                timestamp = getCurrentTime()
            )
            
            _uiState.update {
                it.copy(
                    messages = it.messages + userMessage,
                    inputText = "",
                    isLoading = true
                )
            }
            
            // Add to conversation history
            conversationHistory.add(Message(role = "user", content = input))
            
            // Get AI response from repository (will call OpenAI)
            val aiResponseText = aiRepository.generateAIResponse(
                userMessage = input,
                userId = userId,
                conversationHistory = conversationHistory.toList()
            )
            
            // Add to conversation history
            conversationHistory.add(Message(role = "assistant", content = aiResponseText))
            
            // Create chat message
            val aiMessage = ChatMessage(
                id = UUID.randomUUID().toString(),
                content = aiResponseText,
                isFromAI = true,
                timestamp = getCurrentTime(),
                recommendations = null // OpenAI response is in text, no structured recommendations
            )
            
            _uiState.update {
                it.copy(
                    messages = it.messages + aiMessage,
                    isLoading = false
                )
            }
        }
    }
    
    fun sendSuggestion(suggestion: String) {
        _uiState.update { it.copy(inputText = suggestion) }
        sendMessage()
    }
    
    private fun getCurrentTime(): String {
        val format = SimpleDateFormat("HH:mm", Locale.getDefault())
        return format.format(Date())
    }
}

data class AIAssistantUiState(
    val messages: List<ChatMessage> = emptyList(),
    val inputText: String = "",
    val isLoading: Boolean = false
)
