package com.mariomanhique.dokkhota.model

data class Exam(
    val examId: String = "",
    val category: String = "",
    val question: List<Question> = emptyList(),
)