package com.mariomanhique.dokkhota.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import com.mariomanhique.africanpages.presentation.screens.authentication.signUp.navigation.navigateToSignUp
import com.mariomanhique.africanpages.presentation.screens.authentication.signUp.navigation.signUpRoute
import com.mariomanhique.dokkhota.presentation.screens.analytics.navigation.analyticsRoute
import com.mariomanhique.dokkhota.presentation.screens.authentication.signIn.navigation.navigateToSignIn
import com.mariomanhique.dokkhota.presentation.screens.authentication.signIn.navigation.signInRoute
import com.mariomanhique.dokkhota.presentation.screens.exam.navigation.examGraph
import com.mariomanhique.dokkhota.presentation.screens.exam.navigation.examRoute
import com.mariomanhique.dokkhota.presentation.screens.exam.navigation.examsListRoute
import com.mariomanhique.dokkhota.presentation.screens.exam.navigation.navigateToExamsList
import com.mariomanhique.dokkhota.presentation.screens.home.navigation.homeRoute
import com.mariomanhique.dokkhota.presentation.screens.home.navigation.navigateToHome
import com.mariomanhique.dokkhota.presentation.screens.playScreen.navigation.navigateToPlay
import com.mariomanhique.dokkhota.presentation.screens.playScreen.navigation.playRoute
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
        homeRoute(
            paddingValues = paddingValues
        )


        examGraph(
            paddingValues = paddingValues,
            onCategoryClicked = {
                navController.navigateToExamsList(it)
            },
            nestedGraph = {
                examsListRoute(
                    paddingValues = paddingValues,
                  onExamClicked = {examNr, category->
                      navController.navigateToPlay(examNr, category)
                  }
                )
                playRoute(
                    onBackToHomeClicked = {
                        appState.navigateToTopLevelDestination(TopLevelDestination.HOME)
                    },
                    navigateToSignIn = navController::navigateToSignIn,
                    popBackStack = navController::popBackStack
                )

            }
        )

        analyticsRoute()

        signInRoute(
            navigateToSignUp = navController::navigateToSignUp,
            navigateToHome = {}
        )

        signUpRoute(
            navigateToSignIn = navController::navigateToSignIn,
            navigateToHome = {}
        )


        profileRoute(
            profileViewModel = profileViewModel,
            paddingValues = paddingValues
        )
    }
}