package com.mariomanhique.dokkhota.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import com.mariomanhique.dokkhota.presentation.screens.analytics.navigation.examRoute
import com.mariomanhique.dokkhota.presentation.screens.exam.navigation.analyticsRoute
import com.mariomanhique.dokkhota.presentation.screens.home.navigation.homeRoute
import com.mariomanhique.dokkhota.presentation.screens.profile.ProfileViewModel
import com.mariomanhique.dokkhota.presentation.screens.profile.navigation.profileRoute
import com.mariomanhique.dokkhota.ui.theme.DokkhotaAppState

@Composable
fun NavHost(
    appState: DokkhotaAppState,
    startDestination: String = homeRoute,
    onSplashDismissed: () -> Unit,
    paddingValues: PaddingValues
){

    val navController = appState.navController
    val destination = appState.currentDestination?.route
    val profileViewModel: ProfileViewModel = hiltViewModel()

    NavHost(navController = navController, startDestination = startDestination){


//        signInRoute(
//            navigateToHome = {
//                appState.navigateToTopLevelDestination(HOME)
//            },
//            navigateToSignUp = {
//                navController.navigateToSignUp()
//            }
//        )
//        signUpRoute(
//            navigateToHome = {
//                appState.navigateToTopLevelDestination(HOME)
//            },
//            navigateToSignIn = {
//            navController.navigateToSignIn()
//        })
//
        homeRoute()

        examRoute()

        analyticsRoute()

        profileRoute(
            profileViewModel = profileViewModel
        )
    }
}