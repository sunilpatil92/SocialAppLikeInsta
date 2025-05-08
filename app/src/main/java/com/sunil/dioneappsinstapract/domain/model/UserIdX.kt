package com.sunil.dioneappsinstapract.domain.model

data class UserIdX(
    val _id: String,
    val contactStatus: Boolean,
    val email: String,
    val name: String,
    val phone: Long,
    val profile: String,
    val userType: String,
    val webName: String
)