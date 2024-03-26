package com.mariomanhique.dokkhota.presentation.screens.playScreen.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.mariomanhique.dokkhota.presentation.screens.playScreen.PlayScreen
import com.mariomanhique.dokkhota.util.Constants
import com.mariomanhique.dokkhota.util.Constants.EXAM_NUMBER_ARG

const val playRoute = "playRoute"


fun NavController.navigateToPlay(examNr: String, category: String){
    navigate("$playRoute/$examNr-$category")
}

fun NavGraphBuilder.playRoute(
    onBackToHomeClicked: () -> Unit,
    popBackStack: () -> Unit,

){
    composable(
        "$playRoute/{$EXAM_NUMBER_ARG}",
        arguments = listOf(navArgument(name = Constants.EXAM_NUMBER_ARG){
            type = NavType.StringType
            nullable = true
        })
    ){
        PlayScreen(
            paddingValues = PaddingValues(),
            onBackToHomeClicked = onBackToHomeClicked,
            popBackStack = popBackStack
        )
    }
}