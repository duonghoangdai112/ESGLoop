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
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
fun ExpertHomeScreen(
    navController: NavController
) {
    var selectedTab by rememberSaveable { mutableIntStateOf(0) }
    
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
                            Icons.Default.Business, 
                            contentDescription = "Enterprise",
                            tint = if (selectedTab == 2) colorResource(id = R.color.interactive_primary) else MaterialTheme.colorScheme.onSurfaceVariant
                        ) 
                    },
                    label = { 
                        Text(
                            "Enterprise",
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
                            Icons.Default.Group, 
                            contentDescription = "Community",
                            tint = if (selectedTab == 3) colorResource(id = R.color.interactive_primary) else MaterialTheme.colorScheme.onSurfaceVariant
                        ) 
                    },
                    label = { 
                        Text(
                            "Community",
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
                            Icons.Default.Article, 
                            contentDescription = "News",
                            tint = if (selectedTab == 4) colorResource(id = R.color.interactive_primary) else MaterialTheme.colorScheme.onSurfaceVariant
                        ) 
                    },
                    label = { 
                        Text(
                            "News",
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
            0 -> ExpertHomeContent(navController = navController, modifier = Modifier.padding(paddingValues))
            1 -> ExpertLearningContent(navController = navController, modifier = Modifier.padding(paddingValues))
            2 -> ExpertEnterpriseContent(navController = navController, modifier = Modifier.padding(paddingValues))
            3 -> CommunityContent(navController = navController, modifier = Modifier.padding(paddingValues))
            4 -> NewsContent(navController = navController, modifier = Modifier.padding(paddingValues))
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpertHomeContent(
    navController: NavController,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        // Header
        TopAppBar(
            title = { 
                    Text(
                        text = "ESG Companion",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
            },
            actions = {
                Box(
                    modifier = Modifier
                        .padding(end = 16.dp)
                        .size(40.dp)
                        .clip(RoundedCornerShape(20.dp))
                        .background(colorResource(id = R.color.interactive_primary))
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null
                        ) {
                            navController.navigate("settings")
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "CG",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
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
            // Consultation Stats
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
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
                        modifier = Modifier.padding(20.dp)
                    ) {
                        Text(
                            text = "Consulting Statistics",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column {
                                Text(
                                    text = "15",
                                    style = MaterialTheme.typography.headlineLarge,
                                    fontWeight = FontWeight.Bold,
                                    color = colorResource(id = R.color.interactive_primary)
                                )
                                Text(
                                    text = "Enterprises consulted",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = colorResource(id = R.color.text_secondary)
                                )
                            }
                            Column {
                                Text(
                                    text = "4.8/5.0",
                                    style = MaterialTheme.typography.headlineLarge,
                                    fontWeight = FontWeight.Bold,
                                    color = colorResource(id = R.color.interactive_primary)
                                )
                                Text(
                                    text = "Average rating",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = colorResource(id = R.color.text_secondary)
                                )
                            }
                            Column {
                            Text(
                                    text = "2.5M",
                                style = MaterialTheme.typography.headlineLarge,
                                fontWeight = FontWeight.Bold,
                                    color = colorResource(id = R.color.interactive_primary)
                            )
                            Text(
                                    text = "This month's income",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = colorResource(id = R.color.text_secondary)
                                )
                            }
                        }
                    }
                }
            }
            
            // ESG Statistics by Pillar
            item {
                Text(
                    text = "Statistics by Field",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
            
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
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
                        modifier = Modifier.padding(16.dp)
                    ) {
                        // Environmental
                        ExpertPillarStatItem(
                            title = "Environmental",
                            percentage = 45,
                            projectCount = 23,
                            color = Color(0xFF4CAF50)
                        )
                        
                        Spacer(modifier = Modifier.height(16.dp))
                        
                        // Social
                        ExpertPillarStatItem(
                            title = "Social",
                            percentage = 35,
                            projectCount = 18,
                            color = Color(0xFF2196F3)
                        )
                        
                        Spacer(modifier = Modifier.height(16.dp))
                        
                        // Governance
                        ExpertPillarStatItem(
                            title = "Governance",
                            percentage = 20,
                            projectCount = 10,
                            color = Color(0xFFFF9800)
                        )
                    }
                }
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
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    ExpertQuickActionCard(
                        title = "View New Requests",
                        icon = Icons.Default.Notifications,
                        onClick = { /* View new requests */ },
                        modifier = Modifier.weight(1f)
                    )
                    ExpertQuickActionCard(
                        title = "Consultation Schedule",
                        icon = Icons.Default.Schedule,
                        onClick = { /* View schedule */ },
                        modifier = Modifier.weight(1f)
                    )
                }
            }
            
            // Recent Consultations
            item {
                Text(
                    text = "Recent Consulting Projects",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
            
            items(3) { index ->
                ExpertConsultationCard(
                    title = when (index) {
                        0 -> "ESG Consulting for ABC Bank"
                        1 -> "Sustainability Report Assessment XYZ Corp"
                        2 -> "ESG Workshop for 50 Employees"
                        else -> "Other Project"
                    },
                    status = when (index) {
                        0 -> "Completed"
                        1 -> "In Progress"
                        2 -> "Starting Soon"
                        else -> "Other"
                    },
                    client = when (index) {
                        0 -> "ABC Bank"
                        1 -> "XYZ Corporation"
                        2 -> "TechStart Vietnam"
                        else -> "Other Client"
                    },
                    amount = when (index) {
                        0 -> "5,000,000 VND"
                        1 -> "3,500,000 VND"
                        2 -> "2,000,000 VND"
                        else -> "0 VND"
                    }
                )
            }
            
            // New Consultation Requests
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
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
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(40.dp)
                                    .clip(RoundedCornerShape(20.dp))
                                    .background(colorResource(id = R.color.interactive_primary).copy(alpha = 0.1f)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    Icons.Default.Notifications,
                                    contentDescription = null,
                                    tint = colorResource(id = R.color.interactive_primary),
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                            Column {
                                Text(
                                    text = "New Consultation Requests",
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                                Text(
                                    text = "3 pending requests",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = colorResource(id = R.color.text_secondary)
                                )
                            }
                        }
                        Text(
                            text = "3",
                            style = MaterialTheme.typography.headlineMedium,
                            fontWeight = FontWeight.Bold,
                            color = colorResource(id = R.color.interactive_primary)
                        )
                    }
                }
            }
            
            // Consultation Schedule
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
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
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(40.dp)
                                    .clip(RoundedCornerShape(20.dp))
                                    .background(colorResource(id = R.color.interactive_primary).copy(alpha = 0.1f)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    Icons.Default.Schedule,
                                    contentDescription = null,
                                    tint = colorResource(id = R.color.interactive_primary),
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                            Column {
                                Text(
                                    text = "This Week's Schedule",
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                                Text(
                                    text = "5 appointments scheduled",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = colorResource(id = R.color.text_secondary)
                                )
                            }
                        }
                        Text(
                            text = "5",
                            style = MaterialTheme.typography.headlineMedium,
                            fontWeight = FontWeight.Bold,
                            color = colorResource(id = R.color.interactive_primary)
                        )
                    }
                }
            }
            
            // Expert Profile
            item {
                Text(
                    text = "Professional Profile",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
            
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
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
                        modifier = Modifier.padding(16.dp)
                    ) {
                        // Experience
                        ExpertProfileItem(
                            icon = Icons.Default.Work,
                            title = "Experience",
                            value = "5+ years ESG consulting",
                            color = colorResource(id = R.color.interactive_primary)
                        )
                        
                        Spacer(modifier = Modifier.height(12.dp))
                        
                        // Certifications
                        ExpertProfileItem(
                            icon = Icons.Default.Verified,
                            title = "Certifications",
                            value = "ESG Expert Level 3",
                            color = Color(0xFF4CAF50)
                        )
                        
                        Spacer(modifier = Modifier.height(12.dp))
                        
                        // Specializations
                        ExpertProfileItem(
                            icon = Icons.Default.Star,
                            title = "Specialization",
                            value = "Environmental, Social, Governance",
                            color = Color(0xFFFF9800)
                        )
                        
                        Spacer(modifier = Modifier.height(12.dp))
                        
                        // Languages
                        ExpertProfileItem(
                            icon = Icons.Default.Language,
                            title = "Languages",
                            value = "Vietnamese, English",
                            color = Color(0xFF2196F3)
                        )
                    }
                }
            }
        }

    }
}

@Composable
fun ExpertLearningContent(
    navController: NavController,
    modifier: Modifier = Modifier
) {
    // Import LearningHubScreen and display it directly
    LearningHubScreen(
        navController = navController,
        modifier = modifier
    )
}

@Composable
fun ExpertEnterpriseContent(
    navController: NavController,
    modifier: Modifier = Modifier
) {
    ExpertEnterpriseScreen(
        modifier = modifier,
        navController = navController
    )
}

@Composable
fun CommunityContent(
    navController: NavController,
    modifier: Modifier = Modifier
) {
    ExpertCommunityTabScreen(
        modifier = modifier
    )
}

@Composable
fun NewsContent(
    navController: NavController,
    modifier: Modifier = Modifier
) {
    ExpertNewsTabScreen(
        modifier = modifier,
        navController = navController
    )
}

@Composable
fun ExpertKnowledgeAreaCard(
    pillarInfo: ESGPillarInfo,
    progress: Float
) {
    Card(
        modifier = Modifier.width(140.dp),
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
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = pillarInfo.icon,
                fontSize = 32.sp
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = pillarInfo.name,
                style = MaterialTheme.typography.bodySmall,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(8.dp))
            LinearProgressIndicator(
                progress = progress,
                modifier = Modifier.fillMaxWidth(),
                color = colorResource(id = R.color.interactive_primary),
                trackColor = colorResource(id = R.color.border_card)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "${(progress * 100).toInt()}%",
                style = MaterialTheme.typography.bodySmall,
                color = colorResource(id = R.color.interactive_primary)
            )
        }
    }
}

@Composable
fun ExpertLearningCard(
    title: String,
    progress: Float,
    time: String
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
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
                    modifier = Modifier.weight(1f),
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = "${(progress * 100).toInt()}%",
                    style = MaterialTheme.typography.bodySmall,
                    color = colorResource(id = R.color.interactive_primary)
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            LinearProgressIndicator(
                progress = progress,
                modifier = Modifier.fillMaxWidth(),
                color = colorResource(id = R.color.interactive_primary),
                trackColor = colorResource(id = R.color.border_card)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = time,
                style = MaterialTheme.typography.bodySmall,
                color = colorResource(id = R.color.text_secondary)
            )
        }
    }
}

@Composable
fun ExpertAchievementCard(
    title: String,
    icon: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
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
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = icon,
                fontSize = 32.sp
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = title,
                style = MaterialTheme.typography.bodySmall,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.fillMaxWidth(),
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpertQuickActionCard(
    title: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        onClick = onClick,
        colors = CardDefaults.cardColors(
            containerColor = colorResource(id = R.color.interactive_primary)
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = title,
                modifier = Modifier.size(32.dp),
                tint = Color.White
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = title,
                style = MaterialTheme.typography.bodySmall,
                textAlign = TextAlign.Center,
                color = Color.White,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@Composable
fun ExpertConsultationCard(
    title: String,
    status: String,
    client: String,
    amount: String
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
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
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.weight(1f)
                )
                Surface(
                    color = when (status) {
                        "Completed" -> Color(0xFF4CAF50).copy(alpha = 0.1f)
                        "In Progress" -> Color(0xFFFF9800).copy(alpha = 0.1f)
                        "Starting Soon" -> Color(0xFF2196F3).copy(alpha = 0.1f)
                        else -> colorResource(id = R.color.border_card)
                    },
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(
                        text = status,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        style = MaterialTheme.typography.bodySmall,
                        color = when (status) {
                            "Completed" -> Color(0xFF4CAF50)
                            "In Progress" -> Color(0xFFFF9800)
                            "Starting Soon" -> Color(0xFF2196F3)
                            else -> colorResource(id = R.color.text_secondary)
                        },
                        fontWeight = FontWeight.Medium
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                text = "Client: $client",
                style = MaterialTheme.typography.bodyMedium,
                color = colorResource(id = R.color.text_secondary)
            )
            
            Spacer(modifier = Modifier.height(4.dp))
            
            Text(
                text = "Value: $amount",
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Medium,
                color = colorResource(id = R.color.interactive_primary)
            )
        }
    }
}

@Composable
fun ExpertProfileItem(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    title: String,
    value: String,
    color: Color
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(RoundedCornerShape(20.dp))
                .background(color.copy(alpha = 0.1f)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = title,
                tint = color,
                modifier = Modifier.size(20.dp)
            )
        }
        
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Medium,
                color = colorResource(id = R.color.text_secondary)
            )
            Text(
                text = value,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}

@Composable
fun ExpertPillarStatItem(
    title: String,
    percentage: Int,
    projectCount: Int,
    color: Color
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = "$projectCount projects",
                style = MaterialTheme.typography.bodyMedium,
                color = colorResource(id = R.color.text_secondary)
            )
        }
        
        Column(
            horizontalAlignment = Alignment.End
        ) {
            Text(
                text = "$percentage%",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = color
            )
            LinearProgressIndicator(
                progress = percentage / 100f,
                modifier = Modifier.width(60.dp),
                color = color,
                trackColor = colorResource(id = R.color.border_card)
            )
        }
    }
}
