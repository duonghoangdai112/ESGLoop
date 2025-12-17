package com.ignitech.esgcompanion.presentation.screen.student

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.ignitech.esgcompanion.presentation.screen.LearningHubScreen

@Composable
fun StudentLearningScreen(
    navController: NavController,
    modifier: Modifier = Modifier
) {
    LearningHubScreen(
        navController = navController,
        modifier = modifier
    )
}
