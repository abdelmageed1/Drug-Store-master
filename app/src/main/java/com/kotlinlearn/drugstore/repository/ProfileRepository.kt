package com.kotlinlearn.drugstore.repository

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import com.kotlinlearn.drugstore.utils.Constants
import com.kotlinlearn.drugstore.model.User
import java.util.HashMap

class ProfileRepository {
    private var myAuth = FirebaseAuth.getInstance()
    private var storageRef = FirebaseStorage.getInstance().reference
    var userMutableLiveData = MutableLiveData<User>()
    private var reference = FirebaseDatabase.getInstance().reference

    fun getCurrentUser() {
        reference.child(Constants.UserPath).child(myAuth.currentUser?.uid.toString())
            .addValueEventListener(object :ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    userMutableLiveData.postValue(snapshot.getValue(User::class.java))
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })

    }

    fun setImage(uri: Uri) {
        val imageRef = storageRef.child("${Constants.ProfileImagesPath}/${myAuth.currentUser?.uid.toString()}")
        imageRef.putFile(uri).addOnSuccessListener {
            imageRef.downloadUrl.addOnCompleteListener {
                setUserImage(it.result.toString())
            }
        }
    }

    private fun setUserImage(url: String) {
        reference.child(Constants.UserPath).child(myAuth.currentUser?.uid.toString()).child("image").setValue(url)
    }

    fun setUserInfo(userMap: HashMap<String, String>) {
        reference.child(Constants.UserPath).child(myAuth.currentUser?.uid.toString()).updateChildren(
            userMap as Map<String, Any>
        )
    }
}