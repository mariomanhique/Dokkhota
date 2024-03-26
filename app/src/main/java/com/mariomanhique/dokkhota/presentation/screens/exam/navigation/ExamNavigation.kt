package com.mariomanhique.dokkhota.presentation.screens.exam.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.mariomanhique.dokkhota.presentation.screens.exam.ExamScreen
import com.mariomanhique.dokkhota.presentation.screens.exam.ExamsListScreen
import com.mariomanhique.dokkhota.util.Constants.CATEGORY_ARG

const val examRoute = "examRoute"

const val EXAMS_GRAPH_ROUTE_PATTERN = "examsGraph"

const val examsListRoute = "examsListRoute"

fun NavController.navigateToExamsGraph(navOptions: NavOptions? = null){
    navigate(EXAMS_GRAPH_ROUTE_PATTERN, navOptions)
}

fun NavController.navigateToExamsList(category: String){
    navigate("$examsListRoute/$category")
}



fun NavGraphBuilder.examGraph(
    onCategoryClicked: (String) -> Unit,
    nestedGraph: NavGraphBuilder.() -> Unit,
    paddingValues: PaddingValues
){

    navigation(
        route = EXAMS_GRAPH_ROUTE_PATTERN,
        startDestination = examRoute){
        composable(route = examRoute){
            ExamScreen(
                onCategoryClicked = onCategoryClicked,
                paddingValues = paddingValues
            )
        }
        nestedGraph()
    }
}

fun NavGraphBuilder.examsListRoute(
    paddingValues: PaddingValues,
    onExamClicked: (String, String) -> Unit,
){
    composable(route = "$examsListRoute/{$CATEGORY_ARG}",
        arguments = listOf(navArgument(name = CATEGORY_ARG){
            type = NavType.StringType
            nullable = true
        })
        ){
        ExamsListScreen(
           paddingValues = paddingValues ,
            onExamClicked = onExamClicked
        )
    }
}