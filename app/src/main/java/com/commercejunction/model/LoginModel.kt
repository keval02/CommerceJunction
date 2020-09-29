package com.commercejunction.model

data class LoginModel(
    val ResponseCode: Int,
    val ResponseData: LoginResponseData,
    val ResponseMsg: String
)