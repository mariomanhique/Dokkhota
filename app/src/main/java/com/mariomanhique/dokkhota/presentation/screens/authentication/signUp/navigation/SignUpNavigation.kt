package com.mariomanhique.africanpages.presentation.screens.authentication.signUp.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.mariomanhique.africanpages.presentation.screens.authentication.signUp.SignUpScreen

const val signUpRoute = "signUp_route"

fun NavController.navigateToSignUp(){
    navigate(signUpRoute){
        popUpTo(signUpRoute){
            inclusive = true
        }
    }
}

fun NavGraphBuilder.signUpRoute(
    navigateToSignIn: () -> Unit,
    navigateToHome: () -> Unit,
){
    composable(signUpRoute){
        SignUpScreen(
            navigateToHome = navigateToHome,
            navigateToSignIn = navigateToSignIn
        )
    }
}