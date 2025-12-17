package com.ignitech.esgcompanion.presentation.screen.instructor

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.hilt.navigation.compose.hiltViewModel
import com.ignitech.esgcompanion.R
import com.ignitech.esgcompanion.presentation.viewmodel.CreateQuestionViewModel
import com.ignitech.esgcompanion.ui.theme.*
import com.ignitech.esgcompanion.utils.AppColors

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateQuestionScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: CreateQuestionViewModel = hiltViewModel()
) {
    var questionText by remember { mutableStateOf("") }
    var selectedType by remember { mutableStateOf("") }
    var selectedDifficulty by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf("") }
    var points by remember { mutableStateOf("") }
    var explanation by remember { mutableStateOf("") }
    var showTypeDropdown by remember { mutableStateOf(false) }
    var showDifficultyDropdown by remember { mutableStateOf(false) }
    var showCategoryDropdown by remember { mutableStateOf(false) }
    val colors = AppColors()
    
    // Collect UI state from ViewModel
    val uiState by viewModel.uiState.collectAsState()
    
    // Handle success state
    LaunchedEffect(uiState.isSuccess) {
        if (uiState.isSuccess) {
            // Show success message and navigate back
            navController.popBackStack()
            viewModel.resetSuccess()
        }
    }
    
    // Handle error state
    LaunchedEffect(uiState.error) {
        uiState.error?.let { error ->
            // TODO: Show error snackbar or dialog
            viewModel.clearError()
        }
    }

    val questionTypes = listOf("Multiple Choice", "Essay", "True/False", "Fill in Blank", "Matching")
    val difficulties = listOf("Easy", "Medium", "Hard", "Very Hard")
    val categories = listOf("Environmental", "Social", "Governance", "General", "Practical")
    val questionTemplates = getQuestionTemplates()

    Column(
        modifier = modifier.fillMaxSize()
    ) {
        // Header
        TopAppBar(
            title = { 
                Text(
                    text = "Create Question",
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

        // Question Type Selection
        item {
            Text(
                text = "Question Type",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
        }

        item {
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(questionTypes) { type ->
                    QuestionTypeChip(
                        text = type,
                        selected = selectedType == type,
                        onClick = { selectedType = type }
                    )
                }
            }
        }

        // Question Settings
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

                // Category Selection
                Box(modifier = Modifier.weight(1f)) {
                    OutlinedTextField(
                        value = selectedCategory,
                        onValueChange = { selectedCategory = it },
                        label = { Text("Category") },
                        placeholder = { 
                            Text(
                                "Select category",
                                color = colorResource(id = R.color.text_secondary)
                            ) 
                        },
                        readOnly = true,
                        trailingIcon = { 
                            IconButton(onClick = { showCategoryDropdown = true }) {
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
                        expanded = showCategoryDropdown,
                        onDismissRequest = { showCategoryDropdown = false }
                    ) {
                        categories.forEach { category ->
                            DropdownMenuItem(
                                text = { Text(category) },
                                onClick = {
                                    selectedCategory = category
                                    showCategoryDropdown = false
                                }
                            )
                        }
                    }
                }
            }
        }

        item {
            OutlinedTextField(
                value = points,
                onValueChange = { points = it },
                label = { Text("Points") },
                placeholder = { 
                    Text(
                        "1",
                        color = colorResource(id = R.color.text_secondary)
                    ) 
                },
                modifier = Modifier.fillMaxWidth(),
                leadingIcon = { 
                    Icon(
                        Icons.Default.Grade, 
                        contentDescription = null,
                        tint = colorResource(id = R.color.interactive_primary)
                    ) 
                },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = colorResource(id = R.color.interactive_primary),
                    unfocusedBorderColor = colorResource(id = R.color.border_card)
                )
            )
        }

        // Question Content
        item {
            Text(
                text = "Question Content",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
        }

        item {
            OutlinedTextField(
                value = questionText,
                onValueChange = { questionText = it },
                label = { Text("Question") },
                placeholder = { 
                    Text(
                        "Enter question content...",
                        color = colorResource(id = R.color.text_secondary)
                    ) 
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp),
                minLines = 3,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = colorResource(id = R.color.interactive_primary),
                    unfocusedBorderColor = colorResource(id = R.color.border_card)
                )
            )
        }

        // Answer Options (for multiple choice)
        if (selectedType == "Multiple Choice") {
            item {
                Text(
                    text = "Answer Options",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }

            item {
                MultipleChoiceOptions()
            }
        }

        // True/False Options
        if (selectedType == "True/False") {
            item {
                Text(
                    text = "Answer Selection",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }

            item {
                TrueFalseOptions()
            }
        }

        // Fill in the blank
        if (selectedType == "Fill in Blank") {
            item {
                Text(
                    text = "Words to Fill",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }

            item {
                FillInBlankOptions()
            }
        }

        // Explanation
        item {
            Text(
                text = "Answer Explanation",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
        }

        item {
            OutlinedTextField(
                value = explanation,
                onValueChange = { explanation = it },
                label = { Text("Explanation") },
                placeholder = { 
                    Text(
                        "Explain why this answer is correct...",
                        color = colorResource(id = R.color.text_secondary)
                    ) 
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp),
                minLines = 2,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = colorResource(id = R.color.interactive_primary),
                    unfocusedBorderColor = colorResource(id = R.color.border_card)
                )
            )
        }

        // Question Tags
        item {
            Text(
                text = "Tags",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
        }

        item {
            QuestionTagsSection()
        }

        // Question Templates
        item {
            Text(
                text = "Question Templates",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
        }

        items(questionTemplates) { template ->
            QuestionTemplateCard(
                template = template,
                onClick = { /* Apply template */ }
            )
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
                    onClick = { 
                        if (questionText.isNotBlank() && selectedType.isNotBlank() && 
                            selectedDifficulty.isNotBlank() && selectedCategory.isNotBlank() && 
                            points.isNotBlank()) {
                            
                            val options = when (selectedType) {
                                "Multiple Choice" -> listOf("A", "B", "C", "D") // TODO: Get from MultipleChoiceOptions
                                "True/False" -> listOf("True", "False")
                                "Fill in Blank" -> listOf("") // TODO: Get from FillInBlankOptions
                                else -> emptyList()
                            }
                            
                            val correctAnswer = when (selectedType) {
                                "Multiple Choice" -> "A" // TODO: Get from MultipleChoiceOptions
                                "True/False" -> "True" // TODO: Get from TrueFalseOptions
                                "Fill in Blank" -> "" // TODO: Get from FillInBlankOptions
                                else -> ""
                            }
                            
                            val tags = listOf("ESG", "Sustainable", "Environmental") // TODO: Get from QuestionTagsSection
                            
                            viewModel.createQuestion(
                                questionText = questionText,
                                type = selectedType,
                                difficulty = selectedDifficulty,
                                category = selectedCategory,
                                points = points.toIntOrNull() ?: 1,
                                explanation = explanation,
                                authorId = "instructor_001", // TODO: Get from current user
                                authorName = "Instructor", // TODO: Get from current user
                                options = options,
                                correctAnswer = correctAnswer,
                                tags = tags,
                                hint = ""
                            )
                        }
                    },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorResource(id = R.color.interactive_primary)
                    ),
                    enabled = !uiState.isLoading && questionText.isNotBlank() && 
                             selectedType.isNotBlank() && selectedDifficulty.isNotBlank() && 
                             selectedCategory.isNotBlank() && points.isNotBlank()
                ) {
                    if (uiState.isLoading) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(16.dp),
                            color = Color.White
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                    }
                    Text(if (uiState.isLoading) "Saving..." else "Save Question")
                }
            }
        }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuestionTypeChip(
    text: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    FilterChip(
        onClick = onClick,
        label = { 
            Text(
                text = text,
                fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal
            ) 
        },
        selected = selected,
        leadingIcon = if (selected) {
            { 
                Icon(
                    Icons.Default.Check, 
                    contentDescription = null, 
                    modifier = Modifier.size(18.dp),
                    tint = Color.White
                ) 
            }
        } else null,
        colors = FilterChipDefaults.filterChipColors(
            selectedContainerColor = colorResource(id = R.color.interactive_primary),
            selectedLabelColor = Color.White,
            containerColor = Color.Transparent,
            labelColor = colorResource(id = R.color.text_secondary)
        ),
        border = FilterChipDefaults.filterChipBorder(
            selectedBorderColor = colorResource(id = R.color.interactive_primary),
            borderColor = colorResource(id = R.color.border_card),
            selectedBorderWidth = 1.dp,
            borderWidth = 1.dp
        )
    )
}

@Composable
fun MultipleChoiceOptions() {
    var options by remember { mutableStateOf(listOf("", "", "", "")) }
    var correctAnswer by remember { mutableStateOf(0) }

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        options.forEachIndexed { index, option ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = correctAnswer == index,
                    onClick = { correctAnswer = index }
                )
                Spacer(modifier = Modifier.width(8.dp))
                OutlinedTextField(
                    value = option,
                    onValueChange = { newValue ->
                        options = options.toMutableList().apply { set(index, newValue) }
                    },
                    label = { Text("Option ${('A' + index)}") },
                    modifier = Modifier.weight(1f),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = colorResource(id = R.color.interactive_primary),
                        unfocusedBorderColor = colorResource(id = R.color.border_card)
                    )
                )
                if (options.size > 2) {
                    IconButton(
                        onClick = {
                            options = options.filterIndexed { i, _ -> i != index }
                        }
                    ) {
                        Icon(Icons.Default.Delete, contentDescription = "Delete")
                    }
                }
            }
        }
        
        if (options.size < 6) {
            Button(
                onClick = {
                    options = options + ""
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(id = R.color.interactive_primary)
                )
            ) {
                Icon(Icons.Default.Add, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Add Option")
            }
        }
    }
}

@Composable
fun TrueFalseOptions() {
    var correctAnswer by remember { mutableStateOf(true) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .selectableGroup()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .selectable(
                    selected = correctAnswer,
                    onClick = { correctAnswer = true }
                ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            RadioButton(
                selected = correctAnswer,
                onClick = { correctAnswer = true }
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text("True")
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .selectable(
                    selected = !correctAnswer,
                    onClick = { correctAnswer = false }
                ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            RadioButton(
                selected = !correctAnswer,
                onClick = { correctAnswer = false }
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text("Sai")
        }
    }
}

@Composable
fun FillInBlankOptions() {
    var answers by remember { mutableStateOf(listOf("")) }

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        answers.forEachIndexed { index, answer ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = answer,
                    onValueChange = { newValue ->
                        answers = answers.toMutableList().apply { set(index, newValue) }
                    },
                    label = { Text("Answer ${index + 1}") },
                    modifier = Modifier.weight(1f),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = colorResource(id = R.color.interactive_primary),
                        unfocusedBorderColor = colorResource(id = R.color.border_card)
                    )
                )
                if (answers.size > 1) {
                    IconButton(
                        onClick = {
                            answers = answers.filterIndexed { i, _ -> i != index }
                        }
                    ) {
                        Icon(Icons.Default.Delete, contentDescription = "Delete")
                    }
                }
            }
        }
        
        Button(
            onClick = {
                answers = answers + ""
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = colorResource(id = R.color.interactive_primary)
            )
        ) {
            Icon(Icons.Default.Add, contentDescription = null)
            Spacer(modifier = Modifier.width(8.dp))
            Text("Add Answer")
        }
    }
}

@Composable
fun QuestionTagsSection() {
    var tags by remember { mutableStateOf(listOf("ESG", "Sustainable", "Environmental")) }
    var newTag by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(tags) { tag ->
                AssistChip(
                    onClick = {
                        tags = tags.filter { it != tag }
                    },
                    label = { Text(tag) },
                    trailingIcon = {
                        Icon(
                            Icons.Default.Close, 
                            contentDescription = "Delete", 
                            modifier = Modifier.size(16.dp),
                            tint = colorResource(id = R.color.text_secondary)
                        )
                    },
                    colors = AssistChipDefaults.assistChipColors(
                        containerColor = Color.Transparent,
                        labelColor = colorResource(id = R.color.text_secondary)
                    ),
                    border = AssistChipDefaults.assistChipBorder(
                        borderColor = colorResource(id = R.color.border_card),
                        borderWidth = 1.dp
                    )
                )
            }
        }
        
        Spacer(modifier = Modifier.height(12.dp))
        
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = newTag,
                onValueChange = { newTag = it },
                label = { Text("Add tag") },
                placeholder = { 
                    Text(
                        "Enter new tag",
                        color = colorResource(id = R.color.text_secondary)
                    ) 
                },
                modifier = Modifier.weight(1f),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = colorResource(id = R.color.interactive_primary),
                    unfocusedBorderColor = colorResource(id = R.color.border_card)
                )
            )
            Spacer(modifier = Modifier.width(12.dp))
            Button(
                onClick = {
                    if (newTag.isNotBlank()) {
                        tags = tags + newTag
                        newTag = ""
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(id = R.color.interactive_primary)
                )
            ) {
                Text("Add")
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuestionTemplateCard(
    template: QuestionTemplate,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        onClick = onClick,
        colors = CardDefaults.cardColors(containerColor = Color.Transparent),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        shape = RoundedCornerShape(16.dp),
        border = androidx.compose.foundation.BorderStroke(
            width = 1.dp,
            color = colorResource(id = R.color.border_card)
        )
    ) {
        Row(
            modifier = Modifier.padding(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = template.icon,
                contentDescription = null,
                tint = template.color,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = template.name,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = template.description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = colorResource(id = R.color.text_secondary)
                )
            }
            Column(
                horizontalAlignment = Alignment.End
            ) {
                Text(
                    text = template.type,
                    style = MaterialTheme.typography.bodyMedium,
                    color = colorResource(id = R.color.interactive_primary),
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = template.difficulty,
                    style = MaterialTheme.typography.bodySmall,
                    color = colorResource(id = R.color.text_secondary)
                )
            }
        }
    }
}

// Data classes
data class QuestionTemplate(
    val name: String,
    val description: String,
    val type: String,
    val difficulty: String,
    val icon: androidx.compose.ui.graphics.vector.ImageVector,
    val color: Color
)

// Mock data
@Composable
private fun getQuestionTemplates(): List<QuestionTemplate> {
    return listOf(
        QuestionTemplate(
            name = "Basic ESG Question Template",
            description = "Multiple choice question about ESG",
            type = "Multiple Choice",
            difficulty = "Easy",
            icon = Icons.Default.Quiz,
            color = colorResource(id = R.color.interactive_primary)
        ),
        QuestionTemplate(
            name = "Practical Question Template",
            description = "Essay question about ESG application",
            type = "Essay",
            difficulty = "Hard",
            icon = Icons.Default.Edit,
            color = colorResource(id = R.color.interactive_primary)
        ),
        QuestionTemplate(
            name = "True/False Question Template",
            description = "True/false question about ESG regulations",
            type = "True/False",
            difficulty = "Medium",
            icon = Icons.Default.CheckCircle,
            color = colorResource(id = R.color.interactive_primary)
        )
    )
}