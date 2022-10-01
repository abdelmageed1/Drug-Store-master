package com.kotlinlearn.drugstore.model

import android.os.Parcelable
import com.google.firebase.auth.FirebaseUser
import java.io.Serializable

data class User(var firstName:String = "",
                var  lastName: String = "",
                var uid:String = "",
                var email :String = "",
                var password :String = "",
                var phone :String = "",
                var image :String = "",
                var address:String = ""):Serializable