package com.kotlinlearn.drugstore.repository


import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.kotlinlearn.drugstore.model.CartItem
import com.kotlinlearn.drugstore.model.Product
import com.kotlinlearn.drugstore.utils.Constants

class ProductsRepository(application: Application) {
    var productsMutableLiveData = MutableLiveData<MutableList<Product>>()
    var favouritesMutableLiveData = MutableLiveData<MutableList<Product>>()
    var cartItemsMutableLiveData = MutableLiveData<MutableList<CartItem>>()
    private var reference = FirebaseDatabase.getInstance().reference
    private var authRef = FirebaseAuth.getInstance()
    private val TAG = "ProductsRepository"

    fun getProducts(category:String) {
        var products = mutableListOf<Product>()
        reference.child(category).addValueEventListener(object :
            ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for ( child in snapshot.children){
                    products.add(child.getValue(Product::class.java) as Product)
                }
                productsMutableLiveData.postValue(products)
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }

    fun addToFavourites(product: Product) {
        reference.child(Constants.FavouritesPath).child(authRef.currentUser?.uid.toString()).child(product.pName).setValue(product)
    }

    fun removeFromFavourites(product: Product) {
        reference.child(Constants.FavouritesPath).child(authRef.currentUser?.uid.toString()).child(product.pName).removeValue()
    }

    fun getFavourites() {
        var favourites = mutableListOf<Product>()

        reference.child(Constants.FavouritesPath).child(authRef.currentUser?.uid.toString()).addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                for (child in snapshot.children) {
                    favourites.add(child.getValue(Product::class.java) as Product)
                    Log.d(TAG, "onDataChange: ${favourites.size}")
                }
                favouritesMutableLiveData.postValue(favourites)
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })

    }

    fun addToCart(cartItem: CartItem) {
        reference.child(Constants.CartPath).child(authRef.currentUser?.uid.toString()).child(Constants.CartProductsPath)
            .child(cartItem.itemName)
            .setValue(cartItem)
    }

    fun removeFromCart(itemName: String){
        reference.child(Constants.CartPath).child(authRef.currentUser?.uid.toString())
            .child(Constants.CartProductsPath).child(itemName).removeValue()
    }

    fun setCartItemCount(count : Int, itemName : String){
        reference.child(Constants.CartPath).child(authRef.currentUser?.uid.toString())
            .child(Constants.CartProductsPath).child(itemName).child("itemCount").setValue(count)
    }

    fun getCartItems(){
        var cartItems = mutableListOf<CartItem>()
        reference.child(Constants.CartPath).child(authRef.currentUser?.uid.toString()).child(Constants.CartProductsPath)
            .addValueEventListener(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    for (child in snapshot.children) {
                        cartItems.add(child.getValue(CartItem::class.java) as CartItem)
                    }
                    cartItemsMutableLiveData.postValue(cartItems)
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })
    }


}