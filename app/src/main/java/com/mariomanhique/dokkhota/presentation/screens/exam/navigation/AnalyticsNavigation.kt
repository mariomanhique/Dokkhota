package com.mariomanhique.dokkhota.presentation.screens.exam.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.mariomanhique.dokkhota.presentation.screens.exam.AnalyticsScreen

const val analyticsRoute = "analyticsRoute"

fun NavController.navigateToAnalytics(){
    navigate(analyticsRoute)
}


fun NavGraphBuilder.analyticsRoute(){
    composable(route = analyticsRoute){
        AnalyticsScreen()
    }
}