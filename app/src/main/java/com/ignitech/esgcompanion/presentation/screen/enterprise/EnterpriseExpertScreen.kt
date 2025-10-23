package com.ignitech.esgcompanion.presentation.screen.enterprise

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.draw.scale
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import coil.compose.AsyncImage
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarDuration
import com.ignitech.esgcompanion.R
import com.ignitech.esgcompanion.domain.entity.ExpertData
import com.ignitech.esgcompanion.data.local.entity.EnterpriseExpertConnectionEntity
import com.ignitech.esgcompanion.presentation.viewmodel.EnterpriseExpertViewModel
import com.ignitech.esgcompanion.presentation.viewmodel.ExpertWithConnection

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EnterpriseExpertScreen(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    viewModel: EnterpriseExpertViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    var selectedTab by remember { mutableStateOf(0) }
    var showConsultationDialog by remember { mutableStateOf(false) }
    var showConnectionDialog by remember { mutableStateOf(false) }
    var selectedExpertId by remember { mutableStateOf("") }
    var selectedExpert by remember { mutableStateOf<ExpertData?>(null) }
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        viewModel.loadExpertData()
    }

    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "ESG Experts",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                }
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // Custom Tab Row
            ExpertTabRow(
                selectedTab = selectedTab,
                onTabSelected = { selectedTab = it }
            )

            // Tab Content
            when (selectedTab) {
                0 -> ConnectedExpertsTab(
                    connectedExperts = uiState.connectedExperts,
                    onExpertClick = { viewModel.selectExpert(it) },
                    onBookConsultation = { expertId ->
                        selectedExpertId = expertId
                        showConsultationDialog = true
                    },
                    onConnect = { expertId ->
                        val expert = uiState.connectedExperts.find { it.expert.id == expertId }?.expert
                        selectedExpert = expert
                        selectedExpertId = expertId
                        showConnectionDialog = true
                    }
                )
                1 -> CommunityExpertsTab(
                    allExperts = uiState.allExperts,
                    onExpertClick = { viewModel.selectExpert(it) },
                    onBookConsultation = { expertId ->
                        selectedExpertId = expertId
                        showConsultationDialog = true
                    },
                    onConnect = { expertId ->
                        val expert = uiState.allExperts.find { it.expert.id == expertId }?.expert
                        selectedExpert = expert
                        selectedExpertId = expertId
                        showConnectionDialog = true
                    }
                )
            }
        }
        
        // Consultation Options Dialog
        if (showConsultationDialog) {
            ConsultationOptionsDialog(
                onDismiss = { showConsultationDialog = false },
                onPhoneCall = { 
                    showConsultationDialog = false
                    viewModel.initiatePhoneCall(selectedExpertId)
                },
                onScheduleMeeting = { 
                    showConsultationDialog = false
                    viewModel.scheduleMeeting(selectedExpertId)
                },
                onSendMessage = { 
                    showConsultationDialog = false
                    viewModel.sendMessage(selectedExpertId)
                }
            )
        }
        
        // Connection Confirmation Dialog
        if (showConnectionDialog && selectedExpert != null) {
            ConnectionConfirmationDialog(
                expert = selectedExpert!!,
                onDismiss = { showConnectionDialog = false },
                onConfirm = {
                    showConnectionDialog = false
                    viewModel.sendConnectionRequest(selectedExpertId)
                }
            )
        }
    }
}

@Composable
fun ExpertTabRow(
    selectedTab: Int,
    onTabSelected: (Int) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        ExpertTab(
            label = "Connected",
            color = colorResource(id = R.color.interactive_primary),
            isSelected = selectedTab == 0,
            onClick = { onTabSelected(0) },
            modifier = Modifier.weight(1f)
        )
        
        ExpertTab(
            label = "Community",
            color = colorResource(id = R.color.interactive_primary),
            isSelected = selectedTab == 1,
            onClick = { onTabSelected(1) },
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
fun ExpertTab(
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
fun ConnectedExpertsTab(
    connectedExperts: List<ExpertWithConnection>,
    onExpertClick: (String) -> Unit,
    onBookConsultation: (String) -> Unit,
    onConnect: (String) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        if (connectedExperts.isEmpty()) {
            item {
                EmptyState(
                    title = "No experts yet",
                    description = "You haven't connected with any ESG experts yet. Explore the expert community to find suitable experts."
                )
            }
        } else {
            item {
                Text(
                    text = "Connected Experts (${connectedExperts.size})",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }

            item {
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(connectedExperts) { expertWithConnection ->
                        ExpertCard(
                            expert = expertWithConnection.expert,
                            connection = expertWithConnection.connection,
                            onClick = { onExpertClick(expertWithConnection.expert.id) }
                        )
                    }
                }
            }

                        items(connectedExperts) { expertWithConnection ->
                            ExpertListItem(
                                expert = expertWithConnection.expert,
                                connection = expertWithConnection.connection,
                                isConnected = expertWithConnection.isConnected,
                                isPending = expertWithConnection.isPending,
                                onClick = { onExpertClick(expertWithConnection.expert.id) },
                                onBookConsultation = { onBookConsultation(expertWithConnection.expert.id) },
                                onConnect = { onConnect(expertWithConnection.expert.id) }
                            )
                        }
        }
    }
}

@Composable
fun CommunityExpertsTab(
    allExperts: List<ExpertWithConnection>,
    onExpertClick: (String) -> Unit,
    onBookConsultation: (String) -> Unit,
    onConnect: (String) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        if (allExperts.isEmpty()) {
            item {
                EmptyState(
                    title = "No experts yet",
                    description = "There are currently no ESG experts in the community. Please try again later."
                )
            }
        } else {
            item {
                Text(
                    text = "Expert Community (${allExperts.size})",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }

                        items(allExperts) { expertWithConnection ->
                            ExpertListItem(
                                expert = expertWithConnection.expert,
                                connection = expertWithConnection.connection,
                                isConnected = expertWithConnection.isConnected,
                                isPending = expertWithConnection.isPending,
                                onClick = { onExpertClick(expertWithConnection.expert.id) },
                                onBookConsultation = { onBookConsultation(expertWithConnection.expert.id) },
                                onConnect = { onConnect(expertWithConnection.expert.id) }
                            )
                        }
        }
    }
}

@Composable
fun EmptyState(
    title: String,
    description: String
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface,
            textAlign = TextAlign.Center
        )

        Text(
            text = description,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center,
            lineHeight = 20.sp
        )
    }
}

@Composable
fun ExpertCard(
    expert: ExpertData,
    connection: EnterpriseExpertConnectionEntity?,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .width(200.dp)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) { onClick() }
            .border(
                width = 1.dp,
                color = colorResource(id = R.color.border_card),
                shape = RoundedCornerShape(12.dp)
            ),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Avatar
            Box(
                modifier = Modifier
                    .size(60.dp)
                    .clip(CircleShape)
                    .background(colorResource(id = R.color.interactive_primary).copy(alpha = 0.1f)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = expert.name.take(2),
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = colorResource(id = R.color.interactive_primary)
                )
            }

            // Name
            Text(
                text = expert.name,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface,
                textAlign = TextAlign.Center
            )

            // Specialization
            Text(
                text = expert.specialization,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

            // Rating
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
                    text = expert.rating.toString(),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            // Price
            Text(
                text = "${expert.hourlyRate}K VND/hour",
                style = MaterialTheme.typography.bodySmall,
                fontWeight = FontWeight.Medium,
                color = colorResource(id = R.color.interactive_primary)
            )

            // Connection status
            if (connection != null) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Icon(
                        Icons.Default.CheckCircle,
                        contentDescription = null,
                        tint = Color(0xFF4CAF50),
                        modifier = Modifier.size(16.dp)
                    )
                    Text(
                        text = "Connected",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color(0xFF4CAF50),
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
    }
}

@Composable
fun ExpertListItem(
    expert: ExpertData,
    connection: EnterpriseExpertConnectionEntity?,
    isConnected: Boolean,
    isPending: Boolean,
    onClick: () -> Unit,
    onBookConsultation: () -> Unit,
    onConnect: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) { onClick() }
            .border(
                width = 1.dp,
                color = colorResource(id = R.color.border_card),
                shape = RoundedCornerShape(12.dp)
            ),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Avatar
            Box(
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape)
                    .background(colorResource(id = R.color.interactive_primary).copy(alpha = 0.1f)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = expert.name.take(2),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = colorResource(id = R.color.interactive_primary)
                )
            }

            // Content
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = expert.name,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )

                Text(
                    text = expert.specialization,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
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
                            text = expert.rating.toString(),
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    Text(
                        text = "•",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    Text(
                        text = "${expert.hourlyRate}K VND/hour",
                        style = MaterialTheme.typography.bodySmall,
                        color = colorResource(id = R.color.interactive_primary)
                    )
                }
            }

            // Action Button
            when {
                isConnected -> {
                    Button(
                        onClick = onBookConsultation,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = colorResource(id = R.color.esg_warning)
                        ),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(
                            text = "Consult",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color.White
                        )
                    }
                }
                isPending -> {
                    OutlinedButton(
                        onClick = { /* TODO: Cancel request */ },
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = colorResource(id = R.color.esg_warning)
                        ),
                        border = BorderStroke(1.dp, colorResource(id = R.color.esg_warning)),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(
                            text = "Pending",
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }
                else -> {
                    Button(
                        onClick = onConnect,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = colorResource(id = R.color.interactive_primary)
                        ),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(
                            text = "Connect",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color.White
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ConsultationOptionsDialog(
    onDismiss: () -> Unit,
    onPhoneCall: () -> Unit,
    onScheduleMeeting: () -> Unit,
    onSendMessage: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = Color.White,
        title = {
            Text(
                text = "Select Consultation Method",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
        },
        text = {
            Column(
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                ConsultationOptionItem(
                    icon = Icons.Default.Phone,
                    title = "By Phone",
                    description = "Call the expert directly",
                    onClick = onPhoneCall
                )
                
                ConsultationOptionItem(
                    icon = Icons.Default.Schedule,
                    title = "Schedule Appointment",
                    description = "Sắp xếp cuộc họp trực tiếp hoặc online",
                    onClick = onScheduleMeeting
                )
                
                ConsultationOptionItem(
                    icon = Icons.Default.Message,
                    title = "Gửi tin nhắn",
                    description = "Gửi câu hỏi qua tin nhắn",
                    onClick = onSendMessage
                )
            }
        },
        confirmButton = {
            TextButton(
                onClick = onDismiss,
                colors = ButtonDefaults.textButtonColors(
                    contentColor = MaterialTheme.colorScheme.onSurfaceVariant
                )
            ) {
                Text("Hủy")
            }
        }
    )
}

@Composable
fun ConsultationOptionItem(
    icon: ImageVector,
    title: String,
    description: String,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) { onClick() }
            .border(
                width = 1.dp,
                color = colorResource(id = R.color.border_card),
                shape = RoundedCornerShape(12.dp)
            ),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = colorResource(id = R.color.interactive_primary),
                modifier = Modifier.size(24.dp)
            )
            
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = description,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            
            Icon(
                imageVector = Icons.Default.ChevronRight,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.size(20.dp)
            )
        }
    }
}

@Composable
fun ConnectionConfirmationDialog(
    expert: ExpertData,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = Color.White,
        title = {
            Text(
                text = "Xác nhận kết nối",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
        },
        text = {
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Expert Info Card
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(
                            width = 1.dp,
                            color = colorResource(id = R.color.border_card),
                            shape = RoundedCornerShape(12.dp)
                        ),
                    colors = CardDefaults.cardColors(containerColor = Color.Transparent),
                    elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(48.dp)
                                .clip(CircleShape)
                                .background(MaterialTheme.colorScheme.primaryContainer),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Person,
                                contentDescription = "Expert Profile Picture",
                                tint = MaterialTheme.colorScheme.onPrimaryContainer,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                        
                        Column(
                            modifier = Modifier.weight(1f)
                        ) {
                            Text(
                                text = expert.name,
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Text(
                                text = expert.specialization,
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Icon(
                                    Icons.Default.Star,
                                    contentDescription = "Rating",
                                    tint = Color(0xFFFFD700),
                                    modifier = Modifier.size(16.dp)
                                )
                                Text(
                                    text = expert.rating.toString(),
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                                Text(
                                    text = "•",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                                Text(
                                    text = "${expert.hourlyRate}K VND/hour",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                    }
                }
                
                // Connection Info
                Text(
                    text = "Bạn sẽ gửi yêu cầu kết nối tới chuyên gia này. Sau khi chuyên gia chấp nhận, bạn có thể:",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
                
                // Data Access Warning
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(
                            width = 1.dp,
                            color = colorResource(id = R.color.esg_warning),
                            shape = RoundedCornerShape(8.dp)
                        ),
                    colors = CardDefaults.cardColors(
                        containerColor = colorResource(id = R.color.esg_warning).copy(alpha = 0.1f)
                    ),
                    elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Info,
                            contentDescription = null,
                            tint = colorResource(id = R.color.esg_warning),
                            modifier = Modifier.size(20.dp)
                        )
                        Text(
                            text = "Chuyên gia sẽ có quyền truy cập vào dữ liệu ESG của doanh nghiệp để đưa ra tư vấn chính xác",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurface,
                            lineHeight = 16.sp
                        )
                    }
                }
                
                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    ConnectionBenefitItem(
                        icon = Icons.Default.Phone,
                        text = "Gọi điện tư vấn trực tiếp"
                    )
                    ConnectionBenefitItem(
                        icon = Icons.Default.Schedule,
                        text = "Đặt lịch hẹn tư vấn"
                    )
                    ConnectionBenefitItem(
                        icon = Icons.Default.Message,
                        text = "Gửi tin nhắn trao đổi"
                    )
                    ConnectionBenefitItem(
                        icon = Icons.Default.Assignment,
                        text = "Nhận tư vấn chuyên sâu về ESG"
                    )
                    ConnectionBenefitItem(
                        icon = Icons.Default.Analytics,
                        text = "Chuyên gia có thể xem dữ liệu ESG để phân tích và đưa ra khuyến nghị"
                    )
                }
            }
        },
        confirmButton = {
            Button(
                onClick = onConfirm,
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(id = R.color.interactive_primary)
                ),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(
                    text = "Kết nối",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.White
                )
            }
        },
        dismissButton = {
            TextButton(
                onClick = onDismiss,
                colors = ButtonDefaults.textButtonColors(
                    contentColor = MaterialTheme.colorScheme.onSurfaceVariant
                )
            ) {
                Text("Hủy")
            }
        }
    )
}

@Composable
fun ConnectionBenefitItem(
    icon: ImageVector,
    text: String
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = colorResource(id = R.color.interactive_primary),
            modifier = Modifier.size(16.dp)
        )
        Text(
            text = text,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

