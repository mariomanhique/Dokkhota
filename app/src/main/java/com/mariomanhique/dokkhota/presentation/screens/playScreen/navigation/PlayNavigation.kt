package com.mariomanhique.dokkhota.presentation.screens.playScreen.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.mariomanhique.dokkhota.presentation.screens.playScreen.PlayScreen

const val playRoute = "playRoute"


fun NavController.navigateToPlay(){
    navigate(playRoute)
}

fun NavGraphBuilder.playRoute(){
    composable(playRoute){
        PlayScreen(
            paddingValues = PaddingValues()
        )
    }
}