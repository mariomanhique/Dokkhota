package com.mariomanhique.africanpages.presentation.screens.authentication.signIn

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mariomanhique.dokkhota.presentation.screens.authentication.AuthViewModel
import com.mariomanhique.dokkhota.presentation.screens.authentication.signIn.SignInContent

@Composable
fun SignInScreen(
    navigateToHome: () -> Unit,
    navigateToSignUp: () -> Unit,
//    onShowSnackbar: suspend (String, String?) -> Boolean,
    authViewModel: AuthViewModel = hiltViewModel()
){
    val loadingState by authViewModel.loadingState
    val authUiState by authViewModel.authUiState.collectAsStateWithLifecycle()



    SignInContent(
        onSignInClicked = { email, password->
            authViewModel.signIn(
                email = email,
                password = password,
                onSuccess = {
                    navigateToHome()
                },
                onFailure = {

                }
            )

        },
        navigateToSignUp = navigateToSignUp
    )

//    if (loadingState){
//        Box(
//            modifier = Modifier.fillMaxSize(),
//            contentAlignment = Alignment.Center
//        ) {
//            CircularProgressIndicator()
//        }
//    }

}