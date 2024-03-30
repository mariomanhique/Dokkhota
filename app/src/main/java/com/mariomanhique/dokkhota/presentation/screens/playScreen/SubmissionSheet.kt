package com.mariomanhique.dokkhota.presentation.screens.playScreen

import android.util.Log
import android.widget.Space
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFromBaseline
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.auth.FirebaseAuth
import com.mariomanhique.dokkhota.R
import com.mariomanhique.dokkhota.presentation.components.InputTextField
import com.mariomanhique.dokkhota.presentation.screens.profile.InputField
import com.mariomanhique.dokkhota.ui.theme.AppColors
import com.mariomanhique.dokkhota.util.formatSecondsToTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SubmissionSheet(
    onSheetDismissed: () -> Unit,
    answeredQuestions: Int,
    totalQuestions: Int,
    timeLeft: Long,
    onSubmitClicked: (String,String) -> Unit
){

    val ratingState = remember { mutableIntStateOf(0) }
    var comment by remember {
        mutableStateOf("")
    }
    ModalBottomSheet(
        modifier = Modifier
            .fillMaxSize(),
        onDismissRequest = {
            onSheetDismissed()
        },
    ) {
        Box {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 40.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top

        ) {
            Text(text = "Rating (Optional)")
            Spacer(modifier = Modifier.height(10.dp))

            UserRatingBar(
                // 2. Customized UserRatingBar
                ratingState = ratingState,
//                ratingIconPainter = painterResource(id = R.drawable.ic_star_2),
                size = 48.dp,
                selectedColor = Color(0xFF5A966E),
            )

//            Text(
//                text = "Current Rating Bar Value: ${ratingState.intValue}",
//                fontWeight = FontWeight.Bold,
//                fontSize = 20.sp
//            )
            Spacer(modifier = Modifier.height(10.dp))


            Log.d("Ratting", "SubmissionSheet: ${ratingState.value}")

            Text(text = "What can be improved?")
            Spacer(modifier = Modifier.height(10.dp))

            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.2f),
                value = comment,
                onValueChange = {
                    comment = it
                },
                maxLines = Int.MAX_VALUE,
                placeholder = {
                    Text(text = stringResource(id = R.string.comment))
                },
                colors = TextFieldDefaults.colors(
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent
                ),
                shape = MaterialTheme.shapes.medium
            )

            Spacer(modifier = Modifier.height(20.dp))
            
            Text(
                textAlign = TextAlign.Center,
                text = "You answered $answeredQuestions out of $totalQuestions. Time left: ${formatSecondsToTime(timeLeft)}. Are you willing to submit?")

            Spacer(modifier = Modifier.height(20.dp))

            Button(
                modifier = Modifier
                    .fillMaxWidth(),
                shape = MaterialTheme.shapes.small,
                onClick = { onSubmitClicked("${ratingState.intValue}",comment) },
                colors = ButtonColors(
                    containerColor = AppColors.AppBlue,
                    contentColor = MaterialTheme.colorScheme.secondary,
                    disabledContainerColor = Color.Gray,
                    disabledContentColor = MaterialTheme.colorScheme.secondary
                )
            ) {
                Text(
                    color = MaterialTheme.colorScheme.onSurface,
                    text = "Submit"
                )
            }


        }

    }

    }
}


