package com.mariomanhique.dokkhota.presentation.screens.exam.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.mariomanhique.dokkhota.presentation.screens.exam.ExamScreen

const val examRoute = "examRoute"

fun NavController.navigateToExam(navOptions: NavOptions? = null){
    navigate(examRoute, navOptions)
}


fun NavGraphBuilder.examRoute(){
    composable(route = examRoute){
        ExamScreen()
    }
}