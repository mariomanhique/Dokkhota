package com.mariomanhique.dokkhota.presentation.screens.profile

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.mariomanhique.dokkhota.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileTopBar(
){
    CenterAlignedTopAppBar(
        navigationIcon = {
        },

        title = {
            Column {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = stringResource(id = R.string.profile),
                )
            }
        },

    )
}
