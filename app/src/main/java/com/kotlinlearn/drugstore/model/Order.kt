package com.kotlinlearn.drugstore.model

import java.io.Serializable

data class Order(
    val id : String = "",
    val fullName : String = "",
    val phone : String = "",
    val address : String = "",
    val state : String = "",
    val date : String = "",
    val time : String = "",
    val totalAmount : Int = 0,
    val products : MutableList<CartItem> = mutableListOf(),
    val paymentMethod : String = ""
) : Serializable
