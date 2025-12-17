package com.ignitech.esgcompanion.presentation.screen

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.background
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.ignitech.esgcompanion.R
import com.ignitech.esgcompanion.presentation.viewmodel.UserViewModel
import com.ignitech.esgcompanion.presentation.viewmodel.LanguageViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    navController: NavController,
    viewModel: UserViewModel = hiltViewModel(),
    languageViewModel: LanguageViewModel = hiltViewModel()
) {
    val currentUser by viewModel.currentUser.collectAsState()
    val currentLanguage by languageViewModel.currentLanguage.collectAsState()
    var showLogoutDialog by remember { mutableStateOf(false) }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Settings") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // User Profile Header
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.Transparent
                    )
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        // Avatar
                        currentUser?.let { user ->
                            Box(
                                modifier = Modifier
                                    .size(80.dp)
                                    .background(
                                        color = colorResource(id = R.color.interactive_primary),
                                        shape = androidx.compose.foundation.shape.CircleShape
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = user.name.take(2).uppercase(),
                                    style = MaterialTheme.typography.headlineMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                )
                            }
                        }
                        
                        Spacer(modifier = Modifier.height(16.dp))
                        
                        // User Info
                        currentUser?.let { user ->
                            Text(
                                text = user.name,
                                style = MaterialTheme.typography.headlineSmall,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onPrimaryContainer
                            )
                            
                            Spacer(modifier = Modifier.height(4.dp))
                            
                            Text(
                                text = user.email,
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.7f)
                            )
                            
                            // Class Information for Students
                            if (user.role == com.ignitech.esgcompanion.domain.entity.UserRole.ACADEMIC && user.companyName != null) {
                                Spacer(modifier = Modifier.height(4.dp))
                                
                                Text(
                                    text = user.companyName,
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.8f),
                                    fontWeight = FontWeight.Medium
                                )
                            }
                            
                            Spacer(modifier = Modifier.height(8.dp))
                            
                            // Role Badge
                            Card(
                                colors = CardDefaults.cardColors(
                                    containerColor = MaterialTheme.colorScheme.primary
                                )
                            ) {
                                Text(
                                    text = when (user.role) {
                                        com.ignitech.esgcompanion.domain.entity.UserRole.ENTERPRISE -> "Enterprise"
                                        com.ignitech.esgcompanion.domain.entity.UserRole.EXPERT -> "Expert"
                                        com.ignitech.esgcompanion.domain.entity.UserRole.ACADEMIC -> "Student"
                                        com.ignitech.esgcompanion.domain.entity.UserRole.INSTRUCTOR -> "Instructor"
                                        com.ignitech.esgcompanion.domain.entity.UserRole.REGULATORY -> "Regulatory"
                                    },
                                    style = MaterialTheme.typography.labelMedium,
                                    color = MaterialTheme.colorScheme.onPrimary,
                                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                                )
                            }
                        }
                    }
                }
            }
            
            // Settings Section
            item {
                Column(
                    modifier = Modifier.padding(horizontal = 16.dp)
                ) {
                        
                        val settingsItems = listOf(
                            SettingsItem(
                                icon = Icons.Default.Person,
                                title = "Account Information",
                                subtitle = "View and edit personal information",
                                onClick = { navController.navigate("account_info") }
                            ),
                            SettingsItem(
                                icon = Icons.Default.Language,
                                title = "Language",
                                subtitle = when (currentLanguage) {
                                    "vi" -> "Vietnamese"
                                    "en" -> "English"
                                    else -> "Vietnamese"
                                },
                                onClick = { navController.navigate("language_settings") }
                            ),
                            SettingsItem(
                                icon = Icons.Default.Notifications,
                                title = "Notifications",
                                subtitle = "Manage notifications",
                                onClick = { navController.navigate("notifications") }
                            ),
                            SettingsItem(
                                icon = Icons.Default.Security,
                                title = "Security",
                                subtitle = "Password and security",
                                onClick = { navController.navigate("security") }
                            ),
                            SettingsItem(
                                icon = Icons.Default.Help,
                                title = "Help",
                                subtitle = "FAQ and support",
                                onClick = { navController.navigate("help") }
                            ),
                            SettingsItem(
                                icon = Icons.Default.Info,
                                title = "About",
                                subtitle = "Version 1.0.0",
                                onClick = { navController.navigate("about") }
                            ),
                            SettingsItem(
                                icon = Icons.Default.Logout,
                                title = "Logout",
                                subtitle = "Sign out of current account",
                                onClick = { showLogoutDialog = true }
                            )
                        )
                        
                        settingsItems.forEachIndexed { index, item ->
                            SettingsItemRow(item = item)
                            if (index < settingsItems.size - 1) {
                                Spacer(modifier = Modifier.height(12.dp))
                            }
                        }
                }
            }
        }
        
        // Logout Confirmation Dialog
        if (showLogoutDialog) {
            AlertDialog(
                onDismissRequest = { showLogoutDialog = false },
                containerColor = Color.White,
                title = {
                    Text(
                        text = "Confirm Logout",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                },
                text = {
                    Text(
                        text = "Are you sure you want to logout from your current account?",
                        style = MaterialTheme.typography.bodyMedium
                    )
                },
                confirmButton = {
                    TextButton(
                        onClick = {
                            showLogoutDialog = false
                            viewModel.logout()
                            navController.navigate("login") {
                                popUpTo("settings") { inclusive = true }
                            }
                        }
                    ) {
                        Text(
                            text = "Logout",
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                },
                dismissButton = {
                    TextButton(
                        onClick = { showLogoutDialog = false }
                    ) {
                        Text("Cancel")
                    }
                }
            )
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsItemRow(
    item: SettingsItem
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        onClick = item.onClick,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Icon with background
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .background(
                        color = MaterialTheme.colorScheme.primary,
                        shape = androidx.compose.foundation.shape.CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = item.icon,
                    contentDescription = item.title,
                    modifier = Modifier.size(24.dp),
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
            
            Spacer(modifier = Modifier.width(16.dp))
            
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = item.title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                item.subtitle?.let { subtitle ->
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = subtitle,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f)
                    )
                }
            }
            
            Icon(
                imageVector = Icons.Default.ChevronRight,
                contentDescription = "Navigate",
                tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f)
            )
        }
    }
}

data class SettingsItem(
    val icon: ImageVector,
    val title: String,
    val subtitle: String?,
    val onClick: () -> Unit
)
