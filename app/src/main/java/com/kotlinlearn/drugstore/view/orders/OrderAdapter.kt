package com.kotlinlearn.drugstore.view.orders

import android.app.AlertDialog
import com.kotlinlearn.drugstore.databinding.OrderLayoutBinding
import com.kotlinlearn.drugstore.model.Order
import android.content.Context
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.kotlinlearn.drugstore.R
import com.kotlinlearn.drugstore.interfaces.OrderHelper

class OrderAdapter(private val context: Context?, private val orderHelper: OrderHelper) : RecyclerView.Adapter<OrderAdapter.OrderViewHolder>(){
    private var ordersList:MutableList<Order> = mutableListOf<Order>()

    inner class OrderViewHolder(itemView: OrderLayoutBinding): RecyclerView.ViewHolder(itemView.root){
        var orderPCount : TextView = itemView.orderProductsCount
        var orderAmount : TextView = itemView.orderAmount
        var orderState : TextView = itemView.orderState
        var orderDate : TextView = itemView.orderDate
        var orderTime : TextView = itemView.orderTime
        var removeOrderBtn : Button = itemView.orderRemove
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val binding = OrderLayoutBinding.inflate(LayoutInflater.from(context), parent, false)
        return OrderViewHolder(binding)
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        var order = ordersList[position]

        holder.orderPCount.text = order.products.size.toString()
        holder.orderAmount.text = "${order.totalAmount} L.E"
        holder.orderState.text = order.state
        holder.orderDate.text = order.date
        holder.orderTime.text = order.time

        holder.removeOrderBtn.setOnClickListener { makeRemoveDialog(order) }

    }

    private fun makeRemoveDialog(order: Order) {
        var bulider = AlertDialog.Builder(context)
        bulider.apply {
            setTitle("Confirmation ")
            setMessage("Are you sure to delete this order ?")
            setIcon(R.drawable.ic_pharmacy_icon_svgrepo_com)
            setPositiveButton("Yes", DialogInterface.OnClickListener { dialogInterface, i ->
                orderHelper.removeOrder(order.id)
                ordersList.remove(order)
                notifyDataSetChanged()
                Toast.makeText(context, "Deleted Successfully ", Toast.LENGTH_SHORT).show()
            })
            setNegativeButton("No", DialogInterface.OnClickListener { dialogInterface, i ->

            })

        }
        bulider.create()
        bulider.show()
    }


    override fun getItemCount(): Int {
        return ordersList.size
    }

    fun setData(ordersList: MutableList<Order>){
        this.ordersList = ordersList
        notifyDataSetChanged() //to notify adapter that new data change has been happened to adapt it
    }

}
