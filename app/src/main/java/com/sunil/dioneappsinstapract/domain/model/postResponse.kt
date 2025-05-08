package com.sunil.dioneappsinstapract.domain.model

data class postResponse(
    val currentPage: Int,
    val `data`: List<Data>,
    val message: String,
    val status: String,
    val totalCount: Int
)