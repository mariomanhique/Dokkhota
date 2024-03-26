package com.mariomanhique.dokkhota.data.repository.authRepository
import  com.mariomanhique.dokkhota.model.Result


interface AuthRepository {
    suspend fun signIn(email:String,password:String):Result<Boolean>
    suspend fun signUp(email:String,username:String,password:String): Result<Boolean>

}