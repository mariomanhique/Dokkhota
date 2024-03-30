package com.mariomanhique.dokkhota.presentation.screens.playScreen

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.mariomanhique.dokkhota.data.repository.analyticsRepository.AnalyticsRepository
import com.mariomanhique.dokkhota.data.repository.examsRepository.ExamsRepository
import com.mariomanhique.dokkhota.data.repository.examsRepository.Questions
import com.mariomanhique.dokkhota.model.Question
import com.mariomanhique.dokkhota.model.Rating
import com.mariomanhique.dokkhota.model.Result
import com.mariomanhique.dokkhota.model.Score
import com.mariomanhique.dokkhota.util.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class PlayViewModel @Inject constructor(
    private val examsRepository: ExamsRepository,
    private val analyticsRepository: AnalyticsRepository,
    private val savedStateHandle: SavedStateHandle
): ViewModel() {


    private var scoreUpdateInProgress = mutableStateOf(false)
    private var _exams = MutableStateFlow<Questions>(Result.Loading)
    val exams = _exams.asStateFlow()

    val _examNr:MutableState<String> = mutableStateOf("")
    val _category:MutableState<String> = mutableStateOf("")
    val _attempts:MutableState<Int> = mutableIntStateOf(0)

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

    fun saveRating(
         stars: String = "",
         comment: String = "",
         onSuccess: () -> Unit,
         onFailure: () -> Unit
    ){
        viewModelScope.launch {
            val user = FirebaseAuth.getInstance().currentUser
            if (user != null){
                val result = analyticsRepository
                    .SaveRating(
                        rating = Rating(
                            userId = user.uid,
                            userEmail = user.email.toString(),
                            stars = stars,
                            comment = comment
                        )
                    )
                when(result){
                    is Result.Success -> onSuccess()
                    else -> onFailure()
                }
            }
        }

    }

    fun saveOrUpdateScore(
        examNr: String,
        category: String,
        percentage: Long,
        onSuccess: () -> Unit,
        onFailure: () -> Unit
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            analyticsRepository.getSingleExamScore(
                examNr = examNr
            ).let { result ->
                when (result) {
                    is Result.Success -> {
                        val scores = result.data
                        if (scores.attempts == 0) {
                            _attempts.value = 1
                            saveExamScore(
                                category = category,
                                examNr = examNr,
                                percentage = percentage,
                                onSuccess = onSuccess,
                                onFailure = onFailure
                            )
                        } else {
                            Log.d("Score", "saveOrUpdateScore:$scores ")
                            // Check if the update operation has already been initiated
                            if (!scoreUpdateInProgress.value) {
                                scoreUpdateInProgress.value = true
                                update(
                                    scoreId = scores.scoreId,
                                    attempts = scores.attempts + 1,
                                    percentage = percentage,
                                    onSuccess = {
                                        _attempts.value = scores.attempts+1
                                        // Reset the flag on success
                                        scoreUpdateInProgress.value = false
                                        onSuccess()
                                    },
                                    onFailure = {
                                        // Reset the flag on failure
                                        scoreUpdateInProgress.value = false
                                        onFailure()
                                    }
                                )
                            }
                        }
                    }
                    else -> {
                        Log.e("SaveOrUpdateScore", "Error retrieving exam score")
                        onFailure()
                    }
                }
            }
        }
    }

    private suspend fun update(
        scoreId: String,
        attempts: Int,
        percentage: Long,
        onSuccess: () -> Unit,
        onFailure: () -> Unit
    ){
        val scoreToUpdate = analyticsRepository.updateExamScore(
            scoreId = scoreId,
            attempts = attempts,
            percentage = percentage
        )
        when(scoreToUpdate){
            is Result.Success -> onSuccess()
            else -> onFailure()
        }
    }

   private suspend fun saveExamScore(
        category: String,
        examNr: String,
        percentage: Long,
        onSuccess: () -> Unit,
        onFailure: () -> Unit
    ){
        val user = FirebaseAuth.getInstance().currentUser
        if (user != null){
            val result = analyticsRepository.saveExamScore(
                score = Score(
                    userID = user.uid,
                    attempts = 1,
                    category = category,
                    examNr = examNr,
                    percentage = percentage
                )
            )
            when(result){
                is Result.Success -> onSuccess()
                else -> onFailure()
            }
        }
    }

    private fun getQuestion(
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

sealed interface QuestionsUiState {
    data class Success(val news: List<Question>) : QuestionsUiState
    data object Error : QuestionsUiState
    data object Loading : QuestionsUiState
}

