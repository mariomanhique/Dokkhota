package com.mariomanhique.dokkhota.presentation.components

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InputTextWithNoIcon(
    modifier: Modifier = Modifier,
    value: String,
    keyboardType: KeyboardType,
    colors: TextFieldColors,
    isSingleLine: Boolean = true,
    maxLines: Int = 1,
    onIconClicked: () -> Unit = {  },
    visualTransformation: VisualTransformation,
    focusDirection: FocusDirection? = null,
    focusManager: FocusManager? = null,
    @StringRes placeholder: Int,
    onValueChange: (String) -> Unit
){

    TextField(
        modifier = modifier,
        value = value,
        onValueChange = onValueChange,
        singleLine = isSingleLine,
        maxLines = maxLines,
        placeholder = {
            Text(
                text = stringResource(id = placeholder),
                color = Color.Gray
            )
        },
        shape = MaterialTheme.shapes.medium,
        colors = colors,
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Next,
            keyboardType =keyboardType
        ),
        keyboardActions = KeyboardActions (
            onNext = {
                if (focusDirection != null){
                    focusManager?.moveFocus(focusDirection)
                }
                focusManager?.clearFocus()
            }
        ),
        visualTransformation = visualTransformation
    )
}