package com.ignitech.esgcompanion.presentation.screen.instructor

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.ignitech.esgcompanion.R
import com.ignitech.esgcompanion.ui.theme.*
import com.ignitech.esgcompanion.utils.AppColors

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateLessonScreen(
    navController: NavController,
    modifier: Modifier = Modifier
) {
    var lessonTitle by remember { mutableStateOf("") }
    var lessonDescription by remember { mutableStateOf("") }
    var selectedClass by remember { mutableStateOf("") }
    var selectedDuration by remember { mutableStateOf("") }
    var selectedDifficulty by remember { mutableStateOf("") }
    var lessonContent by remember { mutableStateOf("") }
    var showClassDropdown by remember { mutableStateOf(false) }
    var showDurationDropdown by remember { mutableStateOf(false) }
    var showDifficultyDropdown by remember { mutableStateOf(false) }

    val classes = listOf("ESG-101", "ESG-102", "ESG-201")
    val durations = listOf("30 phút", "45 phút", "60 phút", "90 phút", "120 phút")
    val difficulties = listOf("Cơ bản", "Trung bình", "Nâng cao")
    val lessonTemplates = getLessonTemplates()

    Column(
        modifier = modifier.fillMaxSize()
    ) {
        // Header
        TopAppBar(
            title = { 
                Text(
                    text = "Tạo bài giảng",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
            },
            navigationIcon = {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Quay lại")
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
                text = "Thông tin cơ bản",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
        }

        item {
            OutlinedTextField(
                value = lessonTitle,
                onValueChange = { lessonTitle = it },
                label = { Text("Tiêu đề bài giảng") },
                placeholder = { 
                    Text(
                        "Nhập tiêu đề bài giảng",
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
                value = lessonDescription,
                onValueChange = { lessonDescription = it },
                label = { Text("Mô tả bài giảng") },
                placeholder = { 
                    Text(
                        "Mô tả ngắn gọn về nội dung bài giảng",
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
                        label = { Text("Lớp học") },
                        placeholder = { 
                            Text(
                                "Chọn lớp",
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

                // Duration Selection
                Box(modifier = Modifier.weight(1f)) {
                    OutlinedTextField(
                        value = selectedDuration,
                        onValueChange = { selectedDuration = it },
                        label = { Text("Thời lượng") },
                        placeholder = { 
                            Text(
                                "Chọn thời lượng",
                                color = colorResource(id = R.color.text_secondary)
                            ) 
                        },
                        readOnly = true,
                        trailingIcon = { 
                            IconButton(onClick = { showDurationDropdown = true }) {
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
                        expanded = showDurationDropdown,
                        onDismissRequest = { showDurationDropdown = false }
                    ) {
                        durations.forEach { duration ->
                            DropdownMenuItem(
                                text = { Text(duration) },
                                onClick = {
                                    selectedDuration = duration
                                    showDurationDropdown = false
                                }
                            )
                        }
                    }
                }
            }
        }

        item {
            Box(modifier = Modifier.fillMaxWidth()) {
                OutlinedTextField(
                    value = selectedDifficulty,
                    onValueChange = { selectedDifficulty = it },
                    label = { Text("Độ khó") },
                    placeholder = { 
                        Text(
                            "Chọn độ khó",
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
        }

        // Lesson Content
        item {
            Text(
                text = "Nội dung bài giảng",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
        }

        item {
            OutlinedTextField(
                value = lessonContent,
                onValueChange = { lessonContent = it },
                label = { Text("Nội dung chi tiết") },
                placeholder = { 
                    Text(
                        "Nhập nội dung bài giảng chi tiết...",
                        color = colorResource(id = R.color.text_secondary)
                    ) 
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp),
                minLines = 6,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = colorResource(id = R.color.interactive_primary),
                    unfocusedBorderColor = colorResource(id = R.color.border_card)
                )
            )
        }

        // Media Upload
        item {
            Text(
                text = "Tài liệu đính kèm",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
        }

        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color.Transparent),
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
                        .padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Upload,
                        contentDescription = null,
                        modifier = Modifier.size(40.dp),
                        tint = colorResource(id = R.color.interactive_primary)
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = "Kéo thả file hoặc nhấn để chọn",
                        style = MaterialTheme.typography.bodyMedium,
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Hỗ trợ: PDF, PPT, DOC, MP4, MP3",
                        style = MaterialTheme.typography.bodySmall,
                        color = colorResource(id = R.color.text_secondary),
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(
                        onClick = { /* Handle file upload */ },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = colorResource(id = R.color.interactive_primary)
                        )
                    ) {
                        Text("Chọn file")
                    }
                }
            }
        }

        // Lesson Templates
        item {
            Text(
                text = "Mẫu bài giảng",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
        }

        items(lessonTemplates) { template ->
            LessonTemplateCard(
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
                    Text("Hủy")
                }
                Button(
                    onClick = { /* Save lesson */ },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorResource(id = R.color.interactive_primary)
                    )
                ) {
                    Text("Lưu bài giảng")
                }
            }
        }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LessonTemplateCard(
    template: LessonTemplate,
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
            Text(
                text = template.duration,
                style = MaterialTheme.typography.bodyMedium,
                color = colorResource(id = R.color.interactive_primary),
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

// Data classes
data class LessonTemplate(
    val name: String,
    val description: String,
    val duration: String,
    val icon: androidx.compose.ui.graphics.vector.ImageVector,
    val color: Color
)

// Mock data
@Composable
private fun getLessonTemplates(): List<LessonTemplate> {
    return listOf(
        LessonTemplate(
            name = "Mẫu bài giảng ESG cơ bản",
            description = "Cấu trúc chuẩn cho bài giảng ESG",
            duration = "45 phút",
            icon = Icons.Default.VideoLibrary,
            color = colorResource(id = R.color.interactive_primary)
        ),
        LessonTemplate(
            name = "Mẫu thuyết trình",
            description = "Template cho bài thuyết trình",
            duration = "30 phút",
            icon = Icons.Default.PresentToAll,
            color = colorResource(id = R.color.interactive_primary)
        ),
        LessonTemplate(
            name = "Mẫu thảo luận nhóm",
            description = "Template cho hoạt động thảo luận",
            duration = "60 phút",
            icon = Icons.Default.Group,
            color = colorResource(id = R.color.interactive_primary)
        )
    )
}