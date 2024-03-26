package com.mariomanhique.dokkhota.presentation.components

import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.paddingFromBaseline
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mariomanhique.dokkhota.R

@Composable
fun AuthTextEvents(
    @StringRes authAlternativeText: Int,
    @StringRes authAlternativeTextAction: Int,
    onTextClicked: () -> Unit,
){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .paddingFromBaseline(
                top = 30.dp,
                bottom = 30.dp
            ),
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            fontSize = 16.sp,
            text = buildAnnotatedString {
            withStyle(style = SpanStyle()){
                append(stringResource(id = authAlternativeText))
            }
            withStyle(style = SpanStyle(

            )){
                append(" "+stringResource(id = authAlternativeTextAction))
            }
        },
            modifier = Modifier.clickable(
//                indication = null,
//                interactionSource = remember {
//                    MutableInteractionSource()
//                },
            ){
                onTextClicked()
            }
        )
    }
}