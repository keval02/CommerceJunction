package com.commercejunction.model

import java.io.Serializable

data class StandardListData (
    val StandardId: Int,
    val StandardName: String,
    var isSelected : Boolean = false
): Serializable
