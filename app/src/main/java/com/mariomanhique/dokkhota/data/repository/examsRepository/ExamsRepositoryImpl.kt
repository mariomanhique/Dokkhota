package com.mariomanhique.dokkhota.data.repository.examsRepository

import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.snapshots
import com.google.firebase.firestore.toObjects
import com.mariomanhique.dokkhota.model.Question
import com.mariomanhique.dokkhota.model.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await
import javax.inject.Inject


class ExamsRepositoryImpl @Inject constructor(): ExamsRepository {

    private var category: List<String> = emptyList()
    private var examsList: List<String> = emptyList()
    private val categoriesCollection = FirebaseFirestore.getInstance().collection("categories")
    override suspend fun getExams():Flow<Questions> {
        return try {
            val result = categoriesCollection.snapshots()
                .map {
                   Result.Success(it.toObjects<Question>())
                }
            result
        } catch (e: FirebaseFirestoreException){
            flowOf(Result.Error(exception = e))
        }
    }

    override suspend fun getExamsByCategory(
            category: String,
            examNr: String,
    ): Flow<Questions> {

        return try {

          val result = categoriesCollection
              .document(category)
              .collection("exams")
              .document(examNr)
              .collection("questions")
              .snapshots()
              .map {snapshot->
                  Result.Success(snapshot.toObjects<Question>())
              }
            result
        } catch (e: FirebaseFirestoreException){
            flowOf(Result.Error(exception = e))
        }
    }



    override suspend fun getCategories(): Flow<List<String>>{
        val document = categoriesCollection.get().await()

        category = document.map {
            it.id
        }
        return flow { emit(category) }
    }

    override suspend fun getExamsList(
        category: String
    ): Flow<List<String>> {
        val document = categoriesCollection
            .document(category)
            .collection("exams")
            .get().await()

        examsList = document.map {
            it.id
        }
        return flow { emit(examsList) }
    }

}