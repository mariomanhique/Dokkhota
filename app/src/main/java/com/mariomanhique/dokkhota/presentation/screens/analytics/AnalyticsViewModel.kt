package com.mariomanhique.dokkhota.presentation.screens.analytics

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.mariomanhique.dokkhota.data.repository.analyticsRepository.AnalyticsRepository
import com.mariomanhique.dokkhota.model.Result
import com.mariomanhique.dokkhota.model.Score
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AnalyticsViewModel @Inject constructor(
    private val analyticsRepository: AnalyticsRepository
): ViewModel() {

    val user = FirebaseAuth.getInstance().currentUser

    val scores: StateFlow<List<Score>> =
            analyticsRepository
                .getAllExamsScores()
                .stateIn(
                    scope = viewModelScope,
                    started = SharingStarted.WhileSubscribed(5_000),
                    initialValue = emptyList()
                )
}


