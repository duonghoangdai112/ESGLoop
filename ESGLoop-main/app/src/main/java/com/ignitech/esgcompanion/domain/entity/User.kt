package com.ignitech.esgcompanion.domain.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val id: String,
    val email: String,
    val password: String,
    val name: String,
    val role: UserRole,
    val companyId: String? = null,
    val companyName: String? = null,
    val subscriptionPlan: SubscriptionPlan = SubscriptionPlan.FREE,
    val avatarUrl: String? = null,
    val createdAt: Long,
    val lastLoginAt: Long? = null,
    val isActive: Boolean = true
) : Parcelable

// Demo users for testing
object DemoUsers {
    val enterpriseUser = User(
        id = "enterprise_001",
        email = "admin@xaydungxanh.vn",
        password = "123456",
        name = "Nguyễn Văn Minh",
        role = UserRole.ENTERPRISE,
        companyId = "company_001",
        companyName = "Công ty TNHH Xây Dựng Xanh",
        subscriptionPlan = SubscriptionPlan.ENTERPRISE,
        createdAt = System.currentTimeMillis() - 86400000 * 30, // 30 days ago
        lastLoginAt = System.currentTimeMillis()
    )
    
    val individualUser = User(
        id = "individual_001",
        email = "expert@esg.vn",
        password = "123456",
        name = "Trần Thị Lan",
        role = UserRole.EXPERT,
        subscriptionPlan = SubscriptionPlan.PRO,
        createdAt = System.currentTimeMillis() - 86400000 * 15, // 15 days ago
        lastLoginAt = System.currentTimeMillis()
    )
    
    val regulatoryUser = User(
        id = "regulatory_001",
        email = "admin@moit.gov.vn",
        password = "123456",
        name = "Lê Văn Hùng",
        role = UserRole.REGULATORY,
        companyId = "gov_001",
        companyName = "Bộ Công Thương",
        subscriptionPlan = SubscriptionPlan.ENTERPRISE,
        createdAt = System.currentTimeMillis() - 86400000 * 60, // 60 days ago
        lastLoginAt = System.currentTimeMillis()
    )
    
    val academicUser = User(
        id = "academic_001",
        email = "prof.nguyen@university.edu.vn",
        password = "123456",
        name = "PGS.TS Nguyễn Thị Minh",
        role = UserRole.ACADEMIC,
        companyId = "edu_001",
        companyName = "Đại học Kinh tế TP.HCM",
        subscriptionPlan = SubscriptionPlan.ENTERPRISE,
        createdAt = System.currentTimeMillis() - 86400000 * 30, // 30 days ago
        lastLoginAt = System.currentTimeMillis()
    )
}
