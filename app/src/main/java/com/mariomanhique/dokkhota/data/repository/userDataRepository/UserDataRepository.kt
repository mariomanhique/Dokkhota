package com.mariomanhique.dokkhota.data.repository.userDataRepository

import com.mariomanhique.dokkhota.model.UserData
import kotlinx.coroutines.flow.Flow

interface UserDataRepository {

    /**
     * Stream of [UserData]
     */
    val userData: Flow<UserData>

    suspend fun setExamNr(examNr: String)

}