package com.kotlinlearn.drugstore.view.main.authentication

import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.kotlinlearn.drugstore.view.main.MainActivity
import com.kotlinlearn.drugstore.databinding.FragmentSignUpBinding


class SignUpFragment : Fragment() {
    private lateinit var binding: FragmentSignUpBinding
    private lateinit var authenticationViewModel: AuthenticationViewModel
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
        binding = FragmentSignUpBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        authenticationViewModel = ViewModelProvider(this)[AuthenticationViewModel::class.java]
        authenticationViewModel.userMutableLiveData.observe(viewLifecycleOwner, Observer {
            if(it != null) {

                Toast.makeText(context, "User Registered Successfully", Toast.LENGTH_SHORT).apply {
                    setGravity(Gravity.TOP,0,0)
                    show()
                }
                startActivity(Intent(context, MainActivity::class.java))
            }
        })
        initView()
    }

    private fun initView() {
        binding.btnSignUp.setOnClickListener {
            var email:String = binding.rgisterEmail.text.toString() + "@gmail.com"
            var password:String = binding.registerPassword.text.toString()
            val firstName=binding.firstname.editText?.text.toString()
            val lastName=binding.lastname.editText?.text.toString()
            if (!(email.isNullOrEmpty() || password.isNullOrEmpty() || firstName.isNullOrEmpty() || lastName.isNullOrEmpty())) {
                authenticationViewModel.register(email, password, firstName, lastName)
                //startActivity(Intent(context, HomeActivity::class.java))
            } else {
                Toast.makeText(context, "Empty Field is not allowed here", Toast.LENGTH_SHORT).show()
            }

        }
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SignUpFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}