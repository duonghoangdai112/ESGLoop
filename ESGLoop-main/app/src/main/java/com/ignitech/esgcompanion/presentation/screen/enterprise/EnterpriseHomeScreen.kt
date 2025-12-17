package com.ignitech.esgcompanion.presentation.screen.enterprise

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.compose.ui.res.colorResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ignitech.esgcompanion.domain.entity.ESGPillar
import com.ignitech.esgcompanion.domain.entity.ESGPillarInfo
import com.ignitech.esgcompanion.domain.entity.*
import com.ignitech.esgcompanion.ui.theme.*
import com.ignitech.esgcompanion.R
import com.ignitech.esgcompanion.utils.AppColors
import com.ignitech.esgcompanion.presentation.screen.LearningHubScreen
import com.ignitech.esgcompanion.presentation.screen.enterprise.EnterpriseTrackerScreen
import com.ignitech.esgcompanion.presentation.screen.enterprise.EnterpriseExpertScreen
import com.ignitech.esgcompanion.presentation.viewmodel.EnterpriseHomeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EnterpriseHomeScreen(
    navController: NavHostController
) {
    val colors = AppColors()
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
                            Icons.Default.Assessment, 
                            contentDescription = "Assessment",
                            tint = if (selectedTab == 1) colorResource(id = R.color.interactive_primary) else MaterialTheme.colorScheme.onSurfaceVariant
                        ) 
                    },
                    label = { 
                        Text(
                            "Assessment",
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
                            Icons.Default.School, 
                            contentDescription = "Learning",
                            tint = if (selectedTab == 2) colorResource(id = R.color.interactive_primary) else MaterialTheme.colorScheme.onSurfaceVariant
                        ) 
                    },
                    label = { 
                        Text(
                            "Learning",
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
                            Icons.Default.Timeline, 
                            contentDescription = "Tracker",
                            tint = if (selectedTab == 3) colorResource(id = R.color.interactive_primary) else MaterialTheme.colorScheme.onSurfaceVariant
                        ) 
                    },
                    label = { 
                        Text(
                            "Tracker",
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
                NavigationBarItem(
                    icon = { 
                        Icon(
                            Icons.Default.Person, 
                            contentDescription = "Expert",
                            tint = if (selectedTab == 4) colorResource(id = R.color.interactive_primary) else MaterialTheme.colorScheme.onSurfaceVariant
                        ) 
                    },
                    label = { 
                        Text(
                            "Expert",
                            color = if (selectedTab == 4) colorResource(id = R.color.interactive_primary) else MaterialTheme.colorScheme.onSurfaceVariant,
                            fontSize = 12.sp
                        ) 
                    },
                    selected = selectedTab == 4,
                    onClick = { selectedTab = 4 },
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
            0 -> HomeContent(navController = navController, modifier = Modifier.padding(paddingValues))
            1 -> EnterpriseAssessmentScreen(navController = navController, modifier = Modifier.padding(paddingValues))
            2 -> LearningHubScreen(modifier = Modifier.padding(paddingValues))
            3 -> EnterpriseTrackerScreen(navController = navController, modifier = Modifier.padding(paddingValues))
            4 -> EnterpriseExpertScreen(navController = navController, modifier = Modifier.padding(paddingValues))
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeContent(
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: EnterpriseHomeViewModel = hiltViewModel()
) {
    val colors = AppColors()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    
    LaunchedEffect(Unit) {
        viewModel.loadESGData()
    }
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
                        text = "Green Construction Co., Ltd.",
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
                            text = "DN",
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
        // ESG Score Overview
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
                        text = "ESG Score Overview",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "${uiState.esgScoreData.overallScore}/${uiState.esgScoreData.maxScore}",
                            style = MaterialTheme.typography.headlineLarge,
                            fontWeight = FontWeight.Bold,
                            color = colorResource(id = R.color.interactive_primary)
                        )
                        StatusTag(
                            text = when {
                                uiState.esgScoreData.overallScore >= 90 -> "Excellent"
                                uiState.esgScoreData.overallScore >= 80 -> "Good"
                                uiState.esgScoreData.overallScore >= 70 -> "Fair"
                                uiState.esgScoreData.overallScore >= 60 -> "Average"
                                else -> "Needs Improvement"
                            },
                            color = when {
                                uiState.esgScoreData.overallScore >= 90 -> colorResource(id = R.color.esg_success)
                                uiState.esgScoreData.overallScore >= 80 -> Color(0xFF4CAF50)
                                uiState.esgScoreData.overallScore >= 70 -> Color(0xFFFF9800)
                                uiState.esgScoreData.overallScore >= 60 -> Color(0xFFFF5722)
                                else -> Color(0xFFF44336)
                            }
                        )
                    }
                    
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = uiState.esgScoreData.lastUpdated,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
        
        // ESG Pillars
        item {
            Text(
                text = "3 ESG Pillars",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
        }
        
        item {
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(ESGPillarInfo.ALL_PILLARS) { pillarInfo ->
                    EnterpriseKnowledgeAreaCard(
                        pillarInfo = pillarInfo,
                        score = when (pillarInfo.pillar) {
                            ESGPillar.ENVIRONMENTAL -> uiState.esgScoreData.environmentalScore
                            ESGPillar.SOCIAL -> uiState.esgScoreData.socialScore
                            ESGPillar.GOVERNANCE -> uiState.esgScoreData.governanceScore
                        }
                    )
                }
            }
        }
        
        // AI ESG Assistant Card
        item {
            AIAssistantCard(
                onClick = { navController.navigate("ai_assistant") }
            )
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
                EnterpriseQuickActionCard(
                    title = "New Assessment",
                    onClick = { 
                        // Navigate to assessment with proper navigation stack
                        navController.navigate("enterprise_assessment") {
                            popUpTo("enterprise_home") { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    modifier = Modifier.weight(1f)
                )
                EnterpriseQuickActionCard(
                    title = "Create Report",
                    onClick = { /* Generate report */ },
                    modifier = Modifier.weight(1f)
                )
            }
        }
        
        // Recent Activities
        item {
            Text(
                text = "Recent Activities",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
        }
        
        items(3) { index ->
            EnterpriseActivityCard(
                title = when (index) {
                    0 -> "Completed ESG assessment Q2 2025"
                    1 -> "Created sustainability report"
                    2 -> "Updated environmental policy"
                    else -> "Other activity"
                },
                time = when (index) {
                    0 -> "3 days ago"
                    1 -> "1 week ago"
                    2 -> "2 weeks ago"
                    else -> "Other"
                }
            )
        }
        
        // Loading state
        if (uiState.isLoading) {
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
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(24.dp),
                            color = colorResource(id = R.color.interactive_primary)
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            text = "Loading ESG data...",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }
        
        // Error state
        uiState.error?.let { error ->
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(
                            width = 1.dp,
                            color = Color(0xFFD32F2F),
                            shape = MaterialTheme.shapes.medium
                        ),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.Transparent
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            Icons.Default.Error,
                            contentDescription = "Error",
                            tint = Color(0xFFD32F2F),
                            modifier = Modifier.size(32.dp)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Error loading data",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFFD32F2F)
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = error,
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color(0xFFD32F2F),
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Button(
                            onClick = { viewModel.refreshData() },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFFD32F2F)
                            )
                        ) {
                            Text("Retry", color = Color.White)
                        }
                    }
                }
            }
        }
        }
    }
}


@Composable
fun StatusTag(
    text: String,
    color: Color
) {
    Surface(
        modifier = Modifier,
        shape = androidx.compose.foundation.shape.RoundedCornerShape(12.dp),
        color = color
    ) {
        Text(
            text = text,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
            style = MaterialTheme.typography.bodySmall,
            color = Color.White,
            fontWeight = FontWeight.Medium
        )
    }
}

@Composable
fun LearningContent(modifier: Modifier = Modifier) {
    val colors = AppColors()
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Text(
                text = "ESG Learning",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
        }
        
        item {
            Card(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = "Basic ESG Course",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Learn about ESG principles and how to apply them in enterprises",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    LinearProgressIndicator(
                        progress = 0.65f,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "65% completed",
                        style = MaterialTheme.typography.bodySmall,
                        color = colors.primary
                    )
                }
            }
        }
    }
}



@Composable
fun EnterpriseKnowledgeAreaCard(
    pillarInfo: ESGPillarInfo,
    score: Int
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
                text = "$score/100",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = colorResource(id = R.color.interactive_primary),
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            LinearProgressIndicator(
                progress = score / 100f,
                modifier = Modifier.fillMaxWidth(),
                color = colorResource(id = R.color.interactive_primary),
                trackColor = colorResource(id = R.color.border_light)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = when (pillarInfo.pillar) {
                    ESGPillar.ENVIRONMENTAL -> "Environmental"
                    ESGPillar.SOCIAL -> "Social"
                    ESGPillar.GOVERNANCE -> "Governance"
                },
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EnterpriseQuickActionCard(
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

@Composable
fun EnterpriseActivityCard(
    title: String,
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
            Text(
                text = title,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = time,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
fun AIAssistantCard(
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
        shape = androidx.compose.foundation.shape.RoundedCornerShape(16.dp),
        border = androidx.compose.foundation.BorderStroke(
            width = 2.dp,
            color = colorResource(id = R.color.interactive_primary)
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    colorResource(id = R.color.interactive_primary).copy(alpha = 0.05f)
                )
                .padding(20.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.weight(1f)
            ) {
                // AI Icon
                Box(
                    modifier = Modifier
                        .size(56.dp)
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
                        modifier = Modifier.size(32.dp)
                    )
                }
                
                // Content
                Column(
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = "AI ESG Assistant",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Surface(
                            color = colorResource(id = R.color.esg_warning),
                            shape = androidx.compose.foundation.shape.RoundedCornerShape(8.dp)
                        ) {
                            Text(
                                text = "NEW",
                                style = MaterialTheme.typography.bodySmall,
                                fontWeight = FontWeight.Bold,
                                color = Color.White,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                            )
                        }
                    }
                    
                    Text(
                        text = "Receive intelligent analysis & suggestions based on your ESG data",
                        style = MaterialTheme.typography.bodyMedium,
                        color = colorResource(id = R.color.text_secondary)
                    )
                    
                    Spacer(modifier = Modifier.height(4.dp))
                    
                    // Quick insights preview
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.TipsAndUpdates,
                            contentDescription = null,
                            tint = colorResource(id = R.color.interactive_primary),
                            modifier = Modifier.size(16.dp)
                        )
                        Text(
                            text = "3 new suggestions to improve ESG score",
                            style = MaterialTheme.typography.bodySmall,
                            fontWeight = FontWeight.Medium,
                            color = colorResource(id = R.color.interactive_primary)
                        )
                    }
                }
            }
            
            // Arrow icon
            Icon(
                imageVector = Icons.Default.ChevronRight,
                contentDescription = null,
                tint = colorResource(id = R.color.interactive_primary),
                modifier = Modifier.size(32.dp)
            )
        }
    }
}