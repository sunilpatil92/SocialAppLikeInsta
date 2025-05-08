package com.sunil.dioneappsinstapract.domain.model

data class GroupId(
    val _id: String,
    val groupName: String,
    val groupProfile: String,
    val groupType: String,
    val isDeleted: Boolean,
    val isJoined: Boolean,
    val isSeenStory: Boolean,
    val storyCount: Int,
    val userRole: String
)