package com.kotlinlearn.drugstore.view.products

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.kotlinlearn.drugstore.R
import com.kotlinlearn.drugstore.databinding.ProductLayoutBinding
import com.kotlinlearn.drugstore.interfaces.Favourite
import com.kotlinlearn.drugstore.model.Product
import com.kotlinlearn.drugstore.view.products.product.ProductFragment
import com.squareup.picasso.Picasso

class ProductAdapter(private val context: Context?, private val favourite: Favourite, private val favouritesList:MutableList<Product>) : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>(){
    private var productsList:MutableList<Product> = mutableListOf<Product>()
    private val TAG = "ProdutAdapter"

    inner class ProductViewHolder(itemView: ProductLayoutBinding): RecyclerView.ViewHolder(itemView.root){
        var productImage : ImageView = itemView.productImage
        var productName : TextView = itemView.productName
        var productPrice : TextView = itemView.productPrice
        var isFav : CheckBox = itemView.favouriteChb
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding = ProductLayoutBinding.inflate(LayoutInflater.from(context), parent, false)
        return ProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        var product = productsList[position]

        if(!product.img.isNullOrEmpty()) {
            Picasso.get().load(product.img).placeholder(R.drawable.profile)
                .into(holder.productImage)
        }

        holder.productName.text = product.pName
        holder.productPrice.text = "${product.price.toString()} L.E"

        if (favouritesList.find { it.pName == product.pName } != null){
            holder.isFav.isChecked = true
        }

        holder.isFav.setOnClickListener {
            if(holder.isFav.isChecked) {
                favourite.addToFavourites(product)
            }else{
                favourite.removeFromFavourites(product)
            }

        }
        holder.productName.setOnClickListener {
            navigateToProducts(product, it, holder.isFav.isChecked)
        }

        holder.productImage.setOnClickListener {
            navigateToProducts(product, it, holder.isFav.isChecked)
        }

    }

    private fun navigateToProducts(product: Product, it: View, checked: Boolean) {
        if (Navigation.findNavController(it).currentDestination?.id == R.id.productsFragment){
            Navigation.findNavController(it).navigate(R.id.action_productsFragment_to_productFragment)
            ProductFragment.getProductInfo(product, checked)
        }else if (Navigation.findNavController(it).currentDestination?.id == R.id.navigation_home){
            Navigation.findNavController(it).navigate(R.id.action_homeFragment_to_productFragment)
            ProductFragment.getProductInfo(product, checked)
        }
    }

    override fun getItemCount(): Int {
        return productsList.size
    }

    fun setData(productsList: MutableList<Product>){

        this.productsList = productsList
        notifyDataSetChanged() //to notify adapter that new data change has been happened to adapt it
    }

}
