package com.mariomanhique.dokkhota.presentation.screens.profile

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.auth.FirebaseAuth
import com.mariomanhique.diaryapp.presentation.screens.profile.GalleryImage
import com.mariomanhique.diaryapp.presentation.screens.profile.ProfileState
import com.mariomanhique.dokkhota.data.repository.profileRepository.ProfileRepository
import com.mariomanhique.dokkhota.model.UserData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val profileRepository: ProfileRepository,
): ViewModel() {

    val profileState = ProfileState()

    private var _userData: MutableStateFlow<UserData> =
        MutableStateFlow(UserData("","",""))

    val userData = _userData.asStateFlow()

    init {
        getCurrentUser()
    }
    private fun getCurrentUser(){
        viewModelScope.launch {
            profileRepository.getProfile().collect{userData->

                userData?.let {

                   _userData.value = it
               }
            }
        }
    }

    fun addImage(
        image: Uri,
        imageType:String,
    ){
        val user = FirebaseAuth.getInstance().currentUser
        val remoteImagePath = "images/${user?.uid}/" +
                "${image.lastPathSegment}-${System.currentTimeMillis()}.$imageType"

        if (remoteImagePath.isNotEmpty()){
            profileState.addImageProfile(
                GalleryImage(
                    image = image,
                    remoteImagePath = remoteImagePath
                )
            )
        }
    }

    fun updateUsername(username: String){
        if (username.isEmpty()){
            return
        }
        viewModelScope.launch {
            profileRepository
                .updateUsername(username)
        }
    }

    fun updateImageProfile(
        imageUrl: String
    ){
        if (imageUrl.isEmpty()){
            return
        }
//        uploadImageToFirebase()
        viewModelScope.launch {
            profileRepository.updateImageProfile(
                imageUrl
            )
        }
    }
}

fun extractImagePath(fullImageUrl: String): String {
    val chunks = fullImageUrl.split("%2F")
    val imageName = chunks[2].split("?").first()
    return "images/${Firebase.auth.currentUser?.uid}/$imageName"
}