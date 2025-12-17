package com.ignitech.esgcompanion.presentation.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.compose.ui.res.colorResource
import com.ignitech.esgcompanion.R
import com.ignitech.esgcompanion.presentation.viewmodel.LoginViewModel
import com.ignitech.esgcompanion.utils.AppColors

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    navController: NavController,
    viewModel: LoginViewModel = hiltViewModel()
) {
    val colors = AppColors()
    val uiState by viewModel.uiState.collectAsState()
    val currentUser by viewModel.currentUser.collectAsState()
    
    var passwordVisible by remember { mutableStateOf(false) }
    
    // Navigate when login is successful
    LaunchedEffect(uiState.isLoggedIn, currentUser) {
        if (uiState.isLoggedIn && currentUser != null) {
            currentUser?.let { user ->
                when (user.role) {
                    com.ignitech.esgcompanion.domain.entity.UserRole.ENTERPRISE -> {
                        navController.navigate("enterprise_home") {
                            popUpTo("login") { inclusive = true }
                        }
                    }
                    com.ignitech.esgcompanion.domain.entity.UserRole.EXPERT -> {
                        navController.navigate("expert_home") {
                            popUpTo("login") { inclusive = true }
                        }
                    }
                    com.ignitech.esgcompanion.domain.entity.UserRole.REGULATORY -> {
                        navController.navigate("regulatory_home") {
                            popUpTo("login") { inclusive = true }
                        }
                    }
                    com.ignitech.esgcompanion.domain.entity.UserRole.ACADEMIC -> {
                        navController.navigate("student_home") {
                            popUpTo("login") { inclusive = true }
                        }
                    }
                    com.ignitech.esgcompanion.domain.entity.UserRole.INSTRUCTOR -> {
                        navController.navigate("instructor_home") {
                            popUpTo("login") { inclusive = true }
                        }
                    }
                }
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { 
                    Text(
                        text = "Login",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Logo or App Name
            Text(
                text = "ESG Companion",
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold,
                color = colorResource(id = R.color.interactive_primary)
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                text = "Login to continue",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            
            Spacer(modifier = Modifier.height(32.dp))
            
            // Email Field
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
                    Icon(Icons.Default.Email, contentDescription = "Email")
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                isError = uiState.emailError.isNotEmpty()
            )
            
            if (uiState.emailError.isNotEmpty()) {
                Text(
                    text = uiState.emailError,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(start = 16.dp, top = 4.dp)
                )
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Password Field
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
                    Icon(Icons.Default.Lock, contentDescription = "Password")
                },
                trailingIcon = {
                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(
                            if (passwordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                            contentDescription = if (passwordVisible) "Hide password" else "Show password"
                        )
                    }
                },
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                isError = uiState.passwordError.isNotEmpty()
            )
            
            if (uiState.passwordError.isNotEmpty()) {
                Text(
                    text = uiState.passwordError,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(start = 16.dp, top = 4.dp)
                )
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            
            // Forgot Password Link
            TextButton(
                onClick = { navController.navigate("forgot_password") },
                modifier = Modifier.align(Alignment.End)
            ) {
                Text("Forgot password?")
            }
            
            Spacer(modifier = Modifier.height(24.dp))
            
            // Error Message
            uiState.error?.let { error ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.errorContainer
                    )
                ) {
                    Text(
                        text = error,
                        color = MaterialTheme.colorScheme.onErrorContainer,
                        modifier = Modifier.padding(16.dp),
                        textAlign = TextAlign.Center
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
            }
            
            // Login Button
            Button(
                onClick = { viewModel.login() },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                enabled = !uiState.isLoading,
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
                    Text(
                        text = "Login",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(24.dp))
            
            // Register Link
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Don't have an account? ",
                    style = MaterialTheme.typography.bodyMedium
                )
                TextButton(
                    onClick = { navController.navigate("register") }
                ) {
                    Text(
                        text = "Register now",
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}
