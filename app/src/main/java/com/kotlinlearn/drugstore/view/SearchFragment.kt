package com.kotlinlearn.drugstore.view

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.SearchView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.kotlinlearn.drugstore.R
import com.kotlinlearn.drugstore.databinding.FragmentSearchBinding
import com.kotlinlearn.drugstore.databinding.OrderLayoutBinding
import com.kotlinlearn.drugstore.interfaces.Favourite
import com.kotlinlearn.drugstore.model.Product
import com.kotlinlearn.drugstore.view.products.ProductAdapter
import com.kotlinlearn.drugstore.view.products.ProductsViewModel

class SearchFragment : Fragment() {
    private lateinit var binding: FragmentSearchBinding
    private lateinit var productsViewModel: ProductsViewModel
    private lateinit var adapterCategories: ArrayAdapter<String>
    private var favouritesList:MutableList<Product> = mutableListOf()
    var categories = arrayOf<String>("Hair care", "Vitamins and minerals", "Mom and baby", "Skin care")
    var products = mutableListOf<Product>()

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
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        categories = arrayOf<String>("Hair care", "Vitamins and minerals", "Mom and baby", "Skin care")
        setObservers()
        initView()
    }



    private fun initView() {
        setCategoriesMenu()
        setSearchView()
    }


    private fun setCategoriesMenu() {
        adapterCategories = ArrayAdapter(context as Context, R.layout.category_menu_layout, categories)
        binding.categoriesMenu.setAdapter(adapterCategories)
        binding.categoriesMenu.setOnItemClickListener { parent, view, position, id ->

            var category = parent.getItemAtPosition(position).toString()
            //this.category = category
            productsViewModel.getProducts(category)
        }
    }

    private fun setSearchView() {
        binding.searchView.clearFocus()
        binding.searchView.setOnQueryTextListener(object :SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(p0: String?): Boolean {
                TODO("Not yet implemented")
            }

            override fun onQueryTextChange(text: String?): Boolean {
                filterList(text as String)
                return true
            }

        })
    }

    private fun filterList(text: String) {
        var searchResult = mutableListOf<Product>()
        for (product in products) {
            if (product.pName.contains(text, true)) {
                searchResult.add(product)
            }
        }
        if (searchResult.isEmpty() && !text.isNullOrEmpty()) {
            Toast.makeText(context,"Nothing Found...", Toast.LENGTH_SHORT).show()
        } else {
            setRecycler(searchResult)
        }
    }

    private fun setRecycler(searchResult: MutableList<Product>) {
        binding.resultRecycler.layoutManager = LinearLayoutManager(context)
        val adapter = ProductAdapter(context, object : Favourite {
            override fun addToFavourites(product: Product) {
                productsViewModel.addToFavourites(product)
            }

            override fun removeFromFavourites(product: Product) {
                productsViewModel.removeFromFavourites(product)
            }

        }, favouritesList)

        binding.resultRecycler.adapter = adapter
        adapter.setData(searchResult)

    }

    private fun setObservers() {
        productsViewModel.productsMutableLiveData.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                products = it
            }
        })

        productsViewModel.favouritesMutableLiveData.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                favouritesList = it
            }
        })
    }

    companion object {

        @JvmStatic
        fun newInstance() =
            SearchFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}