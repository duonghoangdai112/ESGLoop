package com.ignitech.esgcompanion.presentation.screen.regulatory

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.ignitech.esgcompanion.domain.entity.*
import com.ignitech.esgcompanion.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegulatoryAssessmentScreen(
    navController: NavController
) {
    var selectedTimeRange by remember { mutableStateOf(TimeRange.MONTHLY) }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "ESG Statistics",
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
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Time Range Selector
            item {
                Text(
                    text = "Time Period",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
            }
            
            item {
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(TimeRange.values()) { range ->
                        FilterChip(
                            onClick = { selectedTimeRange = range },
                            label = { Text(range.displayName) },
                            selected = selectedTimeRange == range,
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f),
                                selectedLabelColor = MaterialTheme.colorScheme.primary
                            )
                        )
                    }
                }
            }
            
            // Key Statistics
            item {
                Text(
                    text = "Overview Statistics",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
            }
            
            item {
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(getKeyStatistics(selectedTimeRange)) { stat ->
                        StatCard(
                            stat = stat,
                            modifier = Modifier.width(150.dp)
                        )
                    }
                }
            }
            
            // ESG Pillar Performance
            item {
                Text(
                    text = "Performance by ESG Pillar",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
            }
            
            item {
                Card(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        ESGPillar.values().forEach { pillar ->
                            PillarPerformanceItem(
                                pillar = pillar,
                                averageScore = getPillarAverageScore(pillar),
                                assessmentCount = getPillarAssessmentCount(pillar),
                                trend = getPillarTrend(pillar)
                            )
                            if (pillar != ESGPillar.values().last()) {
                                Spacer(modifier = Modifier.height(16.dp))
                            }
                        }
                    }
                }
            }
            
            // Industry Breakdown
            item {
                Text(
                    text = "Analysis by Industry",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
            }
            
            items(getIndustryBreakdown(selectedTimeRange)) { industry ->
                IndustryCard(industry = industry)
            }
            
            // Regional Analysis
            item {
                Text(
                    text = "Analysis by Region",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
            }
            
            item {
                Card(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        getRegionalAnalysis(selectedTimeRange).forEach { region ->
                            RegionalItem(region = region)
                            if (region != getRegionalAnalysis(selectedTimeRange).last()) {
                                Spacer(modifier = Modifier.height(12.dp))
                            }
                        }
                    }
                }
            }
            
            // Compliance Status
            item {
                Text(
                    text = "Compliance Status",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
            }
            
            item {
                ComplianceOverviewCard()
            }
        }
    }
}

@Composable
fun StatCard(
    stat: KeyStatistic,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = stat.icon,
                contentDescription = null,
                modifier = Modifier.size(32.dp),
                tint = stat.color
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = stat.value,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = stat.color
            )
            Text(
                text = stat.label,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            if (stat.change != null) {
                Text(
                    text = "${if (stat.change > 0) "+" else ""}${stat.change}%",
                    style = MaterialTheme.typography.labelSmall,
                    color = if (stat.change > 0) ESGSuccess else ESGWarning
                )
            }
        }
    }
}

@Composable
fun PillarPerformanceItem(
    pillar: ESGPillar,
    averageScore: Double,
    assessmentCount: Int,
    trend: Trend
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = getPillarIcon(pillar),
                    style = MaterialTheme.typography.titleLarge
                )
                Text(
                    text = getPillarName(pillar),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
            }
            
            Spacer(modifier = Modifier.height(4.dp))
            
            Text(
                text = "$assessmentCount assessments",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        
        Column(
            horizontalAlignment = Alignment.End
        ) {
            Text(
                text = "${averageScore.toInt()}/100",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = getScoreColor(averageScore)
            )
            
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Icon(
                    imageVector = when (trend.direction) {
                        TrendDirection.UP -> Icons.Default.TrendingUp
                        TrendDirection.DOWN -> Icons.Default.TrendingDown
                        TrendDirection.STABLE -> Icons.Default.TrendingFlat
                    },
                    contentDescription = null,
                    modifier = Modifier.size(16.dp),
                    tint = when (trend.direction) {
                        TrendDirection.UP -> ESGSuccess
                        TrendDirection.DOWN -> ESGWarning
                        TrendDirection.STABLE -> MaterialTheme.colorScheme.onSurfaceVariant
                    }
                )
                Text(
                    text = "${if (trend.percentage > 0) "+" else ""}${trend.percentage}%",
                    style = MaterialTheme.typography.labelSmall,
                    color = when (trend.direction) {
                        TrendDirection.UP -> ESGSuccess
                        TrendDirection.DOWN -> ESGWarning
                        TrendDirection.STABLE -> MaterialTheme.colorScheme.onSurfaceVariant
                    }
                )
            }
        }
    }
}

@Composable
fun IndustryCard(
    industry: IndustryData
) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = industry.name,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                
                Text(
                    text = "${industry.averageScore.toInt()}/100",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = getScoreColor(industry.averageScore)
                )
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "${industry.enterpriseCount} enterprises",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                
                Text(
                    text = "${industry.complianceRate}% tuân thủ",
                    style = MaterialTheme.typography.bodySmall,
                    color = ESGSuccess
                )
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            
            LinearProgressIndicator(
                progress = (industry.averageScore / 100f).toFloat(),
                modifier = Modifier.fillMaxWidth(),
                color = getScoreColor(industry.averageScore)
            )
        }
    }
}

@Composable
fun RegionalItem(
    region: RegionalData
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(
                text = region.name,
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "${region.enterpriseCount} doanh nghiệp",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        
        Column(
            horizontalAlignment = Alignment.End
        ) {
            Text(
                text = "${region.averageScore.toInt()}/100",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold,
                color = getScoreColor(region.averageScore)
            )
            Text(
                text = "Trung bình",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
fun ComplianceOverviewCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Tổng quan tuân thủ ESG",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSecondaryContainer
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                ComplianceItemRegulatory(
                    label = "Tuân thủ đầy đủ",
                    count = 45,
                    total = 100,
                    color = ESGSuccess
                )
                ComplianceItemRegulatory(
                    label = "Cần cải thiện",
                    count = 35,
                    total = 100,
                    color = ESGWarning
                )
                ComplianceItemRegulatory(
                    label = "Chưa đạt",
                    count = 20,
                    total = 100,
                    color = ESGWarning
                )
            }
        }
    }
}

@Composable
fun ComplianceItemRegulatory(
    label: String,
    count: Int,
    total: Int,
    color: Color
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = count.toString(),
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            color = color
        )
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSecondaryContainer
        )
        Text(
            text = "${(count * 100 / total)}%",
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSecondaryContainer
        )
    }
}

// Data classes
enum class TimeRange(val displayName: String) {
    WEEKLY("Tuần"),
    MONTHLY("Tháng"),
    QUARTERLY("Quý"),
    YEARLY("Năm")
}

data class KeyStatistic(
    val label: String,
    val value: String,
    val icon: androidx.compose.ui.graphics.vector.ImageVector,
    val color: Color,
    val change: Int? = null
)

data class Trend(
    val direction: TrendDirection,
    val percentage: Int
)

enum class TrendDirection {
    UP, DOWN, STABLE
}

data class IndustryData(
    val name: String,
    val averageScore: Double,
    val enterpriseCount: Int,
    val complianceRate: Int
)

data class RegionalData(
    val name: String,
    val averageScore: Double,
    val enterpriseCount: Int
)

// Helper functions
private fun getScoreColor(score: Double): Color {
    return when {
        score >= 80 -> ESGSuccess
        score >= 60 -> ESGWarning
        else -> ESGWarning
    }
}

private fun getPillarIcon(pillar: ESGPillar): String {
    return when (pillar) {
        ESGPillar.ENVIRONMENTAL -> "🌱"
        ESGPillar.SOCIAL -> "👥"
        ESGPillar.GOVERNANCE -> "🏛️"
    }
}

private fun getPillarName(pillar: ESGPillar): String {
    return when (pillar) {
        ESGPillar.ENVIRONMENTAL -> "Môi trường"
        ESGPillar.SOCIAL -> "Xã hội"
        ESGPillar.GOVERNANCE -> "Quản trị"
    }
}

// Mock data functions
private fun getKeyStatistics(timeRange: TimeRange): List<KeyStatistic> {
    return listOf(
        KeyStatistic(
            label = "Tổng đánh giá",
            value = "1,234",
            icon = Icons.Default.Assignment,
            color = ESGSuccess,
            change = 12
        ),
        KeyStatistic(
            label = "Doanh nghiệp tham gia",
            value = "456",
            icon = Icons.Default.Business,
            color = ESGSuccess,
            change = 8
        ),
        KeyStatistic(
            label = "Điểm trung bình",
            value = "72",
            icon = Icons.Default.TrendingUp,
            color = ESGInfo,
            change = 5
        ),
        KeyStatistic(
            label = "Tỷ lệ tuân thủ",
            value = "68%",
            icon = Icons.Default.CheckCircle,
            color = ESGSuccess,
            change = 3
        )
    )
}

private fun getPillarAverageScore(pillar: ESGPillar): Double {
    return when (pillar) {
        ESGPillar.ENVIRONMENTAL -> 75.5
        ESGPillar.SOCIAL -> 68.2
        ESGPillar.GOVERNANCE -> 71.8
    }
}

private fun getPillarAssessmentCount(pillar: ESGPillar): Int {
    return when (pillar) {
        ESGPillar.ENVIRONMENTAL -> 412
        ESGPillar.SOCIAL -> 389
        ESGPillar.GOVERNANCE -> 433
    }
}

private fun getPillarTrend(pillar: ESGPillar): Trend {
    return when (pillar) {
        ESGPillar.ENVIRONMENTAL -> Trend(TrendDirection.UP, 8)
        ESGPillar.SOCIAL -> Trend(TrendDirection.DOWN, 3)
        ESGPillar.GOVERNANCE -> Trend(TrendDirection.STABLE, 1)
    }
}

private fun getIndustryBreakdown(timeRange: TimeRange): List<IndustryData> {
    return listOf(
        IndustryData("Sản xuất", 68.5, 156, 72),
        IndustryData("Dịch vụ tài chính", 82.3, 89, 85),
        IndustryData("Bán lẻ", 59.2, 134, 45),
        IndustryData("Nông nghiệp", 71.8, 67, 68),
        IndustryData("Công nghệ", 79.1, 123, 81)
    )
}

private fun getRegionalAnalysis(timeRange: TimeRange): List<RegionalData> {
    return listOf(
        RegionalData("TP. Hồ Chí Minh", 76.2, 234),
        RegionalData("Hà Nội", 74.8, 189),
        RegionalData("Đà Nẵng", 69.5, 67),
        RegionalData("Cần Thơ", 62.3, 45),
        RegionalData("Khác", 65.1, 89)
    )
}
