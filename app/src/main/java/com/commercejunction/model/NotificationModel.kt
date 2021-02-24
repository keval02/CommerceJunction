package com.commercejunction.model

data class NotificationModel(
    val ResponseCode: Int,
    val ResponseMsg: String,
    val ResponseData: NotificationResponseData,
)