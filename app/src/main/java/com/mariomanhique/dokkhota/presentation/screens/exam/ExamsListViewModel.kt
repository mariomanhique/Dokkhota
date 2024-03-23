package com.mariomanhique.dokkhota.presentation.screens.exam

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mariomanhique.dokkhota.data.repository.examsRepository.ExamsRepository
import com.mariomanhique.dokkhota.util.Constants.CATEGORY_ARG
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class ExamsListViewModel @Inject constructor(
    private val examsRepository: ExamsRepository,
    private val savedStateHandle: SavedStateHandle
): ViewModel() {

    init {
        viewModelScope.launch {
           savedStateHandle.getStateFlow<String?>(
                key = CATEGORY_ARG,
                initialValue = null
            ).collect{
                Log.d("ID", ": $it ")
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