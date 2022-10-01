package com.kotlinlearn.drugstore.model

import java.io.Serializable

data class CartItem(
    val itemName:String = "",
    val itemCount:Int = 0,
    val itemPrice:Int = 0,
    val itemImage:String = ""
):Serializable
