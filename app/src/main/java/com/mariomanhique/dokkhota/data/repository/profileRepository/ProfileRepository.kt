package com.mariomanhique.dokkhota.data.repository.profileRepository

import com.mariomanhique.dokkhota.model.RequestState
import com.mariomanhique.dokkhota.model.Result
import com.mariomanhique.dokkhota.model.User
import kotlinx.coroutines.flow.Flow

interface ProfileRepository {

    fun getProfile():Flow<Result<User>>
    fun updateImageProfile(imageUrl: String): RequestState<String>
    fun updateUsername(username: String):RequestState<String>
}