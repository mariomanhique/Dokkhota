package com.mariomanhique.dokkhota.presentation.screens.playScreen

import android.widget.Space
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.auth.FirebaseAuth
import com.mariomanhique.dokkhota.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ResultSheet(
    onSheetDismissed: () -> Unit,
    totalQuestions: Int,
    correctAnswers: Int,
    wrongAnswers: Int,
    attempts: Int
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
                .padding(horizontal = 10.dp)
                .padding(vertical = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally

        ) {
            Text(
                textAlign = TextAlign.Center,
                text = "Find your results here. Know that to see your saved performance you have to have an account")

            Spacer(modifier = Modifier.height(10.dp))
            HorizontalDivider()
            Spacer(modifier = Modifier.height(40.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(30.dp)
            ) {

                ResultCard(
                    resultTitle = R.string.total,
                    resultCount = totalQuestions,
                    color = MaterialTheme.colorScheme.secondary
                )

                ResultCard(
                    resultTitle = R.string.correct,
                    resultCount = correctAnswers,
                    color = Color.Green
                )

                ResultCard(
                    resultTitle =R.string.wrong,
                    resultCount = wrongAnswers,
                    color = Color.Red
                )

                if (FirebaseAuth.getInstance().currentUser != null){
                    ResultCard(
                        resultTitle = R.string.attempts,
                        resultCount = attempts,
                        color = MaterialTheme.colorScheme.secondary
                    )
                }

            }
        }

    }
}


@Composable
fun ResultCard(
    @StringRes resultTitle: Int,
    resultCount: Int,
    color:Color
){
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(id = resultTitle),
            style = MaterialTheme.typography.titleMedium.copy(
                color = color,
                fontSize = 20.sp
            )
        )
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = "$resultCount",
            fontSize = 18.sp
        )
    }
}