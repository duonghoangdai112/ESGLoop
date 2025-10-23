package com.ignitech.esgcompanion.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.colorResource
import com.ignitech.esgcompanion.R

// Color helper functions for consistent color usage across the app
@Composable
fun AppColors() = AppColorsData(
    // Interactive Colors
    primary = colorResource(id = R.color.interactive_primary),
    secondary = colorResource(id = R.color.interactive_secondary),
    disabled = colorResource(id = R.color.interactive_disabled),
    
    // ESG Theme Colors
    success = colorResource(id = R.color.esg_success),
    warning = colorResource(id = R.color.esg_warning),
    info = colorResource(id = R.color.esg_info),
    error = colorResource(id = R.color.esg_error),
    
    // Text Colors
    textPrimary = colorResource(id = R.color.text_primary),
    textSecondary = colorResource(id = R.color.text_secondary),
    textHint = colorResource(id = R.color.text_hint),
    textOnPrimary = colorResource(id = R.color.text_on_primary),
    textOnSurface = colorResource(id = R.color.text_on_surface),
    textOnSurfaceVariant = colorResource(id = R.color.text_on_surface_variant),
    
    // Background Colors
    backgroundPrimary = colorResource(id = R.color.background_primary),
    backgroundSecondary = colorResource(id = R.color.background_secondary),
    backgroundSurface = colorResource(id = R.color.background_surface),
    backgroundSurfaceVariant = colorResource(id = R.color.background_surface_variant),
    
    // Border Colors
    borderLight = colorResource(id = R.color.border_light),
    borderMedium = colorResource(id = R.color.border_medium),
    borderDark = colorResource(id = R.color.border_dark),
    
    // Overlay Colors
    overlayLight = colorResource(id = R.color.overlay_light),
    overlayMedium = colorResource(id = R.color.overlay_medium),
    overlayDark = colorResource(id = R.color.overlay_dark),
    
    // Pillar Specific Colors
    pillarEnvironmental = colorResource(id = R.color.pillar_environmental),
    pillarSocial = colorResource(id = R.color.pillar_social),
    pillarGovernance = colorResource(id = R.color.pillar_governance),
    
    // Status Colors
    statusNotStarted = colorResource(id = R.color.status_not_started),
    statusInProgress = colorResource(id = R.color.status_in_progress),
    statusCompleted = colorResource(id = R.color.status_completed)
)

// Data class to hold all color values
data class AppColorsData(
    // Interactive Colors
    val primary: androidx.compose.ui.graphics.Color,
    val secondary: androidx.compose.ui.graphics.Color,
    val disabled: androidx.compose.ui.graphics.Color,
    
    // ESG Theme Colors
    val success: androidx.compose.ui.graphics.Color,
    val warning: androidx.compose.ui.graphics.Color,
    val info: androidx.compose.ui.graphics.Color,
    val error: androidx.compose.ui.graphics.Color,
    
    // Text Colors
    val textPrimary: androidx.compose.ui.graphics.Color,
    val textSecondary: androidx.compose.ui.graphics.Color,
    val textHint: androidx.compose.ui.graphics.Color,
    val textOnPrimary: androidx.compose.ui.graphics.Color,
    val textOnSurface: androidx.compose.ui.graphics.Color,
    val textOnSurfaceVariant: androidx.compose.ui.graphics.Color,
    
    // Background Colors
    val backgroundPrimary: androidx.compose.ui.graphics.Color,
    val backgroundSecondary: androidx.compose.ui.graphics.Color,
    val backgroundSurface: androidx.compose.ui.graphics.Color,
    val backgroundSurfaceVariant: androidx.compose.ui.graphics.Color,
    
    // Border Colors
    val borderLight: androidx.compose.ui.graphics.Color,
    val borderMedium: androidx.compose.ui.graphics.Color,
    val borderDark: androidx.compose.ui.graphics.Color,
    
    // Overlay Colors
    val overlayLight: androidx.compose.ui.graphics.Color,
    val overlayMedium: androidx.compose.ui.graphics.Color,
    val overlayDark: androidx.compose.ui.graphics.Color,
    
    // Pillar Specific Colors
    val pillarEnvironmental: androidx.compose.ui.graphics.Color,
    val pillarSocial: androidx.compose.ui.graphics.Color,
    val pillarGovernance: androidx.compose.ui.graphics.Color,
    
    // Status Colors
    val statusNotStarted: androidx.compose.ui.graphics.Color,
    val statusInProgress: androidx.compose.ui.graphics.Color,
    val statusCompleted: androidx.compose.ui.graphics.Color
)
