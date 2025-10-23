package com.ignitech.esgcompanion.presentation.screen.student

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.ignitech.esgcompanion.R
import com.ignitech.esgcompanion.domain.entity.ESGPillar
import com.ignitech.esgcompanion.domain.entity.ESGPillarInfo
import com.ignitech.esgcompanion.presentation.screen.LearningHubScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StudentHomeScreen(
    navController: NavController
) {
    var selectedTab by remember { mutableStateOf(0) }
    
    Scaffold(
        bottomBar = {
            NavigationBar(
                containerColor = Color.Transparent,
                tonalElevation = 0.dp
            ) {
                NavigationBarItem(
                    icon = { 
                        Icon(
                            Icons.Default.Home, 
                            contentDescription = "Home",
                            tint = if (selectedTab == 0) colorResource(id = R.color.interactive_primary) else MaterialTheme.colorScheme.onSurfaceVariant
                        ) 
                    },
                    label = { 
                        Text(
                            "Home",
                            color = if (selectedTab == 0) colorResource(id = R.color.interactive_primary) else MaterialTheme.colorScheme.onSurfaceVariant,
                            fontSize = 12.sp
                        ) 
                    },
                    selected = selectedTab == 0,
                    onClick = { selectedTab = 0 },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = colorResource(id = R.color.interactive_primary),
                        selectedTextColor = colorResource(id = R.color.interactive_primary),
                        unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                        unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
                        indicatorColor = colorResource(id = R.color.background_surface)
                    )
                )
                NavigationBarItem(
                    icon = { 
                        Icon(
                            Icons.Default.School, 
                            contentDescription = "Learning",
                            tint = if (selectedTab == 1) colorResource(id = R.color.interactive_primary) else MaterialTheme.colorScheme.onSurfaceVariant
                        ) 
                    },
                    label = { 
                        Text(
                            "Learning",
                            color = if (selectedTab == 1) colorResource(id = R.color.interactive_primary) else MaterialTheme.colorScheme.onSurfaceVariant,
                            fontSize = 12.sp
                        ) 
                    },
                    selected = selectedTab == 1,
                    onClick = { selectedTab = 1 },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = colorResource(id = R.color.interactive_primary),
                        selectedTextColor = colorResource(id = R.color.interactive_primary),
                        unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                        unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
                        indicatorColor = colorResource(id = R.color.background_surface)
                    )
                )
                NavigationBarItem(
                    icon = { 
                        Icon(
                            Icons.Default.Assignment, 
                            contentDescription = "Assignments",
                            tint = if (selectedTab == 2) colorResource(id = R.color.interactive_primary) else MaterialTheme.colorScheme.onSurfaceVariant
                        ) 
                    },
                    label = { 
                        Text(
                            "Assignments",
                            color = if (selectedTab == 2) colorResource(id = R.color.interactive_primary) else MaterialTheme.colorScheme.onSurfaceVariant,
                            fontSize = 12.sp
                        ) 
                    },
                    selected = selectedTab == 2,
                    onClick = { selectedTab = 2 },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = colorResource(id = R.color.interactive_primary),
                        selectedTextColor = colorResource(id = R.color.interactive_primary),
                        unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                        unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
                        indicatorColor = colorResource(id = R.color.background_surface)
                    )
                )
                NavigationBarItem(
                    icon = { 
                        Icon(
                            Icons.Default.Assessment, 
                            contentDescription = "Assessment",
                            tint = if (selectedTab == 3) colorResource(id = R.color.interactive_primary) else MaterialTheme.colorScheme.onSurfaceVariant
                        ) 
                    },
                    label = { 
                        Text(
                            "Assessment",
                            color = if (selectedTab == 3) colorResource(id = R.color.interactive_primary) else MaterialTheme.colorScheme.onSurfaceVariant,
                            fontSize = 12.sp
                        ) 
                    },
                    selected = selectedTab == 3,
                    onClick = { selectedTab = 3 },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = colorResource(id = R.color.interactive_primary),
                        selectedTextColor = colorResource(id = R.color.interactive_primary),
                        unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                        unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
                        indicatorColor = colorResource(id = R.color.background_surface)
                    )
                )
            }
        }
    ) { paddingValues ->
        when (selectedTab) {
            0 -> StudentHomeContent(navController = navController, modifier = Modifier.padding(paddingValues))
            1 -> StudentLearningScreen(modifier = Modifier.padding(paddingValues))
            2 -> StudentAssignmentsScreen(modifier = Modifier.padding(paddingValues), navController = navController)
            3 -> StudentAssessmentScreen(navController = navController, modifier = Modifier.padding(paddingValues))
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StudentHomeContent(
    navController: NavController,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        // Header
        TopAppBar(
            title = { 
                Column {
                    Text(
                        text = "ESG Companion",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Student",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            },
            actions = {
                IconButton(onClick = { navController.navigate("settings") }) {
                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .background(
                                color = colorResource(id = R.color.interactive_primary),
                                shape = androidx.compose.foundation.shape.CircleShape
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "SV",
                            style = MaterialTheme.typography.bodySmall,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }
                }
            }
        )
        
        // Content
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Learning Progress
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(
                            width = 1.dp,
                            color = colorResource(id = R.color.border_card),
                            shape = MaterialTheme.shapes.medium
                        ),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.Transparent
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(20.dp)
                    ) {
                        Text(
                            text = "Learning Progress",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "72%",
                                style = MaterialTheme.typography.headlineLarge,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary
                            )
                            Text(
                                text = "18/25 lessons",
                                style = MaterialTheme.typography.titleMedium,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                        
                        Spacer(modifier = Modifier.height(8.dp))
                        LinearProgressIndicator(
                            progress = 0.72f,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            }
            
            // ESG Knowledge Areas
            item {
                Text(
                    text = "ESG Knowledge Areas",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
            }
            
            item {
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(ESGPillarInfo.ALL_PILLARS) { pillarInfo ->
                        KnowledgeAreaCard(
                            pillarInfo = pillarInfo,
                            progress = when (pillarInfo.pillar) {
                                ESGPillar.ENVIRONMENTAL -> 0.8f
                                ESGPillar.SOCIAL -> 0.6f
                                ESGPillar.GOVERNANCE -> 0.7f
                            }
                        )
                    }
                }
            }
            
            // Quick Actions
            item {
                Text(
                    text = "Quick Actions",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
            }
            
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    StudentQuickActionCard(
                        title = "Continue Learning",
                        onClick = { /* Continue learning */ },
                        modifier = Modifier.weight(1f)
                    )
                    StudentQuickActionCard(
                        title = "Do Assignments",
                        onClick = { /* Do assignment */ },
                        modifier = Modifier.weight(1f)
                    )
                }
            }
            
            // Recent Learning
            item {
                Text(
                    text = "Recent Learning",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
            }
            
            items(3) { index ->
                LearningCard(
                    title = when (index) {
                        0 -> "Understanding Climate Change"
                        1 -> "Human Rights in Business"
                        2 -> "Transparent Corporate Governance"
                        else -> "Other Lesson"
                    },
                    progress = when (index) {
                        0 -> 1.0f
                        1 -> 0.8f
                        2 -> 0.5f
                        else -> 0.0f
                    },
                    time = when (index) {
                        0 -> "Completed yesterday"
                        1 -> "In progress"
                        2 -> "Started today"
                        else -> "Other"
                    }
                )
            }
            
            // Achievements
            item {
                Text(
                    text = "Thành Tích",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
            }
            
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    AchievementCard(
                        title = "Học sinh chăm",
                        description = "Hoàn thành 15 bài học",
                        modifier = Modifier.weight(1f)
                    )
                    AchievementCard(
                        title = "Người học tích cực",
                        description = "Tham gia 8 hoạt động",
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }
    }
}



@Composable
fun KnowledgeAreaCard(
    pillarInfo: ESGPillarInfo,
    progress: Float
) {
    Card(
        modifier = Modifier
            .width(160.dp)
            .border(
                width = 1.dp,
                color = colorResource(id = R.color.border_card),
                shape = MaterialTheme.shapes.medium
            ),
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = pillarInfo.name,
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                color = colorResource(id = R.color.interactive_primary),
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = "${(progress * 100).toInt()}%",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = colorResource(id = R.color.interactive_primary),
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            LinearProgressIndicator(
                progress = progress,
                modifier = Modifier.fillMaxWidth(),
                color = colorResource(id = R.color.interactive_primary),
                trackColor = colorResource(id = R.color.border_light)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = when (pillarInfo.pillar) {
                    ESGPillar.ENVIRONMENTAL -> "Môi trường"
                    ESGPillar.SOCIAL -> "Xã hội"
                    ESGPillar.GOVERNANCE -> "Quản trị"
                },
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
fun LearningCard(
    title: String,
    progress: Float,
    time: String
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .border(
                width = 1.dp,
                color = colorResource(id = R.color.border_card),
                shape = MaterialTheme.shapes.medium
            ),
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.weight(1f)
                )
                Text(
                    text = "${(progress * 100).toInt()}%",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.primary
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            LinearProgressIndicator(
                progress = progress,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = time,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
fun AchievementCard(
    title: String,
    description: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .border(
                width = 1.dp,
                color = colorResource(id = R.color.border_card),
                shape = MaterialTheme.shapes.medium
            ),
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleSmall,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                color = colorResource(id = R.color.interactive_primary),
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = description,
                style = MaterialTheme.typography.bodySmall,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StudentQuickActionCard(
    title: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .border(
                width = 1.dp,
                color = colorResource(id = R.color.border_card),
                shape = MaterialTheme.shapes.medium
            ),
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent
        ),
        onClick = onClick
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}
