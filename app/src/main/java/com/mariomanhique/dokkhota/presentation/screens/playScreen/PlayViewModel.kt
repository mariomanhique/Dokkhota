package com.mariomanhique.dokkhota.presentation.screens.playScreen

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mariomanhique.dokkhota.data.repository.examsRepository.ExamsRepository
import com.mariomanhique.dokkhota.data.repository.examsRepository.Questions
import com.mariomanhique.dokkhota.model.Question
import com.mariomanhique.dokkhota.model.Result
import com.mariomanhique.dokkhota.util.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class PlayViewModel @Inject constructor(
    private val examsRepository: ExamsRepository,
    private val savedStateHandle: SavedStateHandle
): ViewModel() {


    private var _exams = MutableStateFlow<Questions>(Result.Loading)
    val exams = _exams.asStateFlow()

    val _examNr:MutableState<String> = mutableStateOf("")
    val _category:MutableState<String> = mutableStateOf("")

    init {
        val savedArgs = savedStateHandle.get<String?>(
            key = Constants.EXAM_NUMBER_ARG,
        )

        if (savedArgs != null) {

            val keys = savedArgs.split("-")
            val examNr = keys[0]
            val category = keys[1]

            _examNr.value = examNr
            _category.value = category

            Log.d("Keys", ":$examNr $category ")

            getQuestion(
                category = category.lowercase(),
                examNr = examNr
            )
        }
    }



//    val exam_nr = savedStateHandle.getStateFlow<String?>(
//        key = Constants.CATEGORY_ARG,
//        initialValue = null
//    ).flatMapLatest { category ->
//        if (category == null) {
//            flowOf(emptyList())
//        } else {
//            examsRepository.getExamsList(category.lowercase())
//        }
//    }.stateIn(
//        scope = viewModelScope,
//        started = SharingStarted.WhileSubscribed(5_000),
//        initialValue = null,
//    )


//    val questions = savedStateHandle.getStateFlow<String?>(
//        key = Constants.CATEGORY_ARG,
//        initialValue = null
//    ).flatMapLatest {category->
//        if (category == null){
//            flowOf(emptyList())
//        } else{
//
//            when(examsRepository.getExamQuestionsByCategory(
//                category = category,
//                examNr = ""
//            )){
//                is Result.Success -> {
//
//                }else ->{
//
//                }
//            }
//        }
//    }.stateIn(
//        scope = viewModelScope,
//        started = SharingStarted.WhileSubscribed(5_000),
//        initialValue = null,
//    )


    fun getQuestion(
        category: String,
        examNr: String
    ) {
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
}

//fun questionsUiState(
//    savedStateHandle: SavedStateHandle,
//    examsRepository: ExamsRepository
//): Flow<QuestionsUiState> {
//
//    val category = savedStateHandle.get<String?>(
//        key = Constants.CATEGORY_ARG,
//    )
//
//    val exam_nr = savedStateHandle.get<String?>(
//        key = Constants.EXAM_NUMBER_ARG,
//    )
//
//    val questions = examsRepository.getExamQuestionsByCategory()
//}


sealed interface QuestionsUiState {
    data class Success(val news: List<Question>) : QuestionsUiState
    data object Error : QuestionsUiState
    data object Loading : QuestionsUiState
}

