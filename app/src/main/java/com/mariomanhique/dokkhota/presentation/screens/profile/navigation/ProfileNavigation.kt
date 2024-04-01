package com.mariomanhique.dokkhota.presentation.screens.profile.navigation

import android.widget.Toast
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.mariomanhique.dokkhota.presentation.screens.profile.ProfileScreen
import com.mariomanhique.dokkhota.presentation.screens.profile.ProfileViewModel


const val profileRoute = "profile_route"
fun NavController.navigateToProfile(navOptions: NavOptions?=null){
    this.navigate(profileRoute, navOptions)
}
fun NavGraphBuilder.profileRoute(
    navigateToSignIn: () -> Unit,
    paddingValues: PaddingValues,
    profileViewModel: ProfileViewModel
){

    composable(route = profileRoute){


        ProfileScreen(
            navigateToSignIn = navigateToSignIn,
            paddingValues = paddingValues,
            profileViewModel = profileViewModel
        )
    }

}