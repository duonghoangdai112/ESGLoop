package com.ignitech.esgcompanion.presentation.screen.expert

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ignitech.esgcompanion.R
import com.ignitech.esgcompanion.domain.entity.ESGPillar
import com.ignitech.esgcompanion.presentation.viewmodel.ExpertCommunityTabViewModel
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpertCommunityTabScreen(
    modifier: Modifier = Modifier,
    viewModel: ExpertCommunityTabViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.loadCommunityData()
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Header Section
        item {
            Column {
                Text(
                    text = "ESG Community",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = "Connect and share knowledge with expert community",
                    style = MaterialTheme.typography.bodyMedium,
                    color = colorResource(id = R.color.text_secondary)
                )
            }
        }

        // Quick Stats
        item {
            ExpertCommunityStatsSection(
                totalMembers = uiState.totalMembers,
                activeDiscussions = uiState.activeDiscussions,
                expertContributions = uiState.expertContributions,
                myPosts = uiState.myPosts
            )
        }

        // Quick Actions
        item {
            Text(
                text = "Quick Actions",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
        }

        item {
            Column(
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                uiState.quickActions.forEach { action ->
                    CommunityQuickActionButton(
                        title = action.title,
                        onClick = { viewModel.performQuickAction(action.id) }
                    )
                }
            }
        }

        // Featured Discussions
        if (uiState.featuredDiscussions.isNotEmpty()) {
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Featured Discussions",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    
                    TextButton(onClick = { viewModel.viewAllDiscussions() }) {
                        Text("View All")
                        Icon(Icons.Default.ArrowForward, contentDescription = null)
                    }
                }
            }
            
            items(uiState.featuredDiscussions) { discussion ->
                FeaturedDiscussionCard(
                    discussion = discussion,
                    onClick = { viewModel.openDiscussion(discussion.id) },
                    onLike = { viewModel.likeDiscussion(discussion.id) },
                    onComment = { viewModel.commentOnDiscussion(discussion.id) }
                )
            }
        }

        // Recent Discussions
        if (uiState.recentDiscussions.isNotEmpty()) {
            item {
                Text(
                    text = "Recent Discussions",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
            
            items(uiState.recentDiscussions) { discussion ->
                DiscussionCard(
                    discussion = discussion,
                    onClick = { viewModel.openDiscussion(discussion.id) },
                    onLike = { viewModel.likeDiscussion(discussion.id) },
                    onComment = { viewModel.commentOnDiscussion(discussion.id) }
                )
            }
        }

        // Expert Spotlight
        if (uiState.expertSpotlight.isNotEmpty()) {
            item {
                Text(
                    text = "Featured Experts",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
            
            item {
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    contentPadding = PaddingValues(horizontal = 4.dp)
                ) {
                    items(uiState.expertSpotlight) { expert ->
                        ExpertSpotlightCard(
                            expert = expert,
                            onClick = { viewModel.viewExpertProfile(expert.id) }
                        )
                    }
                }
            }
        }

        // Events and Webinars
        if (uiState.upcomingEvents.isNotEmpty()) {
            item {
                Text(
                    text = "Upcoming Events",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
            
            items(uiState.upcomingEvents) { event ->
                EventCard(
                    event = event,
                    onClick = { viewModel.openEvent(event.id) },
                    onRegister = { viewModel.registerForEvent(event.id) }
                )
            }
        }
    }
}

@Composable
fun ExpertCommunityStatsSection(
    totalMembers: Int,
    activeDiscussions: Int,
    expertContributions: Int,
    myPosts: Int
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Row 1: 2 cards
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            CommunityStatCard(
                title = "Members",
                value = totalMembers.toString(),
                modifier = Modifier.weight(1f)
            )
            CommunityStatCard(
                title = "Discussions",
                value = activeDiscussions.toString(),
                modifier = Modifier.weight(1f)
            )
        }
        
        // Row 2: 2 cards
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            CommunityStatCard(
                title = "Contributions",
                value = expertContributions.toString(),
                modifier = Modifier.weight(1f)
            )
            CommunityStatCard(
                title = "My Posts",
                value = myPosts.toString(),
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
fun CommunityStatCard(
    title: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        shape = RoundedCornerShape(12.dp),
        border = androidx.compose.foundation.BorderStroke(
            width = 1.dp,
            color = colorResource(id = R.color.border_card)
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = value,
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = colorResource(id = R.color.interactive_primary)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = title,
                style = MaterialTheme.typography.bodyMedium,
                color = colorResource(id = R.color.text_secondary)
            )
        }
    }
}

@Composable
fun CommunityQuickActionButton(
    title: String,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent
        ),
        elevation = ButtonDefaults.buttonElevation(
            defaultElevation = 0.dp,
            pressedElevation = 0.dp
        ),
        shape = RoundedCornerShape(12.dp),
        border = androidx.compose.foundation.BorderStroke(
            width = 1.dp,
            color = colorResource(id = R.color.border_card)
        ),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp)
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}

@Composable
fun FeaturedDiscussionCard(
    discussion: CommunityDiscussion,
    onClick: () -> Unit,
    onLike: () -> Unit,
    onComment: () -> Unit
) {
    val dateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
    
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
        border = androidx.compose.foundation.BorderStroke(
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
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Surface(
                        color = colorResource(id = R.color.interactive_primary),
                        shape = RoundedCornerShape(20.dp),
                        modifier = Modifier.size(40.dp)
                    ) {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = discussion.authorName.take(1).uppercase(),
                                style = MaterialTheme.typography.titleMedium,
                                color = Color.White,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                    
                    Column {
                        Text(
                            text = discussion.authorName,
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
                    color = colorResource(id = R.color.interactive_primary),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(
                        text = "Featured",
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.White
                    )
                }
            }
            
            Text(
                text = discussion.title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
            
            Text(
                text = discussion.content,
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
                Text(
                    text = dateFormat.format(Date(discussion.createdAt)),
                    style = MaterialTheme.typography.bodySmall,
                    color = colorResource(id = R.color.text_secondary)
                )
                
                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(2.dp)
                    ) {
                        IconButton(onClick = onLike) {
                            Icon(
                                Icons.Default.ThumbUp,
                                contentDescription = "Like",
                                tint = if (discussion.isLiked) colorResource(id = R.color.interactive_primary) else colorResource(id = R.color.text_secondary),
                                modifier = Modifier.size(20.dp)
                            )
                        }
                        Text(
                            text = discussion.likeCount.toString(),
                            style = MaterialTheme.typography.bodySmall,
                            color = colorResource(id = R.color.text_secondary)
                        )
                    }
                    
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(2.dp)
                    ) {
                        IconButton(onClick = onComment) {
                            Icon(
                                Icons.Default.Comment,
                                contentDescription = "Comment",
                                tint = colorResource(id = R.color.text_secondary),
                                modifier = Modifier.size(20.dp)
                            )
                        }
                        Text(
                            text = discussion.commentCount.toString(),
                            style = MaterialTheme.typography.bodySmall,
                            color = colorResource(id = R.color.text_secondary)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun DiscussionCard(
    discussion: CommunityDiscussion,
    onClick: () -> Unit,
    onLike: () -> Unit,
    onComment: () -> Unit
) {
    val dateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
    
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
        border = androidx.compose.foundation.BorderStroke(
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
                                text = discussion.authorName.take(1).uppercase(),
                                style = MaterialTheme.typography.titleMedium,
                                color = colorResource(id = R.color.interactive_primary),
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                    
                    Column {
                        Text(
                            text = discussion.authorName,
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
                
                Text(
                    text = dateFormat.format(Date(discussion.createdAt)),
                    style = MaterialTheme.typography.bodySmall,
                    color = colorResource(id = R.color.text_secondary)
                )
            }
            
            Text(
                text = discussion.title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
            
            Text(
                text = discussion.content,
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
                ExpertPillarChip(pillar = discussion.pillar)
                
                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(2.dp)
                    ) {
                        IconButton(onClick = onLike) {
                            Icon(
                                Icons.Default.ThumbUp,
                                contentDescription = "Like",
                                tint = if (discussion.isLiked) colorResource(id = R.color.interactive_primary) else colorResource(id = R.color.text_secondary),
                                modifier = Modifier.size(20.dp)
                            )
                        }
                        Text(
                            text = discussion.likeCount.toString(),
                            style = MaterialTheme.typography.bodySmall,
                            color = colorResource(id = R.color.text_secondary)
                        )
                    }
                    
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(2.dp)
                    ) {
                        IconButton(onClick = onComment) {
                            Icon(
                                Icons.Default.Comment,
                                contentDescription = "Comment",
                                tint = colorResource(id = R.color.text_secondary),
                                modifier = Modifier.size(20.dp)
                            )
                        }
                        Text(
                            text = discussion.commentCount.toString(),
                            style = MaterialTheme.typography.bodySmall,
                            color = colorResource(id = R.color.text_secondary)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ExpertSpotlightCard(
    expert: ExpertProfile,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .width(150.dp)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) { onClick() },
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        shape = RoundedCornerShape(16.dp),
        border = androidx.compose.foundation.BorderStroke(
            width = 1.dp,
            color = colorResource(id = R.color.border_card)
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Surface(
                color = colorResource(id = R.color.interactive_primary).copy(alpha = 0.1f),
                shape = RoundedCornerShape(30.dp),
                modifier = Modifier.size(60.dp)
            ) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = expert.name.take(1).uppercase(),
                        style = MaterialTheme.typography.headlineSmall,
                        color = colorResource(id = R.color.interactive_primary),
                        fontWeight = FontWeight.Bold
                    )
                }
            }
            
            Text(
                text = expert.name,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface,
                textAlign = androidx.compose.ui.text.style.TextAlign.Center
            )
            
            Text(
                text = expert.specialization,
                style = MaterialTheme.typography.bodySmall,
                color = colorResource(id = R.color.text_secondary),
                textAlign = androidx.compose.ui.text.style.TextAlign.Center
            )
            
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Icon(
                    Icons.Default.Star,
                    contentDescription = null,
                    tint = Color(0xFFFF9800),
                    modifier = Modifier.size(16.dp)
                )
                Text(
                    text = String.format("%.1f", expert.rating),
                    style = MaterialTheme.typography.bodySmall,
                    color = colorResource(id = R.color.text_secondary)
                )
            }
        }
    }
}

@Composable
fun EventCard(
    event: CommunityEvent,
    onClick: () -> Unit,
    onRegister: () -> Unit
) {
    val dateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
    
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
        border = androidx.compose.foundation.BorderStroke(
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
                Text(
                    text = event.title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.weight(1f)
                )
                
                EventTypeChip(type = event.type)
            }
            
            Text(
                text = event.description,
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
                    Icon(
                        Icons.Default.Schedule,
                        contentDescription = null,
                        tint = colorResource(id = R.color.text_secondary),
                        modifier = Modifier.size(16.dp)
                    )
                    Text(
                        text = dateFormat.format(Date(event.startTime)),
                        style = MaterialTheme.typography.bodySmall,
                        color = colorResource(id = R.color.text_secondary)
                    )
                }
                
                Button(
                    onClick = onRegister,
                    colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.interactive_primary))
                ) {
                    Text("Register")
                }
            }
        }
    }
}


@Composable
fun EventTypeChip(type: EventType) {
    val (text, color) = when (type) {
        EventType.WEBINAR -> "Webinar" to Color(0xFF4CAF50)
        EventType.WORKSHOP -> "Workshop" to Color(0xFF2196F3)
        EventType.CONFERENCE -> "Conference" to Color(0xFFFF9800)
        EventType.NETWORKING -> "Networking" to Color(0xFF9C27B0)
    }
    
    Surface(
        color = color.copy(alpha = 0.1f),
        shape = RoundedCornerShape(12.dp)
    ) {
        Text(
            text = text,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
            style = MaterialTheme.typography.bodySmall,
            color = color,
            fontWeight = FontWeight.Medium
        )
    }
}


// Data classes
data class CommunityDiscussion(
    val id: String,
    val title: String,
    val content: String,
    val authorName: String,
    val authorId: String,
    val pillar: ESGPillar,
    val likeCount: Int,
    val commentCount: Int,
    val isLiked: Boolean = false,
    val createdAt: Long,
    val isFeatured: Boolean = false
)

data class CommunityQuickAction(
    val id: String,
    val title: String,
    val icon: androidx.compose.ui.graphics.vector.ImageVector,
    val color: Color
)

data class ExpertProfile(
    val id: String,
    val name: String,
    val specialization: String,
    val rating: Float,
    val contributionCount: Int
)

data class CommunityEvent(
    val id: String,
    val title: String,
    val description: String,
    val type: EventType,
    val startTime: Long,
    val duration: Int, // in minutes
    val isRegistered: Boolean = false
)

enum class EventType {
    WEBINAR, WORKSHOP, CONFERENCE, NETWORKING
}
