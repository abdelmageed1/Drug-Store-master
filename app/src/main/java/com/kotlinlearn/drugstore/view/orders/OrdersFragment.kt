package com.kotlinlearn.drugstore.view.orders

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.kotlinlearn.drugstore.R
import com.kotlinlearn.drugstore.databinding.FragmentOrdersBinding
import com.kotlinlearn.drugstore.interfaces.OrderHelper
import com.kotlinlearn.drugstore.model.MyOrder
import com.kotlinlearn.drugstore.model.Order


class OrdersFragment : Fragment() {
    private lateinit var binding: FragmentOrdersBinding
    private lateinit var orderViewModel: OrderViewModel
    var orders = mutableListOf<Order>()

    override fun onCreate(savedInstanceState: Bundle?) {
        orderViewModel = ViewModelProvider(this)[OrderViewModel::class.java]
        orderViewModel.getOrders()
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOrdersBinding.inflate(inflater,container,false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewOrders()
    }

    private fun viewOrders() {
        orderViewModel.ordersMutableLiveData.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                orders = it
                viewOrdersRecycler(orders)
            }
        })
    }

    private fun viewOrdersRecycler(ordersList: MutableList<Order>) {
        val layoutManager = LinearLayoutManager(context)
        val adapter = OrderAdapter(context, object : OrderHelper {
            override fun removeOrder(id: String) {
                orderViewModel.removeOrder(id)
                orderViewModel.getOrders()
                viewOrdersRecycler(ordersList)

            }

        })
        binding.reycleOfOrder.layoutManager = layoutManager
        binding.reycleOfOrder.adapter = adapter
        adapter.setData(ordersList)
    }


    companion object {
        @JvmStatic
        fun newInstance() =
            OrdersFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}