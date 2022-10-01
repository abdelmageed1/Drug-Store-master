package com.kotlinlearn.drugstore.model

import java.io.Serializable

data class Product(
    var description:String = "",
    var img:String = "",
    var pName:String = "",
    var price:Int = 0,
    var stockCount:Int = 0
):Serializable
