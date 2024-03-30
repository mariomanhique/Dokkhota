package com.mariomanhique.dokkhota.presentation.screens.exam

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mariomanhique.dokkhota.data.repository.examsRepository.ExamsRepository
import com.mariomanhique.dokkhota.util.Constants.CATEGORY_ARG
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class ExamsViewModel @Inject constructor(
    private val examsRepository: ExamsRepository,
    private val savedStateHandle: SavedStateHandle
): ViewModel() {

    private var _categories = MutableStateFlow<List<String>>(emptyList())
    val categories = _categories.asStateFlow()


    init {
        getCategories()

    }

//    val exams = savedStateHandle.getStateFlow<String?>(
//        key = CATEGORY_ARG,
//        initialValue = null
//    ).flatMapLatest {category->
//        if (category == null){
//            flowOf(emptyList())
//        } else{
//            examsRepository.getExamsList(category)
//        }
//    }.stateIn(
//        scope = viewModelScope,
//        started = SharingStarted.WhileSubscribed(5_000),
//        initialValue = null,
//    )

    private fun getCategories(){
        viewModelScope.launch {
            examsRepository
                .getCategories().distinctUntilChanged()
                .collectLatest {
                    _categories.value = it
                }

        }
    }
}