package com.ignitech.esgcompanion.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ignitech.esgcompanion.data.entity.*
import com.ignitech.esgcompanion.data.repository.ESGAssessmentRepository
import com.ignitech.esgcompanion.domain.entity.UserRole
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LearningHubViewModel @Inject constructor(
    private val repository: ESGAssessmentRepository,
    private val authRepository: com.ignitech.esgcompanion.domain.repository.AuthRepository
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(LearningHubUiState())
    val uiState: StateFlow<LearningHubUiState> = _uiState.asStateFlow()
    
    fun loadLearningHub() {
        println("DEBUG: LearningHub - loadLearningHub() called")
        viewModelScope.launch {
            println("DEBUG: LearningHub - Starting to load...")
            _uiState.value = _uiState.value.copy(isLoading = true)
            
            // Get current user role
            val userRole = try {
                val result = authRepository.getCurrentUser().first()
                when (result) {
                    is com.ignitech.esgcompanion.common.Result.Success -> {
                        result.data?.role ?: UserRole.ENTERPRISE
                    }
                    else -> UserRole.ENTERPRISE
                }
            } catch (e: Exception) {
                println("DEBUG: LearningHub - Error getting user role: ${e.message}")
                UserRole.ENTERPRISE
            }
            
            println("DEBUG: LearningHub - Detected userRole: $userRole")
            
            // Update selected role
            _uiState.value = _uiState.value.copy(selectedRole = userRole)
            
            try {
                // Load data from database
                val categories = repository.getLearningCategoriesByUserRole(userRole)
                val resources = repository.getLearningResourcesByUserRole(userRole)
                val featuredResources = repository.getFeaturedResources(userRole)
                
                println("DEBUG: LearningHub - categories: ${categories.size}")
                println("DEBUG: LearningHub - resources: ${resources.size}")
                println("DEBUG: LearningHub - featuredResources: ${featuredResources.size}")
                
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    categories = categories,
                    resources = resources,
                    featuredResources = featuredResources,
                    error = null
                )
            } catch (e: Exception) {
                println("DEBUG: LearningHub - Error loading data: ${e.message}")
                e.printStackTrace()
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = e.message ?: "Error loading learning hub data"
                )
            }
        }
    }
    
    private fun getMockCategories(userRole: UserRole): List<LearningCategoryEntity> {
        return when (userRole) {
            UserRole.ENTERPRISE -> listOf(
                LearningCategoryEntity(
                    id = "cat_esg_basics",
                    name = "ESG Fundamentals",
                    description = "Core knowledge about ESG",
                    icon = "school",
                    color = "#4CAF50",
                    userRole = UserRole.ENTERPRISE,
                    order = 1
                ),
                LearningCategoryEntity(
                    id = "cat_environmental",
                    name = "Environmental (E)",
                    description = "Environmental resources",
                    icon = "eco",
                    color = "#4CAF50",
                    userRole = UserRole.ENTERPRISE,
                    order = 2
                ),
                LearningCategoryEntity(
                    id = "cat_social",
                    name = "Social (S)",
                    description = "Social resources",
                    icon = "people",
                    color = "#4CAF50",
                    userRole = UserRole.ENTERPRISE,
                    order = 3
                ),
                LearningCategoryEntity(
                    id = "cat_governance",
                    name = "Governance (G)",
                    description = "Governance resources",
                    icon = "business",
                    color = "#4CAF50",
                    userRole = UserRole.ENTERPRISE,
                    order = 4
                )
            )
            UserRole.ACADEMIC -> listOf(
                LearningCategoryEntity(
                    id = "cat_student_esg",
                    name = "ESG Basics",
                    description = "Learn ESG from scratch",
                    icon = "school",
                    color = "#4CAF50",
                    userRole = UserRole.ACADEMIC,
                    order = 1
                ),
                LearningCategoryEntity(
                    id = "cat_student_env",
                    name = "Environment",
                    description = "Environmental protection",
                    icon = "eco",
                    color = "#4CAF50",
                    userRole = UserRole.ACADEMIC,
                    order = 2
                )
            )
            UserRole.EXPERT -> listOf(
                LearningCategoryEntity(
                    id = "cat_expert_consulting",
                    name = "Consulting Skills",
                    description = "Professional ESG consulting",
                    icon = "support_agent",
                    color = "#4CAF50",
                    userRole = UserRole.EXPERT,
                    order = 1
                ),
                LearningCategoryEntity(
                    id = "cat_expert_advanced",
                    name = "Advanced ESG",
                    description = "In-depth knowledge",
                    icon = "psychology",
                    color = "#4CAF50",
                    userRole = UserRole.EXPERT,
                    order = 2
                )
            )
            UserRole.INSTRUCTOR -> listOf(
                LearningCategoryEntity(
                    id = "cat_instructor_teaching",
                    name = "Teaching Methods",
                    description = "Effective ESG teaching",
                    icon = "school",
                    color = "#4CAF50",
                    userRole = UserRole.INSTRUCTOR,
                    order = 1
                ),
                LearningCategoryEntity(
                    id = "cat_instructor_material",
                    name = "Teaching Materials",
                    description = "Resources for instructors",
                    icon = "library_books",
                    color = "#4CAF50",
                    userRole = UserRole.INSTRUCTOR,
                    order = 2
                )
            )
            else -> emptyList()
        }
    }
    
    private fun getMockResources(userRole: UserRole): List<LearningResourceEntity> {
        return when (userRole) {
            UserRole.ENTERPRISE -> listOf(
                LearningResourceEntity(
                    id = "res_esg_intro",
                    title = "Introduction to ESG",
                    description = "Learn what ESG is and why businesses need to care",
                    content = "ESG (Environmental, Social, Governance) is a set of criteria for evaluating a company's sustainable development...",
                    type = LearningResourceType.ARTICLE,
                    category = "Fundamentals",
                    subcategory = "ESG",
                    level = LearningLevel.BEGINNER,
                    userRole = UserRole.ENTERPRISE,
                    duration = 15,
                    rating = 4.8f,
                    viewCount = 1250,
                    isFeatured = true,
                    isPublished = true,
                    tags = "[\"ESG\",\"Basics\",\"Enterprise\"]",
                    authorName = "John Smith"
                ),
                LearningResourceEntity(
                    id = "res_carbon_footprint",
                    title = "Measuring Carbon Footprint",
                    description = "Guide to measuring and reducing carbon emissions",
                    content = "Carbon footprint is the total amount of greenhouse gases emitted...",
                    type = LearningResourceType.VIDEO,
                    category = "Environment",
                    subcategory = "Carbon",
                    level = LearningLevel.INTERMEDIATE,
                    userRole = UserRole.ENTERPRISE,
                    duration = 30,
                    rating = 4.6f,
                    viewCount = 890,
                    isFeatured = true,
                    isPublished = true,
                    videoUrl = "https://example.com/carbon.mp4",
                    tags = "[\"Environment\",\"Carbon\",\"Emissions\"]",
                    authorName = "Jane Doe"
                ),
                LearningResourceEntity(
                    id = "res_esg_reporting",
                    title = "International ESG Reporting Standards",
                    description = "How to create ESG reports following GRI and SASB standards",
                    content = "ESG reporting following international standards helps businesses maintain transparency...",
                    type = LearningResourceType.COURSE,
                    category = "Governance",
                    subcategory = "Reporting",
                    level = LearningLevel.ADVANCED,
                    userRole = UserRole.ENTERPRISE,
                    duration = 120,
                    rating = 4.9f,
                    viewCount = 650,
                    isFeatured = true,
                    isPublished = true,
                    tags = "[\"Reporting\",\"GRI\",\"SASB\",\"Governance\"]",
                    authorName = "Michael Johnson"
                ),
                LearningResourceEntity(
                    id = "res_social_impact",
                    title = "Measuring Social Impact",
                    description = "Methods for assessing business social impact",
                    content = "Social impact includes activities...",
                    type = LearningResourceType.ARTICLE,
                    category = "Social",
                    subcategory = "Impact",
                    level = LearningLevel.INTERMEDIATE,
                    userRole = UserRole.ENTERPRISE,
                    duration = 20,
                    rating = 4.5f,
                    viewCount = 520,
                    isFeatured = false,
                    isPublished = true,
                    tags = "[\"Social\",\"Impact\",\"Measurement\"]",
                    authorName = "Sarah Williams"
                ),
                LearningResourceEntity(
                    id = "res_green_finance",
                    title = "Green Finance for Businesses",
                    description = "Green finance tools and ESG capital access opportunities",
                    content = "Green finance is the trend...",
                    type = LearningResourceType.VIDEO,
                    category = "Environment",
                    subcategory = "Finance",
                    level = LearningLevel.ADVANCED,
                    userRole = UserRole.ENTERPRISE,
                    duration = 45,
                    rating = 4.7f,
                    viewCount = 430,
                    isFeatured = false,
                    isPublished = true,
                    videoUrl = "https://example.com/finance.mp4",
                    tags = "[\"Green Finance\",\"ESG Capital\"]",
                    authorName = "David Brown"
                )
            )
            UserRole.ACADEMIC -> listOf(
                LearningResourceEntity(
                    id = "res_student_esg_basics",
                    title = "What is ESG?",
                    description = "ESG concepts for students",
                    content = "ESG stands for Environment, Social, Governance...",
                    type = LearningResourceType.VIDEO,
                    category = "ESG Basics",
                    subcategory = "Concepts",
                    level = LearningLevel.BEGINNER,
                    userRole = UserRole.ACADEMIC,
                    duration = 10,
                    rating = 4.9f,
                    viewCount = 2100,
                    isFeatured = true,
                    isPublished = true,
                    videoUrl = "https://example.com/student-esg.mp4",
                    tags = "[\"ESG\",\"Students\",\"Basics\"]",
                    authorName = "Ms. Smith"
                ),
                LearningResourceEntity(
                    id = "res_student_recycle",
                    title = "Recycling and Waste Sorting",
                    description = "Guide to proper recycling",
                    content = "Waste sorting helps with effective recycling...",
                    type = LearningResourceType.ARTICLE,
                    category = "Environment",
                    subcategory = "Recycling",
                    level = LearningLevel.BEGINNER,
                    userRole = UserRole.ACADEMIC,
                    duration = 8,
                    rating = 4.8f,
                    viewCount = 1800,
                    isFeatured = true,
                    isPublished = true,
                    tags = "[\"Recycling\",\"Environment\",\"Waste\"]",
                    authorName = "Mr. Johnson"
                ),
                LearningResourceEntity(
                    id = "res_student_climate",
                    title = "Climate Change",
                    description = "Understanding climate change and how to respond",
                    content = "Climate change is affecting our lives...",
                    type = LearningResourceType.COURSE,
                    category = "Environment",
                    subcategory = "Climate",
                    level = LearningLevel.INTERMEDIATE,
                    userRole = UserRole.ACADEMIC,
                    duration = 60,
                    rating = 4.7f,
                    viewCount = 1500,
                    isFeatured = true,
                    isPublished = true,
                    tags = "[\"Climate\",\"Environment\",\"Earth\"]",
                    authorName = "Ms. Williams"
                )
            )
            UserRole.EXPERT -> listOf(
                LearningResourceEntity(
                    id = "res_expert_consulting",
                    title = "Professional ESG Consulting Skills",
                    description = "Effective consulting methods for businesses",
                    content = "Professional ESG consulting requires deep knowledge...",
                    type = LearningResourceType.COURSE,
                    category = "Consulting Skills",
                    subcategory = "Consulting",
                    level = LearningLevel.ADVANCED,
                    userRole = UserRole.EXPERT,
                    duration = 180,
                    rating = 4.9f,
                    viewCount = 560,
                    isFeatured = true,
                    isPublished = true,
                    tags = "[\"Consulting\",\"Expert\",\"Skills\"]",
                    authorName = "Dr. Anderson"
                ),
                LearningResourceEntity(
                    id = "res_expert_standards",
                    title = "International ESG Standards",
                    description = "Overview of ESG standards: GRI, SASB, TCFD, CDP",
                    content = "GRI, SASB, TCFD, CDP are reporting standards...",
                    type = LearningResourceType.ARTICLE,
                    category = "Advanced ESG",
                    subcategory = "Standards",
                    level = LearningLevel.ADVANCED,
                    userRole = UserRole.EXPERT,
                    duration = 40,
                    rating = 4.8f,
                    viewCount = 720,
                    isFeatured = true,
                    isPublished = true,
                    tags = "[\"International Standards\",\"GRI\",\"SASB\",\"TCFD\"]",
                    authorName = "Dr. Martinez"
                ),
                LearningResourceEntity(
                    id = "res_expert_audit",
                    title = "ESG Auditing",
                    description = "ESG auditing and assessment methods",
                    content = "ESG auditing is the process of verification...",
                    type = LearningResourceType.VIDEO,
                    category = "Advanced ESG",
                    subcategory = "Auditing",
                    level = LearningLevel.ADVANCED,
                    userRole = UserRole.EXPERT,
                    duration = 90,
                    rating = 4.6f,
                    viewCount = 480,
                    isFeatured = false,
                    isPublished = true,
                    videoUrl = "https://example.com/audit.mp4",
                    tags = "[\"Auditing\",\"Assessment\",\"Verification\"]",
                    authorName = "Dr. Taylor"
                )
            )
            UserRole.INSTRUCTOR -> listOf(
                LearningResourceEntity(
                    id = "res_instructor_methods",
                    title = "Effective ESG Teaching Methods",
                    description = "ESG teaching techniques for students",
                    content = "ESG teaching methods should focus on...",
                    type = LearningResourceType.COURSE,
                    category = "Teaching Methods",
                    subcategory = "Techniques",
                    level = LearningLevel.INTERMEDIATE,
                    userRole = UserRole.INSTRUCTOR,
                    duration = 120,
                    rating = 4.8f,
                    viewCount = 890,
                    isFeatured = true,
                    isPublished = true,
                    tags = "[\"Teaching\",\"Methods\",\"Instructor\"]",
                    authorName = "Prof. Roberts"
                ),
                LearningResourceEntity(
                    id = "res_instructor_materials",
                    title = "ESG Teaching Materials Package",
                    description = "Slides, exercises and reference materials",
                    content = "Complete ESG teaching materials package...",
                    type = LearningResourceType.DOCUMENT,
                    category = "Teaching Materials",
                    subcategory = "Materials",
                    level = LearningLevel.BEGINNER,
                    userRole = UserRole.INSTRUCTOR,
                    duration = 0,
                    rating = 4.9f,
                    viewCount = 1120,
                    isFeatured = true,
                    isPublished = true,
                    documentUrl = "https://example.com/materials.pdf",
                    tags = "[\"Materials\",\"Slides\",\"Exercises\"]",
                    authorName = "Assoc. Prof. Davis"
                ),
                LearningResourceEntity(
                    id = "res_instructor_assessment",
                    title = "Assessing Student ESG Competency",
                    description = "Designing tests and assessment rubrics",
                    content = "Designing ESG competency assessment rubrics...",
                    type = LearningResourceType.ARTICLE,
                    category = "Teaching Methods",
                    subcategory = "Assessment",
                    level = LearningLevel.ADVANCED,
                    userRole = UserRole.INSTRUCTOR,
                    duration = 25,
                    rating = 4.7f,
                    viewCount = 670,
                    isFeatured = false,
                    isPublished = true,
                    tags = "[\"Assessment\",\"Testing\",\"Rubric\"]",
                    authorName = "Dr. Thompson"
                )
            )
            else -> emptyList()
        }
    }
    
    fun selectRole(role: UserRole) {
        _uiState.value = _uiState.value.copy(selectedRole = role)
        loadLearningHub()
    }
    
    fun selectCategory(categoryId: String) {
        _uiState.value = _uiState.value.copy(selectedCategory = categoryId)
        filterResources()
    }
    
    fun selectLevel(level: LearningLevel?) {
        _uiState.value = _uiState.value.copy(selectedLevel = level)
        filterResources()
    }
    
    fun selectType(type: LearningResourceType?) {
        _uiState.value = _uiState.value.copy(selectedType = type)
        filterResources()
    }
    
    fun updateSearchQuery(query: String) {
        _uiState.value = _uiState.value.copy(searchQuery = query)
    }
    
    fun performSearch() {
        // Use mock data for search
        val allResources = getMockResources(_uiState.value.selectedRole)
        val query = _uiState.value.searchQuery.lowercase()
        
        val results = if (query.isBlank()) {
            allResources
        } else {
            allResources.filter { resource ->
                resource.title.lowercase().contains(query) ||
                resource.description.lowercase().contains(query) ||
                resource.tags.lowercase().contains(query)
            }
        }
        
        _uiState.value = _uiState.value.copy(resources = results)
    }
    
    fun toggleSearch() {
        _uiState.value = _uiState.value.copy(
            isSearchVisible = !_uiState.value.isSearchVisible
        )
    }
    
    fun toggleFilter() {
        _uiState.value = _uiState.value.copy(
            isFilterVisible = !_uiState.value.isFilterVisible
        )
    }
    
    fun toggleSort() {
        // TODO: Implement sorting logic
    }
    
    fun openResource(resource: LearningResourceEntity) {
        // TODO: Navigate to resource detail
    }
    
    fun toggleBookmark(resourceId: String) {
        viewModelScope.launch {
            try {
                repository.toggleBookmark("current_user_id", resourceId)
                // Refresh resources to update bookmark status
                loadLearningHub()
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    error = e.message ?: "Bookmark error"
                )
            }
        }
    }
    
    fun refresh() {
        loadLearningHub()
    }
    
    private fun filterResources() {
        // Use mock data for filtering
        val allResources = getMockResources(_uiState.value.selectedRole)
        
        val filteredResources = allResources.filter { resource ->
            val categoryMatch = if (_uiState.value.selectedCategory.isNotEmpty()) {
                resource.category == _uiState.value.selectedCategory
            } else {
                true
            }
            
            val levelMatch = if (_uiState.value.selectedLevel != null) {
                resource.level == _uiState.value.selectedLevel
            } else {
                true
            }
            
            val typeMatch = if (_uiState.value.selectedType != null) {
                resource.type == _uiState.value.selectedType
            } else {
                true
            }
            
            categoryMatch && levelMatch && typeMatch
        }
        
        _uiState.value = _uiState.value.copy(resources = filteredResources)
    }
}

data class LearningHubUiState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val selectedRole: UserRole = UserRole.ENTERPRISE,
    val selectedCategory: String = "",
    val selectedLevel: LearningLevel? = null,
    val selectedType: LearningResourceType? = null,
    val searchQuery: String = "",
    val isSearchVisible: Boolean = false,
    val isFilterVisible: Boolean = false,
    val categories: List<LearningCategoryEntity> = emptyList(),
    val resources: List<LearningResourceEntity> = emptyList(),
    val featuredResources: List<LearningResourceEntity> = emptyList()
)
