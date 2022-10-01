package com.kotlinlearn.drugstore.view.products

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import com.kotlinlearn.drugstore.R
import com.kotlinlearn.drugstore.databinding.FragmentProductsBinding
import com.kotlinlearn.drugstore.interfaces.Favourite
import com.kotlinlearn.drugstore.model.Product

class ProductsFragment : Fragment() {
    private lateinit var binding:FragmentProductsBinding
    private lateinit var category:String
    private lateinit var productsViewModel:ProductsViewModel
    private var favouritesList:MutableList<Product> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        productsViewModel = ViewModelProvider(this)[ProductsViewModel::class.java]
        productsViewModel.getFavourites()
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentProductsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //observe
        productsViewModel.favouritesMutableLiveData.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                favouritesList = it
            }
        })
        category = staticCategory
        getProducts(category)
        setListeners()
        initView()
    }



    private fun initView() {
        binding.categoryName.text = category
        //getProducts()
    }

    private fun setProductsRecyclerView(products: MutableList<Product>) {
        val layoutManager = GridLayoutManager(context, 2)
        val adapter = ProductAdapter(context, object : Favourite {
            override fun addToFavourites(product: Product) {
                productsViewModel.addToFavourites(product)
            }

            override fun removeFromFavourites(product: Product) {
                productsViewModel.removeFromFavourites(product)
            }

        }, favouritesList)
        binding.productsRecyclerView.layoutManager = layoutManager
        binding.productsRecyclerView.adapter = adapter
        adapter.setData(products)
    }

    private fun getProducts(category: String){
        productsViewModel.getProducts(category)
        productsViewModel.productsMutableLiveData.observe(viewLifecycleOwner, Observer {
            if(it != null){
                setProductsRecyclerView(it)
            }
        })
    }

    private fun setListeners() {
        binding.backBtn.setOnClickListener {

        }
        binding.homeBtn.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_productsFragment_to_navigation_home)
        }
    }

    companion object {
        lateinit var staticCategory:String

        @JvmStatic
        fun newInstance() =
            ProductsFragment().apply {
                arguments = Bundle().apply {

                }
            }

        fun setIsFav(isFav:Boolean, product: Product){

        }

        fun getCategory(tv: String) {
            staticCategory = tv
        }
    }
}