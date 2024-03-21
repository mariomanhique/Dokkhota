package com.mariomanhique.dokkhota.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Article
import androidx.compose.material.icons.outlined.AutoGraph
import androidx.compose.material.icons.outlined.Bookmarks
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.rounded.Article
import androidx.compose.material.icons.rounded.AutoGraph
import androidx.compose.material.icons.rounded.Bookmarks
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Notifications
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material.icons.rounded.Search
import androidx.compose.ui.graphics.vector.ImageVector
import com.mariomanhique.dokkhota.R

enum class TopLevelDestination(
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val iconTextId: Int,
    val titleTextId: Int
) {
    HOME(
        selectedIcon = Icons.Rounded.Home,
        unselectedIcon = Icons.Outlined.Home,
        iconTextId = R.string.home,
        titleTextId = R.string.app_name
    ),
    EXAM(
        selectedIcon = Icons.Rounded.Article,
        unselectedIcon = Icons.Outlined.Article,
        iconTextId = R.string.exam,
        titleTextId =R.string.exam

    ),
    ANALYTICS(
        selectedIcon = Icons.Rounded.AutoGraph,
        unselectedIcon = Icons.Outlined.AutoGraph,
        iconTextId = R.string.analytics,
        titleTextId = R.string.analytics

    ),

    PROFILE(
        selectedIcon = Icons.Rounded.Person,
        unselectedIcon = Icons.Outlined.Person,
        iconTextId = R.string.profile,
        titleTextId = R.string.profile
    )
}