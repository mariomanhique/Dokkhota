package com.mariomanhique.dokkhota.presentation.screens.exam

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mariomanhique.dokkhota.data.repository.examsRepository.ExamsRepository
import com.mariomanhique.dokkhota.data.repository.userDataRepository.UserDataRepository
import com.mariomanhique.dokkhota.model.Exam
import com.mariomanhique.dokkhota.model.Result
import com.mariomanhique.dokkhota.util.Constants.CATEGORY_ARG
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class ExamsListViewModel @Inject constructor(
    private val examsRepository: ExamsRepository,
    private val savedStateHandle: SavedStateHandle,
    private val userDataRepository: UserDataRepository
): ViewModel() {

    private var _examDetails = MutableStateFlow<Result<Exam>>(Result.Loading)
    val examDetails = _examDetails

    init {
        val category = savedStateHandle.get<String>(
        key = CATEGORY_ARG)
        viewModelScope.launch {
            userDataRepository.userData
                .distinctUntilChanged()
                .collectLatest { userData ->
                    getExamDetails(
                        examNr = userData.examNumber,
                        category = category.toString().lowercase(),
                    )
                }
        }

    }



   suspend fun setExamNr(number: String){
       userDataRepository.setExamNr(
           examNr = number
       )
    }

    private fun getExamDetails(
        examNr: String,
        category: String,
    ){
        viewModelScope.launch {
                examsRepository.getExamDetails(
                    examNr = examNr,
                    category = category
                ).distinctUntilChanged()
                    .collectLatest { result->

                        when(result){
                            is Result.Success -> {
                            }
                            is Result.Error -> {
                                Log.d("Exam", "getExamDetails: ${result.exception}")
                            }
                            is Result.Loading -> {
                                Log.d("Exam", "getExamDetails: Loading")
                            }

                        }
                    _examDetails.value = result

                }

        }
    }


    val category = savedStateHandle.getStateFlow<String?>(
        key = CATEGORY_ARG,
        initialValue = null
    ).stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = null,
    )

    val exams = savedStateHandle.getStateFlow<String?>(
        key = CATEGORY_ARG,
        initialValue = null
    ).flatMapLatest {category->
        if (category == null){
            flowOf(emptyList())
        } else{
            examsRepository.getExamsList(category.lowercase())
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = null,
    )

}

sealed interface ExamUiState {
    data class Success(val news: Exam) : ExamUiState
    data object Error : ExamUiState
    data object Loading : ExamUiState
}