package com.ignitech.esgcompanion.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.ignitech.esgcompanion.presentation.screen.*
import com.ignitech.esgcompanion.presentation.screen.enterprise.EnterpriseHomeScreen
import com.ignitech.esgcompanion.presentation.screen.enterprise.EnterpriseAssessmentScreen
import com.ignitech.esgcompanion.presentation.screen.enterprise.AssessmentHistoryScreen
import com.ignitech.esgcompanion.presentation.screen.enterprise.ESGAssessmentWorkScreen
import com.ignitech.esgcompanion.presentation.screen.enterprise.ESGAssessmentFormScreen
import com.ignitech.esgcompanion.presentation.screen.enterprise.AddActivityScreen
import com.ignitech.esgcompanion.presentation.screen.enterprise.AIAssistantScreen
import com.ignitech.esgcompanion.presentation.screen.enterprise.ReportBuilderScreen
import com.ignitech.esgcompanion.presentation.screen.enterprise.ReportSectionDetailScreen
import com.ignitech.esgcompanion.presentation.screen.enterprise.ReportViewerScreen
import com.ignitech.esgcompanion.presentation.screen.expert.ExpertHomeScreen
import com.ignitech.esgcompanion.presentation.screen.expert.ExpertEnterpriseScreen
import com.ignitech.esgcompanion.presentation.screen.expert.EnterpriseESGDataScreen
import com.ignitech.esgcompanion.presentation.screen.expert.ExpertReportScreen
import com.ignitech.esgcompanion.presentation.screen.expert.ExpertQuizTakingScreen
import com.ignitech.esgcompanion.presentation.screen.expert.ExpertQuizResultsScreen
import com.ignitech.esgcompanion.presentation.screen.expert.ExpertNewsDetailScreen
import com.ignitech.esgcompanion.presentation.screen.student.StudentHomeScreen
import com.ignitech.esgcompanion.presentation.screen.student.StudentAssessmentScreen
import com.ignitech.esgcompanion.presentation.screen.student.CategoryDetailScreen
import com.ignitech.esgcompanion.presentation.screen.student.AssignmentDetailScreen
import com.ignitech.esgcompanion.presentation.screen.student.AssignmentWorkScreen
import com.ignitech.esgcompanion.presentation.screen.instructor.InstructorHomeScreen
import com.ignitech.esgcompanion.presentation.screen.instructor.InstructorClassesScreen
import com.ignitech.esgcompanion.presentation.screen.instructor.ClassDetailScreen
import com.ignitech.esgcompanion.presentation.viewmodel.ClassDetailViewModel
import com.ignitech.esgcompanion.presentation.screen.instructor.CreateLessonScreen
import com.ignitech.esgcompanion.presentation.screen.instructor.CreateAssignmentScreen
import com.ignitech.esgcompanion.presentation.screen.instructor.CreateQuestionScreen
import com.ignitech.esgcompanion.presentation.screen.instructor.QuestionLibraryScreen
import com.ignitech.esgcompanion.presentation.screen.instructor.AssignmentGradingScreen
import com.ignitech.esgcompanion.presentation.screen.instructor.GradeSubmissionScreen
import com.ignitech.esgcompanion.presentation.screen.instructor.AssignmentGradesScreen
import com.ignitech.esgcompanion.presentation.screen.regulatory.RegulatoryHomeScreen
import com.ignitech.esgcompanion.presentation.screen.regulatory.RegulatoryAssessmentScreen
import com.ignitech.esgcompanion.presentation.screen.LearningHubScreen
import com.ignitech.esgcompanion.presentation.viewmodel.LoginViewModel
import com.ignitech.esgcompanion.domain.entity.ESGPillar
import com.ignitech.esgcompanion.domain.entity.ReportStandard

@Composable
fun AppNavigation(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    loginViewModel: LoginViewModel = hiltViewModel()
) {
    NavHost(
        navController = navController,
        startDestination = "splash",
        modifier = modifier
    ) {
        composable("splash") {
            SplashScreen(navController = navController)
        }
        composable("login") {
            LoginScreen(navController = navController)
        }
        
        composable("register") {
            RegisterScreen(navController = navController)
        }
        
        composable("forgot_password") {
            ForgotPasswordScreen(navController = navController)
        }
        
        composable("enterprise_home") {
            EnterpriseHomeScreen(navController = navController)
        }
        
        composable("ai_assistant") {
            AIAssistantScreen(navController = navController)
        }
        
        composable("expert_home") {
            ExpertHomeScreen(navController = navController)
        }
        
        composable("student_home") {
            StudentHomeScreen(navController = navController)
        }
        
        composable("instructor_home") {
            InstructorHomeScreen(navController = navController)
        }
        
        composable("class_detail/{classId}") { backStackEntry ->
            val classId = backStackEntry.arguments?.getString("classId") ?: ""
            val viewModel: ClassDetailViewModel = hiltViewModel()
            ClassDetailScreen(
                navController = navController,
                classId = classId,
                viewModel = viewModel
            )
        }
        
        composable("regulatory_home") {
            RegulatoryHomeScreen(navController = navController)
        }
        
        // Feature screens - Assessment based on role
        composable("assessment") {
            // This will be handled by role-based navigation in home screens
            AssessmentScreen(navController = navController)
        }
        
        composable("enterprise_assessment") {
            EnterpriseAssessmentScreen(navController = navController)
        }
        
        composable("assessment_history") {
            AssessmentHistoryScreen(navController = navController)
        }
        
        composable("esg_assessment_work/{pillar}") { backStackEntry ->
            val pillarString = backStackEntry.arguments?.getString("pillar")
            val pillar = try {
                pillarString?.let { ESGPillar.valueOf(it) }
            } catch (e: IllegalArgumentException) {
                null
            }
            pillar?.let {
                ESGAssessmentWorkScreen(
                    navController = navController,
                    pillar = it
                )
            }
        }
        
        composable("assessment_form/{pillar}") { backStackEntry ->
            val pillarString = backStackEntry.arguments?.getString("pillar")
            val pillar = try {
                pillarString?.let { ESGPillar.valueOf(it) }
            } catch (e: IllegalArgumentException) {
                null
            }
            ESGAssessmentFormScreen(
                navController = navController,
                pillar = pillar
            )
        }
        
        composable("expert_enterprise") {
            ExpertEnterpriseScreen(navController = navController)
        }
        
        composable("enterprise_esg_data/{enterpriseId}/{enterpriseName}") { backStackEntry ->
            val enterpriseId = backStackEntry.arguments?.getString("enterpriseId") ?: ""
            val encodedName = backStackEntry.arguments?.getString("enterpriseName") ?: ""
            val enterpriseName = java.net.URLDecoder.decode(encodedName, "UTF-8")
            EnterpriseESGDataScreen(
                navController = navController,
                enterpriseId = enterpriseId,
                enterpriseName = enterpriseName
            )
        }
        
        composable("student_assessment") {
            StudentAssessmentScreen(navController = navController)
        }
        
        composable("category_detail/{categoryName}") { backStackEntry ->
            val categoryName = backStackEntry.arguments?.getString("categoryName") ?: ""
            CategoryDetailScreen(navController = navController, categoryName = categoryName)
        }
        
        composable("regulatory_assessment") {
            RegulatoryAssessmentScreen(navController = navController)
        }
        
        composable("learning") {
            LearningScreen(navController = navController)
        }
        
        composable("learning_hub") {
            LearningHubScreen()
        }
        
        composable("add_activity") {
            AddActivityScreen(
                onBack = { navController.popBackStack() },
                onSave = { navController.popBackStack() }
            )
        }
        
        
        composable("expert_report") {
            ExpertReportScreen(navController = navController)
        }
        
        composable("report_builder/{template}") { backStackEntry ->
            val templateString = backStackEntry.arguments?.getString("template")
            val template = try {
                templateString?.let { ReportStandard.valueOf(it) } ?: ReportStandard.CUSTOM
            } catch (e: IllegalArgumentException) {
                ReportStandard.CUSTOM
            }
            ReportBuilderScreen(
                navController = navController,
                template = template
            )
        }
        
        composable("report_section_detail/{sectionId}/{template}") { backStackEntry ->
            val sectionId = backStackEntry.arguments?.getString("sectionId") ?: ""
            val templateString = backStackEntry.arguments?.getString("template")
            val template = try {
                templateString?.let { ReportStandard.valueOf(it) } ?: ReportStandard.CUSTOM
            } catch (e: IllegalArgumentException) {
                ReportStandard.CUSTOM
            }
            ReportSectionDetailScreen(
                navController = navController,
                sectionId = sectionId,
                template = template
            )
        }
        
        composable("report_viewer/{reportId}") { backStackEntry ->
            val reportId = backStackEntry.arguments?.getString("reportId") ?: ""
            ReportViewerScreen(
                reportId = reportId,
                navController = navController
            )
        }
        
        composable("reports") {
            ReportsScreen(navController = navController)
        }
        
        composable("tracker") {
            TrackerScreen(navController = navController)
        }
        
        composable("profile") {
            ProfileScreen(navController = navController)
        }
        
        composable("team") {
            TeamScreen(navController = navController)
        }
        
        composable("community") {
            CommunityScreen(navController = navController)
        }
        
        composable("news") {
            NewsScreen(navController = navController)
        }
        
        composable("analytics") {
            AnalyticsScreen(navController = navController)
        }
        
        composable("content") {
            ContentScreen(navController = navController)
        }
        
        composable("policy") {
            PolicyScreen(navController = navController)
        }
        
        composable("users") {
            UsersScreen(navController = navController)
        }
        
        composable("settings") {
            SettingsScreen(navController = navController)
        }
        
        // Assignment screens
        composable("assignment_detail/{assignmentId}") { backStackEntry ->
            val assignmentId = backStackEntry.arguments?.getString("assignmentId") ?: ""
            AssignmentDetailScreen(navController = navController, assignmentId = assignmentId)
        }
        
               composable("assignment_work/{assignmentId}") { backStackEntry ->
                   val assignmentId = backStackEntry.arguments?.getString("assignmentId") ?: ""
                   AssignmentWorkScreen(navController = navController, assignmentId = assignmentId)
               }
        
        composable("language_settings") {
            LanguageSettingsScreen(navController = navController)
        }
        
        composable("account_info") {
            AccountInformationScreen(navController = navController)
        }
        
        composable("notifications") {
            NotificationScreen(navController = navController)
        }
        
        composable("security") {
            SecurityScreen(navController = navController)
        }
        
        composable("help") {
            HelpScreen(navController = navController)
        }
        
        composable("about") {
            AboutScreen(navController = navController)
        }
        
        // Quiz screens
        composable("expert_quiz_taking/{quizId}") { backStackEntry ->
            val quizId = backStackEntry.arguments?.getString("quizId") ?: ""
            ExpertQuizTakingScreen(
                navController = navController,
                quizId = quizId
            )
        }
        
        composable("expert_quiz_results/{quizId}/{score}") { backStackEntry ->
            val quizId = backStackEntry.arguments?.getString("quizId") ?: ""
            val score = backStackEntry.arguments?.getString("score")?.toIntOrNull() ?: 0
            ExpertQuizResultsScreen(
                navController = navController,
                quizId = quizId,
                score = score
            )
        }
        
        composable("expert_news_detail/{newsId}") { backStackEntry ->
            val newsId = backStackEntry.arguments?.getString("newsId") ?: ""
            ExpertNewsDetailScreen(
                navController = navController,
                newsId = newsId
            )
        }
        
        // Instructor Content Creation Routes
        composable("create_lesson") {
            CreateLessonScreen(navController = navController)
        }
        
        composable("create_assignment") {
            CreateAssignmentScreen(navController = navController)
        }
        
        composable("create_question") {
            CreateQuestionScreen(navController = navController)
        }
        
        composable("question_library") {
            QuestionLibraryScreen(
                navController = navController,
                onQuestionsSelected = { /* Handle selected questions */ }
            )
        }
        
        // Assignment Grading Routes
        composable("assignment_grading/{assignmentId}") { backStackEntry ->
            val assignmentId = backStackEntry.arguments?.getString("assignmentId") ?: ""
            AssignmentGradingScreen(
                assignmentId = assignmentId,
                navController = navController
            )
        }
        
        composable("grade_submission/{submissionId}") { backStackEntry ->
            val submissionId = backStackEntry.arguments?.getString("submissionId") ?: ""
            GradeSubmissionScreen(
                submissionId = submissionId,
                navController = navController
            )
        }
        
        composable("assignment_grades/{assignmentId}") { backStackEntry ->
            val assignmentId = backStackEntry.arguments?.getString("assignmentId") ?: ""
            AssignmentGradesScreen(
                assignmentId = assignmentId,
                navController = navController
            )
        }
    }
}

