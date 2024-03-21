package com.mariomanhique.dokkhota.presentation.screens.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.launch
import java.time.ZonedDateTime
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
) :ViewModel() {

    private lateinit var allDiariesJob: Job
    private lateinit var allFilteredDiariesJob: Job

    var dateIsSelected by mutableStateOf(false)
        private set// modify only withing viewModel


    init{
        getDiaries()
    }

    fun getDiaries(zonedDateTime: ZonedDateTime?=null){
        dateIsSelected = zonedDateTime != null
        if(dateIsSelected && zonedDateTime != null){
            observeFilteredDiaries(zonedDateTime = zonedDateTime)
        } else{
            observeAllDiaries()
        }

    }

     fun observeFilteredDiaries(zonedDateTime: ZonedDateTime){
       allFilteredDiariesJob = viewModelScope.launch {
           if(::allDiariesJob.isInitialized){
               allDiariesJob.cancelAndJoin()
           }

        }
    }
    private fun observeAllDiaries(){

        allDiariesJob = viewModelScope.launch {
            if(::allFilteredDiariesJob.isInitialized){
                allFilteredDiariesJob.cancelAndJoin()
            }


        }

    }

    fun deleteDiaries(
        onSuccess: (Boolean) -> Unit,
        onError: (Exception) -> Unit
    ){
        viewModelScope.launch() {

        }

    }


    fun deleteAllDiaries(
        onSuccess: (Boolean) -> Unit,
        onError: (Exception) -> Unit
    ){
         val user = FirebaseAuth.getInstance().currentUser

    }




}