package com.ignitech.esgcompanion.presentation.screen.enterprise

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ignitech.esgcompanion.R
import com.ignitech.esgcompanion.data.entity.*
import com.ignitech.esgcompanion.domain.entity.ESGPillar
import com.ignitech.esgcompanion.presentation.viewmodel.AddActivityViewModel
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddActivityScreen(
    onBack: () -> Unit,
    onSave: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: AddActivityViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = { 
                    Text(
                        text = "Add ESG Activity",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Activity Type Selection
            item {
                Text(
                    text = "Activity Type",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
            
            item {
                ActivityTypeSelector(
                    selectedType = uiState.selectedActivityType,
                    onTypeSelected = viewModel::selectActivityType
                )
            }

            // Basic Information
            item {
                Text(
                    text = "Basic Information",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }

            // Title
            item {
                OutlinedTextField(
                    value = uiState.title,
                    onValueChange = viewModel::updateTitle,
                    label = { Text("Activity Title") },
                    placeholder = { 
                        Text(
                            text = "Enter activity title...",
                            color = colorResource(id = R.color.text_secondary)
                        ) 
                    },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    isError = uiState.titleError.isNotEmpty(),
                    supportingText = if (uiState.titleError.isNotEmpty()) {
                        { Text(uiState.titleError, color = MaterialTheme.colorScheme.error) }
                    } else null,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = colorResource(id = R.color.interactive_primary),
                        unfocusedBorderColor = colorResource(id = R.color.border_card),
                        focusedLabelColor = colorResource(id = R.color.interactive_primary),
                        unfocusedLabelColor = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                )
            }

            // Description
            item {
                OutlinedTextField(
                    value = uiState.description,
                    onValueChange = viewModel::updateDescription,
                    label = { Text("Detailed Description") },
                    placeholder = { 
                        Text(
                            text = "Describe the activity in detail...",
                            color = colorResource(id = R.color.text_secondary)
                        ) 
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(120.dp),
                    maxLines = 4,
                    isError = uiState.descriptionError.isNotEmpty(),
                    supportingText = if (uiState.descriptionError.isNotEmpty()) {
                        { Text(uiState.descriptionError, color = MaterialTheme.colorScheme.error) }
                    } else null,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = colorResource(id = R.color.interactive_primary),
                        unfocusedBorderColor = colorResource(id = R.color.border_card),
                        focusedLabelColor = colorResource(id = R.color.interactive_primary),
                        unfocusedLabelColor = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                )
            }

            // Pillar Selection
            item {
                Text(
                    text = "ESG Pillar",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
            
            item {
                PillarSelectionRow(
                    selectedPillar = uiState.selectedPillar,
                    onPillarSelected = viewModel::selectPillar
                )
            }

            // Priority Selection
            item {
                Text(
                    text = "Priority Level",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
            
            item {
                PrioritySelectionRow(
                    selectedPriority = uiState.selectedPriority,
                    onPrioritySelected = viewModel::selectPriority
                )
            }

            // Date Selection
            item {
                Text(
                    text = "Implementation Date",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
            
            item {
                DateSelectionCard(
                    selectedDate = uiState.plannedDate,
                    onDateSelected = viewModel::selectDate
                )
            }

            // Additional Information
            item {
                Text(
                    text = "Additional Information",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }

            // Department
            item {
                OutlinedTextField(
                    value = uiState.department,
                    onValueChange = viewModel::updateDepartment,
                    label = { Text("Department") },
                    placeholder = { 
                        Text(
                            text = "e.g., Environmental Department",
                            color = colorResource(id = R.color.text_secondary)
                        ) 
                    },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = colorResource(id = R.color.interactive_primary),
                        unfocusedBorderColor = colorResource(id = R.color.border_card),
                        focusedLabelColor = colorResource(id = R.color.interactive_primary),
                        unfocusedLabelColor = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                )
            }

            // Location
            item {
                OutlinedTextField(
                    value = uiState.location,
                    onValueChange = viewModel::updateLocation,
                    label = { Text("Location") },
                    placeholder = { 
                        Text(
                            text = "e.g., Headquarters",
                            color = colorResource(id = R.color.text_secondary)
                        ) 
                    },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = colorResource(id = R.color.interactive_primary),
                        unfocusedBorderColor = colorResource(id = R.color.border_card),
                        focusedLabelColor = colorResource(id = R.color.interactive_primary),
                        unfocusedLabelColor = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                )
            }

            // Budget
            item {
                OutlinedTextField(
                    value = uiState.budget,
                    onValueChange = viewModel::updateBudget,
                    label = { Text("Budget (VND)") },
                    placeholder = { 
                        Text(
                            text = "e.g., 10000000",
                            color = colorResource(id = R.color.text_secondary)
                        ) 
                    },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    isError = uiState.budgetError.isNotEmpty(),
                    supportingText = if (uiState.budgetError.isNotEmpty()) {
                        { Text(uiState.budgetError, color = MaterialTheme.colorScheme.error) }
                    } else null,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = colorResource(id = R.color.interactive_primary),
                        unfocusedBorderColor = colorResource(id = R.color.border_card),
                        focusedLabelColor = colorResource(id = R.color.interactive_primary),
                        unfocusedLabelColor = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                )
            }

            // Notes
            item {
                OutlinedTextField(
                    value = uiState.notes,
                    onValueChange = viewModel::updateNotes,
                    label = { Text("Notes") },
                    placeholder = { 
                        Text(
                            text = "Additional notes...",
                            color = colorResource(id = R.color.text_secondary)
                        ) 
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp),
                    maxLines = 3,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = colorResource(id = R.color.interactive_primary),
                        unfocusedBorderColor = colorResource(id = R.color.border_card),
                        focusedLabelColor = colorResource(id = R.color.interactive_primary),
                        unfocusedLabelColor = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                )
            }

            // Save Button
            item {
                Button(
                    onClick = {
                        viewModel.saveActivity()
                        onSave()
                    },
                    enabled = uiState.isFormValid,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorResource(id = R.color.interactive_primary),
                        disabledContainerColor = colorResource(id = R.color.border_card)
                    ),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(
                        text = "Save Activity",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = if (uiState.isFormValid) 
                            Color.White 
                        else 
                            colorResource(id = R.color.text_secondary)
                    )
                }
            }

            // Spacer for bottom padding
            item {
                Spacer(modifier = Modifier.height(32.dp))
            }
        }
    }
}

@Composable
fun ActivityTypeSelector(
    selectedType: ESGTrackerType?,
    onTypeSelected: (ESGTrackerType) -> Unit
) {
    var showDialog by remember { mutableStateOf(false) }
    
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) { showDialog = true },
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent
        ),
        border = androidx.compose.foundation.BorderStroke(
            width = 1.dp,
            color = colorResource(id = R.color.border_card)
        ),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = "Activity Type",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = selectedType?.let { type ->
                        when (type) {
                            ESGTrackerType.ENERGY_EFFICIENCY -> "Energy Efficiency"
                            ESGTrackerType.WASTE_REDUCTION -> "Waste Reduction"
                            ESGTrackerType.WATER_CONSERVATION -> "Water Conservation"
                            ESGTrackerType.RENEWABLE_ENERGY -> "Renewable Energy"
                            ESGTrackerType.CARBON_REDUCTION -> "Carbon Reduction"
                            ESGTrackerType.POLLUTION_CONTROL -> "Pollution Control"
                            ESGTrackerType.BIODIVERSITY -> "Biodiversity"
                            ESGTrackerType.SUSTAINABLE_MATERIALS -> "Sustainable Materials"
                            ESGTrackerType.EMPLOYEE_TRAINING -> "Employee Training"
                            ESGTrackerType.SAFETY_IMPROVEMENT -> "Safety Improvement"
                            ESGTrackerType.COMMUNITY_OUTREACH -> "Community Outreach"
                            ESGTrackerType.DIVERSITY_INCLUSION -> "Diversity & Inclusion"
                            ESGTrackerType.WORK_LIFE_BALANCE -> "Work-Life Balance"
                            ESGTrackerType.HEALTH_WELLNESS -> "Health & Wellness"
                            ESGTrackerType.EDUCATION_SUPPORT -> "Education Support"
                            ESGTrackerType.LOCAL_DEVELOPMENT -> "Local Development"
                            ESGTrackerType.POLICY_UPDATE -> "Policy Update"
                            ESGTrackerType.COMPLIANCE_AUDIT -> "Compliance Audit"
                            ESGTrackerType.RISK_MANAGEMENT -> "Risk Management"
                            ESGTrackerType.TRANSPARENCY_REPORT -> "Transparency Report"
                            ESGTrackerType.STAKEHOLDER_ENGAGEMENT -> "Stakeholder Engagement"
                            ESGTrackerType.ETHICS_TRAINING -> "Ethics Training"
                            ESGTrackerType.BOARD_DIVERSITY -> "Board Diversity"
                            ESGTrackerType.SUSTAINABILITY_STRATEGY -> "Sustainability Strategy"
                        }
                    } ?: "Select activity type",
                    style = MaterialTheme.typography.bodyLarge,
                    color = if (selectedType != null) 
                        MaterialTheme.colorScheme.onSurface 
                    else 
                        colorResource(id = R.color.text_secondary)
                )
            }
            Icon(
                Icons.Default.ArrowDropDown,
                contentDescription = "Chọn loại hoạt động",
                tint = colorResource(id = R.color.text_secondary)
            )
        }
    }
    
    if (showDialog) {
        ActivityTypeDialog(
            selectedType = selectedType,
            onTypeSelected = { type ->
                onTypeSelected(type)
                showDialog = false
            },
            onDismiss = { showDialog = false }
        )
    }
}

@Composable
fun ActivityTypeDialog(
    selectedType: ESGTrackerType?,
    onTypeSelected: (ESGTrackerType) -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = Color.White,
        title = {
            Text(
                text = "Chọn loại hoạt động",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
        },
        text = {
            LazyColumn(
                modifier = Modifier.height(400.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(ESGTrackerType.values().toList()) { type ->
                    ActivityTypeDialogItem(
                        type = type,
                        isSelected = type == selectedType,
                        onClick = { onTypeSelected(type) }
                    )
                }
            }
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text(
                    "Đóng",
                    color = colorResource(id = R.color.text_secondary)
                )
            }
        }
    )
}

@Composable
fun ActivityTypeDialogItem(
    type: ESGTrackerType,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) { onClick() },
        color = if (isSelected) 
            colorResource(id = R.color.interactive_primary).copy(alpha = 0.1f)
        else 
            Color.Transparent,
        shape = RoundedCornerShape(8.dp),
        border = androidx.compose.foundation.BorderStroke(
            width = 1.dp,
            color = if (isSelected) 
                colorResource(id = R.color.interactive_primary) 
            else 
                colorResource(id = R.color.border_card)
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = when (type) {
                    ESGTrackerType.ENERGY_EFFICIENCY -> "Tiết kiệm năng lượng"
                    ESGTrackerType.WASTE_REDUCTION -> "Giảm rác thải"
                    ESGTrackerType.WATER_CONSERVATION -> "Tiết kiệm nước"
                    ESGTrackerType.RENEWABLE_ENERGY -> "Năng lượng tái tạo"
                    ESGTrackerType.CARBON_REDUCTION -> "Giảm phát thải"
                    ESGTrackerType.POLLUTION_CONTROL -> "Kiểm soát ô nhiễm"
                    ESGTrackerType.BIODIVERSITY -> "Đa dạng sinh học"
                    ESGTrackerType.SUSTAINABLE_MATERIALS -> "Vật liệu bền vững"
                    ESGTrackerType.EMPLOYEE_TRAINING -> "Đào tạo nhân viên"
                    ESGTrackerType.SAFETY_IMPROVEMENT -> "Cải thiện an toàn"
                    ESGTrackerType.COMMUNITY_OUTREACH -> "Hỗ trợ cộng đồng"
                    ESGTrackerType.DIVERSITY_INCLUSION -> "Đa dạng & hòa nhập"
                    ESGTrackerType.WORK_LIFE_BALANCE -> "Cân bằng công việc"
                    ESGTrackerType.HEALTH_WELLNESS -> "Sức khỏe & phúc lợi"
                    ESGTrackerType.EDUCATION_SUPPORT -> "Hỗ trợ giáo dục"
                    ESGTrackerType.LOCAL_DEVELOPMENT -> "Phát triển địa phương"
                    ESGTrackerType.POLICY_UPDATE -> "Cập nhật chính sách"
                    ESGTrackerType.COMPLIANCE_AUDIT -> "Kiểm toán tuân thủ"
                    ESGTrackerType.RISK_MANAGEMENT -> "Quản lý rủi ro"
                    ESGTrackerType.TRANSPARENCY_REPORT -> "Báo cáo minh bạch"
                    ESGTrackerType.STAKEHOLDER_ENGAGEMENT -> "Tương tác bên liên quan"
                    ESGTrackerType.ETHICS_TRAINING -> "Đào tạo đạo đức"
                    ESGTrackerType.BOARD_DIVERSITY -> "Đa dạng hội đồng"
                    ESGTrackerType.SUSTAINABILITY_STRATEGY -> "Chiến lược bền vững"
                },
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                color = if (isSelected) 
                    colorResource(id = R.color.interactive_primary)
                else 
                    MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.weight(1f)
            )
            if (isSelected) {
                Icon(
                    Icons.Default.Check,
                    contentDescription = "Đã chọn",
                    tint = colorResource(id = R.color.interactive_primary)
                )
            }
        }
    }
}

@Composable
fun PillarSelectionRow(
    selectedPillar: ESGPillar?,
    onPillarSelected: (ESGPillar) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        ESGPillar.values().forEach { pillar ->
            PillarSelectionCard(
                pillar = pillar,
                isSelected = pillar == selectedPillar,
                onClick = { onPillarSelected(pillar) },
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
fun PillarSelectionCard(
    pillar: ESGPillar,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val (title, color) = when (pillar) {
        ESGPillar.ENVIRONMENTAL -> "Môi trường" to Color(0xFF4CAF50)
        ESGPillar.SOCIAL -> "Xã hội" to Color(0xFF2196F3)
        ESGPillar.GOVERNANCE -> "Quản trị" to Color(0xFF9C27B0)
    }
    
    Surface(
        modifier = modifier
            .height(60.dp)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) { onClick() },
        color = if (isSelected) 
            colorResource(id = R.color.interactive_primary) 
        else 
            Color.Transparent,
        shape = RoundedCornerShape(12.dp),
        border = androidx.compose.foundation.BorderStroke(
            width = 1.dp,
            color = if (isSelected) 
                colorResource(id = R.color.interactive_primary) 
            else 
                colorResource(id = R.color.border_card)
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.bodySmall,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                color = if (isSelected) 
                    Color.White 
                else 
                    MaterialTheme.colorScheme.onSurface,
                textAlign = androidx.compose.ui.text.style.TextAlign.Center
            )
        }
    }
}

@Composable
fun PrioritySelectionRow(
    selectedPriority: TrackerPriority?,
    onPrioritySelected: (TrackerPriority) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        TrackerPriority.values().forEach { priority ->
            PriorityChip(
                priority = priority,
                isSelected = priority == selectedPriority,
                onClick = { onPrioritySelected(priority) },
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
fun PriorityChip(
    priority: TrackerPriority,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val (text, color) = when (priority) {
        TrackerPriority.LOW -> "Thấp" to Color(0xFF4CAF50)
        TrackerPriority.MEDIUM -> "Trung bình" to Color(0xFFFF9800)
        TrackerPriority.HIGH -> "Cao" to Color(0xFFFF5722)
        TrackerPriority.CRITICAL -> "Khẩn cấp" to Color(0xFFF44336)
    }
    
    Surface(
        modifier = modifier
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) { onClick() },
        color = if (isSelected) 
            colorResource(id = R.color.interactive_primary) 
        else 
            Color.Transparent,
        shape = RoundedCornerShape(20.dp),
        border = androidx.compose.foundation.BorderStroke(
            width = 1.dp,
            color = if (isSelected) 
                colorResource(id = R.color.interactive_primary) 
            else 
                colorResource(id = R.color.border_card)
        )
    ) {
        Text(
            text = text,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
            color = if (isSelected) 
                Color.White 
            else 
                MaterialTheme.colorScheme.onSurface,
            textAlign = androidx.compose.ui.text.style.TextAlign.Center
        )
    }
}

@Composable
fun DateSelectionCard(
    selectedDate: Long,
    onDateSelected: (Long) -> Unit
) {
    val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    var showDatePicker by remember { mutableStateOf(false) }
    
    OutlinedTextField(
        value = dateFormat.format(Date(selectedDate)),
        onValueChange = { },
        label = { Text("Ngày dự kiến thực hiện") },
        readOnly = true,
        modifier = Modifier
            .fillMaxWidth()
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) { showDatePicker = true },
        trailingIcon = {
            Icon(
                Icons.Default.CalendarToday,
                contentDescription = "Chọn ngày",
                tint = colorResource(id = R.color.text_secondary)
            )
        },
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = colorResource(id = R.color.interactive_primary),
            unfocusedBorderColor = colorResource(id = R.color.border_card),
            focusedLabelColor = colorResource(id = R.color.interactive_primary),
            unfocusedLabelColor = MaterialTheme.colorScheme.onSurfaceVariant
        )
    )
    
    if (showDatePicker) {
        // TODO: Implement date picker dialog
    }
}
