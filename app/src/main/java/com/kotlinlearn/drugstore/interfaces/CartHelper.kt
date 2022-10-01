package com.kotlinlearn.drugstore.interfaces

interface CartHelper {
    fun setCartItemCount(count : Int, itemName : String)
    fun removeFromCart(itemName: String)
}