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

fun formatSecondsToTime(seconds: Long): String {
    val hours = seconds / 3600
    val minutes = (seconds % 3600) / 60
    val remainingSeconds = seconds % 60

    return "%02d:%02d:%02d".format(hours, minutes, remainingSeconds)
}