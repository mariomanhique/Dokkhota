package com.mariomanhique.dokkhota.data.repository.userDataRepository

import androidx.datastore.core.DataStore
import com.mariomanhique.dokkhota.model.UserData
import com.mariomanhique.dokkhota.model.UserPreferences
import javax.inject.Inject
import kotlinx.coroutines.flow.map

class UserDataRepositoryImpl @Inject constructor(
    private val userPreferences: DataStore<UserPreferences>
): UserDataRepository {

    override val userData = userPreferences.data
        .map {
            UserData(
                examNumber = it.examNumber
            )
        }

    override suspend fun setExamNr(examNr: String){
        userPreferences.updateData {
            it.copy(
                examNumber = examNr
            )
        }
    }


}