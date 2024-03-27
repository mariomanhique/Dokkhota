package com.mariomanhique.dokkhota.ui.theme

import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavDestination
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import androidx.tracing.trace
import com.mariomanhique.dokkhota.navigation.TopLevelDestination
import com.mariomanhique.dokkhota.presentation.screens.analytics.navigation.analyticsRoute
import com.mariomanhique.dokkhota.presentation.screens.analytics.navigation.navigateToAnalytics
import com.mariomanhique.dokkhota.presentation.screens.exam.navigation.examRoute
import com.mariomanhique.dokkhota.presentation.screens.exam.navigation.examsListRoute
import com.mariomanhique.dokkhota.presentation.screens.exam.navigation.navigateToExamsGraph
import com.mariomanhique.dokkhota.presentation.screens.home.navigation.homeRoute
import com.mariomanhique.dokkhota.presentation.screens.home.navigation.navigateToHome
import com.mariomanhique.dokkhota.presentation.screens.profile.navigation.navigateToProfile
import com.mariomanhique.dokkhota.presentation.screens.profile.navigation.profileRoute
import com.mariomanhique.dokkhota.util.Constants.CATEGORY_ARG
import kotlinx.coroutines.CoroutineScope


@Composable
fun rememberAppState(
    windowSizeClass: WindowSizeClass,
    navController: NavHostController = rememberNavController(),
    coroutineScope: CoroutineScope = rememberCoroutineScope(),

    ): DokkhotaAppState{

    return DokkhotaAppState(
        navController,
        windowSizeClass,
        coroutineScope,
    )

}

class DokkhotaAppState(
    val navController: NavHostController,
    private val windowSizeClass: WindowSizeClass,
    private val coroutineScope: CoroutineScope,
){

    val currentDestination: NavDestination?
        @Composable get() = navController
            .currentBackStackEntryAsState().value?.destination

    val currentTopLevelDestination: TopLevelDestination?
        @Composable get() = when (currentDestination?.route) {
//            homeRoute -> TopLevelDestination.HOME
            examRoute, "$examsListRoute/{$CATEGORY_ARG}" -> TopLevelDestination.EXAM
            analyticsRoute -> TopLevelDestination.ANALYTICS
            profileRoute -> TopLevelDestination.PROFILE
            else -> null
        }


    val shouldShowBottomBar: Boolean
        get() = windowSizeClass.widthSizeClass == WindowWidthSizeClass.Compact

    val shouldShowNavRail: Boolean
        get() = !shouldShowBottomBar

//    val isOffline = connectivity.observe()
//        .map{
//            it
//        }.stateIn(
//            scope = coroutineScope,
//            started = SharingStarted.WhileSubscribed(5_000),
//            initialValue = ConnectivityObserver.Status.Lost
//        )

    val topLevelDestinations: List<TopLevelDestination> = TopLevelDestination.values().asList()



    fun navigateToTopLevelDestination(topLevelDestination: TopLevelDestination) {
        trace("Navigation: ${topLevelDestination.name}"){
            val topLevelNavOptions = navOptions {
                // Pop up to the start destination of the graph to
                // avoid building up a large stack of destinations
                // on the back stack as users select items
                popUpTo(navController.graph.findStartDestination().id){
                    saveState = true
                }

                launchSingleTop = true

                restoreState = true
            }

            when(topLevelDestination){
//                TopLevelDestination.HOME -> navController.navigateToHome(topLevelNavOptions)
                TopLevelDestination.PROFILE -> navController.navigateToProfile(topLevelNavOptions)
                TopLevelDestination.ANALYTICS -> navController.navigateToAnalytics(topLevelNavOptions)
                TopLevelDestination.EXAM -> navController.navigateToExamsGraph(topLevelNavOptions)
            }
        }

    }



}