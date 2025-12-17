package com.ignitech.esgcompanion.presentation.screen.enterprise

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.ignitech.esgcompanion.R
import com.ignitech.esgcompanion.presentation.viewmodel.AIAssistantViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AIAssistantScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: AIAssistantViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val listState = rememberLazyListState()
    
    LaunchedEffect(Unit) {
        viewModel.loadInitialInsights()
    }
    
    // Auto-scroll to bottom when new message arrives
    LaunchedEffect(uiState.messages.size) {
        if (uiState.messages.isNotEmpty()) {
            listState.animateScrollToItem(uiState.messages.size - 1)
        }
    }

    Column(
        modifier = modifier.fillMaxSize()
    ) {
        // Header
        TopAppBar(
            title = {
                Column {
                    Text(
                        text = "AI ESG Assistant",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Your intelligent assistant",
                        style = MaterialTheme.typography.bodyMedium,
                        color = colorResource(id = R.color.text_secondary)
                    )
                }
            },
            navigationIcon = {
                IconButton(onClick = { navController.navigateUp() }) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back"
                    )
                }
            }
        )

        // Messages
        LazyColumn(
            state = listState,
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(uiState.messages) { message ->
                if (message.isFromAI) {
                    AIMessageBubble(message = message)
                } else {
                    UserMessageBubble(message = message)
                }
            }
            
            if (uiState.isLoading) {
                item {
                    AITypingIndicator()
                }
            }
        }

        // Input Area
        Surface(
            color = Color.White,
            shadowElevation = 8.dp
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                // Quick suggestion chips
                if (uiState.messages.isEmpty()) {
                    Text(
                        text = "Suggested questions:",
                        style = MaterialTheme.typography.bodySmall,
                        fontWeight = FontWeight.Medium,
                        color = colorResource(id = R.color.text_secondary)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        SuggestionChip(
                            text = "Analyze ESG Score",
                            onClick = { viewModel.sendSuggestion("Analyze my ESG score") }
                        )
                        SuggestionChip(
                            text = "Improvement Suggestions",
                            onClick = { viewModel.sendSuggestion("Provide suggestions to improve ESG score") }
                        )
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                }
                
                // Input field
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OutlinedTextField(
                        value = uiState.inputText,
                        onValueChange = viewModel::updateInputText,
                        modifier = Modifier.weight(1f),
                        placeholder = {
                            Text(
                                "Enter your question...",
                                color = colorResource(id = R.color.text_hint)
                            )
                        },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = colorResource(id = R.color.interactive_primary),
                            unfocusedBorderColor = colorResource(id = R.color.border_card)
                        ),
                        shape = RoundedCornerShape(24.dp),
                        maxLines = 3
                    )
                    
                    IconButton(
                        onClick = { viewModel.sendMessage() },
                        enabled = uiState.inputText.isNotBlank() && !uiState.isLoading
                    ) {
                        Box(
                            modifier = Modifier
                                .size(48.dp)
                                .background(
                                    color = if (uiState.inputText.isNotBlank())
                                        colorResource(id = R.color.interactive_primary)
                                    else
                                        colorResource(id = R.color.interactive_disabled),
                                    shape = androidx.compose.foundation.shape.CircleShape
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Send,
                                contentDescription = "Send",
                                tint = Color.White
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun AIMessageBubble(message: ChatMessage) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Start
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(0.85f)
        ) {
            // AI Avatar
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(32.dp)
                        .background(
                            color = colorResource(id = R.color.interactive_primary),
                            shape = androidx.compose.foundation.shape.CircleShape
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.AutoAwesome,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(20.dp)
                    )
                }
                Text(
                    text = "AI Assistant",
                    style = MaterialTheme.typography.bodySmall,
                    fontWeight = FontWeight.Bold,
                    color = colorResource(id = R.color.interactive_primary)
                )
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            
            // Message bubble
            Surface(
                color = colorResource(id = R.color.interactive_primary).copy(alpha = 0.1f),
                shape = RoundedCornerShape(
                    topStart = 4.dp,
                    topEnd = 16.dp,
                    bottomStart = 16.dp,
                    bottomEnd = 16.dp
                ),
                border = androidx.compose.foundation.BorderStroke(
                    width = 1.dp,
                    color = colorResource(id = R.color.interactive_primary).copy(alpha = 0.3f)
                )
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = message.content,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    
                    // Recommendations if available
                    message.recommendations?.let { recommendations ->
                        Spacer(modifier = Modifier.height(12.dp))
                        recommendations.forEach { rec ->
                            RecommendationItem(recommendation = rec)
                            Spacer(modifier = Modifier.height(8.dp))
                        }
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(4.dp))
            
            Text(
                text = message.timestamp,
                style = MaterialTheme.typography.bodySmall,
                color = colorResource(id = R.color.text_secondary)
            )
        }
    }
}

@Composable
fun UserMessageBubble(message: ChatMessage) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.End
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(0.85f),
            horizontalAlignment = Alignment.End
        ) {
            Surface(
                color = colorResource(id = R.color.interactive_primary),
                shape = RoundedCornerShape(
                    topStart = 16.dp,
                    topEnd = 16.dp,
                    bottomStart = 16.dp,
                    bottomEnd = 4.dp
                )
            ) {
                Text(
                    text = message.content,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.White,
                    modifier = Modifier.padding(16.dp)
                )
            }
            
            Spacer(modifier = Modifier.height(4.dp))
            
            Text(
                text = message.timestamp,
                style = MaterialTheme.typography.bodySmall,
                color = colorResource(id = R.color.text_secondary)
            )
        }
    }
}

@Composable
fun RecommendationItem(recommendation: AIRecommendation) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        when (recommendation.type) {
            RecommendationType.STRENGTH -> Icon(
                imageVector = Icons.Default.CheckCircle,
                contentDescription = null,
                tint = colorResource(id = R.color.interactive_primary),
                modifier = Modifier.size(20.dp)
            )
            RecommendationType.WARNING -> Icon(
                imageVector = Icons.Default.Warning,
                contentDescription = null,
                tint = colorResource(id = R.color.esg_warning),
                modifier = Modifier.size(20.dp)
            )
            RecommendationType.SUGGESTION -> Icon(
                imageVector = Icons.Default.TipsAndUpdates,
                contentDescription = null,
                tint = colorResource(id = R.color.interactive_primary),
                modifier = Modifier.size(20.dp)
            )
        }
        
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = recommendation.title,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = recommendation.description,
                style = MaterialTheme.typography.bodySmall,
                color = colorResource(id = R.color.text_secondary)
            )
        }
    }
}

@Composable
fun AITypingIndicator() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Start
    ) {
        Surface(
            color = colorResource(id = R.color.interactive_primary).copy(alpha = 0.1f),
            shape = RoundedCornerShape(16.dp),
            border = androidx.compose.foundation.BorderStroke(
                width = 1.dp,
                color = colorResource(id = R.color.interactive_primary).copy(alpha = 0.3f)
            )
        ) {
            Row(
                modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                CircularProgressIndicator(
                    modifier = Modifier.size(16.dp),
                    color = colorResource(id = R.color.interactive_primary),
                    strokeWidth = 2.dp
                )
                Text(
                    text = "AI is analyzing...",
                    style = MaterialTheme.typography.bodyMedium,
                    color = colorResource(id = R.color.interactive_primary),
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}

@Composable
fun SuggestionChip(
    text: String,
    onClick: () -> Unit
) {
    Surface(
        onClick = onClick,
        color = Color.Transparent,
        shape = RoundedCornerShape(16.dp),
        border = androidx.compose.foundation.BorderStroke(
            width = 1.dp,
            color = colorResource(id = R.color.border_card)
        )
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
        )
    }
}

// Data classes
data class ChatMessage(
    val id: String,
    val content: String,
    val isFromAI: Boolean,
    val timestamp: String,
    val recommendations: List<AIRecommendation>? = null
)

data class AIRecommendation(
    val type: RecommendationType,
    val title: String,
    val description: String
)

enum class RecommendationType {
    STRENGTH,
    WARNING,
    SUGGESTION
}

