//Codded by : Bilal Azzam
package com.kotlinlearn.drugstore.view.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import com.google.firebase.auth.FirebaseAuth
import com.kotlinlearn.drugstore.R
import com.kotlinlearn.drugstore.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var myAuth:FirebaseAuth
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()

    }

    private fun initView() {

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_fragment) as NavHostFragment
        //val navController = navHostFragment.navController
        //supportFragmentManager.beginTransaction().replace(R.id.nav_fragment, SplashFragment()).commit()
    }
}