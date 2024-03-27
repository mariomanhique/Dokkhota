package com.mariomanhique.dokkhota.presentation.components

import androidx.annotation.StringRes
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.mariomanhique.dokkhota.R

@Composable
fun CustomDialog(
    onDismissDialog: () -> Unit,
    confirmButton: () -> Unit,
    @StringRes title: Int,
    @StringRes text: Int,
){

    AlertDialog(
        title = {
            Text(text = stringResource(id =title))
        },
        text = {
            Text(text = stringResource(id = text))
        },
        onDismissRequest = {
            onDismissDialog()
        }, confirmButton = {
            Button(onClick = {
                confirmButton()
            }) {
                Text(text = "Yes")
            }
        },
        dismissButton = {
            Button(onClick = {
                onDismissDialog()
            }) {
                Text(text = "No")
            }
        }
    )
}