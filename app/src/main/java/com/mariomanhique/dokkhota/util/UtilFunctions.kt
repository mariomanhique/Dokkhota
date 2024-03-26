package com.mariomanhique.dokkhota.util

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable

@Composable
fun HandleBackButtonPress(
    onBackPressed: () -> Unit
) {
    BackHandler(enabled = true) {
        onBackPressed.invoke()
    }

}