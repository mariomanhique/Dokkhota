package com.mariomanhique.dokkhota.data.repository.examsRepository

import com.mariomanhique.dokkhota.model.Question
import com.mariomanhique.dokkhota.model.Result
import kotlinx.coroutines.flow.Flow

typealias Questions = Result<List<Question>>

interface ExamsRepository {
    suspend fun getExams():Flow<Questions>
    suspend fun getAll():List<Map<String, Any>>
    suspend fun getExamQuestionsByCategory(
        category: String,
        examNr: String,
    ):Flow<Questions>

    suspend fun getCategories(): Flow<List<String>>
    suspend fun getExamsList(
        category: String
    ): Flow<List<String>>
}