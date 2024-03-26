package com.mariomanhique.dokkhota.model

data class Score(
    val category: String,
    val nr: String,
    val percentage: String,
    val rightAnswers: Int,
    val wrongAnswers: Int,
    val totalQuestions: Int
)