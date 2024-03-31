package com.mariomanhique.dokkhota.data.repository.profileRepository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.snapshots
import com.google.firebase.firestore.toObject
import com.mariomanhique.dokkhota.model.RequestState
import com.mariomanhique.dokkhota.model.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import com.mariomanhique.dokkhota.model.Result
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ProfileRepositoryImpl @Inject constructor(
): ProfileRepository {
    private val rf = FirebaseFirestore.getInstance().collection("profile")

    private lateinit var updateUser: RequestState<String>

    override fun getProfile(): Flow<Result<User>> {
        return flow {
            val user = FirebaseAuth.getInstance().currentUser
            if (user != null) {
                rf.document(user.uid)
                    .snapshots()
                    .map { snapshot ->
                        val userProfile = snapshot.toObject<User>()
                        if (userProfile != null) {
                            emit(Result.Success(userProfile))
                        } else {
                            // Emit a default User object if snapshot is null
                            emit(Result.Error(FirebaseFirestoreException("User Is Null",FirebaseFirestoreException.Code.NOT_FOUND)))
                        }
                    }
            } else {
                // Emit a default User object if user is null
                emit(Result.Error())
            }
        }
    }


    override fun updateImageProfile(imageUrl: String): RequestState<String> {
        val user = FirebaseAuth.getInstance().currentUser

        if (user !=null){
            return try {
                rf.document(user.uid)
                    .update(
                        mapOf(
                            "profilePictureUrl" to imageUrl
                        )
                    ).addOnSuccessListener {
                        updateUser = RequestState.Success("Sucesso")
                    }
                updateUser
            }catch (e:Exception){
                RequestState.Error(e)
            }
        }
        return RequestState.Error(Exception("user not authenticated"))
    }

    override fun updateUsername(username: String): RequestState<String> {
        val user = FirebaseAuth.getInstance().currentUser

        if (user !=null){
            return try {
                rf.document(user.uid)
                    .update(
                        mapOf(
                            "username" to username
                        )
                    ).addOnSuccessListener {
                        updateUser = RequestState.Success("Sucesso")
                    }
                updateUser
            }catch (e:Exception){
                RequestState.Error(e)
            }
        }
        return RequestState.Error(Exception("user not authenticated"))
    }

}