package com.sunil.dioneappsinstapract.domain.model

data class Media(
    val _id: String,
    val aspectRatio: Double,
    val duration: String,
    val mediaTagUser: List<Any>,
    val size: Int,
    val thumbnail: String,
    val type: String,
    val url: String
)