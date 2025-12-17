package com.ignitech.esgcompanion.util

object ValidationUtils {
    
    fun isValidEmail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
    
    fun isValidPassword(password: String): Boolean {
        return password.length >= 6
    }
    
    fun isValidName(name: String): Boolean {
        return name.trim().length >= 2
    }
    
    fun isValidPhone(phone: String): Boolean {
        return phone.matches(Regex("^[0-9]{10,11}$"))
    }
}

