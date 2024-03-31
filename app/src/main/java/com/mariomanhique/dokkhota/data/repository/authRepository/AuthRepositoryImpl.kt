package com.mariomanhique.dokkhota.data.repository.authRepository


import com.mariomanhique.dokkhota.data.repository.authRepository.utils.await
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.firestore.FirebaseFirestore
import  com.mariomanhique.dokkhota.model.Result
import com.mariomanhique.dokkhota.model.User
import javax.inject.Inject


class AuthRepositoryImpl @Inject constructor(
): AuthRepository {

    override suspend fun signIn(email: String, password: String): Result<Boolean> {
        val firebaseAuth = FirebaseAuth.getInstance()

        return try {
            firebaseAuth.signInWithEmailAndPassword(email,password).await().user
            Result.Success(true)
        }catch (e: Exception){
            Result.Error(e)
        }
    }

    override suspend fun signUp(email: String, username: String, password: String): Result<Boolean> {
        val  firebaseAuth = FirebaseAuth.getInstance()
        val firestore = FirebaseFirestore.getInstance().collection("profile")

        return try {
            val user = firebaseAuth.createUserWithEmailAndPassword(email,password).await().user
            if  (user != null){
                user.updateProfile(UserProfileChangeRequest.Builder().setDisplayName(username).build())
                    firestore.document(user.uid).set(
                        User(
                            user.uid,
                            username = username,
                            email = email,
                            profilePictureUrl = ""
                        )
                    )
                Result.Success(true)
            } else{
                Result.Success(false)
            }

        }catch (e: Exception){
            Result.Error(e)
        }

    }

}