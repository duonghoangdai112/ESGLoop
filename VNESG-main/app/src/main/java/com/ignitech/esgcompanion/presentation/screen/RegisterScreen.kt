package com.ignitech.esgcompanion.presentation.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.compose.ui.res.colorResource
import com.ignitech.esgcompanion.R
import com.ignitech.esgcompanion.domain.entity.UserRole
import com.ignitech.esgcompanion.presentation.viewmodel.RegisterViewModel
import com.ignitech.esgcompanion.utils.AppColors

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(
    navController: NavController,
    viewModel: RegisterViewModel = hiltViewModel()
) {
    val colors = AppColors()
    val uiState by viewModel.uiState.collectAsState()
    val currentUser by viewModel.currentUser.collectAsState()

    LaunchedEffect(currentUser) {
        if (currentUser != null) {
            when (currentUser!!.role) {
                UserRole.ENTERPRISE -> navController.navigate("enterprise_home")
                UserRole.EXPERT -> navController.navigate("expert_home")
                UserRole.ACADEMIC -> navController.navigate("student_home")
                UserRole.INSTRUCTOR -> navController.navigate("instructor_home")
                UserRole.REGULATORY -> navController.navigate("regulatory_home")
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Register Account",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = colorResource(id = R.color.interactive_primary)
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Create your ESG Companion account",
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(32.dp))

        OutlinedTextField(
            value = uiState.name,
            onValueChange = viewModel::updateName,
            placeholder = { 
                Text(
                    text = "Full Name",
                    color = colors.textSecondary.copy(alpha = 0.6f)
                ) 
            },
            leadingIcon = {
                Icon(Icons.Default.Person, contentDescription = null)
            },
            isError = uiState.nameError.isNotEmpty(),
            supportingText = if (uiState.nameError.isNotEmpty()) {
                { Text(uiState.nameError) }
            } else null,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = uiState.email,
            onValueChange = viewModel::updateEmail,
            placeholder = { 
                Text(
                    text = "Email",
                    color = colors.textSecondary.copy(alpha = 0.6f)
                ) 
            },
            leadingIcon = {
                Icon(Icons.Default.Email, contentDescription = null)
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            isError = uiState.emailError.isNotEmpty(),
            supportingText = if (uiState.emailError.isNotEmpty()) {
                { Text(uiState.emailError) }
            } else null,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = uiState.password,
            onValueChange = viewModel::updatePassword,
            placeholder = { 
                Text(
                    text = "Password",
                    color = colors.textSecondary.copy(alpha = 0.6f)
                ) 
            },
            leadingIcon = {
                Icon(Icons.Default.Lock, contentDescription = null)
            },
            trailingIcon = {
                IconButton(onClick = { viewModel.togglePasswordVisibility() }) {
                    Icon(
                        if (uiState.isPasswordVisible) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                        contentDescription = if (uiState.isPasswordVisible) "Hide password" else "Show password"
                    )
                }
            },
            visualTransformation = if (uiState.isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            isError = uiState.passwordError.isNotEmpty(),
            supportingText = if (uiState.passwordError.isNotEmpty()) {
                { Text(uiState.passwordError) }
            } else null,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = uiState.confirmPassword,
            onValueChange = viewModel::updateConfirmPassword,
            placeholder = { 
                Text(
                    text = "Confirm Password",
                    color = colors.textSecondary.copy(alpha = 0.6f)
                ) 
            },
            leadingIcon = {
                Icon(Icons.Default.Lock, contentDescription = null)
            },
            trailingIcon = {
                IconButton(onClick = { viewModel.toggleConfirmPasswordVisibility() }) {
                    Icon(
                        if (uiState.isConfirmPasswordVisible) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                        contentDescription = if (uiState.isConfirmPasswordVisible) "Hide password" else "Show password"
                    )
                }
            },
            visualTransformation = if (uiState.isConfirmPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            isError = uiState.confirmPasswordError.isNotEmpty(),
            supportingText = if (uiState.confirmPasswordError.isNotEmpty()) {
                { Text(uiState.confirmPasswordError) }
            } else null,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Role Selection
        ExposedDropdownMenuBox(
            expanded = uiState.isRoleMenuExpanded,
            onExpandedChange = { viewModel.toggleRoleMenu() }
        ) {
            OutlinedTextField(
                value = uiState.selectedRole?.let { role ->
                    when (role) {
                        UserRole.ENTERPRISE -> "Enterprise"
                        UserRole.EXPERT -> "Expert"
                        UserRole.ACADEMIC -> "Student"
                        UserRole.INSTRUCTOR -> "Instructor"
                        UserRole.REGULATORY -> "Regulatory"
                    }
                } ?: "",
                onValueChange = {},
                readOnly = true,
                placeholder = { 
                    Text(
                        text = "Account Type",
                        color = colors.textSecondary.copy(alpha = 0.6f)
                    ) 
                },
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = uiState.isRoleMenuExpanded)
                },
                isError = uiState.roleError.isNotEmpty(),
                supportingText = if (uiState.roleError.isNotEmpty()) {
                    { Text(uiState.roleError) }
                } else null,
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor()
            )

            ExposedDropdownMenu(
                expanded = uiState.isRoleMenuExpanded,
                onDismissRequest = { viewModel.toggleRoleMenu() }
            ) {
                UserRole.values().forEach { role ->
                    DropdownMenuItem(
                        text = {
                            Text(
                                when (role) {
                                    UserRole.ENTERPRISE -> "Enterprise"
                                    UserRole.EXPERT -> "Expert"
                                    UserRole.ACADEMIC -> "Student"
                                    UserRole.INSTRUCTOR -> "Instructor"
                                    UserRole.REGULATORY -> "Regulatory"
                                }
                            )
                        },
                        onClick = {
                            viewModel.updateRole(role)
                            viewModel.toggleRoleMenu()
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = { viewModel.register() },
            enabled = !uiState.isLoading,
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = colorResource(id = R.color.interactive_primary),
                contentColor = androidx.compose.ui.graphics.Color.White
            )
        ) {
            if (uiState.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(20.dp),
                    color = MaterialTheme.colorScheme.onPrimary
                )
            } else {
                Text("Register")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        TextButton(
            onClick = { navController.navigate("login") }
        ) {
            Text("Already have an account? Login")
        }
    }
}
