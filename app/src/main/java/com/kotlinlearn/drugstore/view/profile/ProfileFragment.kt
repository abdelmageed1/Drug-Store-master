package com.kotlinlearn.drugstore.view.profile

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.kotlinlearn.drugstore.view.main.MainActivity
import com.kotlinlearn.drugstore.R
import com.kotlinlearn.drugstore.databinding.FragmentProfileBinding
import com.kotlinlearn.drugstore.model.User
import com.kotlinlearn.drugstore.view.main.authentication.AuthenticationViewModel
import com.kotlinlearn.drugstore.view.splash.SplashActivity
import com.squareup.picasso.Picasso

class ProfileFragment : Fragment() {
    private lateinit var currentUser: User
    private lateinit var binding: FragmentProfileBinding
    private lateinit var authenticationViewModel: AuthenticationViewModel
    private lateinit var profileViewModel: ProfileViewModel
    private lateinit var launcher:ActivityResultLauncher<String>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        authenticationViewModel = ViewModelProvider(this)[AuthenticationViewModel::class.java]
        profileViewModel = ViewModelProvider(this)[ProfileViewModel::class.java]
        profileViewModel.getCurrentUser()
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListeners()
        setObservers()
        launcher = registerForActivityResult(ActivityResultContracts.GetContent(), ActivityResultCallback {
            profileViewModel.setImage(it as Uri)
        })
    }



    private fun setObservers() {
        // observe for log out
        authenticationViewModel.loggedOutMutableLiveData.observe(viewLifecycleOwner, Observer{
            if (it){
                startActivity(Intent(context, SplashActivity::class.java))
            }
        })

        //observe for current user info

        profileViewModel.userMutableLiveData.observe(viewLifecycleOwner, Observer {
            if(it != null){
                currentUser = it
                initView()
            }
        })
    }

    private fun setListeners() {
        //menu
        binding.menuBtn.setOnClickListener {
            val array = resources.getStringArray(R.array.items)
            val builder = AlertDialog.Builder(context as Context)
            builder.setTitle("Select one")
                .setItems(R.array.items){ dialog, position ->
                    when(position) {
                        2 -> {authenticationViewModel.logOut()} //log out
                    }
                }.show()
        }

        //profile picture
        binding.changeProfileImg.setOnClickListener {
            openGallery()
        }

        //update user info
        binding.saveBtn.setOnClickListener {
            setUserInfo()
        }


    }

    private fun setUserInfo() {
        var userMap = HashMap<String, String>()
        userMap.put("address", binding.profileAddress.text.toString())
        userMap.put("email", binding.profileEmail.text.toString())
        userMap.put("firstName", binding.profileFname.text.toString())
        userMap.put("lastName", binding.profileLname.text.toString())
        userMap.put("phone", binding.profilePhone.text.toString())
        profileViewModel.setUserInfo(userMap)
    }

    private fun initView() {
        binding.profileFname.setText(currentUser.firstName)
        binding.profileLname.setText(currentUser.lastName)
        binding.profileEmail.setText(currentUser.email)
        binding.profilePhone.setText(currentUser.phone)
        binding.profileAddress.setText(currentUser.address)
        if(!currentUser.image.isNullOrEmpty()){
            Picasso.get().load(currentUser.image).placeholder(R.drawable.profile).into(binding.profileImg)
        }
    }



    private fun openGallery() {

        launcher.launch("image/*")
    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ProfileFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}