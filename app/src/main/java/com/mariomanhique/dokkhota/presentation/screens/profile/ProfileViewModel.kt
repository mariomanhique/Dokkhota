package com.mariomanhique.dokkhota.presentation.screens.profile

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.auth.FirebaseAuth
import com.mariomanhique.dokkhota.data.repository.profileRepository.ProfileRepository
import com.mariomanhique.dokkhota.model.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import com.mariomanhique.dokkhota.model.Result
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val profileRepository: ProfileRepository,
): ViewModel() {

    val profileState = ProfileState()

    private var _userData: MutableStateFlow<Result<User>> = MutableStateFlow(Result.Loading)

    val userData = _userData.asStateFlow()

    val profile: StateFlow<Result<User>> =
                    profileRepository
                        .getProfile()
                        .stateIn(
                            scope = viewModelScope,
                            started = SharingStarted.Eagerly,
                            initialValue = Result.Loading
                        )

//    init {
//        getCurrentUser()
//    }
//    private fun getCurrentUser(){
//        viewModelScope.launch {
//            profileRepository.getProfile().collect{user->
//                user.let {
//                   fetchImageFromFirebase(
//                       remoteImagePath = it,
//                       onImageDownload = {downloadedImage->
//                           profileState.addImageProfile(
//                               GalleryImage(
//                                   image = downloadedImage,
//                                   remoteImagePath = extractImagePath(
//                                       fullImageUrl = downloadedImage.toString()
//                                   )
//                               )
//                           )
//                       },
//                       onImageDownloadFailed = {}
//                   )
//                   _userData.value = it
//               }
//            }
//        }
//    }

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

    private fun uploadImageToFirebase(){
        viewModelScope.launch {
          val  galleryImage = profileState.image.value
            val storage = FirebaseStorage.getInstance().reference
            val imagePath = storage.child(galleryImage.remoteImagePath)

            imagePath.putFile(galleryImage.image).addOnProgressListener{
                it.uploadSessionUri
            }
        }
    }
    fun updateUsername(username: String){
        if (username.isEmpty()){
            return
        }
        viewModelScope.launch {
            uploadImageToFirebase()
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
        uploadImageToFirebase()
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

fun fetchImageFromFirebase(
    remoteImagePath: String,
    onImageDownload: (Uri) -> Unit,
    onImageDownloadFailed:(Exception) -> Unit,
){
    if(remoteImagePath.trim().isNotEmpty()){
        FirebaseStorage.getInstance().reference.child(remoteImagePath.trim()).downloadUrl
            .addOnSuccessListener{
                onImageDownload(it)
            }.addOnFailureListener{
                onImageDownloadFailed(it)
            }
    }
}