package com.ignitech.esgcompanion.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.BorderStroke
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
import com.ignitech.esgcompanion.presentation.viewmodel.LearningHubViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LearningHubScreen(
    navController: NavController? = null,
    modifier: Modifier = Modifier,
    viewModel: LearningHubViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.loadLearningHub()
    }

    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = { 
                    Text(
                        "ESG Learning Hub",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White
                )
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            // Loading state
            if (uiState.isLoading) {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(32.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(
                            color = colorResource(id = R.color.interactive_primary)
                        )
                    }
                }
            }
            
            // Error state
            else if (uiState.error != null) {
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.errorContainer
                        )
                    ) {
                        Text(
                            text = uiState.error ?: "An error occurred",
                            modifier = Modifier.padding(16.dp),
                            color = MaterialTheme.colorScheme.onErrorContainer
                        )
                    }
                }
            }
            
            // Categories section
            else if (uiState.categories.isNotEmpty()) {
                item {
                    Text(
                        text = "Learning Categories",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
                
                items(uiState.categories) { category ->
                    val resourceCount = uiState.resources.count { resource ->
                        resource.category.equals(category.name, ignoreCase = true) && 
                        resource.userRole == category.userRole
                    }
                    CategoryCard(
                        category = category,
                        resourceCount = resourceCount,
                        onClick = {
                            navController?.navigate("learning_hub_category/${category.id}") 
                                ?: viewModel.selectCategory(category.id)
                        }
                    )
                }
                
                // Featured Resources
                if (uiState.featuredResources.isNotEmpty()) {
                    item {
                        Text(
                            text = "Featured Resources",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                    
                    items(uiState.featuredResources.take(3)) { resource ->
                        LearningResourceCard(
                            resource = resource,
                            onClick = { viewModel.openResource(resource) }
                        )
                    }
                }
                
                // All Resources
                if (uiState.resources.isNotEmpty()) {
                    item {
                        Text(
                            text = "All Resources (${uiState.resources.size})",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                    
                    items(uiState.resources) { resource ->
                        LearningResourceCard(
                            resource = resource,
                            onClick = { viewModel.openResource(resource) }
                        )
                    }
                }
            }
            
            // Empty state
            else {
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
                                text = "No learning resources yet",
                                style = MaterialTheme.typography.titleMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Button(
                                onClick = { viewModel.refresh() },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = colorResource(id = R.color.interactive_primary)
                                )
                            ) {
                                Text("Refresh")
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CategoryCard(
    category: LearningCategoryEntity,
    resourceCount: Int,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) { onClick() },
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
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.weight(1f)
            ) {
                // Icon
                Box(
                    modifier = Modifier
                        .size(48.dp)
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
                        modifier = Modifier.size(24.dp)
                    )
                }
                
                // Info
                Column(
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        text = category.name,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = "$resourceCount resources",
                        style = MaterialTheme.typography.bodyMedium,
                        color = colorResource(id = R.color.text_secondary)
                    )
                }
            }
            
            Icon(
                Icons.Default.ChevronRight,
                contentDescription = null,
                tint = colorResource(id = R.color.text_secondary)
            )
        }
    }
}

@Composable
fun LearningResourceCard(
    resource: LearningResourceEntity,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) { onClick() },
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
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.weight(1f)
                ) {
                    // Type badge
                    Surface(
                        color = colorResource(id = R.color.interactive_primary).copy(alpha = 0.1f),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(
                            text = getResourceTypeLabel(resource.type),
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Medium,
                            color = colorResource(id = R.color.interactive_primary)
                        )
                    }
                    
                    // Level badge
                    Surface(
                        color = colorResource(id = R.color.text_secondary).copy(alpha = 0.1f),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(
                            text = getLevelLabel(resource.level),
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Medium,
                            color = colorResource(id = R.color.text_secondary)
                        )
                    }
                }
                
                // Featured badge
                if (resource.isFeatured) {
                    Surface(
                        color = colorResource(id = R.color.esg_warning),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(
                            text = "Featured",
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }
                }
            }
            
            // Title
            Text(
                text = resource.title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            
            // Description
            Text(
                text = resource.description,
                style = MaterialTheme.typography.bodyMedium,
                color = colorResource(id = R.color.text_secondary),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            
            // Footer info
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Duration
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            Icons.Default.Schedule,
                            contentDescription = null,
                            tint = colorResource(id = R.color.text_secondary),
                            modifier = Modifier.size(16.dp)
                        )
                        Text(
                            text = "${resource.duration} min",
                            style = MaterialTheme.typography.bodySmall,
                            color = colorResource(id = R.color.text_secondary)
                        )
                    }
                    
                    // Rating
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            Icons.Default.Star,
                            contentDescription = null,
                            tint = colorResource(id = R.color.esg_warning),
                            modifier = Modifier.size(16.dp)
                        )
                        Text(
                            text = String.format("%.1f", resource.rating),
                            style = MaterialTheme.typography.bodySmall,
                            color = colorResource(id = R.color.text_secondary)
                        )
                    }
                    
                    // Views
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            Icons.Default.Visibility,
                            contentDescription = null,
                            tint = colorResource(id = R.color.text_secondary),
                            modifier = Modifier.size(16.dp)
                        )
                        Text(
                            text = "${resource.viewCount}",
                            style = MaterialTheme.typography.bodySmall,
                            color = colorResource(id = R.color.text_secondary)
                        )
                    }
                }
            }
        }
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

private fun getCategoryIcon(iconName: String): androidx.compose.ui.graphics.vector.ImageVector {
    return when (iconName) {
        "school" -> Icons.Default.School
        "eco" -> Icons.Default.Eco
        "people" -> Icons.Default.People
        "business" -> Icons.Default.Business
        "support_agent" -> Icons.Default.SupportAgent
        "psychology" -> Icons.Default.Psychology
        "library_books" -> Icons.Default.LibraryBooks
        else -> Icons.Default.MenuBook
    }
}
