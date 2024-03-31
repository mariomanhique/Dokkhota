package com.mariomanhique.dokkhota.presentation.screens.exam

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mariomanhique.dokkhota.data.repository.examsRepository.ExamsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExamsViewModel @Inject constructor(
    private val examsRepository: ExamsRepository,
): ViewModel() {

    private var _categories = MutableStateFlow<List<String>>(emptyList())
    val categories = _categories.asStateFlow()


    init {
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
}