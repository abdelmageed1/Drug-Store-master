package com.kotlinlearn.drugstore.view.cart

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.kotlinlearn.drugstore.R
import com.kotlinlearn.drugstore.databinding.FragmentCartBinding
import com.kotlinlearn.drugstore.interfaces.CartHelper
import com.kotlinlearn.drugstore.model.CartItem
import com.kotlinlearn.drugstore.view.checkout.CheckOutFragment
import com.kotlinlearn.drugstore.view.products.ProductsViewModel

class CartFragment : Fragment() {
    lateinit var binding: FragmentCartBinding
    var cartItems  = mutableListOf<CartItem>()
    private lateinit var productsViewModel : ProductsViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        productsViewModel = ViewModelProvider(this)[ProductsViewModel::class.java]
        productsViewModel.getCartItems()
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =FragmentCartBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        productsViewModel.cartItemsMutableLiveData.observe(viewLifecycleOwner, Observer {
            if (it != null){
                cartItems = it
                setCartRecycler(cartItems)
                //countTotalAmount()
            }
        })

        binding.checkOutBtn.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_cartFragment_to_checkOutFragment)
            CheckOutFragment.getCartData(countTotalAmount(cartItems), cartItems)
        }

    }

    private fun setCartRecycler(cartItems: MutableList<CartItem>) {
        val layoutManager = LinearLayoutManager(context)
        val adapter = CartAdapter(context, object : CartHelper{
            override fun setCartItemCount(count: Int, itemName: String) {
                productsViewModel.setCartItemCount(count, itemName)
                productsViewModel.getCartItems()
                setCartRecycler(cartItems)
                //countTotalAmount()
            }

            override fun removeFromCart(itemName: String) {
                productsViewModel.removeFromCart(itemName)
                productsViewModel.getCartItems()
                setCartRecycler(cartItems)
                //countTotalAmount()
            }

        })
        binding.reycleOfCard.layoutManager = layoutManager
        binding.reycleOfCard.adapter = adapter
        adapter.setData(cartItems)
        countTotalAmount(cartItems)
    }

    fun countTotalAmount(cartItems:MutableList<CartItem>) : Int {
        var total:Int = 0
        for (item in cartItems){
            total += item.itemCount * item.itemPrice
        }
        binding.totalAmount.text = "$total L.E"
        return total
    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            CartFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}