package com.ignitech.esgcompanion.presentation.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.background
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HelpScreen(
    navController: NavController
) {
    var expandedFAQ by remember { mutableStateOf<String?>(null) }
    var showContactDialog by remember { mutableStateOf(false) }
    
    val faqItems = listOf(
        FAQItem(
            id = "faq_1",
            question = "How do I create an ESG assessment?",
            answer = "To create an ESG assessment:\n\n1. Go to 'Assessment' screen from home\n2. Select assessment type suitable for your role\n3. Fill in information for each section (Environmental, Social, Governance)\n4. Review and submit assessment\n5. Track results in 'Reports' section"
        ),
        FAQItem(
            id = "faq_2",
            question = "Can I edit an assessment after submission?",
            answer = "You can edit an assessment within 24 hours after submission. After this time, you'll need to create a new assessment to update information."
        ),
        FAQItem(
            id = "faq_3",
            question = "How do I view ESG reports?",
            answer = "To view ESG reports:\n\n1. Go to 'Reports' screen from home\n2. Select the report you want to view\n3. The report will display your ESG assessment results\n4. You can download reports in PDF format"
        ),
        FAQItem(
            id = "faq_4",
            question = "Does the app support multiple languages?",
            answer = "Currently the app supports Vietnamese and English. You can change the language in 'Settings' > 'Language'."
        ),
        FAQItem(
            id = "faq_5",
            question = "How do I upgrade my service plan?",
            answer = "To upgrade your service plan:\n\n1. Go to 'Settings' > 'Account Information'\n2. View current service plan\n3. Contact support team for plan consultation\n4. Complete payment and activate new plan"
        ),
        FAQItem(
            id = "faq_6",
            question = "I forgot my password, how can I recover it?",
            answer = "To recover your password:\n\n1. Go to login screen\n2. Click 'Forgot Password?'\n3. Enter your registered email\n4. Check email and follow instructions\n5. Create new password"
        )
    )
    
    val supportOptions = listOf(
        SupportOption(
            icon = Icons.Default.Email,
            title = "Support Email",
            subtitle = "support@esgcompanion.vn",
            onClick = { /* Open email client */ }
        ),
        SupportOption(
            icon = Icons.Default.Phone,
            title = "Hotline",
            subtitle = "1900 1234",
            onClick = { /* Open phone dialer */ }
        ),
        SupportOption(
            icon = Icons.Default.Chat,
            title = "Online Chat",
            subtitle = "24/7 Support",
            onClick = { /* Open chat */ }
        ),
        SupportOption(
            icon = Icons.Default.VideoCall,
            title = "Video call",
            subtitle = "Direct consultation",
            onClick = { /* Open video call */ }
        )
    )
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Help") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp)
        ) {
            // Welcome Section
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer
                    )
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            imageVector = Icons.Default.Help,
                            contentDescription = null,
                            modifier = Modifier.size(48.dp),
                            tint = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                        
                        Spacer(modifier = Modifier.height(16.dp))
                        
                        Text(
                            text = "We are always ready to help you!",
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onPrimaryContainer,
                            textAlign = TextAlign.Center
                        )
                        
                        Spacer(modifier = Modifier.height(8.dp))
                        
                        Text(
                            text = "Find answers to frequently asked questions or contact us for support.",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.8f),
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
            
            // FAQ Section
            item {
                HelpSectionCard(
                    title = "Frequently Asked Questions",
                    icon = Icons.Default.QuestionAnswer,
                    items = faqItems.map { faq ->
                        HelpItem(
                            title = faq.question,
                            subtitle = if (expandedFAQ == faq.id) faq.answer else null,
                            icon = Icons.Default.HelpOutline,
                            isExpandable = true,
                            isExpanded = expandedFAQ == faq.id,
                            onClick = {
                                expandedFAQ = if (expandedFAQ == faq.id) null else faq.id
                            }
                        )
                    }
                )
            }
            
            // Support Section
            item {
                HelpSectionCard(
                    title = "Contact Support",
                    icon = Icons.Default.Support,
                    items = supportOptions.map { option ->
                        HelpItem(
                            title = option.title,
                            subtitle = option.subtitle,
                            icon = option.icon,
                            onClick = option.onClick
                        )
                    }
                )
            }
            
            // Quick Actions Section
            item {
                HelpSectionCard(
                    title = "Quick Actions",
                    icon = Icons.Default.Speed,
                    items = listOf(
                        HelpItem(
                            title = "User Guide",
                            subtitle = "Watch detailed tutorial videos",
                            icon = Icons.Default.PlayCircle,
                            onClick = { /* Navigate to tutorial */ }
                        ),
                        HelpItem(
                            title = "App Feedback",
                            subtitle = "Share your thoughts",
                            icon = Icons.Default.Feedback,
                            onClick = { /* Open feedback form */ }
                        ),
                        HelpItem(
                            title = "Report Bug",
                            subtitle = "Report issues encountered",
                            icon = Icons.Default.BugReport,
                            onClick = { /* Open bug report form */ }
                        )
                    )
                )
            }
            
            // Resources Section
            item {
                HelpSectionCard(
                    title = "Resources",
                    icon = Icons.Default.LibraryBooks,
                    items = listOf(
                        HelpItem(
                            title = "ESG Documents",
                            subtitle = "Learn about ESG and sustainability",
                            icon = Icons.Default.MenuBook,
                            onClick = { /* Navigate to ESG docs */ }
                        ),
                        HelpItem(
                            title = "Tutorial Videos",
                            subtitle = "Watch detailed tutorial videos",
                            icon = Icons.Default.VideoLibrary,
                            onClick = { /* Navigate to video tutorials */ }
                        ),
                        HelpItem(
                            title = "Community",
                            subtitle = "Join the Vietnam ESG community",
                            icon = Icons.Default.Group,
                            onClick = { /* Navigate to community */ }
                        )
                    )
                )
            }
        }
    }
}

@Composable
fun HelpSectionCard(
    title: String,
    icon: ImageVector,
    items: List<HelpItem>
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(32.dp)
                        .background(
                            color = MaterialTheme.colorScheme.primary,
                            shape = CircleShape
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp),
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                }
                
                Spacer(modifier = Modifier.width(10.dp))
                
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
            
            items.forEachIndexed { index, item ->
                HelpItemRow(item = item)
                if (index < items.size - 1) {
                    Spacer(modifier = Modifier.height(2.dp))
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HelpItemRow(
    item: HelpItem
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        onClick = item.onClick,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = item.icon,
                    contentDescription = item.title,
                    modifier = Modifier.size(24.dp),
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                )
                
                Spacer(modifier = Modifier.width(12.dp))
                
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = item.title,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Medium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    item.subtitle?.let { subtitle ->
                        Spacer(modifier = Modifier.height(1.dp))
                        Text(
                            text = subtitle,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f)
                        )
                    }
                }
                
                if (item.isExpandable) {
                    Icon(
                        imageVector = if (item.isExpanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                        contentDescription = if (item.isExpanded) "Collapse" else "Expand",
                        tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f)
                    )
                } else {
                    Icon(
                        imageVector = Icons.Default.ChevronRight,
                        contentDescription = "Navigate",
                        tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f)
                    )
                }
            }
            
            // Expanded content
            if (item.isExpanded && item.subtitle != null) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = item.subtitle,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    lineHeight = MaterialTheme.typography.bodyMedium.lineHeight * 1.4
                )
            }
        }
    }
}

data class HelpItem(
    val icon: ImageVector,
    val title: String,
    val subtitle: String?,
    val onClick: () -> Unit,
    val isExpandable: Boolean = false,
    val isExpanded: Boolean = false
)

data class FAQItem(
    val id: String,
    val question: String,
    val answer: String
)

data class SupportOption(
    val icon: ImageVector,
    val title: String,
    val subtitle: String,
    val onClick: () -> Unit
)
