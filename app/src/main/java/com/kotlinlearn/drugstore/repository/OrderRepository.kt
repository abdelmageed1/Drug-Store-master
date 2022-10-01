package com.kotlinlearn.drugstore.repository

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.kotlinlearn.drugstore.model.Order
import com.kotlinlearn.drugstore.utils.Constants

class OrderRepository(application: Application) {

    private var reference = FirebaseDatabase.getInstance().reference
    private var authRef = FirebaseAuth.getInstance()
    private val currentUserRef = authRef.currentUser?.uid.toString()
    var ordersMutableLiveData = MutableLiveData<MutableList<Order>>()

    fun sendOrder(order: Order) {
        reference.child(Constants.OrderPath).child(currentUserRef).child(order.id).setValue(order)
        cartCleanUp()
    }

    private fun cartCleanUp() {
        reference.child(Constants.CartPath).child(currentUserRef).child(Constants.CartProductsPath).removeValue()
    }

    fun getOrders(){
        var orders = mutableListOf<Order>()
        reference.child(Constants.OrderPath).child(currentUserRef).addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                for (child in snapshot.children) {
                    orders.add(child.getValue(Order::class.java) as Order)
                }
                ordersMutableLiveData.postValue(orders)
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
    }

    fun removeOrder(id : String) {
        reference.child(Constants.OrderPath).child(currentUserRef).child(id).removeValue()
    }

}