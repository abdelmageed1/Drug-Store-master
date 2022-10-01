//Codded by Bilal Azzam
package com.kotlinlearn.drugstore.view.checkout

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.google.firebase.auth.FirebaseAuth
import com.kotlinlearn.drugstore.R
import com.kotlinlearn.drugstore.databinding.FragmentCheckOutBinding
import com.kotlinlearn.drugstore.model.CartItem
import com.kotlinlearn.drugstore.model.Order
import com.kotlinlearn.drugstore.utils.Constants
import com.kotlinlearn.drugstore.view.checkout.payment.PaymentFragment
import com.kotlinlearn.drugstore.view.orders.OrderViewModel
import java.text.SimpleDateFormat
import java.util.*

class CheckOutFragment : Fragment() {
    private lateinit var binding : FragmentCheckOutBinding
    private lateinit var order : Order
    private lateinit var orderViewModel: OrderViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        orderViewModel = ViewModelProvider(this)[OrderViewModel::class.java]
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCheckOutBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.homeBtn.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_checkOutFragment_to_navigation_home)
        }

        binding.confirmBtn.setOnClickListener {
            sendOrder(it)
        }
    }

    private fun sendOrder(it : View) {
        collectOrderData()
        when(binding.radioGroup.checkedRadioButtonId){
            binding.paymentByVodafoneCash.id -> {Navigation.findNavController(it).navigate(R.id.action_checkOutFragment_to_paymentFragment)
                PaymentFragment.sendOrder(order)
            }
            binding.PaymentWhenRecieved.id -> {orderViewModel.sendOrder(order)
                Toast.makeText(context, "Order Sended Successfully", Toast.LENGTH_SHORT).show()
                Navigation.findNavController(it).navigate(R.id.action_checkOutFragment_to_navigation_orders)
            }
        }
    }

    private fun collectOrderData() {
        val date = getCurrentDate().toString()
        val time = getCurrentTime().toString()
        val id = "${FirebaseAuth.getInstance().currentUser?.uid.toString()}$date$time"
        val state = Constants.State_Not_Shipped
        val totalAmount = orderTotalAmount
        val products = orderItems
        val fullName = binding.fnameEdtxt.text.toString()
        val phone = binding.phoneEdtxt.text.toString()
        val address = binding.addressEdtxt.text.toString()
        val paymentMethod = getPaymentMethod()
        order = Order(id, fullName, phone, address, state, date, time, totalAmount, products, paymentMethod)
    }

    private fun getPaymentMethod(): String {
        var paymentMethod = "None"
        when(binding.radioGroup.checkedRadioButtonId) {
            binding.PaymentWhenRecieved.id -> paymentMethod = Constants.Payment_At_Recieved
            binding.paymentByVodafoneCash.id -> paymentMethod = Constants.Payment_Vodafone_Cash
        }
        return paymentMethod
    }


    fun getCurrentDate(): String? {
        val calendar = Calendar.getInstance()
        val simpleDateFormat =
            SimpleDateFormat("MMM dd, yyyy")
        return simpleDateFormat.format(calendar.time)
    }

    fun getCurrentTime(): String? {
        val calendar = Calendar.getInstance()
        val simpleDateFormat = SimpleDateFormat("HH:mm:ss a")
        return simpleDateFormat.format(calendar.time)
    }
    

    companion object {

        var orderTotalAmount : Int = 0
        var orderItems : MutableList<CartItem> = mutableListOf()
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            CheckOutFragment().apply {
                arguments = Bundle().apply {
                }
            }

        fun getCartData(totalAmount : Int,cartItems : MutableList<CartItem>){
            orderItems.addAll(cartItems)
            orderTotalAmount = totalAmount
        }
    }
}