package com.ignitech.esgcompanion.data.seed

import com.ignitech.esgcompanion.data.entity.*
import java.util.Date

object ESGAssignmentSeeder {
    
    fun getESGAssignments(): List<AssignmentEntity> {
        return listOf(
            // ===== ASSIGNMENT 1: MIDTERM EXAM (STANDARD) =====
            AssignmentEntity(
                id = "esg_midterm_1",
                title = "Midterm Exam - ESG Fundamentals",
                description = "Test basic ESG knowledge with 10 multiple choice questions and 2 essay questions",
                detailedDescription = "",
                dueDate = "20/12/2024",
                status = AssignmentStatus.NOT_STARTED,
                type = AssignmentType.REPORT,
                difficulty = AssignmentDifficulty.MEDIUM,
                estimatedTime = 60, // 60 minutes
                maxScore = 20,
                instructions = listOf(
                    "Read the questions carefully before answering",
                    "Multiple choice section: Choose 1 best answer",
                    "Essay section: Present clearly with structure",
                    "Review your work before submitting",
                    "Submit on time"
                ),
                requirements = listOf(
                    "Complete all 10 multiple choice questions",
                    "Answer 2 essay questions with appropriate length",
                    "Submit within 60 minutes",
                    "No reference materials allowed",
                    "Clear and readable presentation"
                ),
                resources = listOf(
                    AssignmentResource(
                        id = "res_midterm_1",
                        title = "GRI Standards - Sustainability Reporting Guide",
                        type = ResourceType.DOCUMENT,
                        url = "https://www.globalreporting.org/standards/"
                    ),
                    AssignmentResource(
                        id = "res_midterm_2",
                        title = "SASB Standards - Sustainability Accounting Standards",
                        type = ResourceType.DOCUMENT,
                        url = "https://www.sasb.org/standards/"
                    ),
                    AssignmentResource(
                        id = "res_midterm_3",
                        title = "TCFD Recommendations - Climate Risk Reporting",
                        type = ResourceType.DOCUMENT,
                        url = "https://www.fsb-tcfd.org/recommendations/"
                    )
                ),
                submissionType = SubmissionType.MULTIPLE_CHOICE,
                createdAt = Date(),
                updatedAt = Date()
            ),

            // ===== ASSIGNMENT 2: FINAL EXAM =====
            AssignmentEntity(
                id = "esg_final_2",
                title = "Final Exam: ESG (Environmental – Social – Governance)",
                description = "Final exam with essay format and business case analysis",
                detailedDescription = "",
                dueDate = "15/01/2025",
                status = AssignmentStatus.NOT_STARTED,
                type = AssignmentType.REPORT,
                difficulty = AssignmentDifficulty.HARD,
                estimatedTime = 90, // 90 minutes
                maxScore = 20,
                instructions = listOf(
                    "Time limit: 90 minutes",
                    "Format: Essay + Case analysis",
                    "Total score: 20 points",
                    "Read the questions carefully before answering",
                    "Present clearly with structure"
                ),
                requirements = listOf(
                    "Complete all 3 short essay questions (12 points)",
                    "Analyze TechGreen case study (8 points)",
                    "Submit within 90 minutes",
                    "Clear and readable presentation",
                    "Include specific examples"
                ),
                resources = listOf(
                    AssignmentResource(
                        id = "res_final_1",
                        title = "UN SDGs - Sustainable Development Goals",
                        type = ResourceType.DOCUMENT,
                        url = "https://www.un.org/sustainabledevelopment/sustainable-development-goals/"
                    ),
                    AssignmentResource(
                        id = "res_final_2",
                        title = "ESG vs CSR - Key Differences",
                        type = ResourceType.DOCUMENT,
                        url = "https://www.investopedia.com/articles/investing/022516/difference-between-csr-and-esg.asp"
                    ),
                    AssignmentResource(
                        id = "res_final_3",
                        title = "ESG Risk Management Framework",
                        type = ResourceType.DOCUMENT,
                        url = "https://www.mckinsey.com/capabilities/sustainability/our-insights"
                    )
                ),
                submissionType = SubmissionType.ESSAY,
                createdAt = Date(),
                updatedAt = Date()
            )
        )
    }
    
    fun getESGQuestions(): List<QuestionEntity> {
        return listOf(
            // Questions for Assignment 1 (Midterm Exam)
            QuestionEntity(
                id = "q1_1",
                assignmentId = "esg_midterm_1",
                question = "Which factor is NOT part of ESG criteria?",
                type = QuestionType.MULTIPLE_CHOICE,
                options = listOf("Environmental", "Technology", "Social", "Governance"),
                correctAnswer = "Technology",
                explanation = "ESG consists of 3 pillars: Environmental, Social, and Governance. Technology is not an ESG pillar."
            ),
            QuestionEntity(
                id = "q1_2",
                assignmentId = "esg_midterm_1",
                question = "Scope 2 emissions include:",
                type = QuestionType.MULTIPLE_CHOICE,
                options = listOf(
                    "Emissions from factories directly operated by the enterprise",
                    "Emissions from delivery activities",
                    "Emissions from purchased electricity consumption",
                    "Emissions from customers using the product"
                ),
                correctAnswer = "Emissions from purchased electricity consumption",
                explanation = "Scope 2 includes indirect emissions from purchased energy (electricity, steam, cooling) from external suppliers."
            ),
            QuestionEntity(
                id = "q1_3",
                assignmentId = "esg_midterm_1",
                question = "Fair compensation policy for employees falls under which ESG factor?",
                type = QuestionType.MULTIPLE_CHOICE,
                options = listOf("Environmental", "Social", "Governance", "All of the above"),
                correctAnswer = "Social",
                explanation = "Fair compensation policy belongs to the Social pillar as it relates to employee rights and working conditions."
            ),
            QuestionEntity(
                id = "q1_4",
                assignmentId = "esg_midterm_1",
                question = "Which organization initiated the first ESG reporting in the world?",
                type = QuestionType.MULTIPLE_CHOICE,
                options = listOf("UNDP", "SASB", "GRI", "WEF"),
                correctAnswer = "GRI",
                explanation = "GRI (Global Reporting Initiative) was established in 1997 and was the first organization to develop a global sustainability reporting framework."
            ),
            QuestionEntity(
                id = "q1_5",
                assignmentId = "esg_midterm_1",
                question = "ESG is considered as a factor that:",
                type = QuestionType.MULTIPLE_CHOICE,
                options = listOf(
                    "Is mandatory by law in all countries",
                    "Is only for large companies",
                    "Supports sustainable business development",
                    "Is not related to finance"
                ),
                correctAnswer = "Supports sustainable business development",
                explanation = "ESG is an assessment framework that helps businesses develop sustainably, manage risks and create long-term value for stakeholders."
            ),
            QuestionEntity(
                id = "q1_6",
                assignmentId = "esg_midterm_1",
                question = "Which of the following examples does NOT belong to Environmental (E) factor?",
                type = QuestionType.MULTIPLE_CHOICE,
                options = listOf(
                    "Reducing CO₂ emissions",
                    "Waste recycling",
                    "Gender balance in leadership",
                    "Using renewable energy"
                ),
                correctAnswer = "Gender balance in leadership",
                explanation = "Gender balance in leadership belongs to the Social pillar, not Environmental."
            ),
            QuestionEntity(
                id = "q1_7",
                assignmentId = "esg_midterm_1",
                question = "In ESG, \"governance\" does NOT include which of the following factors:",
                type = QuestionType.MULTIPLE_CHOICE,
                options = listOf(
                    "Internal audit",
                    "Board of directors",
                    "Environmental protection campaign",
                    "Financial reporting transparency"
                ),
                correctAnswer = "Environmental protection campaign",
                explanation = "Environmental protection campaign belongs to the Environmental pillar, not Governance."
            ),
            QuestionEntity(
                id = "q1_8",
                assignmentId = "esg_midterm_1",
                question = "A company penalized for hiding emission data will be rated low in which factor?",
                type = QuestionType.MULTIPLE_CHOICE,
                options = listOf("Social", "Governance", "Environmental", "CSR"),
                correctAnswer = "Governance",
                explanation = "Hiding data violates transparency and honesty principles in corporate governance, belonging to the Governance pillar."
            ),
            QuestionEntity(
                id = "q1_9",
                assignmentId = "esg_midterm_1",
                question = "Which of the following ESG KPIs is appropriate for Social (S) factor?",
                type = QuestionType.MULTIPLE_CHOICE,
                options = listOf(
                    "Water consumption",
                    "Employee turnover rate",
                    "Number of major shareholders",
                    "Electricity consumption level"
                ),
                correctAnswer = "Employee turnover rate",
                explanation = "Employee turnover rate is an important indicator in the Social pillar, reflecting employee satisfaction and engagement."
            ),
            QuestionEntity(
                id = "q1_10",
                assignmentId = "esg_midterm_1",
                question = "What is \"Greenwashing\"?",
                type = QuestionType.MULTIPLE_CHOICE,
                options = listOf(
                    "An environmental cleaning technology",
                    "A positive communication technique",
                    "False ESG representation to polish image",
                    "Comprehensive business greening process"
                ),
                correctAnswer = "False ESG representation to polish image",
                explanation = "Greenwashing is the practice of creating misleading impressions about a company's environmental or ESG commitments to polish its image without real actions."
            ),

            // Questions for Assignment 2 (Final Exam)
            QuestionEntity(
                id = "q2_1",
                assignmentId = "esg_final_2",
                question = "Explain the relationship between ESG and Sustainable Development Goals (SDGs). What are the similarities and differences between ESG and SDGs? Clarify the role of ESG in achieving the SDGs set by the United Nations. Provide 2 specific examples of SDG goals that can be directly related to each E, S or G factor.",
                type = QuestionType.ESSAY,
                options = null,
                correctAnswer = null,
                explanation = "Essay question about the relationship between ESG and SDGs, requiring in-depth analysis and specific examples."
            ),
            QuestionEntity(
                id = "q2_2",
                assignmentId = "esg_final_2",
                question = "Identify and analyze 3 main risks that enterprises may face if ESG is not properly implemented: Legal risk, Financial/investment risk, Reputation/brand risk. You may cite 1-2 real examples if needed.",
                type = QuestionType.ESSAY,
                options = null,
                correctAnswer = null,
                explanation = "Essay question about ESG risks, requiring analysis and real examples."
            ),
            QuestionEntity(
                id = "q2_3",
                assignmentId = "esg_final_2",
                question = "Compare ESG and CSR (Corporate Social Responsibility). What are the similarities between ESG and CSR? Where are the biggest differences between ESG and CSR (objectives, implementation, measurement, strategic alignment)? Provide examples of a traditional CSR activity and a strategic ESG activity.",
                type = QuestionType.ESSAY,
                options = null,
                correctAnswer = null,
                explanation = "Essay question comparing ESG and CSR, requiring analysis and illustrative examples."
            ),
            QuestionEntity(
                id = "q2_4",
                assignmentId = "esg_final_2",
                question = "Case Study TechGreen: TechGreen is an education technology startup facing 3 issues: (1) High turnover rate, employees overworked, (2) No published data privacy policy, (3) High electricity consumption from traditional grid. Classify each issue into the 3 ESG factors and analyze consequences if ignored.",
                type = QuestionType.ESSAY,
                options = null,
                correctAnswer = null,
                explanation = "Case study analyzing TechGreen business situation, requiring ESG classification of issues and consequence assessment."
            ),
            QuestionEntity(
                id = "q2_5",
                assignmentId = "esg_final_2",
                question = "Propose a 6-month ESG action plan for TechGreen, including: 1 specific action for each issue, Identify responsible department and State KPIs to measure results (e.g., electricity consumption reduction, employee satisfaction level, number of people trained on security…).",
                type = QuestionType.ESSAY,
                options = null,
                correctAnswer = null,
                explanation = "Essay question about ESG action plan, requiring specific proposals with measurable KPIs."
            )
        )
    }
}
