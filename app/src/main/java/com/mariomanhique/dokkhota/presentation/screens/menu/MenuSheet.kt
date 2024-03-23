package com.mariomanhique.dokkhota.presentation.screens.menu

import android.util.Log
import android.view.View
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.auth.FirebaseAuth

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MenuSheet(
    onSheetDismissed: () -> Unit,
    onSignedOut: () -> Unit
){
    ModalBottomSheet(
        onDismissRequest = {
        onSheetDismissed()
    },
    modifier = Modifier.fillMaxSize()
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp)

        ) {
            Text(text = "BottomSheet")

            HorizontalDivider()

            ClickableText(
                text = AnnotatedString("Sign Out"),
                modifier = Modifier
                    .padding(10.dp),
            ) {
                onSignedOut()
            }

        }

    }
}