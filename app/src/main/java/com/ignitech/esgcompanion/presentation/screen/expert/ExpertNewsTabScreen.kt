package com.ignitech.esgcompanion.presentation.screen.expert

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.foundation.BorderStroke
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ignitech.esgcompanion.R
import com.ignitech.esgcompanion.domain.entity.ESGPillar
import com.ignitech.esgcompanion.presentation.viewmodel.ExpertNewsTabViewModel
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpertNewsTabScreen(
    modifier: Modifier = Modifier,
    navController: androidx.navigation.NavController? = null,
    viewModel: ExpertNewsTabViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        navController?.let { viewModel.setNavController(it) }
        viewModel.loadNewsData()
    }

    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Header Section
        item {
            Column {
                Text(
                    text = "ESG News",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = "Latest ESG news and trends",
                    style = MaterialTheme.typography.bodyMedium,
                    color = colorResource(id = R.color.text_secondary)
                )
            }
        }

        // Search and Filter
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedTextField(
                    value = uiState.searchQuery,
                    onValueChange = viewModel::updateSearchQuery,
                    modifier = Modifier.weight(1f),
                    placeholder = { 
                        Text(
                            "Search news...",
                            color = colorResource(id = R.color.text_hint)
                        ) 
                    },
                    leadingIcon = { 
                        Icon(
                            Icons.Default.Search, 
                            contentDescription = null,
                            tint = colorResource(id = R.color.text_secondary)
                        ) 
                    },
                    trailingIcon = {
                        if (uiState.searchQuery.isNotEmpty()) {
                            IconButton(onClick = { viewModel.clearSearch() }) {
                                Icon(
                                    Icons.Default.Clear, 
                                    contentDescription = "Clear",
                                    tint = colorResource(id = R.color.text_secondary)
                                )
                            }
                        }
                    },
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = colorResource(id = R.color.interactive_primary),
                        unfocusedBorderColor = colorResource(id = R.color.border_card)
                    )
                )
                
                IconButton(onClick = { viewModel.toggleFilter() }) {
                    Icon(
                        Icons.Default.FilterList, 
                        contentDescription = "Filter",
                        tint = colorResource(id = R.color.interactive_primary)
                    )
                }
            }
        }

        // Filter Section
        if (uiState.isFilterVisible) {
            item {
                NewsFilterSection(
                    selectedCategory = uiState.selectedCategory,
                    selectedPillar = uiState.selectedPillar,
                    selectedTimeRange = uiState.selectedTimeRange,
                    onCategorySelected = viewModel::selectCategory,
                    onPillarSelected = viewModel::selectPillar,
                    onTimeRangeSelected = viewModel::selectTimeRange
                )
            }
        }

        // Breaking News
        if (uiState.breakingNews.isNotEmpty()) {
            item {
                Text(
                    text = "Breaking News",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
            
            item {
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(uiState.breakingNews) { news ->
                        BreakingNewsCard(
                            news = news,
                            onClick = { viewModel.openNews(news.id) }
                        )
                    }
                }
            }
        }

        // Featured News
        if (uiState.featuredNews.isNotEmpty()) {
            item {
                Text(
                    text = "Featured News",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
            
            items(uiState.featuredNews) { news ->
                FeaturedNewsCard(
                    news = news,
                    onClick = { viewModel.openNews(news.id) },
                    onBookmark = { viewModel.toggleBookmark(news.id) },
                    onShare = { viewModel.shareNews(news.id) }
                )
            }
        }

        // News Categories
        item {
            Text(
                text = "News Categories",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
        }

        item {
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(uiState.newsCategories) { category ->
                    NewsCategoryCard(
                        category = category,
                        onClick = { viewModel.selectCategory(category.id) }
                    )
                }
            }
        }

        // Latest News
        if (uiState.latestNews.isNotEmpty()) {
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Latest News",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    
                    TextButton(onClick = { viewModel.viewAllNews() }) {
                        Text("View All")
                        Icon(Icons.Default.ArrowForward, contentDescription = null)
                    }
                }
            }
            
            items(uiState.latestNews) { news ->
                NewsCard(
                    news = news,
                    onClick = { viewModel.openNews(news.id) },
                    onBookmark = { viewModel.toggleBookmark(news.id) },
                    onShare = { viewModel.shareNews(news.id) }
                )
            }
        }

        // Expert Insights
        if (uiState.expertInsights.isNotEmpty()) {
            item {
                Text(
                    text = "Expert Insights",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
            
            items(uiState.expertInsights) { insight ->
                ExpertInsightCard(
                    insight = insight,
                    onClick = { viewModel.openNews(insight.id) }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsFilterSection(
    selectedCategory: String?,
    selectedPillar: ESGPillar?,
    selectedTimeRange: TimeRange?,
    onCategorySelected: (String?) -> Unit,
    onPillarSelected: (ESGPillar?) -> Unit,
    onTimeRangeSelected: (TimeRange?) -> Unit
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
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = "Filters",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
            
            // Category Filter
            Text(
                text = "Category",
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Medium,
                color = colorResource(id = R.color.text_secondary)
            )
            
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(listOf(null) + listOf("All", "Environmental", "Social", "Governance", "Economic", "Technology")) { category ->
                    FilterChip(
                        selected = category == selectedCategory,
                        onClick = { onCategorySelected(category) },
                        label = { Text(category ?: "All") }
                    )
                }
            }
            
            // Pillar Filter
            Text(
                text = "ESG Pillar",
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Medium,
                color = colorResource(id = R.color.text_secondary)
            )
            
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(listOf(null) + ESGPillar.values().toList()) { pillar ->
                    PillarFilterChip(
                        pillar = pillar,
                        isSelected = pillar == selectedPillar,
                        onClick = { onPillarSelected(pillar) }
                    )
                }
            }
            
            // Time Range Filter
            Text(
                text = "Time",
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Medium,
                color = colorResource(id = R.color.text_secondary)
            )
            
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(listOf(null) + TimeRange.values().toList()) { timeRange ->
                    TimeRangeFilterChip(
                        timeRange = timeRange,
                        isSelected = timeRange == selectedTimeRange,
                        onClick = { onTimeRangeSelected(timeRange) }
                    )
                }
            }
        }
    }
}

@Composable
fun BreakingNewsCard(
    news: ESGNews,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .width(280.dp)
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
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Surface(
                color = colorResource(id = R.color.esg_warning),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(
                    text = "BREAKING",
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            }
            
            Text(
                text = news.title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            
            Text(
                text = news.summary,
                style = MaterialTheme.typography.bodyMedium,
                color = colorResource(id = R.color.text_secondary),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Composable
fun FeaturedNewsCard(
    news: ESGNews,
    onClick: () -> Unit,
    onBookmark: () -> Unit,
    onShare: () -> Unit
) {
    val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    
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
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    color = colorResource(id = R.color.interactive_primary),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(
                        text = "FEATURED",
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                }
                
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    IconButton(onClick = onBookmark) {
                        Icon(
                            Icons.Default.BookmarkBorder,
                            contentDescription = "Bookmark",
                            tint = if (news.isBookmarked) 
                                colorResource(id = R.color.interactive_primary) 
                            else 
                                colorResource(id = R.color.text_secondary)
                        )
                    }
                    IconButton(onClick = onShare) {
                        Icon(
                            Icons.Default.Share,
                            contentDescription = "Share",
                            tint = colorResource(id = R.color.text_secondary)
                        )
                    }
                }
            }
            
            Text(
                text = news.title,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
            
            Text(
                text = news.summary,
                style = MaterialTheme.typography.bodyMedium,
                color = colorResource(id = R.color.text_secondary),
                maxLines = 3,
                overflow = TextOverflow.Ellipsis
            )
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = news.source,
                        style = MaterialTheme.typography.bodySmall,
                        color = colorResource(id = R.color.text_secondary)
                    )
                    
                    Text(
                        text = "•",
                        style = MaterialTheme.typography.bodySmall,
                        color = colorResource(id = R.color.text_secondary)
                    )
                    
                    Text(
                        text = dateFormat.format(Date(news.publishedAt)),
                        style = MaterialTheme.typography.bodySmall,
                        color = colorResource(id = R.color.text_secondary)
                    )
                }
                
                ExpertPillarChip(pillar = news.pillar)
            }
        }
    }
}

@Composable
fun NewsCategoryCard(
    category: NewsCategory,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .width(120.dp)
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
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = category.name,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface,
                textAlign = TextAlign.Center
            )
            
            Text(
                text = "${category.newsCount} news",
                style = MaterialTheme.typography.bodySmall,
                color = colorResource(id = R.color.text_secondary)
            )
        }
    }
}

@Composable
fun NewsCard(
    news: ESGNews,
    onClick: () -> Unit,
    onBookmark: () -> Unit,
    onShare: () -> Unit
) {
    val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    
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
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // News Image Placeholder
            Surface(
                color = colorResource(id = R.color.interactive_primary).copy(alpha = 0.1f),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.size(80.dp)
            ) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Article,
                        contentDescription = null,
                        tint = colorResource(id = R.color.interactive_primary),
                        modifier = Modifier.size(40.dp)
                    )
                }
            }
            
            // News Content
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = news.title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                
                Text(
                    text = news.summary,
                    style = MaterialTheme.typography.bodyMedium,
                    color = colorResource(id = R.color.text_secondary),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = news.source,
                            style = MaterialTheme.typography.bodySmall,
                            color = colorResource(id = R.color.text_secondary)
                        )
                        
                        Text(
                            text = "•",
                            style = MaterialTheme.typography.bodySmall,
                            color = colorResource(id = R.color.text_secondary)
                        )
                        
                        Text(
                            text = dateFormat.format(Date(news.publishedAt)),
                            style = MaterialTheme.typography.bodySmall,
                            color = colorResource(id = R.color.text_secondary)
                        )
                    }
                    
                    ExpertPillarChip(pillar = news.pillar)
                }
            }
            
            // Action Buttons
            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                IconButton(onClick = onBookmark) {
                    Icon(
                        Icons.Default.BookmarkBorder,
                        contentDescription = "Bookmark",
                            tint = if (news.isBookmarked) 
                                colorResource(id = R.color.interactive_primary) 
                            else 
                                colorResource(id = R.color.text_secondary)
                    )
                }
                IconButton(onClick = onShare) {
                    Icon(
                        Icons.Default.Share,
                        contentDescription = "Share",
                        tint = colorResource(id = R.color.text_secondary)
                    )
                }
            }
        }
    }
}

@Composable
fun ExpertInsightCard(
    insight: ExpertInsight,
    onClick: () -> Unit
) {
    val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    
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
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Surface(
                        color = colorResource(id = R.color.interactive_primary).copy(alpha = 0.1f),
                        shape = RoundedCornerShape(20.dp),
                        modifier = Modifier.size(40.dp)
                    ) {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = insight.expertName.take(1).uppercase(),
                                style = MaterialTheme.typography.titleMedium,
                                color = colorResource(id = R.color.interactive_primary),
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                    
                    Column {
                        Text(
                            text = insight.expertName,
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = "ESG Expert",
                            style = MaterialTheme.typography.bodySmall,
                            color = colorResource(id = R.color.text_secondary)
                        )
                    }
                }
                
                Surface(
                    color = colorResource(id = R.color.esg_warning),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(
                        text = "INSIGHT",
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
            
            Text(
                text = insight.title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
            
            Text(
                text = stripHtmlTags(insight.content),
                style = MaterialTheme.typography.bodyMedium,
                color = colorResource(id = R.color.text_secondary),
                maxLines = 3,
                overflow = TextOverflow.Ellipsis
            )
            
            Text(
                text = dateFormat.format(Date(insight.publishedAt)),
                style = MaterialTheme.typography.bodySmall,
                color = colorResource(id = R.color.text_secondary)
            )
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PillarFilterChip(
    pillar: ESGPillar?,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    
    Surface(
        modifier = Modifier.clickable(
            interactionSource = remember { MutableInteractionSource() },
            indication = null
        ) { onClick() },
        color = if (isSelected) colorResource(id = R.color.interactive_primary) else Color.Transparent,
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(
            width = 1.dp,
            color = if (isSelected) colorResource(id = R.color.interactive_primary) else colorResource(id = R.color.border_card)
        )
    ) {
        Text(
            text = when (pillar) {
                ESGPillar.ENVIRONMENTAL -> "Environmental"
                ESGPillar.SOCIAL -> "Social"
                ESGPillar.GOVERNANCE -> "Governance"
                null -> "All"
            },
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
            style = MaterialTheme.typography.bodySmall,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
            color = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurface
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimeRangeFilterChip(
    timeRange: TimeRange?,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    
    Surface(
        modifier = Modifier.clickable(
            interactionSource = remember { MutableInteractionSource() },
            indication = null
        ) { onClick() },
        color = if (isSelected) colorResource(id = R.color.interactive_primary) else Color.Transparent,
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(
            width = 1.dp,
            color = if (isSelected) colorResource(id = R.color.interactive_primary) else colorResource(id = R.color.border_card)
        )
    ) {
        Text(
            text = when (timeRange) {
                TimeRange.TODAY -> "Today"
                TimeRange.WEEK -> "This Week"
                TimeRange.MONTH -> "This Month"
                TimeRange.YEAR -> "This Year"
                null -> "All"
            },
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
            style = MaterialTheme.typography.bodySmall,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
            color = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurface
        )
    }
}

// Data classes
data class ESGNews(
    val id: String,
    val title: String,
    val summary: String,
    val content: String,
    val source: String,
    val pillar: ESGPillar,
    val publishedAt: Long,
    val isBookmarked: Boolean = false,
    val isBreaking: Boolean = false,
    val isFeatured: Boolean = false
)

data class NewsCategory(
    val id: String,
    val name: String,
    val icon: String,
    val newsCount: Int
)

data class ExpertInsight(
    val id: String,
    val title: String,
    val content: String,
    val expertName: String,
    val expertId: String,
    val publishedAt: Long
)

enum class TimeRange {
    TODAY, WEEK, MONTH, YEAR
}

// Helper function to strip HTML tags and extract plain text
fun stripHtmlTags(htmlContent: String): String {
    var result = htmlContent
    
    // Remove all HTML tags
    result = result.replace(Regex("<[^>]*>"), "")
    
    // Decode common HTML entities
    result = result.replace("&nbsp;", " ")
    result = result.replace("&amp;", "&")
    result = result.replace("&lt;", "<")
    result = result.replace("&gt;", ">")
    result = result.replace("&quot;", "\"")
    result = result.replace("&#39;", "'")
    
    // Clean up multiple spaces and newlines
    result = result.replace(Regex("\\s+"), " ")
    result = result.trim()
    
    return result
}
