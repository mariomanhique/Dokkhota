package com.mariomanhique.dokkhota.presentation.components

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp

@Composable
fun CustomButton(
    modifier: Modifier = Modifier,
    @StringRes buttonText: Int,
    buttonEnabled: Boolean,
    onClicked: () -> Unit,
){
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center
    ) {

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .heightIn(max = 70.dp, min = 50.dp),
            enabled = buttonEnabled,
            onClick = onClicked,
            shape = MaterialTheme.shapes.medium,
//            colors = ButtonDefaults.buttonColors(
////                containerColor = Color.Unspecified,
//            )
        ) {
            Text(text = stringResource(id = buttonText))
        }
    }
}




