package com.mariomanhique.dokkhota.ui.theme

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.NavigationRailItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTagsAsResourceId
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import com.mariomanhique.dokkhota.navigation.NavHost
import com.mariomanhique.dokkhota.navigation.TopLevelDestination
import com.mariomanhique.dokkhota.presentation.components.HomeTopBar
import com.mariomanhique.dokkhota.presentation.screens.menu.MenuSheet

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
@Composable
fun DokkhotaApp(
    windowSizeClass: WindowSizeClass,
    appState: DokkhotaAppState = rememberAppState(
        windowSizeClass = windowSizeClass
    )
){

    val snackbarHostState = remember { SnackbarHostState() }
    val destination = appState.currentTopLevelDestination
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    var isSheetOpen by rememberSaveable {
        mutableStateOf(false)
    }


    if (isSheetOpen){
        MenuSheet(
            onSheetDismissed = {
                isSheetOpen = false
            },
            onSignedOut = {

            }
        )
    }

    Scaffold(
        modifier = Modifier
            .semantics {
                testTagsAsResourceId = true
            }
            .background(MaterialTheme.colorScheme.surface)
            .navigationBarsPadding()
            .imePadding()
            .statusBarsPadding(),
        containerColor = Color.Transparent,
        contentColor = MaterialTheme.colorScheme.onBackground,
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            if (
                destination != null
            ){
                HomeTopBar(
                    title = destination.titleTextId,
                    scrollBehavior = scrollBehavior,
                    onMenuClicked = {
                        isSheetOpen = true
                    }
                )
            }
        },
        bottomBar = {
            if (appState.shouldShowBottomBar) {
                if (destination != null) {
                    DokkhotaBottomBar(
                        destinations = appState.topLevelDestinations,
                        onNavigateToDestination = appState::navigateToTopLevelDestination,
                        currentDestination = appState.currentDestination,
                        modifier = Modifier.testTag("DokkhotaBottomBar"),
                    )
                }
            }
        }){

        NavHost(appState = appState, onSplashDismissed = { /*TODO*/ }, paddingValues = it)
    }



}


@Composable
fun DokkhotaBottomBar(
    destinations: List<TopLevelDestination>,
    onNavigateToDestination: (TopLevelDestination) -> Unit,
    currentDestination: NavDestination?,
    modifier: Modifier = Modifier,
){
    DokkhotaNavigationBar(
        modifier = modifier
    ) {

        destinations.forEach {destination->
            val selected = currentDestination.isTopLevelDestinationInHierarchy(destination)
            Log.d("Test Nav", "NiaBottomBar:$destination")
            DokkhotaNavigationBarItem(
                selected = selected,
                onClick = { onNavigateToDestination(destination) },
                icon = {
                    Icon(
                        imageVector = destination.unselectedIcon,
                        contentDescription = null,
                        modifier = Modifier.size(25.dp)
                    )
                },
                selectedIcon = {
                    Icon(
                        imageVector = destination.selectedIcon,
                        contentDescription = null,
                        modifier = Modifier.size(25.dp)
                    )
                },
                label = { Text(stringResource(destination.iconTextId)) },
                modifier = Modifier,
            )

        }
    }

}


@Composable
fun DokkhotaNavigationBar(
    modifier: Modifier = Modifier,
    content: @Composable RowScope.() -> Unit,
) {
    NavigationBar(
        modifier = Modifier,
        contentColor = DokkhotaNavigationDefaults.navigationContentColor(),
        tonalElevation = 3.dp,
        content = content,
    )
}


@Composable
fun RowScope.DokkhotaNavigationBarItem(
    selected: Boolean,
    onClick: () -> Unit,
    icon: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    selectedIcon: @Composable () -> Unit = icon,
    enabled: Boolean = true,
    label: @Composable (() -> Unit)? = null,
    alwaysShowLabel: Boolean = false,
) {
    NavigationBarItem(
        selected = selected,
        onClick = onClick,
        icon = if (selected) selectedIcon else icon,
        modifier = modifier,
        enabled = enabled,
        label = label,
        alwaysShowLabel = alwaysShowLabel,
        colors = NavigationBarItemDefaults.colors(
            selectedIconColor = DokkhotaNavigationDefaults.navigationSelectedItemColor(),
            unselectedIconColor = DokkhotaNavigationDefaults.navigationContentColor(),
            selectedTextColor = DokkhotaNavigationDefaults.navigationSelectedItemColor(),
            unselectedTextColor = DokkhotaNavigationDefaults.navigationContentColor(),
            indicatorColor = DokkhotaNavigationDefaults.navigationIndicatorColor(),
        ),
    )
}


object DokkhotaNavigationDefaults {
    @Composable
    fun navigationContentColor() = MaterialTheme.colorScheme.onSurfaceVariant

    @Composable
    fun navigationSelectedItemColor() = MaterialTheme.colorScheme.onPrimaryContainer

    @Composable
    fun navigationIndicatorColor() = MaterialTheme.colorScheme.primaryContainer
}


@Composable
private fun DokkhotaNavRail(
    destinations: List<TopLevelDestination>,
    onNavigateToDestination: (TopLevelDestination) -> Unit,
    currentDestination: NavDestination?,
    modifier: Modifier = Modifier,
) {
    DokkhotaNavigationRail(modifier = modifier) {
        destinations.forEach { destination ->
            val selected = currentDestination.isTopLevelDestinationInHierarchy(destination)
            DokkhotaNavigationRailItem(
                selected = selected,
                onClick = { onNavigateToDestination(destination) },
                icon = {
                    Icon(
                        imageVector = destination.unselectedIcon,
                        contentDescription = null,
                    )
                },
                selectedIcon = {
                    Icon(
                        imageVector = destination.selectedIcon,
                        contentDescription = null,
                    )
                },
                label = { Text(stringResource(destination.iconTextId)) },
                modifier = Modifier,
            )
        }
    }
}

@Composable
fun DokkhotaNavigationRailItem(
    selected: Boolean,
    onClick: () -> Unit,
    icon: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    selectedIcon: @Composable () -> Unit = icon,
    enabled: Boolean = true,
    label: @Composable (() -> Unit)? = null,
    alwaysShowLabel: Boolean = true,
) {
    NavigationRailItem(
        selected = selected,
        onClick = onClick,
        icon = if (selected) selectedIcon else icon,
        modifier = modifier,
        enabled = enabled,
        label = label,
        alwaysShowLabel = alwaysShowLabel,
        colors = NavigationRailItemDefaults.colors(
            selectedIconColor = DokkhotaNavigationDefaults.navigationSelectedItemColor(),
            unselectedIconColor = DokkhotaNavigationDefaults.navigationContentColor(),
            selectedTextColor = DokkhotaNavigationDefaults.navigationSelectedItemColor(),
            unselectedTextColor = DokkhotaNavigationDefaults.navigationContentColor(),
            indicatorColor = DokkhotaNavigationDefaults.navigationIndicatorColor(),
        ),
    )
}

@Composable
fun DokkhotaNavigationRail(
    modifier: Modifier = Modifier,
    header: @Composable (ColumnScope.() -> Unit)? = null,
    content: @Composable ColumnScope.() -> Unit,
) {
    NavigationRail(
        modifier = modifier,
        containerColor = Color.Transparent,
        contentColor = DokkhotaNavigationDefaults.navigationContentColor(),
        header = header,
        content = content,
    )
}

private fun NavDestination?.isTopLevelDestinationInHierarchy(destination: TopLevelDestination) =
    this?.hierarchy?.any {
        it.route?.contains(destination.name, true) ?: false
    } ?: false


