package com.ignitech.esgcompanion.presentation.screen.instructor

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.hilt.navigation.compose.hiltViewModel
import com.ignitech.esgcompanion.R
import com.ignitech.esgcompanion.presentation.viewmodel.InstructorClassesViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InstructorHomeScreen(
    navController: NavController,
    modifier: Modifier = Modifier
) {
    var selectedTab by remember { mutableIntStateOf(0) }
    
    Scaffold(
        modifier = modifier,
        bottomBar = {
            NavigationBar(
                containerColor = Color.Transparent,
                tonalElevation = 0.dp
            ) {
                NavigationBarItem(
                    icon = { 
                        Icon(
                            Icons.Default.Home, 
                            contentDescription = "Home",
                            tint = if (selectedTab == 0) colorResource(id = R.color.interactive_primary) else MaterialTheme.colorScheme.onSurfaceVariant
                        ) 
                    },
                    label = { 
                        Text(
                            "Home",
                            color = if (selectedTab == 0) colorResource(id = R.color.interactive_primary) else MaterialTheme.colorScheme.onSurfaceVariant,
                            fontSize = 12.sp
                        ) 
                    },
                    selected = selectedTab == 0,
                    onClick = { selectedTab = 0 },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = colorResource(id = R.color.interactive_primary),
                        selectedTextColor = colorResource(id = R.color.interactive_primary),
                        unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                        unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
                        indicatorColor = colorResource(id = R.color.background_surface)
                    )
                )
                NavigationBarItem(
                    icon = { 
                        Icon(
                            Icons.Default.School, 
                            contentDescription = "Classes",
                            tint = if (selectedTab == 1) colorResource(id = R.color.interactive_primary) else MaterialTheme.colorScheme.onSurfaceVariant
                        ) 
                    },
                    label = { 
                        Text(
                            "Classes",
                            color = if (selectedTab == 1) colorResource(id = R.color.interactive_primary) else MaterialTheme.colorScheme.onSurfaceVariant,
                            fontSize = 12.sp
                        ) 
                    },
                    selected = selectedTab == 1,
                    onClick = { selectedTab = 1 },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = colorResource(id = R.color.interactive_primary),
                        selectedTextColor = colorResource(id = R.color.interactive_primary),
                        unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                        unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
                        indicatorColor = colorResource(id = R.color.background_surface)
                    )
                )
                NavigationBarItem(
                    icon = { 
                        Icon(
                            Icons.Default.Create, 
                            contentDescription = "Content",
                            tint = if (selectedTab == 2) colorResource(id = R.color.interactive_primary) else MaterialTheme.colorScheme.onSurfaceVariant
                        ) 
                    },
                    label = { 
                        Text(
                            "Content",
                            color = if (selectedTab == 2) colorResource(id = R.color.interactive_primary) else MaterialTheme.colorScheme.onSurfaceVariant,
                            fontSize = 12.sp
                        ) 
                    },
                    selected = selectedTab == 2,
                    onClick = { selectedTab = 2 },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = colorResource(id = R.color.interactive_primary),
                        selectedTextColor = colorResource(id = R.color.interactive_primary),
                        unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                        unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
                        indicatorColor = colorResource(id = R.color.background_surface)
                    )
                )
            }
        }
    ) { paddingValues ->
        when (selectedTab) {
            0 -> InstructorDashboardScreen(navController = navController, modifier = Modifier.padding(paddingValues))
            1 -> {
                val viewModel: InstructorClassesViewModel = hiltViewModel()
                InstructorClassesScreen(navController = navController, viewModel = viewModel, modifier = Modifier.padding(paddingValues))
            }
            2 -> InstructorContentScreen(navController = navController, modifier = Modifier.padding(paddingValues))
        }
    }
}