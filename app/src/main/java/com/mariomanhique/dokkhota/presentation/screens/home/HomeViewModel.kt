package com.mariomanhique.dokkhota.presentation.screens.home

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.mariomanhique.dokkhota.data.repository.examsRepository.ExamsRepository
import com.mariomanhique.dokkhota.data.repository.examsRepository.Questions
import com.mariomanhique.dokkhota.model.Question
import com.mariomanhique.dokkhota.model.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.ZonedDateTime
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val examsRepository: ExamsRepository
) :ViewModel() {

    private lateinit var allDiariesJob: Job
    private lateinit var allFilteredDiariesJob: Job

    private var _exams = MutableStateFlow<Questions>(Result.Loading)
    val exams = _exams.asStateFlow()

    private var _categories = MutableStateFlow<List<String>>(emptyList())
    val categories = _categories.asStateFlow()



    var dateIsSelected by mutableStateOf(false)
        private set// modify only withing viewModel

    init{
        getQuestions(
            category = "english",
            examNr = "1"
        )
        getCategories()
    }

    private fun getCategories(){
        viewModelScope.launch {
           examsRepository
                .getCategories().distinctUntilChanged()
                .collectLatest {
                    _categories.value = it
                }

        }
    }

   private fun getQuestions(
       category: String,
       examNr: String
   ){
        viewModelScope.launch {
            examsRepository
                .getExamQuestionsByCategory(
                    category = category,
                    examNr = examNr
                )
                .distinctUntilChanged()
                .collectLatest {
                    _exams.value = it
            }
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