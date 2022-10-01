package com.kotlinlearn.drugstore.view.orders

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.kotlinlearn.drugstore.model.Order
import com.kotlinlearn.drugstore.repository.OrderRepository

class OrderViewModel(application: Application) : AndroidViewModel(application) {
    val repository: OrderRepository = OrderRepository(application)
    var ordersMutableLiveData = repository.ordersMutableLiveData

    fun sendOrder(order: Order) {
        repository.sendOrder(order)
    }

    fun getOrders(){
        repository.getOrders()
    }

    fun removeOrder(id : String) {
        repository.removeOrder(id)
    }

}