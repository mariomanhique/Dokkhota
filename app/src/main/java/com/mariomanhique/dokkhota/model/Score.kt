package com.mariomanhique.dokkhota.model

import java.util.UUID

data class Score(
    val scoreId: String = UUID.randomUUID().toString(),
    val category: String = "",
    val examNr: String = "",
    val userID: String ="",
    val attempts: Int = 0,
    val percentage: Long = 0L,
//    val rightAnswers: Int,
//    val wrongAnswers: Int,
//    val totalQuestions: Int
)