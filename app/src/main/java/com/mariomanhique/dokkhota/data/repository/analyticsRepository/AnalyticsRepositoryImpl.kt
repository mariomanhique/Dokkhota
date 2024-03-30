package com.mariomanhique.dokkhota.data.repository.analyticsRepository

import android.security.keystore.UserNotAuthenticatedException
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.FirestoreRegistrar
import com.google.firebase.firestore.snapshots
import com.google.firebase.firestore.toObjects
import com.mariomanhique.dokkhota.data.repository.authRepository.utils.await
import com.mariomanhique.dokkhota.model.Rating
import com.mariomanhique.dokkhota.model.Result
import com.mariomanhique.dokkhota.model.Score
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class AnalyticsRepositoryImpl @Inject constructor():AnalyticsRepository {

    private lateinit var updatedScore: Result<String>
    private lateinit var singleScore: Result<Score>

   private val ref = FirebaseFirestore.getInstance().collection("score")
   private val ratingRef = FirebaseFirestore.getInstance().collection("rating")
   override suspend fun saveExamScore(
     score: Score
   ): Result<Boolean> {
    return try {
          val result = ref.document(score.scoreId)
             .set(score).isSuccessful

         Result.Success(result)
      } catch (e: FirebaseFirestoreException){
         Result.Error(exception = e)
      }
   }

    override suspend fun updateExamScore(
        scoreId: String,
        attempts: Int,
        percentage: Long
    ): Result<String> {
        val user = FirebaseAuth.getInstance().currentUser
        return if (user != null) {
            try {
                val result = ref.document(scoreId).update(
                    mapOf(
                        "percentage" to percentage,
                        "attempts" to attempts,
                    )
                ).await() // Wait for the result of the update operation

                Result.Success("Success Updating Score")
            } catch (e: FirebaseFirestoreException) {
                Result.Error(exception = e)
            }
        } else {
            Result.Error(exception = UserNotAuthenticatedException())
        }
    }

   override fun getAllExamsScores(): Flow<List<Score>> {
       val user = FirebaseAuth.getInstance().currentUser
      return try {
          if (user != null){
              val result = ref
                  .whereEqualTo("userID",user.uid)
                  .snapshots()
                  .map {snapshot->
                      snapshot.toObjects<Score>()
                  }
              result
          }else{
              flowOf(emptyList())
          }

      } catch (e: FirebaseFirestoreException){
          flowOf(emptyList())
      }
   }

    override suspend fun getSingleExamScore(examNr: String): Result<Score> {
        val user = FirebaseAuth.getInstance().currentUser
        return if (user != null) {
            try {
                val querySnapshot = ref.whereEqualTo("userID", user.uid)
                    .whereEqualTo("examNr", examNr)
                    .get().await() // Wait for the result of the asynchronous operation

                if (querySnapshot.isEmpty) {
                    Result.Success(Score())
                } else {
                    Result.Success(querySnapshot.toObjects<Score>().first())
                }
            } catch (e: FirebaseFirestoreException) {
                Result.Error(exception = e)
            }
        } else {
            Result.Error(exception = FirebaseAuthInvalidUserException("Error", "Invalid User"))
        }
    }

    override suspend fun SaveRating(rating: Rating,): Result<Boolean> {
        return try {
            val result = ratingRef.document(rating.userId)
                .set(rating).isSuccessful

            Result.Success(result)
        } catch (e: FirebaseFirestoreException){
            Result.Error(exception = e)
        }
    }
}