package com.kotlinlearn.drugstore.view.products.product

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.kotlinlearn.drugstore.R
import com.kotlinlearn.drugstore.databinding.FragmentProductBinding
import com.kotlinlearn.drugstore.model.CartItem
import com.kotlinlearn.drugstore.model.Product
import com.kotlinlearn.drugstore.view.products.ProductsViewModel
import com.squareup.picasso.Picasso

class ProductFragment : Fragment() {
    private lateinit var binding: FragmentProductBinding
    private lateinit var productsViewModel: ProductsViewModel
    private var count : Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        productsViewModel = ViewModelProvider(this)[ProductsViewModel::class.java]
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentProductBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setCount()
        initView()
    }

    private fun setCount() {
        count = (binding.count.text as String).toInt()
        binding.addBtn.setOnClickListener {
            if (count < currentProduct.stockCount) {
                count++
            } else {
                Toast.makeText(context, "Insufficient amount in stock", Toast.LENGTH_SHORT).show()
            }
            binding.count.text = count.toString()
        }
        binding.minceBtn.setOnClickListener {
            if (count > 1)
                count--
            binding.count.text = count.toString()
        }

    }

    private fun initView() {
        initIsFavourite()
        binding.productName.text = currentProduct.pName
        binding.price.text = "${currentProduct.price.toString()} L.E"
        binding.pDes.text = currentProduct.description
        binding.stockCount.text = "${currentProduct.stockCount} items in Stock"
        if (!currentProduct.img.isNullOrEmpty())
            Picasso.get().load(currentProduct.img).into(binding.productImg)
        binding.addToCartBtn.setOnClickListener { addToCart() }

        binding.homeBtn.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_productFragment_to_navigation_home)
        }
    }
    private fun addToCart() {
        val cartItem = CartItem(currentProduct.pName, count, currentProduct.price, currentProduct.img)
        productsViewModel.addToCart(cartItem)
        Toast.makeText(context, "Added to cart", Toast.LENGTH_SHORT).show()
    }

    private fun initIsFavourite() {
        binding.favouriteChb.isChecked = favProduct
        binding.favouriteChb.setOnClickListener {
            if (binding.favouriteChb.isChecked){
                productsViewModel.addToFavourites(currentProduct)
            } else {
                productsViewModel.removeFromFavourites(currentProduct)
            }
        }
    }

    companion object {
        lateinit var currentProduct:Product
        var favProduct:Boolean = false
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ProductFragment().apply {
                arguments = Bundle().apply {
                }
            }
        fun getProductInfo(product: Product, isFavourite: Boolean) {
            currentProduct = product
            favProduct = isFavourite
        }
    }
}