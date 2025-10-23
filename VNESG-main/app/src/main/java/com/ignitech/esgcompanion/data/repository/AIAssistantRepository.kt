package com.ignitech.esgcompanion.data.repository

import com.ignitech.esgcompanion.data.dao.ESGAssessmentDao
import com.ignitech.esgcompanion.data.dao.ESGTrackerDao
import com.ignitech.esgcompanion.data.remote.ChatCompletionRequest
import com.ignitech.esgcompanion.data.remote.Message
import com.ignitech.esgcompanion.data.remote.OpenAIService
import com.ignitech.esgcompanion.domain.entity.ESGPillar
import kotlinx.coroutines.flow.firstOrNull
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AIAssistantRepository @Inject constructor(
    private val openAIService: OpenAIService,
    private val assessmentDao: ESGAssessmentDao,
    private val trackerDao: ESGTrackerDao
) {
    
    suspend fun generateAIResponse(
        userMessage: String,
        userId: String,
        conversationHistory: List<Message> = emptyList()
    ): String {
        return try {
            // Gather enterprise ESG data
            val context = buildEnterpriseContext(userId)
            
            // Build messages with context
            val messages = buildList {
                // System message with context
                add(Message(
                    role = "system",
                    content = """
                        You are an AI ESG Assistant - an expert advisor on ESG (Environmental, Social, Governance) for financial institutions and enterprises.
                        
                        Your responsibilities:
                        - Analyze enterprise ESG data with focus on banking sector requirements
                        - Provide specific, actionable recommendations based on:
                          * PCAF (Partnership for Carbon Accounting Financials) methodology
                          * TCFD (Task Force on Climate-related Financial Disclosures)
                          * IFRS S2 Climate-related Disclosures
                          * ILO (International Labour Organization) principles
                          * Equator Principles for project finance
                        - Suggest appropriate ESG activities aligned with 1.5°C climate goals
                        - Explain ESG concepts clearly and professionally
                        - Reference specific assessment criteria from ESG frameworks
                        
                        Current enterprise data:
                        $context
                        
                        Response guidelines:
                        - Keep responses concise and practical (max 300 words)
                        - Use bullet points, emojis, and clear structure
                        - Provide quantifiable impacts when possible (e.g., "+12 points", "6-12 months timeline")
                        - Reference industry best practices and benchmarks
                        - Prioritize materiality for financial sector
                    """.trimIndent()
                ))
                
                // Add conversation history
                addAll(conversationHistory)
                
                // Add current user message
                add(Message(
                    role = "user",
                    content = userMessage
                ))
            }
            
            // Call OpenAI API
            val request = ChatCompletionRequest(
                model = "gpt-3.5-turbo",
                messages = messages,
                temperature = 0.7,
                max_tokens = 500
            )
            
            val response = openAIService.chat(request)
            response.choices.firstOrNull()?.message?.content ?: "Sorry, I cannot process this request."
            
        } catch (e: Exception) {
            println("DEBUG: OpenAI API error: ${e.message}")
            // Fallback to mock response
            generateMockResponse(userMessage, userId)
        }
    }
    
    private suspend fun buildEnterpriseContext(userId: String): String {
        val contextParts = mutableListOf<String>()
        
        // Assessment data
        try {
            assessmentDao.getAssessmentsByUser(userId).firstOrNull()?.let { assessments ->
                if (assessments.isNotEmpty()) {
                    val latest = assessments.first()
                    contextParts.add("- Current ESG score: ${latest.totalScore}/100")
                    contextParts.add("- Number of assessments: ${assessments.size}")
                    
                    if (assessments.size > 1) {
                        val previous = assessments[1]
                        val change = latest.totalScore - previous.totalScore
                        val changeText = if (change > 0) "+$change" else "$change"
                        contextParts.add("- Change from previous period: $changeText points")
                    } else {
                        // Only one assessment, no comparison possible
                    }
                } else {
                    contextParts.add("- No ESG assessments yet")
                }
            }
        } catch (e: Exception) {
            println("DEBUG: Error loading assessment: ${e.message}")
        }
        
        // Activity data
        try {
            trackerDao.getActivitiesByUser(userId).firstOrNull()?.let { activities ->
                contextParts.add("- Total ESG activities: ${activities.size}")
                
                if (activities.isNotEmpty()) {
                    val envCount = activities.count { it.pillar == ESGPillar.ENVIRONMENTAL }
                    val socialCount = activities.count { it.pillar == ESGPillar.SOCIAL }
                    val govCount = activities.count { it.pillar == ESGPillar.GOVERNANCE }
                    
                    contextParts.add("  + Environmental: $envCount activities")
                    contextParts.add("  + Social: $socialCount activities")
                    contextParts.add("  + Governance: $govCount activities")
                } else {
                    contextParts.add("- No ESG activities yet")
                }
            }
        } catch (e: Exception) {
            println("DEBUG: Error loading activities: ${e.message}")
        }
        
        return contextParts.joinToString("\n")
    }
    
    private suspend fun generateMockResponse(userMessage: String, userId: String): String {
        // Fallback mock responses when API fails
        // Enhanced responses for banking sector based on ESG Assessment criteria
        return when {
            userMessage.contains("phân tích", ignoreCase = true) || userMessage.contains("analysis", ignoreCase = true) || userMessage.contains("analyze", ignoreCase = true) -> {
                """
                Banking ESG Analysis:
                
                Strengths:
                • Good governance framework in place
                • Consumer protection policies implemented
                • Basic climate disclosure practices
                
                Areas for improvement:
                • Financed emissions disclosure incomplete (only Scope 1&2)
                • Limited coal phase-out strategy
                • MSME lending below 10% threshold
                
                Key Recommendations:
                1. Measure and disclose Scope 3 financed emissions
                2. Set sector-specific 1.5°C aligned reduction targets
                3. Expand financial inclusion programs for MSMEs
                """.trimIndent()
            }
            userMessage.contains("khí thải", ignoreCase = true) || userMessage.contains("emissions", ignoreCase = true) || userMessage.contains("carbon", ignoreCase = true) || userMessage.contains("climate", ignoreCase = true) -> {
                """
                Climate & Emissions Assessment:
                
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
                
                Timeline: Implement within 6-12 months for best results
                """.trimIndent()
            }
            userMessage.contains("gợi ý", ignoreCase = true) || userMessage.contains("cải thiện", ignoreCase = true) || userMessage.contains("suggestion", ignoreCase = true) || userMessage.contains("improve", ignoreCase = true) -> {
                """
                Priority Improvement Plan for Banking:
                
                Environmental (Target: +20 points)
                1. Implement coal phase-out strategy
                   • Exclude coal mining >20% of activities
                   • Set 2030 coal exit deadline
                   - Impact: +8 points
                
                2. Expand green lending
                   • Target 30% renewable energy financing
                   • Offer green bonds
                   - Impact: +12 points
                
                Social (Target: +15 points)
                3. Enhance financial inclusion
                   • Increase MSME loans to >10%
                   • Open rural branches
                   • Launch mobile banking services
                   - Impact: +10 points
                
                4. Strengthen labor rights
                   • Implement ILO principles in procurement
                   • Establish complaint procedures
                   - Impact: +5 points
                
                Governance (Target: +18 points)
                5. Improve transparency
                   • Publish portfolio breakdown by sector
                   • Disclose engagement activities
                   • External verification of sustainability report
                   - Impact: +18 points
                
                Total potential gain: +53 points (6-12 months)
                """.trimIndent()
            }
            userMessage.contains("tài chính toàn diện", ignoreCase = true) || userMessage.contains("financial inclusion", ignoreCase = true) || userMessage.contains("msme", ignoreCase = true) -> {
                """
                Financial Inclusion Enhancement Plan:
                
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
                ROI: 18-24 months (new customer acquisition + ESG score)
                """.trimIndent()
            }
            userMessage.contains("minh bạch", ignoreCase = true) || userMessage.contains("transparency", ignoreCase = true) || userMessage.contains("disclosure", ignoreCase = true) || userMessage.contains("reporting", ignoreCase = true) -> {
                """
                Transparency & Disclosure Roadmap:
                
                Current Compliance Level: 55/100
                
                Gap Analysis:
                
                Not Disclosed:
                • Portfolio companies list
                • New credit recipients
                • Exclusion list with reasons
                • Engagement results
                • Voting record
                
                Already Disclosed:
                • Sustainability report (basic)
                • Client rights policy
                • Complaint handling process
                
                Implementation Plan:
                
                Phase 1 (Q1 2025) - Quick Wins:
                - Publish portfolio breakdown by sector/region
                - Disclose number of ESG engagements
                - Gain: +8 Governance points
                
                Phase 2 (Q2 2025) - Core Disclosures:
                - List project finance transactions
                - Publish voting policy
                - Report engagement results
                - Gain: +12 Governance points
                
                Phase 3 (Q3 2025) - Leading Practice:
                - Disclose portfolio companies
                - Publish exclusion list
                - External report verification
                - Gain: +15 Governance points
                
                Target: 90/100 transparency score by Q3 2025
                Framework: Align with IFRS S2, TCFD, GRI
                """.trimIndent()
            }
            userMessage.contains("than", ignoreCase = true) || userMessage.contains("coal", ignoreCase = true) || userMessage.contains("fossil fuel", ignoreCase = true) -> {
                """
                Fossil Fuel Transition Strategy:
                
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
                
                Expected ESG Score: +25 Environmental points
                """.trimIndent()
            }
            userMessage.contains("hoạt động", ignoreCase = true) || userMessage.contains("activity", ignoreCase = true) || userMessage.contains("activities", ignoreCase = true) -> {
                """
                Priority ESG Activities for Banking:
                
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
                - Join industry ESG initiative (e.g., PCAF)
                """.trimIndent()
            }
            userMessage.contains("điểm", ignoreCase = true) || userMessage.contains("score", ignoreCase = true) || userMessage.contains("rating", ignoreCase = true) -> {
                """
                Current ESG Score Breakdown:
                
                Total Score: 68/100 (FAIR)
                
                Pillar Analysis:
                Environmental: 62/100 (Warning)
                • Climate strategy: Partial
                • Financed emissions: Incomplete disclosure
                • Coal policy: Basic exclusions only
                - Priority improvement area
                
                Social: 71/100 (Good)
                • Financial inclusion: Good progress
                • Labor rights: Policies in place
                • Human rights: Strong weapons exclusions
                - Maintain and expand
                
                Governance: 72/100 (Good)
                • Consumer protection: Strong
                • Transparency: Room for improvement
                • Reporting: Basic compliance
                - Enhance disclosures
                
                Improvement Potential:
                - 6 months: 75/100 (GOOD)
                - 12 months: 82/100 (VERY GOOD)
                - 24 months: 90/100 (EXCELLENT)
                
                Next Milestone: Reach 75+ for "GOOD" rating
                Focus: Climate targets + MSME lending + Portfolio transparency
                """.trimIndent()
            }
            userMessage.contains("hello", ignoreCase = true) || userMessage.contains("hi", ignoreCase = true) || userMessage.contains("xin chào", ignoreCase = true) -> {
                """
                Hello! I'm your AI ESG Assistant for Banking Sector.
                
                I can help you with:
                
                ESG Score Analysis
                - Detailed breakdown by E-S-G pillars
                - Comparison with banking industry benchmarks
                
                Climate & Emissions
                - Financed emissions measurement (PCAF)
                - 1.5°C alignment strategies
                - Fossil fuel phase-out planning
                
                Financial Inclusion
                - MSME lending optimization
                - Rural branch expansion
                - Digital banking services
                
                Transparency & Disclosure
                - TCFD/IFRS S2 implementation
                - Portfolio disclosure requirements
                - Stakeholder reporting
                
                Improvement Suggestions
                - Quick wins for score boost
                - Long-term ESG roadmap
                - Industry best practices
                
                What would you like to know?
                """.trimIndent()
            }
            else -> {
                """
                I can help you with banking ESG topics:
                
                - Climate Strategy & Financed Emissions
                - Financial Inclusion & MSME Lending
                - Transparency & Disclosure Requirements
                - Fossil Fuel Transition Planning
                - ESG Score Analysis & Improvement
                
                Ask me anything about ESG for financial institutions!
                """.trimIndent()
            }
        }
    }
}

