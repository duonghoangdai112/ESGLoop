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
import com.ignitech.esgcompanion.presentation.viewmodel.QuestionLibraryViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuestionLibraryScreen(
    navController: NavController,
    onQuestionsSelected: (Set<String>) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: QuestionLibraryViewModel = hiltViewModel()
) {
    val searchQuery by viewModel.searchQuery.collectAsState()
    val selectedType by viewModel.selectedType.collectAsState()
    val selectedDifficulty by viewModel.selectedDifficulty.collectAsState()
    val selectedQuestions by viewModel.selectedQuestions.collectAsState()
    val questions by viewModel.questions.collectAsState()
    val types by viewModel.types.collectAsState()
    val difficulties by viewModel.difficulties.collectAsState()

    var showTypeDropdown by remember { mutableStateOf(false) }
    var showDifficultyDropdown by remember { mutableStateOf(false) }

    Column(
        modifier = modifier.fillMaxSize()
    ) {
        // Header
        TopAppBar(
            title = { 
                Text(
                    text = "Question Library",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
            },
            navigationIcon = {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                }
            },
            actions = {
                if (selectedQuestions.isNotEmpty()) {
                    TextButton(
                        onClick = { 
                            onQuestionsSelected(selectedQuestions)
                            navController.popBackStack()
                        }
                    ) {
                        Text(
                            "Select (${selectedQuestions.size})",
                            color = colorResource(id = R.color.interactive_primary)
                        )
                    }
                }
            }
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Search and Filters
            item {
                Column(
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    // Search Field
                    OutlinedTextField(
                        value = searchQuery,
                        onValueChange = { viewModel.updateSearchQuery(it) },
                        label = { Text("Search questions") },
                        leadingIcon = { 
                            Icon(
                                Icons.Default.Search, 
                                contentDescription = null,
                                tint = colorResource(id = R.color.interactive_primary)
                            ) 
                        },
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = colorResource(id = R.color.interactive_primary),
                            unfocusedBorderColor = colorResource(id = R.color.border_card)
                        )
                    )
                    
                    // Filter Row
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        // Type Filter
                        Box(modifier = Modifier.weight(1f)) {
                        OutlinedTextField(
                            value = selectedType,
                            onValueChange = { viewModel.updateSelectedType(it) },
                                label = { Text("Type") },
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
                                        viewModel.updateSelectedType(type)
                                        showTypeDropdown = false
                                    }
                                )
                            }
                            }
                        }

                        // Difficulty Filter
                        Box(modifier = Modifier.weight(1f)) {
                        OutlinedTextField(
                            value = selectedDifficulty,
                            onValueChange = { viewModel.updateSelectedDifficulty(it) },
                                label = { Text("Difficulty") },
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
                                        viewModel.updateSelectedDifficulty(difficulty)
                                        showDifficultyDropdown = false
                                    }
                                )
                            }
                            }
                        }
                    }
                }
            }

            // Selected Questions Summary
            if (selectedQuestions.isNotEmpty()) {
                item {
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
                                text = "Selected questions: ${selectedQuestions.size}",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            LazyRow(
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                            items(selectedQuestions.toList()) { questionId ->
                                val question = questions.find { it.id == questionId }
                                question?.let {
                                    AssistChip(
                                        onClick = {
                                            viewModel.toggleQuestionSelection(questionId)
                                        },
                                            label = {
                                                Text(
                                                    text = it.title,
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
                    }
                }
            }

            // Questions List
            items(questions) { question ->
                QuestionLibraryItem(
                    question = question,
                    isSelected = selectedQuestions.contains(question.id),
                    onToggleSelection = { questionId ->
                        viewModel.toggleQuestionSelection(questionId)
                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuestionLibraryItem(
    question: com.ignitech.esgcompanion.data.local.entity.QuestionEntity,
    isSelected: Boolean,
    onToggleSelection: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) { onToggleSelection(question.id) },
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) 
                colorResource(id = R.color.interactive_primary).copy(alpha = 0.1f)
            else 
                Color.Transparent
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        shape = RoundedCornerShape(16.dp),
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
            // Checkbox
            Checkbox(
                checked = isSelected,
                onCheckedChange = { onToggleSelection(question.id) },
                colors = CheckboxDefaults.colors(
                    checkedColor = colorResource(id = R.color.interactive_primary)
                )
            )
            
            Spacer(modifier = Modifier.width(12.dp))
            
            // Question Content
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = question.title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                
                Spacer(modifier = Modifier.height(8.dp))
                
                // Tags
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    item {
                        AssistChip(
                            onClick = { },
                            label = { Text(question.type) },
                            colors = AssistChipDefaults.assistChipColors(
                                containerColor = colorResource(id = R.color.interactive_primary).copy(alpha = 0.1f),
                                labelColor = colorResource(id = R.color.interactive_primary)
                            ),
                            border = AssistChipDefaults.assistChipBorder(
                                borderColor = colorResource(id = R.color.interactive_primary).copy(alpha = 0.3f)
                            )
                        )
                    }
                    item {
                        AssistChip(
                            onClick = { },
                            label = { Text(question.difficulty) },
                            colors = AssistChipDefaults.assistChipColors(
                                containerColor = colorResource(id = R.color.text_secondary).copy(alpha = 0.1f),
                                labelColor = colorResource(id = R.color.text_secondary)
                            ),
                            border = AssistChipDefaults.assistChipBorder(
                                borderColor = colorResource(id = R.color.text_secondary).copy(alpha = 0.3f)
                            )
                        )
                    }
                    item {
                        AssistChip(
                            onClick = { },
                            label = { Text(question.category) },
                            colors = AssistChipDefaults.assistChipColors(
                                containerColor = colorResource(id = R.color.border_card).copy(alpha = 0.1f),
                                labelColor = colorResource(id = R.color.text_secondary)
                            ),
                            border = AssistChipDefaults.assistChipBorder(
                                borderColor = colorResource(id = R.color.border_card)
                            )
                        )
                    }
                }
            }
        }
    }
}

