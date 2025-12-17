package com.ignitech.esgcompanion.presentation.screen.expert

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.hilt.navigation.compose.hiltViewModel
import com.ignitech.esgcompanion.R
import com.ignitech.esgcompanion.presentation.viewmodel.ExpertEnterpriseViewModel
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpertEnterpriseScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: ExpertEnterpriseViewModel = hiltViewModel()
) {
    val enterprises by viewModel.enterprises.collectAsState()
    val connectedEnterprises by viewModel.connectedEnterprises.collectAsState()
    val selectedTab by viewModel.selectedTab.collectAsState()
    
    var showConsultDialog by remember { mutableStateOf(false) }
    var selectedEnterprise by remember { mutableStateOf<ExpertEnterprise?>(null) }

    Column(
        modifier = modifier.fillMaxSize()
    ) {
        // Header
        TopAppBar(
            title = { 
                Text(
                    text = "Enterprises",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
            }
        )

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Tab Row
            item {
                ExpertEnterpriseTabRow(
                    selectedTab = selectedTab,
                    onTabSelected = { viewModel.selectTab(it) }
                )
            }
            
            // Content padding
            item {
                Spacer(modifier = Modifier.height(8.dp))
            }

            // Content based on selected tab
            when (selectedTab) {
                0 -> {
                    // Connected Enterprises
                    if (connectedEnterprises.isEmpty()) {
                        item {
                            Box(modifier = Modifier.padding(horizontal = 16.dp)) {
                                ExpertEmptyState(
                                    title = "No enterprises yet",
                                    description = "You haven't connected with any enterprises. Search and connect with enterprises to start consulting."
                                )
                            }
                        }
                    } else {
                        items(connectedEnterprises) { enterprise ->
                            Box(modifier = Modifier.padding(horizontal = 16.dp)) {
                                ExpertEnterpriseCard(
                                    enterprise = enterprise,
                                    isConnected = true,
                                    onConsultClick = { 
                                        selectedEnterprise = enterprise
                                        showConsultDialog = true
                                    },
                                    onDisconnectClick = { viewModel.disconnectFromEnterprise(enterprise.id) }
                                )
                            }
                        }
                    }
                }
                1 -> {
                    // All Enterprises
                    if (enterprises.isEmpty()) {
                        item {
                            Box(modifier = Modifier.padding(horizontal = 16.dp)) {
                                ExpertEmptyState(
                                    title = "No enterprises",
                                    description = "There are currently no enterprises in the system."
                                )
                            }
                        }
                    } else {
                        items(enterprises) { enterprise ->
                            val isConnected = connectedEnterprises.any { it.id == enterprise.id }
                            Box(modifier = Modifier.padding(horizontal = 16.dp)) {
                                ExpertEnterpriseCard(
                                    enterprise = enterprise,
                                    isConnected = isConnected,
                                    onConnectClick = { viewModel.connectToEnterprise(enterprise.id) },
                                    onConsultClick = { viewModel.startConsultation(enterprise.id) }
                                )
                            }
                        }
                    }
                }
            }
        }
        
        // Consultation Options Dialog
        if (showConsultDialog && selectedEnterprise != null) {
            ConsultationDialog(
                enterprise = selectedEnterprise!!,
                onDismiss = { showConsultDialog = false },
                onPhoneConsult = {
                    showConsultDialog = false
                    // TODO: Implement phone consultation
                },
                onEmailConsult = {
                    showConsultDialog = false
                    // TODO: Implement email consultation
                },
                onViewESGData = {
                    showConsultDialog = false
                    val encodedName = URLEncoder.encode(selectedEnterprise!!.name, StandardCharsets.UTF_8.toString())
                    navController.navigate("enterprise_esg_data/${selectedEnterprise!!.id}/$encodedName")
                }
            )
        }
    }
}

@Composable
fun ExpertEnterpriseTabRow(
    selectedTab: Int,
    onTabSelected: (Int) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        ExpertEnterpriseTab(
            label = "Connected",
            color = colorResource(id = R.color.interactive_primary),
            isSelected = selectedTab == 0,
            onClick = { onTabSelected(0) },
            modifier = Modifier.weight(1f)
        )
        
        ExpertEnterpriseTab(
            label = "All",
            color = colorResource(id = R.color.interactive_primary),
            isSelected = selectedTab == 1,
            onClick = { onTabSelected(1) },
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
fun ExpertEnterpriseTab(
    label: String,
    color: Color,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val animatedScale by animateFloatAsState(
        targetValue = if (isSelected) 1.05f else 1f,
        animationSpec = tween(200)
    )
    
    Surface(
        modifier = modifier
            .padding(horizontal = 2.dp)
            .scale(animatedScale),
        shape = MaterialTheme.shapes.medium,
        color = if (isSelected) 
            color.copy(alpha = 0.1f) 
        else 
            Color.Transparent,
        border = BorderStroke(
            width = if (isSelected) 2.dp else 1.dp,
            color = if (isSelected) 
                color 
            else 
                colorResource(id = R.color.border_light)
        ),
        onClick = onClick
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp, horizontal = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Label
            Text(
                text = label,
                style = MaterialTheme.typography.bodyMedium,
                color = if (isSelected) 
                    color 
                else 
                    MaterialTheme.colorScheme.onSurfaceVariant,
                fontWeight = if (isSelected) 
                    FontWeight.Bold 
                else 
                    FontWeight.Medium
            )
        }
    }
}

@Composable
fun ExpertEnterpriseCard(
    enterprise: ExpertEnterprise,
    isConnected: Boolean,
    onConnectClick: (() -> Unit)? = null,
    onConsultClick: (() -> Unit)? = null,
    onDisconnectClick: (() -> Unit)? = null
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
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Avatar
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .clip(RoundedCornerShape(24.dp))
                        .background(colorResource(id = R.color.interactive_primary)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = enterprise.name.take(2).uppercase(),
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
                
                Spacer(modifier = Modifier.width(12.dp))
                
                // Enterprise Info
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = enterprise.name,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = enterprise.industry,
                        style = MaterialTheme.typography.bodyMedium,
                        color = colorResource(id = R.color.text_secondary)
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = enterprise.location,
                        style = MaterialTheme.typography.bodySmall,
                        color = colorResource(id = R.color.text_secondary)
                    )
                }
                
                // Status
                if (isConnected) {
                    Surface(
                        color = colorResource(id = R.color.interactive_primary).copy(alpha = 0.1f),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text(
                            text = "Connected",
                            style = MaterialTheme.typography.bodySmall,
                            color = colorResource(id = R.color.interactive_primary),
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(12.dp))
            
            // Description
            Text(
                text = enterprise.description,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            
            Spacer(modifier = Modifier.height(12.dp))
            
            // Action Buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                if (isConnected) {
                    Button(
                        onClick = { onConsultClick?.invoke() },
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = colorResource(id = R.color.esg_warning)
                        )
                    ) {
                        Icon(Icons.Default.Chat, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Consult")
                    }
                    
                    OutlinedButton(
                        onClick = { onDisconnectClick?.invoke() },
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = colorResource(id = R.color.text_secondary)
                        ),
                        border = androidx.compose.foundation.BorderStroke(
                            width = 1.dp,
                            color = colorResource(id = R.color.border_card)
                        )
                    ) {
                        Text("Disconnect")
                    }
                } else {
                    Button(
                        onClick = { onConnectClick?.invoke() },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = colorResource(id = R.color.interactive_primary)
                        )
                    ) {
                        Icon(Icons.Default.PersonAdd, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Connect")
                    }
                }
            }
        }
    }
}

@Composable
fun ExpertEmptyState(
    title: String,
    description: String
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
            modifier = Modifier
                .fillMaxWidth()
                .padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                Icons.Default.Business,
                contentDescription = null,
                modifier = Modifier.size(64.dp),
                tint = colorResource(id = R.color.text_secondary)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = description,
                style = MaterialTheme.typography.bodyMedium,
                color = colorResource(id = R.color.text_secondary),
                textAlign = TextAlign.Center
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConsultationDialog(
    enterprise: ExpertEnterprise,
    onDismiss: () -> Unit,
    onPhoneConsult: () -> Unit,
    onEmailConsult: () -> Unit,
    onViewESGData: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = Color.White,
        title = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Enterprise Consultation",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = enterprise.name,
                    style = MaterialTheme.typography.bodyMedium,
                    color = colorResource(id = R.color.text_secondary),
                    textAlign = TextAlign.Center
                )
            }
        },
        text = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    text = "Select consultation method:",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.onSurface
                )
                
                // Phone consultation option
                ConsultationOptionItem(
                    icon = Icons.Default.Phone,
                    title = "Phone Consultation",
                    description = "Direct phone call to the enterprise",
                    onClick = onPhoneConsult
                )
                
                // Email consultation option
                ConsultationOptionItem(
                    icon = Icons.Default.Email,
                    title = "Email Consultation",
                    description = "Send detailed consultation email",
                    onClick = onEmailConsult
                )
                
                // Divider
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                        .height(1.dp)
                        .background(colorResource(id = R.color.border_card))
                )
                
                // View ESG Data option (special)
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null
                        ) { onViewESGData() },
                    colors = CardDefaults.cardColors(
                        containerColor = colorResource(id = R.color.interactive_primary).copy(alpha = 0.1f)
                    ),
                    elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
                    shape = RoundedCornerShape(12.dp),
                    border = BorderStroke(
                        width = 1.dp,
                        color = colorResource(id = R.color.interactive_primary)
                    )
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Assessment,
                            contentDescription = null,
                            tint = colorResource(id = R.color.interactive_primary),
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.width(16.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "View ESG Data",
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.Bold,
                                color = colorResource(id = R.color.interactive_primary)
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "View detailed ESG data of the enterprise",
                                style = MaterialTheme.typography.bodySmall,
                                color = colorResource(id = R.color.text_secondary)
                            )
                        }
                        Icon(
                            imageVector = Icons.Default.ChevronRight,
                            contentDescription = null,
                            tint = colorResource(id = R.color.interactive_primary)
                        )
                    }
                }
            }
        },
        confirmButton = {
            TextButton(
                onClick = onDismiss,
                colors = ButtonDefaults.textButtonColors(
                    contentColor = colorResource(id = R.color.text_secondary)
                )
            ) {
                Text("Close")
            }
        }
    )
}

@Composable
fun ConsultationOptionItem(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    title: String,
    description: String,
    onClick: () -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        onClick = onClick,
        color = Color.Transparent,
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(
            width = 1.dp,
            color = colorResource(id = R.color.border_card)
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = colorResource(id = R.color.esg_warning),
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = description,
                    style = MaterialTheme.typography.bodySmall,
                    color = colorResource(id = R.color.text_secondary)
                )
            }
            Icon(
                imageVector = Icons.Default.ChevronRight,
                contentDescription = null,
                tint = colorResource(id = R.color.text_secondary)
            )
        }
    }
}

data class ExpertEnterprise(
    val id: String,
    val name: String,
    val industry: String,
    val location: String,
    val description: String,
    val size: String,
    val esgScore: Float
)
