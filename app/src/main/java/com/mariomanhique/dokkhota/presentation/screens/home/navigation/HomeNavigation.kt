package com.mariomanhique.dokkhota.presentation.screens.home.navigation

import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.mariomanhique.dokkhota.presentation.screens.home.HomeScreen
import com.mariomanhique.dokkhota.presentation.screens.home.HomeViewModel


const val homeRoute = "home_route"
fun NavController.navigateToHome(navOptions: NavOptions? = null) {
    this.navigate(homeRoute, navOptions)
}

fun NavGraphBuilder.homeRoute(
){

    composable(route = homeRoute){
        val viewModel: HomeViewModel = hiltViewModel()


        HomeScreen(
            )


    }

}