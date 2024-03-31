package com.mariomanhique.dokkhota.presentation.screens.playScreen

import androidx.compose.foundation.clickable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlayTopBar(
    examCategory:String,
    timer:String,
    popBackStack:()-> Unit,
){
    TopAppBar(title = {
        Text(text = examCategory)
    },
        navigationIcon = {
             Icon(
                 modifier = Modifier.clickable {
                     popBackStack()
                 },
                 imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                 contentDescription = null)
        },
        actions = {
            Text(text = timer)
        }
    )
}