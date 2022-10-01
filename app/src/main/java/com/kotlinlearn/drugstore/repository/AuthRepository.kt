package com.kotlinlearn.drugstore.repository

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.kotlinlearn.drugstore.utils.Constants
import com.kotlinlearn.drugstore.model.User

class AuthRepository(application: Application) {
    private var application: Application
    var userMutableLiveData:MutableLiveData<FirebaseUser>
    var loggedOutMutableLiveData:MutableLiveData<Boolean>
    private var myAuth: FirebaseAuth
    private var referance: DatabaseReference

    init {
        this.application = application
        this.userMutableLiveData = MutableLiveData()
        this.loggedOutMutableLiveData = MutableLiveData()
        myAuth = FirebaseAuth.getInstance()
        referance = FirebaseDatabase.getInstance().reference
    }

    fun register(email : String, password : String, firstName:String, lastName:String) {
        myAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if(it.isSuccessful) {
                    initializeUserInfo(email, password, firstName, lastName)
                    userMutableLiveData.postValue(myAuth.currentUser)
                } else {
                    Toast.makeText(application, "Registeration failed!${it.exception?.message.toString()}", Toast.LENGTH_SHORT).show()
                }
            }

    }

    fun initializeUserInfo(email : String, password : String, firstName:String, lastName:String) {
        val uid = myAuth.currentUser?.uid as String
        val user= User(firstName,lastName,uid,email,password, phone = "", image = "", address = "")
        referance.child(Constants.UserPath).child(uid).setValue(user).addOnSuccessListener {
            Toast.makeText(application,"Signed Up Successfully", Toast.LENGTH_SHORT).show()
        }.addOnFailureListener{
            Toast.makeText(application,"${it.message}", Toast.LENGTH_SHORT).show()
        }
    }

    fun login(email:String, password:String) {
        if (!(email.isNullOrEmpty() || password.isNullOrEmpty())) {
            myAuth.signInWithEmailAndPassword(email, password).addOnSuccessListener {
                it?.let {
                    userMutableLiveData.postValue(myAuth.currentUser)
                    Toast.makeText(application,"Logged In Successfully", Toast.LENGTH_SHORT).show()
                }
            }.addOnFailureListener {
                Toast.makeText(application, "${it.toString()}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun logOut() {
        myAuth.signOut()
        loggedOutMutableLiveData.postValue(true)
    }

}