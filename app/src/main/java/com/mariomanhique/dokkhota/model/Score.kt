package com.mariomanhique.dokkhota.model

data class Score(
    val userID: String ="",
    val category: String = "",
    val examNr: String = "",
    val percentage: Float = 0.0F,
//    val rightAnswers: Int,
//    val wrongAnswers: Int,
//    val totalQuestions: Int
)