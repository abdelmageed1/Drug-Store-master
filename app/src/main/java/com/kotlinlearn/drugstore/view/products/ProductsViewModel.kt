package com.kotlinlearn.drugstore.view.products

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.kotlinlearn.drugstore.model.CartItem
import com.kotlinlearn.drugstore.model.Product
import com.kotlinlearn.drugstore.repository.ProductsRepository
import com.kotlinlearn.drugstore.utils.Constants

class ProductsViewModel (application: Application) : AndroidViewModel(application) {
    val repository: ProductsRepository = ProductsRepository(application)
    var productsMutableLiveData = repository.productsMutableLiveData
    var favouritesMutableLiveData = repository.favouritesMutableLiveData
    var cartItemsMutableLiveData = repository.cartItemsMutableLiveData

    fun getProducts(category: String){
        repository.getProducts(category)
    }

    fun addToFavourites(product: Product) {
        repository.addToFavourites(product)
    }

    fun removeFromFavourites(product: Product) {
        repository.removeFromFavourites(product)
    }

    fun getFavourites() {
        repository.getFavourites()
    }

    fun addToCart(cartItem: CartItem) {
        repository.addToCart(cartItem)
    }

    fun removeFromCart(itemName: String){
        repository.removeFromCart(itemName)
    }

    fun setCartItemCount(count : Int, itemName : String){
        repository.setCartItemCount(count, itemName)
    }

    fun getCartItems(){
        repository.getCartItems()
    }

}