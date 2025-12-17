package com.ignitech.esgcompanion.data.seed

import android.content.Context
import com.ignitech.esgcompanion.data.entity.AssignmentEntity
import com.ignitech.esgcompanion.data.entity.QuestionEntity
import com.ignitech.esgcompanion.data.local.AssignmentDao
import com.ignitech.esgcompanion.data.local.QuestionDao
import com.ignitech.esgcompanion.data.local.dao.UserDao
import com.ignitech.esgcompanion.data.local.dao.ESGQuestionDao
import com.ignitech.esgcompanion.data.seed.ESGAssignmentSeeder
import com.ignitech.esgcompanion.data.seed.UserSeeder
import com.ignitech.esgcompanion.data.seed.ESGAssessmentSeeder
import com.ignitech.esgcompanion.data.seed.ExpertSeeder
import com.ignitech.esgcompanion.data.seed.EnterpriseExpertConnectionSeeder
import com.ignitech.esgcompanion.data.seed.QuestionSeeder
import com.ignitech.esgcompanion.data.repository.ESGAssessmentRepository
import com.ignitech.esgcompanion.data.local.dao.ExpertDao
import com.ignitech.esgcompanion.data.local.dao.EnterpriseExpertConnectionDao
import com.ignitech.esgcompanion.data.local.dao.ClassDao
import com.ignitech.esgcompanion.data.local.dao.StudentDao
import com.ignitech.esgcompanion.data.local.dao.EnterpriseDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DatabaseSeeder @Inject constructor(
    private val assignmentDao: AssignmentDao,
    private val assignmentQuestionDao: com.ignitech.esgcompanion.data.local.QuestionDao,
    private val userDao: UserDao,
    private val esgQuestionDao: ESGQuestionDao,
    private val expertDao: ExpertDao,
    private val enterpriseExpertConnectionDao: EnterpriseExpertConnectionDao,
    private val classDao: ClassDao,
    private val studentDao: StudentDao,
    private val enterpriseDao: EnterpriseDao,
    private val esgAssessmentRepository: ESGAssessmentRepository,
    private val questionSeeder: QuestionSeeder
) {
    
    suspend fun seedDatabase() = withContext(Dispatchers.IO) {
        try {
            println("DEBUG: Starting database seeding...")
            
            // Kiểm tra xem đã có dữ liệu chưa
            val assignmentCount = assignmentDao.getAssignmentCount()
            println("DEBUG: Assignment count: $assignmentCount")
            
            if (assignmentCount > 0) {
                println("DEBUG: Database already has data, skipping seed")
                return@withContext // Đã có dữ liệu, không cần seed
            }
            
            println("DEBUG: Database is empty, starting to seed...")
            
            // Seed assignments
            println("DEBUG: Seeding assignments...")
            val assignments = ESGAssignmentSeeder.getESGAssignments()
            assignments.forEach { assignment ->
                assignmentDao.insertAssignment(assignment)
            }
            println("DEBUG: Seeded ${assignments.size} assignments")
            
            // Seed assignment questions
            println("DEBUG: Seeding assignment questions...")
            val questions = ESGAssignmentSeeder.getESGQuestions()
            questions.forEach { question ->
                assignmentQuestionDao.insertQuestion(question)
            }
            println("DEBUG: Seeded ${questions.size} assignment questions")
            
            // Seed question library
            println("DEBUG: Seeding question library...")
            questionSeeder.seedQuestions()
            println("DEBUG: Question library seeded")
            
            // Seed users
            println("DEBUG: Seeding users...")
            val users = UserSeeder.getDemoUsers()
            users.forEach { user ->
                userDao.insertUser(user)
            }
            println("DEBUG: Seeded ${users.size} users")
            
            // Seed ESG assessment questions
            println("DEBUG: Seeding ESG questions...")
            val esgQuestions = ESGAssessmentSeeder.getESGAssessmentQuestions()
            esgQuestionDao.insertQuestions(esgQuestions)
            println("DEBUG: Seeded ${esgQuestions.size} ESG questions")
            
            // Seed tracker data (banking activities)
            println("DEBUG: Seeding tracker data...")
            esgAssessmentRepository.initializeTrackerData()
            println("DEBUG: Tracker data seeded")
            
            // Seed assessment history (for demo enterprise user)
            println("DEBUG: Seeding assessment history...")
            esgAssessmentRepository.initializeAssessmentHistory("user_3") // Demo enterprise user
            println("DEBUG: Assessment history seeded")
            
            // Seed experts
            println("DEBUG: Seeding experts...")
            val experts = ExpertSeeder.getExpertData()
            expertDao.insertExperts(experts)
            println("DEBUG: Seeded ${experts.size} experts")
            
            // Seed enterprises
            println("DEBUG: Seeding enterprises...")
            val enterpriseSeeder = EnterpriseSeeder(enterpriseDao)
            enterpriseSeeder.seed()
            println("DEBUG: Enterprises seeded")
            
            // Seed enterprise-expert connections (for demo enterprise user)
            println("DEBUG: Seeding connections...")
            val enterpriseId = "user_3" // Demo enterprise user ID
            val connections = EnterpriseExpertConnectionSeeder.getConnectionData(enterpriseId)
            enterpriseExpertConnectionDao.insertConnection(connections.first())
            enterpriseExpertConnectionDao.insertConnection(connections[1])
            enterpriseExpertConnectionDao.insertConnection(connections[2])
            enterpriseExpertConnectionDao.insertConnection(connections[3])
            enterpriseExpertConnectionDao.insertConnection(connections[4])
            println("DEBUG: Seeded ${connections.size} connections")
            
            // Seed classes
            println("DEBUG: Seeding classes...")
            val classes = ClassSeeder.getClasses()
            classDao.insertClasses(classes)
            println("DEBUG: Seeded ${classes.size} classes")
            
            // Seed students
            println("DEBUG: Seeding students...")
            val students = StudentSeeder.getStudents()
            studentDao.insertStudents(students)
            println("DEBUG: Seeded ${students.size} students")
            
            println("DEBUG: Database seeding completed successfully!")
            
        } catch (e: Exception) {
            // Log error hoặc handle exception
            println("DEBUG: ERROR in database seeding: ${e.message}")
            e.printStackTrace()
        }
    }
    
    suspend fun clearAndReseed() = withContext(Dispatchers.IO) {
        try {
            println("DEBUG: Starting clearAndReseed")
            // Xóa dữ liệu cũ
            assignmentDao.deleteAllAssignments()
            assignmentQuestionDao.deleteAllQuestions()
            userDao.clearUser()
            esgQuestionDao.deleteAllQuestions()
            expertDao.deleteAllExperts()
            enterpriseExpertConnectionDao.deleteAllConnections()
            classDao.deleteAllClasses()
            studentDao.deleteAllStudents()
            
            // Xóa tracker data cũ
            esgAssessmentRepository.clearTrackerData()
            
            // Seed lại
            seedDatabase()
            println("DEBUG: clearAndReseed completed successfully")
            
        } catch (e: Exception) {
            println("DEBUG: ERROR in clearAndReseed: ${e.message}")
            e.printStackTrace()
        }
    }
}
