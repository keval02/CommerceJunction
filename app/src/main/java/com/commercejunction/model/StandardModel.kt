package com.commercejunction.model

data class StandardModel(
    val ResponseCode: Int,
    val ResponseMsg: String,
    val ResponseData: StandardResponseData,
)