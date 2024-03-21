package com.mariomanhique.dokkhota.presentation.screens.analytics.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.mariomanhique.dokkhota.presentation.screens.analytics.ExamScreen

const val examRoute = "examRoute"

fun NavController.navigateToExam(){
    navigate(examRoute)
}


fun NavGraphBuilder.examRoute(){
    composable(route = examRoute){
        ExamScreen()
    }
}