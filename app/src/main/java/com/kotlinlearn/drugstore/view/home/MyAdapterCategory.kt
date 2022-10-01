package com.kotlinlearn.drugstore.view.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.kotlinlearn.drugstore.R
import com.kotlinlearn.drugstore.model.MyCategory
import com.kotlinlearn.drugstore.view.products.ProductsFragment

class MyAdapterCategory (private val list_category:ArrayList<MyCategory>):RecyclerView.Adapter<MyAdapterCategory.MyViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.row_category,parent,false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
       var currentItem = list_category[position]
        holder.image.setImageResource(currentItem.img)
        holder.tv.text= currentItem.tv
        holder.image.setOnClickListener { openProducts(it, currentItem) }
        holder.tv.setOnClickListener { openProducts(it, currentItem) }
    }

    private fun openProducts(it: View, currentItem: MyCategory) {
        Navigation.findNavController(it).navigate(R.id.action_homeFragment_to_productsFragment)
        ProductsFragment.getCategory(currentItem.tv)
    }


    override fun getItemCount(): Int {
    return list_category.size
    }


    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        val image :ImageView =itemView.findViewById(R.id.row_category_img)
        val tv :TextView =itemView.findViewById(R.id.row_category_tv)

    }


}