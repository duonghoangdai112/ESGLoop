package com.ignitech.esgcompanion.presentation.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.background
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.ignitech.esgcompanion.presentation.viewmodel.UserViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SecurityScreen(
    navController: NavController,
    viewModel: UserViewModel = hiltViewModel()
) {
    var showChangePasswordDialog by remember { mutableStateOf(false) }
    var showDeleteAccountDialog by remember { mutableStateOf(false) }
    var showTwoFactorDialog by remember { mutableStateOf(false) }
    
    // Password change form
    var currentPassword by remember { mutableStateOf("") }
    var newPassword by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var showCurrentPassword by remember { mutableStateOf(false) }
    var showNewPassword by remember { mutableStateOf(false) }
    var showConfirmPassword by remember { mutableStateOf(false) }
    
    // Two-factor authentication
    var twoFactorEnabled by remember { mutableStateOf(false) }
    var backupCodes by remember { mutableStateOf(listOf<String>()) }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Security") },
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
            verticalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp)
        ) {
            // Password Security Section
            item {
                SecuritySectionCard(
                    title = "Password",
                    icon = Icons.Default.Lock,
                    items = listOf(
                        SecurityItem(
                            title = "Change Password",
                            subtitle = "Update current password",
                            icon = Icons.Default.Key,
                            onClick = { showChangePasswordDialog = true }
                        ),
                        SecurityItem(
                            title = "Login History",
                            subtitle = "View recent login activities",
                            icon = Icons.Default.History,
                            onClick = { /* Navigate to login history */ }
                        )
                    )
                )
            }
            
            // Two-Factor Authentication Section
            item {
                SecuritySectionCard(
                    title = "Two-Factor Authentication",
                    icon = Icons.Default.Security,
                    items = listOf(
                        SecurityItem(
                            title = if (twoFactorEnabled) "Disable 2FA" else "Enable 2FA",
                            subtitle = if (twoFactorEnabled) "Security enabled" else "Enhance account security",
                            icon = Icons.Default.VerifiedUser,
                            onClick = { showTwoFactorDialog = true }
                        ),
                        SecurityItem(
                            title = "Backup Codes",
                            subtitle = "Manage recovery codes",
                            icon = Icons.Default.Backup,
                            onClick = { /* Navigate to backup codes */ }
                        )
                    )
                )
            }
            
            // Privacy Settings Section
            item {
                SecuritySectionCard(
                    title = "Privacy",
                    icon = Icons.Default.PrivacyTip,
                    items = listOf(
                        SecurityItem(
                            title = "Data Access Permissions",
                            subtitle = "Manage app access permissions",
                            icon = Icons.Default.Settings,
                            onClick = { /* Navigate to permissions */ }
                        ),
                        SecurityItem(
                            title = "Delete Personal Data",
                            subtitle = "Delete all stored data",
                            icon = Icons.Default.DeleteForever,
                            onClick = { /* Show data deletion dialog */ }
                        )
                    )
                )
            }
            
            // Account Security Section
            item {
                SecuritySectionCard(
                    title = "Account Security",
                    icon = Icons.Default.AccountCircle,
                    items = listOf(
                        SecurityItem(
                            title = "Logout from all devices",
                            subtitle = "End sessions on all devices",
                            icon = Icons.Default.Logout,
                            onClick = { /* Logout from all devices */ }
                        ),
                        SecurityItem(
                            title = "Delete Account",
                            subtitle = "Permanently delete account and all data",
                            icon = Icons.Default.DeleteForever,
                            isDestructive = true,
                            onClick = { showDeleteAccountDialog = true }
                        )
                    )
                )
            }
        }
    }
    
    // Change Password Dialog
    if (showChangePasswordDialog) {
        AlertDialog(
            onDismissRequest = { showChangePasswordDialog = false },
            containerColor = Color.White,
            title = { Text("Change Password") },
            text = {
                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OutlinedTextField(
                        value = currentPassword,
                        onValueChange = { currentPassword = it },
                        label = { Text("Current Password") },
                        visualTransformation = if (showCurrentPassword) VisualTransformation.None else PasswordVisualTransformation(),
                        trailingIcon = {
                            IconButton(onClick = { showCurrentPassword = !showCurrentPassword }) {
                                Icon(
                                    if (showCurrentPassword) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                                    contentDescription = null
                                )
                            }
                        },
                        modifier = Modifier.fillMaxWidth()
                    )
                    
                    OutlinedTextField(
                        value = newPassword,
                        onValueChange = { newPassword = it },
                        label = { Text("New Password") },
                        visualTransformation = if (showNewPassword) VisualTransformation.None else PasswordVisualTransformation(),
                        trailingIcon = {
                            IconButton(onClick = { showNewPassword = !showNewPassword }) {
                                Icon(
                                    if (showNewPassword) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                                    contentDescription = null
                                )
                            }
                        },
                        modifier = Modifier.fillMaxWidth()
                    )
                    
                    OutlinedTextField(
                        value = confirmPassword,
                        onValueChange = { confirmPassword = it },
                        label = { Text("Confirm New Password") },
                        visualTransformation = if (showConfirmPassword) VisualTransformation.None else PasswordVisualTransformation(),
                        trailingIcon = {
                            IconButton(onClick = { showConfirmPassword = !showConfirmPassword }) {
                                Icon(
                                    if (showConfirmPassword) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                                    contentDescription = null
                                )
                            }
                        },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        // Handle password change
                        showChangePasswordDialog = false
                        currentPassword = ""
                        newPassword = ""
                        confirmPassword = ""
                    }
                ) {
                    Text("Change Password")
                }
            },
            dismissButton = {
                TextButton(onClick = { showChangePasswordDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }
    
    // Two-Factor Authentication Dialog
    if (showTwoFactorDialog) {
        AlertDialog(
            onDismissRequest = { showTwoFactorDialog = false },
            containerColor = Color.White,
            title = { Text(if (twoFactorEnabled) "Disable 2FA" else "Enable 2FA") },
            text = {
                Text(
                    if (twoFactorEnabled) {
                        "Are you sure you want to disable two-factor authentication? This will reduce your account security."
                    } else {
                        "Two-factor authentication will enhance your account security by requiring a verification code from an authenticator app."
                    }
                )
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        twoFactorEnabled = !twoFactorEnabled
                        showTwoFactorDialog = false
                    }
                ) {
                    Text(if (twoFactorEnabled) "Disable" else "Enable")
                }
            },
            dismissButton = {
                TextButton(onClick = { showTwoFactorDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }
    
    // Delete Account Dialog
    if (showDeleteAccountDialog) {
        AlertDialog(
            onDismissRequest = { showDeleteAccountDialog = false },
            containerColor = Color.White,
            title = { Text("Delete Account") },
            text = {
                Text(
                    "Are you sure you want to delete your account? This action cannot be undone and all data will be permanently lost."
                )
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        // Handle account deletion
                        showDeleteAccountDialog = false
                        navController.navigate("login") {
                            popUpTo("security") { inclusive = true }
                        }
                    }
                ) {
                    Text("Delete Account")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteAccountDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }
}

@Composable
fun SecuritySectionCard(
    title: String,
    icon: ImageVector,
    items: List<SecurityItem>
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(32.dp)
                        .background(
                            color = MaterialTheme.colorScheme.primary,
                            shape = CircleShape
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp),
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                }
                
                Spacer(modifier = Modifier.width(10.dp))
                
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
            
            items.forEachIndexed { index, item ->
                SecurityItemRow(item = item)
                if (index < items.size - 1) {
                    Spacer(modifier = Modifier.height(2.dp))
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SecurityItemRow(
    item: SecurityItem
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        onClick = item.onClick,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Icon
            Icon(
                imageVector = item.icon,
                contentDescription = item.title,
                modifier = Modifier.size(24.dp),
                tint = if (item.isDestructive) 
                    MaterialTheme.colorScheme.error 
                else 
                    MaterialTheme.colorScheme.onSurfaceVariant
            )
            
            Spacer(modifier = Modifier.width(12.dp))
            
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = item.title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Medium,
                    color = if (item.isDestructive) 
                        MaterialTheme.colorScheme.error 
                    else 
                        MaterialTheme.colorScheme.onSurfaceVariant
                )
                item.subtitle?.let { subtitle ->
                    Spacer(modifier = Modifier.height(1.dp))
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

data class SecurityItem(
    val icon: ImageVector,
    val title: String,
    val subtitle: String?,
    val onClick: () -> Unit,
    val isDestructive: Boolean = false
)
