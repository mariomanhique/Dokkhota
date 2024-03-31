package com.mariomanhique.dokkhota.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import com.google.firebase.auth.FirebaseAuth
import com.mariomanhique.africanpages.presentation.screens.authentication.signUp.navigation.navigateToSignUp
import com.mariomanhique.africanpages.presentation.screens.authentication.signUp.navigation.signUpRoute
import com.mariomanhique.dokkhota.presentation.screens.analytics.navigation.analyticsRoute
import com.mariomanhique.dokkhota.presentation.screens.analytics.navigation.navigateToAnalytics
import com.mariomanhique.dokkhota.presentation.screens.authentication.signIn.navigation.navigateToSignIn
import com.mariomanhique.dokkhota.presentation.screens.authentication.signIn.navigation.signInRoute
import com.mariomanhique.dokkhota.presentation.screens.exam.navigation.EXAMS_GRAPH_ROUTE_PATTERN
import com.mariomanhique.dokkhota.presentation.screens.exam.navigation.examGraph
import com.mariomanhique.dokkhota.presentation.screens.exam.navigation.examsListRoute
import com.mariomanhique.dokkhota.presentation.screens.exam.navigation.navigateToExamsList
import com.mariomanhique.dokkhota.presentation.screens.playScreen.navigation.navigateToPlay
import com.mariomanhique.dokkhota.presentation.screens.playScreen.navigation.playRoute
import com.mariomanhique.dokkhota.presentation.screens.profile.ProfileViewModel
import com.mariomanhique.dokkhota.presentation.screens.profile.navigation.profileRoute
import com.mariomanhique.dokkhota.ui.theme.DokkhotaAppState

@Composable
fun NavHost(
    appState: DokkhotaAppState,
    startDestination: String = EXAMS_GRAPH_ROUTE_PATTERN,
    onSplashDismissed: () -> Unit,
    paddingValues: PaddingValues
){

    val navController = appState.navController
    val profileViewModel: ProfileViewModel = hiltViewModel()

    NavHost(navController = navController, startDestination = startDestination){
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
                        appState.navigateToTopLevelDestination(TopLevelDestination.EXAM)
                    },
                    navigateToSignIn = {
                       if (FirebaseAuth.getInstance().currentUser != null){
                           appState.navigateToTopLevelDestination(TopLevelDestination.ANALYTICS)
                       } else{
                           navController.navigateToSignIn()
                       }
                    },
                    popBackStack = navController::popBackStack
                )

            }
        )

        analyticsRoute(
            paddingValues = paddingValues,
            navigateToSignIn = navController::navigateToSignIn
        )

        signInRoute(
            navigateToSignUp = navController::navigateToSignUp,
            navigateToHome = navController::navigateToAnalytics
        )

        signUpRoute(
            navigateToSignIn = navController::navigateToSignIn,
            navigateToHome = navController::navigateToAnalytics
        )


        profileRoute(
            navigateToSignIn = navController::navigateToSignIn,
            paddingValues = paddingValues
        )
    }
}