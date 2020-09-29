package com.commercejunction.model

data class PDFModel(
    val ResponseCode: Int,
    val ResponseMsg: String,
    val ResponseData: PDFResponseData,
)