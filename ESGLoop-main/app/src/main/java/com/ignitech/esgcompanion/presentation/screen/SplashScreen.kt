package com.ignitech.esgcompanion.presentation.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.ignitech.esgcompanion.R
import com.ignitech.esgcompanion.presentation.viewmodel.SplashViewModel

@Composable
fun SplashScreen(
    navController: NavController,
    viewModel: SplashViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    
    LaunchedEffect(uiState.isLoading, uiState.isLoggedIn) {
        android.util.Log.d("SplashScreen", "isLoading: ${uiState.isLoading}, isLoggedIn: ${uiState.isLoggedIn}")
        
        when {
            uiState.isLoggedIn -> {
                // User is already logged in, navigate to appropriate dashboard
                android.util.Log.d("SplashScreen", "User is logged in, navigating to dashboard")
                val currentUser = uiState.currentUser
                if (currentUser != null) {
                    when (currentUser.role) {
                        com.ignitech.esgcompanion.domain.entity.UserRole.ENTERPRISE -> {
                            navController.navigate("enterprise_home") {
                                popUpTo("splash") { inclusive = true }
                            }
                        }
                        com.ignitech.esgcompanion.domain.entity.UserRole.EXPERT -> {
                            navController.navigate("expert_home") {
                                popUpTo("splash") { inclusive = true }
                            }
                        }
                        com.ignitech.esgcompanion.domain.entity.UserRole.ACADEMIC -> {
                            navController.navigate("student_home") {
                                popUpTo("splash") { inclusive = true }
                            }
                        }
                        com.ignitech.esgcompanion.domain.entity.UserRole.INSTRUCTOR -> {
                            navController.navigate("instructor_home") {
                                popUpTo("splash") { inclusive = true }
                            }
                        }
                        com.ignitech.esgcompanion.domain.entity.UserRole.REGULATORY -> {
                            navController.navigate("regulatory_home") {
                                popUpTo("splash") { inclusive = true }
                            }
                        }
                    }
                }
            }
            !uiState.isLoading -> {
                // User is not logged in, navigate to login
                android.util.Log.d("SplashScreen", "User is not logged in, navigating to login")
                navController.navigate("login") {
                    popUpTo("splash") { inclusive = true }
                }
            }
        }
    }
    
    // Timeout fallback - navigate to login after 3 seconds if still loading
    LaunchedEffect(Unit) {
        kotlinx.coroutines.delay(3000)
        if (uiState.isLoading) {
            android.util.Log.d("SplashScreen", "Timeout reached, forcing navigation to login")
            navController.navigate("login") {
                popUpTo("splash") { inclusive = true }
            }
        }
    }
    
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF388E3C),  // Dark Green
                        Color(0xFF4CAF50)   // Primary Green
                    )
                )
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            
            // App Name
            Text(
                text = "ESG Companion",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                textAlign = TextAlign.Center
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            // Tagline
            Text(
                text = "Supporting Vietnamese enterprises in ESG adoption",
                fontSize = 16.sp,
                color = Color.White.copy(alpha = 0.8f),
                textAlign = TextAlign.Center
            )
            
            Spacer(modifier = Modifier.height(48.dp))
            
            // Loading indicator
            if (uiState.isLoading) {
                CircularProgressIndicator(
                    color = Color.White,
                    strokeWidth = 3.dp
                )
            }
        }
    }
}
