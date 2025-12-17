package com.ignitech.esgcompanion.presentation.screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.ignitech.esgcompanion.R
import com.ignitech.esgcompanion.data.entity.*
import com.ignitech.esgcompanion.presentation.viewmodel.LearningHubCategoryViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LearningHubCategoryDetailScreen(
    navController: NavController,
    categoryId: String,
    modifier: Modifier = Modifier,
    viewModel: LearningHubCategoryViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    
    LaunchedEffect(categoryId) {
        viewModel.loadCategoryResources(categoryId)
    }
    
    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = uiState.category?.name ?: "Category",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White
                )
            )
        }
    ) { paddingValues ->
        if (uiState.isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    color = colorResource(id = R.color.interactive_primary)
                )
            }
        } else if (uiState.error != null) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = uiState.error ?: "Error",
                        color = MaterialTheme.colorScheme.error
                    )
                    Button(
                        onClick = { viewModel.refresh() },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = colorResource(id = R.color.interactive_primary)
                        )
                    ) {
                        Text("Retry")
                    }
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                // Category Info Card
                uiState.category?.let { category ->
                    item {
                        CategoryInfoCard(
                            category = category,
                            resourceCount = uiState.filteredResources.size,
                            totalCount = uiState.resources.size
                        )
                    }
                }
                
                // Filter Section
                if (uiState.resources.isNotEmpty()) {
                    item {
                        FilterSection(
                            selectedLevel = uiState.selectedLevel,
                            selectedType = uiState.selectedType,
                            onLevelSelected = { viewModel.filterByLevel(it) },
                            onTypeSelected = { viewModel.filterByType(it) },
                            onClearFilters = { viewModel.clearFilters() }
                        )
                    }
                }
                
                // Resources List
                if (uiState.filteredResources.isEmpty() && !uiState.isLoading) {
                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(32.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                Icon(
                                    Icons.Default.MenuBook,
                                    contentDescription = null,
                                    modifier = Modifier.size(64.dp),
                                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                                Text(
                                    text = if (uiState.selectedLevel != null || uiState.selectedType != null) {
                                        "No resources match your filters"
                                    } else {
                                        "No resources in this category"
                                    },
                                    style = MaterialTheme.typography.titleMedium,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                                if (uiState.selectedLevel != null || uiState.selectedType != null) {
                                    TextButton(onClick = { viewModel.clearFilters() }) {
                                        Text("Clear Filters")
                                    }
                                }
                            }
                        }
                    }
                } else {
                    items(uiState.filteredResources) { resource ->
                        LearningResourceCard(
                            resource = resource,
                            onClick = {
                                // TODO: Navigate to resource detail
                                // navController.navigate("resource_detail/${resource.id}")
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun CategoryInfoCard(
    category: LearningCategoryEntity,
    resourceCount: Int,
    totalCount: Int
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(
            width = 1.dp,
            color = colorResource(id = R.color.border_card)
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(56.dp)
                        .background(
                            color = colorResource(id = R.color.interactive_primary).copy(alpha = 0.1f),
                            shape = RoundedCornerShape(12.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = getCategoryIcon(category.icon),
                        contentDescription = null,
                        tint = colorResource(id = R.color.interactive_primary),
                        modifier = Modifier.size(28.dp)
                    )
                }
                
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        text = category.name,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = if (resourceCount != totalCount) {
                            "$resourceCount of $totalCount resources"
                        } else {
                            "$resourceCount resources"
                        },
                        style = MaterialTheme.typography.bodyMedium,
                        color = colorResource(id = R.color.text_secondary)
                    )
                }
            }
            
            if (category.description.isNotEmpty()) {
                Text(
                    text = category.description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterSection(
    selectedLevel: LearningLevel?,
    selectedType: LearningResourceType?,
    onLevelSelected: (LearningLevel?) -> Unit,
    onTypeSelected: (LearningResourceType?) -> Unit,
    onClearFilters: () -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Filters",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
            
            if (selectedLevel != null || selectedType != null) {
                TextButton(onClick = onClearFilters) {
                    Text("Clear")
                }
            }
        }
        
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // Level Filter
            FilterChip(
                selected = selectedLevel != null,
                onClick = {
                    if (selectedLevel != null) {
                        onLevelSelected(null)
                    } else {
                        // Show level selection dialog
                    }
                },
                label = {
                    Text(
                        text = selectedLevel?.let { getLevelLabel(it) } ?: "Level"
                    )
                },
                leadingIcon = if (selectedLevel != null) {
                    {
                        Icon(
                            Icons.Default.Check,
                            contentDescription = null,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                } else null,
                modifier = Modifier.weight(1f)
            )
            
            // Type Filter
            FilterChip(
                selected = selectedType != null,
                onClick = {
                    if (selectedType != null) {
                        onTypeSelected(null)
                    } else {
                        // Show type selection dialog
                    }
                },
                label = {
                    Text(
                        text = selectedType?.let { getResourceTypeLabel(it) } ?: "Type"
                    )
                },
                leadingIcon = if (selectedType != null) {
                    {
                        Icon(
                            Icons.Default.Check,
                            contentDescription = null,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                } else null,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

private fun getCategoryIcon(iconName: String): androidx.compose.ui.graphics.vector.ImageVector {
    return when (iconName) {
        "school" -> Icons.Default.School
        "eco" -> Icons.Default.Eco
        "people" -> Icons.Default.People
        "business" -> Icons.Default.Business
        "support_agent" -> Icons.Default.SupportAgent
        "psychology" -> Icons.Default.Psychology
        "library_books" -> Icons.Default.LibraryBooks
        "wb_sunny" -> Icons.Default.WbSunny
        else -> Icons.Default.MenuBook
    }
}

private fun getResourceTypeLabel(type: LearningResourceType): String {
    return when (type) {
        LearningResourceType.VIDEO -> "Video"
        LearningResourceType.ARTICLE -> "Article"
        LearningResourceType.COURSE -> "Course"
        LearningResourceType.DOCUMENT -> "Document"
        LearningResourceType.PODCAST -> "Podcast"
        LearningResourceType.WEBINAR -> "Webinar"
        LearningResourceType.GUIDE -> "Guide"
        LearningResourceType.LESSON -> "Lesson"
        else -> "Other"
    }
}

private fun getLevelLabel(level: LearningLevel): String {
    return when (level) {
        LearningLevel.BEGINNER -> "Beginner"
        LearningLevel.INTERMEDIATE -> "Intermediate"
        LearningLevel.ADVANCED -> "Advanced"
        LearningLevel.EXPERT -> "Expert"
    }
}

