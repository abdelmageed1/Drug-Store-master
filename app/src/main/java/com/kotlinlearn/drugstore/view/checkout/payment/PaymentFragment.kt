package com.kotlinlearn.drugstore.view.checkout.payment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kotlinlearn.drugstore.R
import com.kotlinlearn.drugstore.model.Order

class PaymentFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_payment, container, false)
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            PaymentFragment().apply {
                arguments = Bundle().apply {
                }
            }

        fun sendOrder(order : Order) {

        }
    }
}