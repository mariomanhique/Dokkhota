package com.mariomanhique.dokkhota.presentation.screens.profile

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.firebase.auth.FirebaseAuth
import com.mariomanhique.dokkhota.R
import com.mariomanhique.dokkhota.model.Result
import com.mariomanhique.dokkhota.presentation.components.CustomButton
import com.mariomanhique.dokkhota.presentation.components.EmptyPage

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    onDeleteClicked: (Boolean) -> Unit,
    onLogoutClicked: (Boolean) -> Unit,
    navigateToSignIn: () -> Unit,
    profileViewModel: ProfileViewModel = hiltViewModel(),
    paddingValues: PaddingValues
){



    val userData = profileViewModel.profile.collectAsStateWithLifecycle().value

    var username by remember {
        mutableStateOf("")
    }

    username = when(userData){
        is Result.Success -> {
            userData.data.username
        }
        is Result.Error -> {
            "Null"
        }
        else -> {
            "Loading..."
        }
    }
 
    val context = LocalContext.current
    val profileState = profileViewModel.profileState

    if  (FirebaseAuth.getInstance().currentUser != null){
        Box {
                ProfileContent(
                    imageProfile = profileState.image.value.image,
                    username = username,
                    onSelectImage = {imageUrl->
                        val type = context.contentResolver.getType(imageUrl)?.split("/")?.last() ?: "jpg"
                        profileViewModel.addImage(
                            image = imageUrl,
                            imageType = type
                        )
                    },
                    onProfileSaved = {
                        profileViewModel.updateUsername(
                            username = username.toString()
                        )
                    },
                    onValueChanged = {
                        username = it
                    },
                    onDeleteClicked = onDeleteClicked,
                    onLogoutClicked = onLogoutClicked,
                    onImageUpdated = {
                        profileViewModel.updateImageProfile(
                            profileState.image.value.remoteImagePath
                        )
                    },
                    paddingValues = paddingValues
                )

         }

    }else{

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            EmptyPage(
                title = "No account",
                subtitle = "You don't yet have an account, SignUp/SignIn!"

            )
            CustomButton(
                modifier = Modifier.padding(horizontal = 40.dp),
                buttonText = R.string.sign_In,
                buttonEnabled = true,
                onClicked = navigateToSignIn
            )
        }

    }
}

