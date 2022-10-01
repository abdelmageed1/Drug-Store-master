package com.kotlinlearn.drugstore.view.cart

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kotlinlearn.drugstore.R
import com.kotlinlearn.drugstore.databinding.CartItemLayoutBinding
import com.kotlinlearn.drugstore.interfaces.CartHelper
import com.kotlinlearn.drugstore.model.CartItem
import com.squareup.picasso.Picasso

class CartAdapter(private val context: Context?, private val cartHelper: CartHelper) : RecyclerView.Adapter<CartAdapter.ProductViewHolder>(){
    private var cartItemList:MutableList<CartItem> = mutableListOf<CartItem>()
    private val TAG = "ProdutAdapter"

    inner class ProductViewHolder(itemView: CartItemLayoutBinding): RecyclerView.ViewHolder(itemView.root){
        var itemImage : ImageView = itemView.itemImage
        var itemName : TextView = itemView.itemName
        var itemPrice : TextView = itemView.itemPrice
        var itemCount : TextView = itemView.itemCount
        var itemTotal : TextView = itemView.itemTotal
        var addBtn : ImageButton = itemView.addBtn
        var minceBtn : ImageButton = itemView.minceBtn
        var removeBtn : ImageButton = itemView.itemRemoveBtn
        var count : Int = 0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding = CartItemLayoutBinding.inflate(LayoutInflater.from(context), parent, false)
        return ProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        var item = cartItemList[position]

        if(!item.itemImage.isNullOrEmpty()) {
            Picasso.get().load(item.itemImage).placeholder(R.drawable.profile)
                .into(holder.itemImage)
        }
        holder.itemName.text = item.itemName
        holder.itemPrice.text = "${item.itemPrice.toString()} L.E"
        holder.count = item.itemCount
        holder.itemCount.text = "${holder.count}"
        holder.itemTotal.text = "${item.itemPrice * item.itemCount} L.E"
        holder.addBtn.setOnClickListener {
            holder.count++
            //call setItemCount()
            cartHelper.setCartItemCount(holder.count, item.itemName)
            holder.itemCount.text = "${holder.count}"
        }
        holder.minceBtn.setOnClickListener {
            if(holder.count > 1) {
                holder.count--
                //call setItemCount()
                cartHelper.setCartItemCount(holder.count, item.itemName)
                holder.itemCount.text = "${holder.count}"
            }
        }
        holder.removeBtn.setOnClickListener {
            //call removeCartItem()
            cartHelper.removeFromCart(item.itemName)
            cartItemList.remove(item)
            notifyDataSetChanged()
        }

    }





    override fun getItemCount(): Int {
        return cartItemList.size
    }

    fun setData(cartItemList: MutableList<CartItem>){
        this.cartItemList.addAll(cartItemList)
        notifyDataSetChanged() //to notify adapter that new data change has been happened to adapt it
    }
}