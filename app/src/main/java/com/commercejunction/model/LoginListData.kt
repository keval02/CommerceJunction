package com.commercejunction.model

data class LoginListData(
    val EmailId: String,
    val IsActive: Boolean,
    val IsDelete: Boolean,
    var Mobile: String,
    var Name: String,
    val StudentId: Int
)