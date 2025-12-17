package com.ignitech.esgcompanion.presentation.screen.expert

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.ignitech.esgcompanion.presentation.viewmodel.ExpertNewsDetailViewModel
import com.ignitech.esgcompanion.utils.AppColors
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpertNewsDetailScreen(
    navController: NavController,
    newsId: String,
    viewModel: ExpertNewsDetailViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val colors = AppColors()

    LaunchedEffect(newsId) {
        viewModel.setNavController(navController)
        viewModel.loadNewsDetail(newsId)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "News Details",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = { viewModel.toggleBookmark() }) {
                        Icon(
                            imageVector = if (uiState.isBookmarked) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
                            contentDescription = "Bookmark",
                            tint = if (uiState.isBookmarked) colors.primary else colors.textSecondary
                        )
                    }
                    IconButton(onClick = { viewModel.shareNews() }) {
                        Icon(
                            Icons.Default.Share,
                            contentDescription = "Share",
                            tint = colors.textSecondary
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        if (uiState.isLoading) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else if (uiState.news != null) {
            val news = uiState.news!!
            val dateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
            
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .background(colors.backgroundSurface)
                    .padding(paddingValues),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // News Header
                item {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        // News Type Badge
                        Surface(
                            color = when {
                                news.isBreaking -> Color(0xFFFF5722)
                                news.isFeatured -> colors.primary
                                else -> colors.textSecondary
                            },
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Text(
                                text = when {
                                    news.isBreaking -> "BREAKING"
                                    news.isFeatured -> "FEATURED"
                                    else -> "NEWS"
                                },
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                                style = MaterialTheme.typography.bodyMedium,
                                color = Color.White,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        
                        // Title
                        Text(
                            text = news.title,
                            style = MaterialTheme.typography.headlineMedium,
                            fontWeight = FontWeight.Bold,
                            color = colors.textPrimary
                        )
                        
                        // Meta Information
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
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = colors.textSecondary,
                                    fontWeight = FontWeight.Medium
                                )
                                
                                Text(
                                    text = "•",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = colors.textSecondary
                                )
                                
                                Text(
                                    text = dateFormat.format(Date(news.publishedAt)),
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = colors.textSecondary
                                )
                            }
                            
                            ExpertPillarChip(pillar = news.pillar)
                        }
                    }
                }
                
                // Summary
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(
                            containerColor = colors.primary.copy(alpha = 0.05f)
                        ),
                        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp)
                        ) {
                            Text(
                                text = "Summary",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = colors.textPrimary
                            )
                            
                            Spacer(modifier = Modifier.height(8.dp))
                            
                            Text(
                                text = news.summary,
                                style = MaterialTheme.typography.bodyLarge,
                                color = colors.textSecondary,
                                lineHeight = MaterialTheme.typography.bodyLarge.lineHeight
                            )
                        }
                    }
                }
                
                // Content
                item {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Text(
                            text = "Content",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = colors.textPrimary
                        )
                        
                        // Parse HTML content and display
                        HtmlContent(
                            htmlContent = news.content,
                            colors = colors
                        )
                    }
                }
                
                // Related Actions
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(
                            containerColor = colors.backgroundSurface
                        ),
                        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            Text(
                                text = "Actions",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = colors.textPrimary
                            )
                            
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                Button(
                                    onClick = { viewModel.toggleBookmark() },
                                    modifier = Modifier.weight(1f),
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = if (uiState.isBookmarked) colors.primary else colors.textSecondary
                                    )
                                ) {
                                    Icon(
                                        imageVector = if (uiState.isBookmarked) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
                                        contentDescription = null
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
                                        text = if (uiState.isBookmarked) "Saved" else "Save"
                                    )
                                }
                                
                                OutlinedButton(
                                    onClick = { viewModel.shareNews() },
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Icon(
                                        Icons.Default.Share,
                                        contentDescription = null
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text("Chia sẻ")
                                }
                            }
                        }
                    }
                }
            }
        } else {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Icon(
                        Icons.Default.ErrorOutline,
                        contentDescription = null,
                        modifier = Modifier.size(64.dp),
                        tint = colors.textSecondary
                    )
                    Text(
                        text = "Unable to load news",
                        style = MaterialTheme.typography.titleMedium,
                        color = colors.textSecondary
                    )
                    Button(onClick = { viewModel.loadNewsDetail(newsId) }) {
                        Text("Retry")
                    }
                }
            }
        }
    }
}

@Composable
fun HtmlContent(
    htmlContent: String,
    colors: com.ignitech.esgcompanion.utils.AppColorsData
) {
    val parsedElements = parseHtmlToElements(htmlContent)
    
    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        parsedElements.forEach { element ->
            when (element.type) {
                HtmlElementType.HEADING_2 -> {
                    Text(
                        text = element.content,
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        color = colors.textPrimary
                    )
                }
                HtmlElementType.HEADING_3 -> {
                    Text(
                        text = element.content,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = colors.textPrimary
                    )
                }
                HtmlElementType.PARAGRAPH -> {
                    Text(
                        text = element.content,
                        style = MaterialTheme.typography.bodyLarge,
                        color = colors.textSecondary,
                        lineHeight = MaterialTheme.typography.bodyLarge.lineHeight
                    )
                }
                HtmlElementType.LIST_ITEM -> {
                    Text(
                        text = "• ${element.content}",
                        style = MaterialTheme.typography.bodyLarge,
                        color = colors.textSecondary,
                        modifier = Modifier.padding(start = 16.dp)
                    )
                }
                HtmlElementType.EMPHASIS -> {
                    Text(
                        text = element.content,
                        style = MaterialTheme.typography.bodyMedium,
                        color = colors.textSecondary,
                        fontStyle = androidx.compose.ui.text.font.FontStyle.Italic
                    )
                }
                HtmlElementType.STRONG -> {
                    Text(
                        text = element.content,
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold,
                        color = colors.textSecondary
                    )
                }
                HtmlElementType.PLAIN_TEXT -> {
                    Text(
                        text = element.content,
                        style = MaterialTheme.typography.bodyLarge,
                        color = colors.textSecondary,
                        lineHeight = MaterialTheme.typography.bodyLarge.lineHeight
                    )
                }
            }
        }
    }
}

// Data classes for HTML parsing
data class HtmlElement(
    val content: String,
    val type: HtmlElementType
)

enum class HtmlElementType {
    HEADING_2,
    HEADING_3,
    PARAGRAPH,
    LIST_ITEM,
    EMPHASIS,
    STRONG,
    PLAIN_TEXT
}

// Enhanced HTML parser
fun parseHtmlToElements(htmlContent: String): List<HtmlElement> {
    val elements = mutableListOf<HtmlElement>()
    val lines = htmlContent.split("\n")
    
    lines.forEach { line ->
        val trimmedLine = line.trim()
        if (trimmedLine.isNotEmpty()) {
            when {
                trimmedLine.startsWith("<h2>") && trimmedLine.endsWith("</h2>") -> {
                    val content = parseInlineHtml(trimmedLine.removeSurrounding("<h2>", "</h2>"))
                    elements.add(HtmlElement(content, HtmlElementType.HEADING_2))
                }
                trimmedLine.startsWith("<h3>") && trimmedLine.endsWith("</h3>") -> {
                    val content = parseInlineHtml(trimmedLine.removeSurrounding("<h3>", "</h3>"))
                    elements.add(HtmlElement(content, HtmlElementType.HEADING_3))
                }
                trimmedLine.startsWith("<p>") && trimmedLine.endsWith("</p>") -> {
                    val content = parseInlineHtml(trimmedLine.removeSurrounding("<p>", "</p>"))
                    elements.add(HtmlElement(content, HtmlElementType.PARAGRAPH))
                }
                trimmedLine.startsWith("<li>") && trimmedLine.endsWith("</li>") -> {
                    val content = parseInlineHtml(trimmedLine.removeSurrounding("<li>", "</li>"))
                    elements.add(HtmlElement(content, HtmlElementType.LIST_ITEM))
                }
                trimmedLine.startsWith("<em>") && trimmedLine.endsWith("</em>") -> {
                    val content = trimmedLine.removeSurrounding("<em>", "</em>")
                    elements.add(HtmlElement(content, HtmlElementType.EMPHASIS))
                }
                trimmedLine.startsWith("<strong>") && trimmedLine.endsWith("</strong>") -> {
                    val content = trimmedLine.removeSurrounding("<strong>", "</strong>")
                    elements.add(HtmlElement(content, HtmlElementType.STRONG))
                }
                !trimmedLine.startsWith("<") && !trimmedLine.endsWith(">") -> {
                    // Plain text without HTML tags
                    elements.add(HtmlElement(trimmedLine, HtmlElementType.PLAIN_TEXT))
                }
            }
        }
    }
    
    return elements
}

// Parse inline HTML tags like <strong>, <em> within text
fun parseInlineHtml(text: String): String {
    var result = text
    
    // Remove <strong> tags but keep the content
    result = result.replace(Regex("<strong>(.*?)</strong>")) { matchResult ->
        matchResult.groupValues[1]
    }
    
    // Remove <em> tags but keep the content
    result = result.replace(Regex("<em>(.*?)</em>")) { matchResult ->
        matchResult.groupValues[1]
    }
    
    return result
}

// Data classes
data class NewsDetailUiState(
    val isLoading: Boolean = false,
    val news: ESGNews? = null,
    val isBookmarked: Boolean = false,
    val error: String? = null
)
