package com.mariomanhique.africanpages.presentation.screens.authentication.signUp

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.mariomanhique.dokkhota.presentation.screens.authentication.AuthViewModel
import com.mariomanhique.dokkhota.presentation.screens.authentication.signUp.SignUpContent

@Composable
fun SignUpScreen(
    navigateToSignIn: () -> Unit,
    navigateToHome: () -> Unit,
    authViewModel: AuthViewModel = hiltViewModel()
){

    val loadingState by authViewModel.loadingState

    SignUpContent(
        onSignUpClicked = {email,username,password->
            authViewModel.signUp(
                email = email,
                username = username,
                password = password,
                onSuccess = {
                    navigateToHome()
                },
                onFailure = {

                }
            )
        },
        navigateToSignIn = navigateToSignIn
    )

    if (loadingState){
//        Box(
//            modifier = Modifier.fillMaxSize(),
//            contentAlignment = Alignment.Center
//        ) {
//            CircularProgressIndicator()
//        }
    }
}