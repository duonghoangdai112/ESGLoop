package com.ignitech.esgcompanion.presentation.screen.instructor

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.hilt.navigation.compose.hiltViewModel
import com.ignitech.esgcompanion.R
import com.ignitech.esgcompanion.presentation.viewmodel.CreateAssignmentViewModel
import com.ignitech.esgcompanion.ui.theme.*
import com.ignitech.esgcompanion.utils.AppColors

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateAssignmentScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: CreateAssignmentViewModel = hiltViewModel()
) {
    var assignmentTitle by remember { mutableStateOf("") }
    var assignmentDescription by remember { mutableStateOf("") }
    var selectedClass by remember { mutableStateOf("") }
    var selectedType by remember { mutableStateOf("") }
    var selectedDifficulty by remember { mutableStateOf("") }
    var dueDate by remember { mutableStateOf("") }
    var maxScore by remember { mutableStateOf("") }
    var assignmentInstructions by remember { mutableStateOf("") }
    var selectedQuestions by remember { mutableStateOf(setOf<String>()) }
    var showClassDropdown by remember { mutableStateOf(false) }
    val colors = AppColors()
    var showTypeDropdown by remember { mutableStateOf(false) }
    var showDifficultyDropdown by remember { mutableStateOf(false) }

    val classes = listOf("ESG-101", "ESG-102", "ESG-201")
    val types = listOf("Individual Assignment", "Group Assignment", "Project", "Report", "Presentation")
    val difficulties = listOf("Easy", "Medium", "Hard", "Very Hard")

    Column(
        modifier = modifier.fillMaxSize()
    ) {
        // Header
        TopAppBar(
            title = { 
                Text(
                    text = "Create Assignment",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
            },
            navigationIcon = {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                }
            }
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

        // Basic Information
        item {
            Text(
                text = "Basic Information",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
        }

        item {
            OutlinedTextField(
                value = assignmentTitle,
                onValueChange = { assignmentTitle = it },
                label = { Text("Assignment Title") },
                placeholder = { 
                    Text(
                        "Enter assignment title",
                        color = colorResource(id = R.color.text_secondary)
                    ) 
                },
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = colorResource(id = R.color.interactive_primary),
                    unfocusedBorderColor = colorResource(id = R.color.border_card)
                )
            )
        }

        item {
            OutlinedTextField(
                value = assignmentDescription,
                onValueChange = { assignmentDescription = it },
                label = { Text("Assignment Description") },
                placeholder = { 
                    Text(
                        "Brief description of assignment requirements",
                        color = colorResource(id = R.color.text_secondary)
                    ) 
                },
                modifier = Modifier.fillMaxWidth(),
                minLines = 3,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = colorResource(id = R.color.interactive_primary),
                    unfocusedBorderColor = colorResource(id = R.color.border_card)
                )
            )
        }

        // Class and Settings
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Class Selection
                Box(modifier = Modifier.weight(1f)) {
                    OutlinedTextField(
                        value = selectedClass,
                        onValueChange = { selectedClass = it },
                        label = { Text("Class") },
                        placeholder = { 
                            Text(
                                "Select class",
                                color = colorResource(id = R.color.text_secondary)
                            ) 
                        },
                        readOnly = true,
                        trailingIcon = { 
                            IconButton(onClick = { showClassDropdown = true }) {
                                Icon(Icons.Default.ArrowDropDown, contentDescription = null)
                            }
                        },
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = colorResource(id = R.color.interactive_primary),
                            unfocusedBorderColor = colorResource(id = R.color.border_card)
                        )
                    )
                    
                    DropdownMenu(
                        expanded = showClassDropdown,
                        onDismissRequest = { showClassDropdown = false }
                    ) {
                        classes.forEach { className ->
                            DropdownMenuItem(
                                text = { Text(className) },
                                onClick = {
                                    selectedClass = className
                                    showClassDropdown = false
                                }
                            )
                        }
                    }
                }

                // Type Selection
                Box(modifier = Modifier.weight(1f)) {
                    OutlinedTextField(
                        value = selectedType,
                        onValueChange = { selectedType = it },
                        label = { Text("Assignment Type") },
                        placeholder = { 
                            Text(
                                "Select type",
                                color = colorResource(id = R.color.text_secondary)
                            ) 
                        },
                        readOnly = true,
                        trailingIcon = { 
                            IconButton(onClick = { showTypeDropdown = true }) {
                                Icon(Icons.Default.ArrowDropDown, contentDescription = null)
                            }
                        },
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = colorResource(id = R.color.interactive_primary),
                            unfocusedBorderColor = colorResource(id = R.color.border_card)
                        )
                    )
                    
                    DropdownMenu(
                        expanded = showTypeDropdown,
                        onDismissRequest = { showTypeDropdown = false }
                    ) {
                        types.forEach { type ->
                            DropdownMenuItem(
                                text = { Text(type) },
                                onClick = {
                                    selectedType = type
                                    showTypeDropdown = false
                                }
                            )
                        }
                    }
                }
            }
        }

        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Difficulty Selection
                Box(modifier = Modifier.weight(1f)) {
                    OutlinedTextField(
                        value = selectedDifficulty,
                        onValueChange = { selectedDifficulty = it },
                        label = { Text("Difficulty") },
                        placeholder = { 
                            Text(
                                "Select difficulty",
                                color = colorResource(id = R.color.text_secondary)
                            ) 
                        },
                        readOnly = true,
                        trailingIcon = { 
                            IconButton(onClick = { showDifficultyDropdown = true }) {
                                Icon(Icons.Default.ArrowDropDown, contentDescription = null)
                            }
                        },
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = colorResource(id = R.color.interactive_primary),
                            unfocusedBorderColor = colorResource(id = R.color.border_card)
                        )
                    )
                    
                    DropdownMenu(
                        expanded = showDifficultyDropdown,
                        onDismissRequest = { showDifficultyDropdown = false }
                    ) {
                        difficulties.forEach { difficulty ->
                            DropdownMenuItem(
                                text = { Text(difficulty) },
                                onClick = {
                                    selectedDifficulty = difficulty
                                    showDifficultyDropdown = false
                                }
                            )
                        }
                    }
                }

                // Max Score
                OutlinedTextField(
                    value = maxScore,
                    onValueChange = { maxScore = it },
                    label = { Text("Max Score") },
                    placeholder = { 
                    Text(
                        "100",
                            color = colorResource(id = R.color.text_secondary)
                    ) 
                },
                    modifier = Modifier.weight(1f),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = colorResource(id = R.color.interactive_primary),
                        unfocusedBorderColor = colorResource(id = R.color.border_card)
                    )
                )
            }
        }

        // Due Date
        item {
            OutlinedTextField(
                value = dueDate,
                onValueChange = { dueDate = it },
                label = { Text("Due Date") },
                placeholder = { 
                    Text(
                        "DD/MM/YYYY",
                        color = colorResource(id = R.color.text_secondary)
                    ) 
                },
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = colorResource(id = R.color.interactive_primary),
                    unfocusedBorderColor = colorResource(id = R.color.border_card)
                )
            )
        }

        // Assignment Instructions
        item {
            Text(
                text = "Detailed Instructions",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
        }

        item {
            OutlinedTextField(
                value = assignmentInstructions,
                onValueChange = { assignmentInstructions = it },
                label = { Text("Instructions") },
                placeholder = { 
                    Text(
                        "Enter detailed instructions for students...",
                        color = colorResource(id = R.color.text_secondary)
                    ) 
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp),
                minLines = 5,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = colorResource(id = R.color.interactive_primary),
                    unfocusedBorderColor = colorResource(id = R.color.border_card)
                )
            )
        }

        // Question Selection
        item {
            Text(
                text = "Select Questions from Library",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
        }

        item {
            QuestionSelectionSection(
                navController = navController,
                selectedQuestions = selectedQuestions,
                onQuestionsSelected = { selectedQuestions = it }
            )
        }

        // Assignment Requirements
        item {
            Text(
                text = "Assignment Requirements",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
        }

        item {
            AssignmentRequirementsSection()
        }

        // Action Buttons
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                OutlinedButton(
                    onClick = { navController.popBackStack() },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = colorResource(id = R.color.text_secondary)
                    ),
                    border = androidx.compose.foundation.BorderStroke(
                        width = 1.dp,
                        color = colorResource(id = R.color.border_card)
                    )
                ) {
                    Text("Cancel")
                }
                Button(
                    onClick = { /* Save assignment */ },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorResource(id = R.color.interactive_primary)
                    )
                ) {
                    Text("Create Assignment")
                }
            }
        }
        }
    }
}

@Composable
fun QuestionSelectionSection(
    navController: NavController,
    selectedQuestions: Set<String>,
    onQuestionsSelected: (Set<String>) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        // Selected Questions Summary
        if (selectedQuestions.isNotEmpty()) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = "Selected Questions: ${selectedQuestions.size}",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(selectedQuestions.toList()) { questionId ->
                            AssistChip(
                                onClick = {
                                    onQuestionsSelected(selectedQuestions - questionId)
                                },
                                label = {
                                    Text(
                                        text = "Question $questionId",
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis
                                    )
                                },
                                trailingIcon = {
                                    Icon(
                                        Icons.Default.Close,
                                        contentDescription = "Remove",
                                        modifier = Modifier.size(16.dp)
                                    )
                                }
                            )
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(12.dp))
        }

        // Question Library Button
        Button(
            onClick = { 
                // Navigate to QuestionLibraryScreen
                navController.navigate("question_library")
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = colorResource(id = R.color.interactive_primary)
            )
        ) {
            Icon(Icons.Default.LibraryBooks, contentDescription = null)
            Spacer(modifier = Modifier.width(8.dp))
            Text("Select Questions from Library")
        }
    }
}


@Composable
fun AssignmentRequirementsSection() {
    var wordCount by remember { mutableStateOf(false) }
    var fileFormat by remember { mutableStateOf(false) }
    var citation by remember { mutableStateOf(false) }
    var plagiarism by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .selectableGroup()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .selectable(
                    selected = wordCount,
                    onClick = { wordCount = !wordCount },
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = wordCount,
                onCheckedChange = { wordCount = it }
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text("Yêu cầu số từ tối thiểu")
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .selectable(
                    selected = fileFormat,
                    onClick = { fileFormat = !fileFormat },
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = fileFormat,
                onCheckedChange = { fileFormat = it }
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text("Yêu cầu định dạng file cụ thể")
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .selectable(
                    selected = citation,
                    onClick = { citation = !citation },
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = citation,
                onCheckedChange = { citation = it }
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text("Yêu cầu trích dẫn nguồn")
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .selectable(
                    selected = plagiarism,
                    onClick = { plagiarism = !plagiarism },
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = plagiarism,
                onCheckedChange = { plagiarism = it }
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text("Kiểm tra đạo văn")
        }
    }
}
