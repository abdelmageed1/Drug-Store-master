package com.kotlinlearn.drugstore.interfaces

import com.kotlinlearn.drugstore.model.Product

interface Favourite {
    fun addToFavourites(product: Product)
    fun removeFromFavourites(product: Product)
}