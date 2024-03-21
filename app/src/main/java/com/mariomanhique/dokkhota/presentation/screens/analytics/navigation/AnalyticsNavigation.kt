package com.mariomanhique.dokkhota.presentation.screens.analytics.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.mariomanhique.dokkhota.presentation.screens.analytics.AnalyticsScreen

const val analyticsRoute = "analyticsRoute"

fun NavController.navigateToAnalytics(navOptions: NavOptions? = null){
    navigate(analyticsRoute, navOptions)
}


fun NavGraphBuilder.analyticsRoute(){
    composable(route = analyticsRoute){
        AnalyticsScreen()
    }
}