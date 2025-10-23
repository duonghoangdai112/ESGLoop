package com.ignitech.esgcompanion.data.seed

import com.ignitech.esgcompanion.data.repository.NotificationRepositoryImpl
import com.ignitech.esgcompanion.domain.entity.Notification
import com.ignitech.esgcompanion.domain.entity.NotificationType
import com.ignitech.esgcompanion.domain.entity.NotificationPriority
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NotificationSeeder @Inject constructor(
    private val notificationRepository: NotificationRepositoryImpl
) {
    
    fun seedNotifications() {
        CoroutineScope(Dispatchers.IO).launch {
            val notifications = generateSampleNotifications()
            notificationRepository.insertNotifications(notifications)
        }
    }
    
    private fun generateSampleNotifications(): List<Notification> {
        val currentTime = System.currentTimeMillis()
        val oneHourAgo = currentTime - 3600000
        val oneDayAgo = currentTime - 86400000
        val threeDaysAgo = currentTime - 259200000
        val oneWeekAgo = currentTime - 604800000
        
        return listOf(
            // Enterprise User Notifications
            Notification(
                id = "notif_001",
                title = "ESG Assessment Completed",
                message = "ESG assessment for December 2024 has been completed. You can view the detailed report in the Reports section.",
                type = NotificationType.ASSESSMENT,
                userId = "enterprise_001",
                isRead = false,
                createdAt = oneHourAgo,
                priority = NotificationPriority.HIGH,
                actionUrl = "enterprise_report"
            ),
            
            Notification(
                id = "notif_002",
                title = "ESG Report Ready",
                message = "ESG report for December 2024 from Green Construction Co., Ltd. has been successfully created and is ready for download.",
                type = NotificationType.REPORT,
                userId = "enterprise_001",
                isRead = false,
                createdAt = oneDayAgo,
                priority = NotificationPriority.NORMAL,
                actionUrl = "enterprise_report"
            ),
            
            Notification(
                id = "notif_003",
                title = "Data Update Reminder",
                message = "Please update environmental and social data for Q4/2024 before December 31, 2024.",
                type = NotificationType.REMINDER,
                userId = "enterprise_001",
                isRead = true,
                createdAt = threeDaysAgo,
                priority = NotificationPriority.HIGH,
                actionUrl = "enterprise_assessment"
            ),
            
            Notification(
                id = "notif_004",
                title = "New ESG Course",
                message = "The course 'Environmental Risk Management in Construction' has been added to Learning Hub. Join to enhance your knowledge!",
                type = NotificationType.LEARNING,
                userId = "enterprise_001",
                isRead = true,
                createdAt = oneWeekAgo,
                priority = NotificationPriority.NORMAL,
                actionUrl = "learning_hub"
            ),
            
            // Expert User Notifications
            Notification(
                id = "notif_005",
                title = "New Consultation Request",
                message = "You have a new ESG consultation request from ABC Company. Please review and respond within 24 hours.",
                type = NotificationType.ASSESSMENT,
                userId = "individual_001",
                isRead = false,
                createdAt = oneHourAgo,
                priority = NotificationPriority.HIGH,
                actionUrl = "expert_assessment"
            ),
            
            Notification(
                id = "notif_006",
                title = "App Update",
                message = "Version 1.2.0 has been released with many new features. Update now for the best experience!",
                type = NotificationType.UPDATE,
                userId = "individual_001",
                isRead = false,
                createdAt = oneDayAgo,
                priority = NotificationPriority.NORMAL,
                actionUrl = null
            ),
            
            Notification(
                id = "notif_007",
                title = "Pro Plan Promotion",
                message = "30% discount on Pro plan in December. Upgrade now to enjoy premium features!",
                type = NotificationType.PROMOTION,
                userId = "individual_001",
                isRead = true,
                createdAt = threeDaysAgo,
                priority = NotificationPriority.LOW,
                actionUrl = "settings"
            ),
            
            // Academic User Notifications
            Notification(
                id = "notif_008",
                title = "ESG Research Report",
                message = "Research report 'ESG Trends in Vietnamese Universities' has been successfully published.",
                type = NotificationType.REPORT,
                userId = "academic_001",
                isRead = false,
                createdAt = oneHourAgo,
                priority = NotificationPriority.NORMAL,
                actionUrl = "expert_report"
            ),
            
            Notification(
                id = "notif_009",
                title = "Upcoming ESG Conference",
                message = "Conference 'ESG and Sustainable Development in Higher Education' will be held on December 15, 2024 in HCMC.",
                type = NotificationType.LEARNING,
                userId = "academic_001",
                isRead = true,
                createdAt = oneDayAgo,
                priority = NotificationPriority.NORMAL,
                actionUrl = "learning_hub"
            ),
            
            Notification(
                id = "notif_010",
                title = "Report Submission Reminder",
                message = "ESG report for semester 1/2024-2025 from HCMC University of Economics needs to be submitted before December 20, 2024.",
                type = NotificationType.REMINDER,
                userId = "academic_001",
                isRead = false,
                createdAt = threeDaysAgo,
                priority = NotificationPriority.HIGH,
                actionUrl = "regulatory_assessment"
            ),
            
            // Regulatory User Notifications
            Notification(
                id = "notif_011",
                title = "Security Alert",
                message = "Unusual login activity detected from unknown IP address. Please review and change password if necessary.",
                type = NotificationType.SECURITY,
                userId = "regulatory_001",
                isRead = false,
                createdAt = oneHourAgo,
                priority = NotificationPriority.URGENT,
                actionUrl = "settings"
            ),
            
            Notification(
                id = "notif_012",
                title = "New Regulatory Report",
                message = "Circular 15/2024/TT-BCT on ESG reporting for enterprises has been issued. Please update reporting procedures.",
                type = NotificationType.SYSTEM,
                userId = "regulatory_001",
                isRead = true,
                createdAt = oneDayAgo,
                priority = NotificationPriority.HIGH,
                actionUrl = "policy"
            ),
            
            Notification(
                id = "notif_013",
                title = "ESG Report Statistics",
                message = "ESG report summary from enterprises in November 2024: 245 enterprises have successfully submitted reports.",
                type = NotificationType.REPORT,
                userId = "regulatory_001",
                isRead = true,
                createdAt = threeDaysAgo,
                priority = NotificationPriority.NORMAL,
                actionUrl = "analytics"
            ),
            
            // Additional notifications for variety
            Notification(
                id = "notif_014",
                title = "24/7 Customer Support",
                message = "Our support team is always ready to help you 24/7. Contact us via email or live chat.",
                type = NotificationType.SYSTEM,
                userId = "enterprise_001",
                isRead = true,
                createdAt = oneWeekAgo,
                priority = NotificationPriority.LOW,
                actionUrl = null
            ),
            
            Notification(
                id = "notif_015",
                title = "Satisfaction Survey",
                message = "We value your feedback. Please take a short survey to help us improve our service.",
                type = NotificationType.SYSTEM,
                userId = "individual_001",
                isRead = false,
                createdAt = oneWeekAgo,
                priority = NotificationPriority.LOW,
                actionUrl = null
            ),
            
            Notification(
                id = "notif_016",
                title = "Happy New Year 2025",
                message = "Wishing you a successful and sustainable new year! Thank you for trusting and using ESG Companion service.",
                type = NotificationType.SYSTEM,
                userId = "academic_001",
                isRead = true,
                createdAt = currentTime,
                priority = NotificationPriority.NORMAL,
                actionUrl = null
            )
        )
    }
}
