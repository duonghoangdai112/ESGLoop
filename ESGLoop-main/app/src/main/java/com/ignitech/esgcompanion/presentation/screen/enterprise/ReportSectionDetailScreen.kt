package com.ignitech.esgcompanion.presentation.screen.enterprise

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ignitech.esgcompanion.domain.entity.*
import com.ignitech.esgcompanion.presentation.viewmodel.ReportSectionDetailViewModel
import com.ignitech.esgcompanion.utils.AppColors
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReportSectionDetailScreen(
    navController: androidx.navigation.NavHostController,
    sectionId: String,
    template: ReportStandard,
    modifier: Modifier = Modifier,
    viewModel: ReportSectionDetailViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val colors = AppColors()

    LaunchedEffect(sectionId, template) {
        viewModel.loadSection(sectionId, template)
    }

    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = { 
                    Column {
                        Text(
                            text = uiState.section?.title ?: "Report Section Details",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = uiState.section?.description ?: "",
                            style = MaterialTheme.typography.bodySmall,
                            color = colors.textSecondary
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            Icons.Default.ArrowBack, 
                            contentDescription = "Back",
                            tint = colors.textPrimary
                        )
                    }
                },
                actions = {
                    // Removed save button from title bar
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = colors.backgroundSurface,
                    titleContentColor = colors.textPrimary
                )
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(colors.backgroundSurface),
            contentPadding = PaddingValues(
                top = paddingValues.calculateTopPadding() + 16.dp,
                start = 16.dp,
                end = 16.dp,
                bottom = paddingValues.calculateBottomPadding() + 16.dp
            ),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Section Info
            item {
                SectionInfoCard(section = uiState.section)
            }

            // Form Fields
            items(uiState.formFields) { field ->
                when (field.type) {
                    FieldType.TEXT -> TextFieldCard(
                        field = field,
                        onValueChange = { viewModel.updateFieldValue(field.id, it) }
                    )
                    FieldType.NUMBER -> NumberFieldCard(
                        field = field,
                        onValueChange = { viewModel.updateFieldValue(field.id, it) }
                    )
                    FieldType.DATE -> DateFieldCard(
                        field = field,
                        onValueChange = { viewModel.updateFieldValue(field.id, it) }
                    )
                    FieldType.SELECT -> SelectFieldCard(
                        field = field,
                        onValueChange = { viewModel.updateFieldValue(field.id, it) }
                    )
                    FieldType.MULTILINE_TEXT -> MultilineTextFieldCard(
                        field = field,
                        onValueChange = { viewModel.updateFieldValue(field.id, it) }
                    )
                    FieldType.CHECKBOX -> CheckboxFieldCard(
                        field = field,
                        onValueChange = { viewModel.updateFieldValue(field.id, it) }
                    )
                }
            }

            // Progress indicator
            item {
                ProgressCard(
                    completedFields = uiState.completedFields,
                    totalFields = uiState.formFields.size
                )
            }

            // Action Buttons
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    OutlinedButton(
                        onClick = { viewModel.saveDraft() },
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = Color(0xFFFF9800)
                        ),
                        border = BorderStroke(1.dp, Color(0xFFFF9800))
                    ) {
                        Icon(Icons.Default.Save, contentDescription = null, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Save Draft")
                    }
                    
                    Button(
                        onClick = { 
                            viewModel.completeSection()
                            navController.popBackStack()
                        },
                        modifier = Modifier.weight(1f),
                        enabled = uiState.completedFields == uiState.formFields.size
                    ) {
                        Icon(Icons.Default.Check, contentDescription = null, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Complete")
                    }
                }
            }
        }
    }
}

@Composable
fun SectionInfoCard(section: ReportSection?) {
    val colors = AppColors()
    
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = colors.primary.copy(alpha = 0.1f)),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = section?.title ?: "Report Section",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = colors.textPrimary
            )
            
            Text(
                text = section?.description ?: "",
                style = MaterialTheme.typography.bodyMedium,
                color = colors.textSecondary
            )
            
            if (!section?.requirements.isNullOrEmpty()) {
                Text(
                    text = "Requirements:",
                    style = MaterialTheme.typography.bodySmall,
                    fontWeight = FontWeight.Medium,
                    color = colors.textPrimary
                )
                
                section?.requirements?.forEach { requirement ->
                    Text(
                        text = "• $requirement",
                        style = MaterialTheme.typography.bodySmall,
                        color = colors.textSecondary
                    )
                }
            }
        }
    }
}

@Composable
fun TextFieldCard(
    field: FormField,
    onValueChange: (String) -> Unit
) {
    val colors = AppColors()
    
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = field.label,
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Medium,
                color = colors.textPrimary
            )
            
            if (field.description.isNotEmpty()) {
                Text(
                    text = field.description,
                    style = MaterialTheme.typography.bodySmall,
                    color = colors.textSecondary
                )
            }
            
            OutlinedTextField(
                value = field.value,
                onValueChange = onValueChange,
                modifier = Modifier.fillMaxWidth(),
                placeholder = { 
                    Text(
                        text = field.placeholder,
                        color = colors.textSecondary.copy(alpha = 0.6f)
                    ) 
                },
                isError = field.isRequired && field.value.isEmpty(),
                supportingText = if (field.isRequired && field.value.isEmpty()) {
                    { Text("This field is required", color = Color.Red) }
                } else null
            )
        }
    }
}

@Composable
fun NumberFieldCard(
    field: FormField,
    onValueChange: (String) -> Unit
) {
    val colors = AppColors()
    
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = field.label,
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Medium,
                color = colors.textPrimary
            )
            
            if (field.description.isNotEmpty()) {
                Text(
                    text = field.description,
                    style = MaterialTheme.typography.bodySmall,
                    color = colors.textSecondary
                )
            }
            
            OutlinedTextField(
                value = field.value,
                onValueChange = onValueChange,
                modifier = Modifier.fillMaxWidth(),
                placeholder = { 
                    Text(
                        text = field.placeholder,
                        color = colors.textSecondary.copy(alpha = 0.6f)
                    ) 
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                isError = field.isRequired && field.value.isEmpty(),
                supportingText = if (field.isRequired && field.value.isEmpty()) {
                    { Text("This field is required", color = Color.Red) }
                } else null
            )
        }
    }
}

@Composable
fun DateFieldCard(
    field: FormField,
    onValueChange: (String) -> Unit
) {
    val colors = AppColors()
    var showDatePicker by remember { mutableStateOf(false) }
    
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = field.label,
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Medium,
                color = colors.textPrimary
            )
            
            if (field.description.isNotEmpty()) {
                Text(
                    text = field.description,
                    style = MaterialTheme.typography.bodySmall,
                    color = colors.textSecondary
                )
            }
            
            OutlinedTextField(
                value = field.value,
                onValueChange = onValueChange,
                modifier = Modifier.fillMaxWidth(),
                placeholder = { 
                    Text(
                        text = field.placeholder,
                        color = colors.textSecondary.copy(alpha = 0.6f)
                    ) 
                },
                readOnly = true,
                trailingIcon = {
                    IconButton(onClick = { showDatePicker = true }) {
                        Icon(Icons.Default.DateRange, contentDescription = "Select date")
                    }
                },
                isError = field.isRequired && field.value.isEmpty(),
                supportingText = if (field.isRequired && field.value.isEmpty()) {
                    { Text("This field is required", color = Color.Red) }
                } else null
            )
        }
    }
    
    if (showDatePicker) {
        // TODO: Implement date picker
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectFieldCard(
    field: FormField,
    onValueChange: (String) -> Unit
) {
    val colors = AppColors()
    var expanded by remember { mutableStateOf(false) }
    
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = field.label,
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Medium,
                color = colors.textPrimary
            )
            
            if (field.description.isNotEmpty()) {
                Text(
                    text = field.description,
                    style = MaterialTheme.typography.bodySmall,
                    color = colors.textSecondary
                )
            }
            
            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded }
            ) {
                OutlinedTextField(
                    value = field.value,
                    onValueChange = onValueChange,
                    readOnly = true,
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor(),
                    placeholder = { 
                        Text(
                            text = field.placeholder,
                            color = colors.textSecondary.copy(alpha = 0.6f)
                        ) 
                    },
                    isError = field.isRequired && field.value.isEmpty(),
                    supportingText = if (field.isRequired && field.value.isEmpty()) {
                        { Text("This field is required", color = Color.Red) }
                    } else null
                )
                
                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    field.options.forEach { option ->
                        DropdownMenuItem(
                            text = { Text(option) },
                            onClick = {
                                onValueChange(option)
                                expanded = false
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun MultilineTextFieldCard(
    field: FormField,
    onValueChange: (String) -> Unit
) {
    val colors = AppColors()
    
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = field.label,
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Medium,
                color = colors.textPrimary
            )
            
            if (field.description.isNotEmpty()) {
                Text(
                    text = field.description,
                    style = MaterialTheme.typography.bodySmall,
                    color = colors.textSecondary
                )
            }
            
            OutlinedTextField(
                value = field.value,
                onValueChange = onValueChange,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp),
                placeholder = { 
                    Text(
                        text = field.placeholder,
                        color = colors.textSecondary.copy(alpha = 0.6f)
                    ) 
                },
                maxLines = 5,
                isError = field.isRequired && field.value.isEmpty(),
                supportingText = if (field.isRequired && field.value.isEmpty()) {
                    { Text("This field is required", color = Color.Red) }
                } else null
            )
        }
    }
}

@Composable
fun CheckboxFieldCard(
    field: FormField,
    onValueChange: (String) -> Unit
) {
    val colors = AppColors()
    val isChecked = field.value == "true"
    
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        shape = RoundedCornerShape(8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = isChecked,
                onCheckedChange = { onValueChange(it.toString()) }
            )
            
            Spacer(modifier = Modifier.width(12.dp))
            
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = field.label,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Medium,
                    color = colors.textPrimary
                )
                
                if (field.description.isNotEmpty()) {
                    Text(
                        text = field.description,
                        style = MaterialTheme.typography.bodySmall,
                        color = colors.textSecondary
                    )
                }
            }
        }
    }
}

@Composable
fun ProgressCard(
    completedFields: Int,
    totalFields: Int
) {
    val colors = AppColors()
    val progress = if (totalFields > 0) completedFields.toFloat() / totalFields else 0f
    
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = colors.backgroundSurface),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        shape = RoundedCornerShape(8.dp)
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
                    text = "Completion Progress",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Medium,
                    color = colors.textPrimary
                )
                
                Text(
                    text = "$completedFields/$totalFields",
                    style = MaterialTheme.typography.bodySmall,
                    color = colors.textSecondary
                )
            }
            
            LinearProgressIndicator(
                progress = progress,
                modifier = Modifier.fillMaxWidth(),
                color = colors.primary,
                trackColor = colors.textSecondary.copy(alpha = 0.3f)
            )
        }
    }
}

// Data classes
enum class FieldType {
    TEXT, NUMBER, DATE, SELECT, MULTILINE_TEXT, CHECKBOX
}

data class FormField(
    val id: String,
    val label: String,
    val description: String = "",
    val placeholder: String = "",
    val type: FieldType,
    val value: String = "",
    val isRequired: Boolean = false,
    val options: List<String> = emptyList()
)
