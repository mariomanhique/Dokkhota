package com.mariomanhique.dokkhota.data.repository.profileRepository

import com.mariomanhique.dokkhota.model.RequestState
import com.mariomanhique.dokkhota.model.UserData
import kotlinx.coroutines.flow.Flow

interface ProfileRepository {

    fun getProfile():Flow<UserData?>
    fun updateImageProfile(imageUrl: String): RequestState<String>
    fun updateUsername(username: String):RequestState<String>
}