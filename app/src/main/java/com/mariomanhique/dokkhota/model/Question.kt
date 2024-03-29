package com.mariomanhique.dokkhota.model

data class Question(
    val choices: List<String> = emptyList(),
//    val isQuestionAnswered: Boolean = false,
    val answer: String ="",
    val question: String=""
)




