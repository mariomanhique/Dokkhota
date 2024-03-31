package com.mariomanhique.dokkhota.data.repository.examsRepository

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.snapshots
import com.google.firebase.firestore.toObject
import com.google.firebase.firestore.toObjects
import com.mariomanhique.dokkhota.model.Exam
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

    override suspend fun getAll(): List<Map<String, Any>> {
        val db = FirebaseFirestore.getInstance()
        val categoriesCollection = db.collection("categories")

        val allExams = mutableListOf<Map<String, Any>>()

        val categoryDocuments = categoriesCollection.get().await()
        for (categoryDocument in categoryDocuments) {
            val examsCollection = categoryDocument.reference.collection("exams")
            val examDocuments = examsCollection.get().await()
            for (examDocument in examDocuments) {
                val examData = examDocument.data
                allExams.add(examData)
            }
        }

        return allExams

    }

    override suspend fun getExamQuestionsByCategory(
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

    override fun getExamDetails(examNr: String, category: String): Flow<Result<Exam>> {
        return try {
                    FirebaseFirestore.getInstance().collection("categories")
                    .document(category)
                    .collection("exams")
                    .document(examNr)
                    .snapshots()
                    .map {snapshot->
                        val exam = snapshot.toObject<Exam>()
                        if (exam != null) {
                            Result.Success(exam)
                        } else {
                            // Emit a default User object if snapshot is null
                            Result.Error(FirebaseFirestoreException("Not Found",FirebaseFirestoreException.Code.NOT_FOUND))
                        }
                    }
            }catch(e: FirebaseFirestoreException){
                flowOf(Result.Error(e))
            }
    }

}