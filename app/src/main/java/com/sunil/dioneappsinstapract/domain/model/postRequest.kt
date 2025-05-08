package com.sunil.dioneappsinstapract.domain.model

data class postRequest(
    val postIdList : List<String> = emptyList(),
    val shots : Boolean=false
)