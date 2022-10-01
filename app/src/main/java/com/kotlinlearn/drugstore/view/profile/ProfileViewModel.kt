package com.kotlinlearn.drugstore.view.profile

import android.app.Application
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kotlinlearn.drugstore.model.User
import com.kotlinlearn.drugstore.repository.ProfileRepository
import java.util.HashMap

class ProfileViewModel(application: Application) : AndroidViewModel(application) {
    val repository: ProfileRepository = ProfileRepository()
    var userMutableLiveData = repository.userMutableLiveData

    fun getCurrentUser() {
        repository.getCurrentUser()
    }

    fun setImage(uri: Uri) {
        repository.setImage(uri)
    }

    fun setUserInfo(userMap: HashMap<String, String>) {
        repository.setUserInfo(userMap)
    }
}