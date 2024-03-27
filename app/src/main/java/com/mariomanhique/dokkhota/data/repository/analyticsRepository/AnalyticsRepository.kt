package com.mariomanhique.dokkhota.data.repository.analyticsRepository

import com.mariomanhique.dokkhota.model.Result
import com.mariomanhique.dokkhota.model.Score
import kotlinx.coroutines.flow.Flow


interface AnalyticsRepository {

    suspend fun saveExamScore(
        score: Score, // This is going to be the user ID,
    ):Result<Boolean>

    suspend fun updateExamScore(
        scoreId: String, // We will use this ti find the score and then update it.
        category: String,
        examNr: String,
        percentage: Float
    ):Result<Boolean>

    fun getAllExamsScores(
        userId: String
    ): Flow<Result<List<Score>>>
}