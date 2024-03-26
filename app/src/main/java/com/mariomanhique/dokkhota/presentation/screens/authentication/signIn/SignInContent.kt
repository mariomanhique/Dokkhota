package com.mariomanhique.dokkhota.presentation.screens.authentication.signIn

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFromBaseline
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.RemoveRedEye
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mariomanhique.dokkhota.R
import com.mariomanhique.dokkhota.presentation.components.AuthTextEvents
import com.mariomanhique.dokkhota.presentation.components.CustomButton
import com.mariomanhique.dokkhota.presentation.components.InputTextField


@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
@Composable
fun SignInContent(
    onSignInClicked: (String, String) -> Unit,
    navigateToSignUp: () -> Unit

){

    val focusManager = LocalFocusManager.current
    val context = LocalContext.current
    val keyboardController = LocalSoftwareKeyboardController.current
    var password by remember {
        mutableStateOf("")
    }
    var username by remember {
        mutableStateOf("")
    }
    var passwordVisibility by remember {
        mutableStateOf(false)
    }
    var buttonEnabled by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(key1 = username, key2 = password){
        buttonEnabled = username.isNotEmpty() && password.isNotEmpty()
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            modifier = Modifier.paddingFromBaseline(top = 50.dp, bottom = 50.dp),
            text = stringResource(id = R.string.app_name),
            style = MaterialTheme.typography.headlineLarge.copy(
                fontSize = 60.sp
            )
            )

        InputTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            value = username,
            onValueChange = {
                username = it
            },
            leadingIcon = R.drawable.ic_profile,
            colors = TextFieldDefaults.colors(
                unfocusedIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                disabledContainerColor = MaterialTheme.colorScheme.inverseOnSurface
            ),
            placeholder = R.string.email,
            focusManager = focusManager,
            keyboardType = KeyboardType.Text,
            visualTransformation = VisualTransformation.None
        )

        Spacer(modifier = Modifier.height(10.dp))
        InputTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            value = password,
            onValueChange = {
                password = it
            },
            leadingIcon = R.drawable.lock,
            placeholder = R.string.password,
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = MaterialTheme.colorScheme.inverseOnSurface,
                unfocusedIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent),
            trailingIcon = Icons.Default.RemoveRedEye,
            iconTint = if(passwordVisibility)
                MaterialTheme.colorScheme.secondary
            else MaterialTheme.colorScheme.secondaryContainer,
            onIconClicked = {
                passwordVisibility = !passwordVisibility
            },
            focusManager = focusManager,
            keyboardType = KeyboardType.Password,
            visualTransformation = if (passwordVisibility) VisualTransformation.None
            else PasswordVisualTransformation()
        )

        CustomButton(
            modifier = Modifier.fillMaxWidth(),
            buttonText = R.string.sign_In,
            buttonEnabled = buttonEnabled,
            onClicked = {
                if (username.isNotEmpty() && password.isNotEmpty()){
                    onSignInClicked(username, password)
                                    keyboardController?.hide()
                }else{
                    Toast.makeText(context, "Fields shouldn't be empty", Toast.LENGTH_SHORT).show()
                }
            })

        AuthTextEvents(
            authAlternativeText = R.string.sign_up_text,
            authAlternativeTextAction = R.string.sign_up,
            onTextClicked = navigateToSignUp
            )
    }
}

