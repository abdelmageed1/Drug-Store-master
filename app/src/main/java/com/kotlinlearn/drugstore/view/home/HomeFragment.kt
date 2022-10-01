package com.kotlinlearn.drugstore.view.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.denzcoskun.imageslider.models.SlideModel
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import com.kotlinlearn.drugstore.R
import com.kotlinlearn.drugstore.databinding.FragmentHomeBinding
import com.kotlinlearn.drugstore.model.MyCategory
import com.kotlinlearn.drugstore.model.Product
import com.kotlinlearn.drugstore.interfaces.Favourite
import com.kotlinlearn.drugstore.view.products.ProductAdapter
import com.kotlinlearn.drugstore.view.products.ProductsViewModel
import com.kotlinlearn.drugstore.view.profile.ProfileViewModel
import com.squareup.picasso.Picasso

class HomeFragment : Fragment() {
    private lateinit var profileViewModel: ProfileViewModel
    lateinit var binding: FragmentHomeBinding
    private lateinit var productsViewModel: ProductsViewModel
    var arr_Category = ArrayList<MyCategory>()
    var favouritesList = mutableListOf<Product>()
    lateinit var adapter:ProductAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        profileViewModel = ViewModelProvider(this)[ProfileViewModel::class.java]
        profileViewModel.getCurrentUser()
        productsViewModel = ViewModelProvider(this)[ProductsViewModel::class.java]
        productsViewModel.getFavourites()
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.photoProfile.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_navigation_home_to_navigation_profile)
        }
        binding.homeUserName.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_navigation_home_to_navigation_profile)
        }
        setObservers()
        initViewCategory()
        //initFavouriteRecycler(favouritesList)
        //viewFavourites()
        //setSlider()
    }

    private fun viewFavourites() {
        adapter.setData(favouritesList)
    }


    private fun setObservers() {
        profileViewModel.userMutableLiveData.observe(viewLifecycleOwner, Observer {
            if(it != null){
                binding.homeUserName.text = "${it.firstName} ${it.lastName}"
                if (!it.image.isNullOrEmpty()){
                    Picasso.get().load(it.image).into(binding.photoProfile)
                }

            }
        })

        productsViewModel.favouritesMutableLiveData.observe(viewLifecycleOwner, Observer{
            if (it != null) {
                favouritesList = it
                //adapter.setData(mutableListOf())
                initFavouriteRecycler(favouritesList)
            }

        })
    }

    private fun initFavouriteRecycler(favourites :MutableList<Product>) {
        val layoutManager = GridLayoutManager(context, 2)
        adapter = ProductAdapter(context, object : Favourite {
            override fun addToFavourites(product: Product) {
                productsViewModel.addToFavourites(product)
            }

            override fun removeFromFavourites(product: Product) {
                productsViewModel.removeFromFavourites(product)
                productsViewModel.getFavourites()
                initFavouriteRecycler(favourites)
            }

        }, favourites)
        binding.recycleMyFavorite.layoutManager = layoutManager
        binding.recycleMyFavorite.adapter = adapter
        adapter.setData(favourites)
    }

    private fun initViewCategory() {
        binding.recycleCategory.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        addItemCategory()
        var adapter = MyAdapterCategory(arr_Category)
        binding.recycleCategory.adapter = adapter
    }
    private fun addItemCategory(){
        arr_Category.add(MyCategory(R.drawable.photo_vitamins_minerals, "Vitamins and minerals"))
        arr_Category.add(MyCategory(R.drawable.photo_haircare, "Hair care"))
        arr_Category.add(MyCategory(R.drawable.photo_mom_baby, "Mom and baby"))
        arr_Category.add(MyCategory(R.drawable.photo_skincare, "Skin care"))

    }
/*
    fun setSlider() {
        var imageList = ArrayList<SlideModel>()

        var sliderPhotoPath: StorageReference = Firebase.storage.reference.child("SliderAds")
        sliderPhotoPath.child("ads.jpg").downloadUrl.addOnSuccessListener {
            imageList.add(SlideModel("$it"))
        }
        sliderPhotoPath.child("ads2.jpg").downloadUrl.addOnSuccessListener {
            imageList.add(SlideModel("$it"))
        }
        sliderPhotoPath.child("ads3.jpg").downloadUrl.addOnSuccessListener {
            imageList.add(SlideModel("$it"))
        }
        sliderPhotoPath.child("ads4.jpg").downloadUrl.addOnSuccessListener {
            imageList.add(SlideModel("$it"))
            binding.sliderAds.setImageList(imageList)
        }

    }

 */

    companion object {
        @JvmStatic
        fun newInstance() =
            HomeFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}