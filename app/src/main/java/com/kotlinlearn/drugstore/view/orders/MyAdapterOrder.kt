package com.kotlinlearn.drugstore.view.orders

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.kotlinlearn.drugstore.R
import com.kotlinlearn.drugstore.model.MyOrder

class MyAdapterOrder(val listOfOrder:ArrayList<MyOrder>):RecyclerView.Adapter<MyAdapterOrder.myViewHolerOfOrder>()  {
    lateinit var context: Context
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): myViewHolerOfOrder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.row_order,parent,false)
        context = parent.context
        return myViewHolerOfOrder(itemView)
    }

    override fun onBindViewHolder(holder: myViewHolerOfOrder, position: Int) {
        var current = listOfOrder[position]
        holder.imgOrder.setImageResource(current.imgOrder)
        holder.tvOrderDescription.text=current.descriptionOrder
        holder.tvOrderName.text = current.nameOrder

        holder.btnRemove.setOnClickListener {
           makeDialog(position)
        }
      //
    }

    override fun getItemCount(): Int {
        return listOfOrder.size
    }

    class myViewHolerOfOrder(itemview :View) : RecyclerView.ViewHolder(itemview){
        var tvOrderName : TextView = itemView.findViewById(R.id.row_Order_tv_name)
        var tvOrderDescription : TextView = itemView.findViewById(R.id.row_Order_description)
        var imgOrder : ImageView = itemView.findViewById(R.id.row_Order_img)
        var btnRemove :Button = itemview.findViewById(R.id.btn_row_Order_remove)

    }
    fun makeDialog(index :Int){
        var bulider = AlertDialog.Builder(context)
        bulider.apply {
            setTitle("Confirmation ")
            setMessage("Are you sure to delete this item ?")
            setIcon(R.drawable.ic_pharmacy_icon_svgrepo_com)
            setPositiveButton("Yes", DialogInterface.OnClickListener { dialogInterface, i ->
                Toast.makeText(context, "Deleted Successfully ", Toast.LENGTH_SHORT).show()
                listOfOrder.remove(listOfOrder[index])
                notifyDataSetChanged()
            })
            setNegativeButton("No", DialogInterface.OnClickListener { dialogInterface, i ->

            })

        }
        bulider.create()
        bulider.show()
    }


}