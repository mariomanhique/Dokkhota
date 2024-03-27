package com.mariomanhique.dokkhota.data.repository.analyticsRepository

import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.snapshots
import com.google.firebase.firestore.toObjects
import com.mariomanhique.dokkhota.model.Question
import com.mariomanhique.dokkhota.model.Result
import com.mariomanhique.dokkhota.model.Score
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class AnalyticsRepositoryImpl @Inject constructor():AnalyticsRepository {

   private  val ref = FirebaseFirestore.getInstance().collection("score")
   override suspend fun saveExamScore(
     score: Score
   ): Result<Boolean> {
    return try {
          val result = ref.document()
             .set(score).isSuccessful

         Result.Success(result)
      } catch (e: FirebaseFirestoreException){
         Result.Error(exception = e)
      }
   }

   override suspend fun updateExamScore(
      scoreId: String,
      category: String,
      examNr: String,
      percentage: Float
   ): Result<Boolean> {
      TODO("Not yet implemented")
   }

   override fun getAllExamsScores(userId: String): Flow<Result<List<Score>>> {
      return try {
         val result = ref.snapshots()
              .map {snapshot->
                  Result.Success(snapshot.toObjects<Score>())
              }
          result

      } catch (e: FirebaseFirestoreException){
          flowOf(Result.Error(exception = e))
      }
   }


}