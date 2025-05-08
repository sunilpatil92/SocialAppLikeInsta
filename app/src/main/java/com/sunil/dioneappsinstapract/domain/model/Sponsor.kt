package com.sunil.dioneappsinstapract.domain.model

data class Sponsor(
    val _id: String,
    val groupDescription: String,
    val groupName: String,
    val groupProfile: String,
    val isSeenStory: Boolean,
    val memberLength: Int,
    val pendingMembers: List<Any>,
    val storyCount: Int
)