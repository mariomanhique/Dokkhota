package com.mariomanhique.dokkhota.presentation.screens.profile

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ProfileScreen(
    profileViewModel: ProfileViewModel,
    paddingValues: PaddingValues
){



    val userData = profileViewModel.userData.collectAsStateWithLifecycle().value
    var username by remember {
        mutableStateOf(userData.username)
    }
 
    val context = LocalContext.current
    val profileState = profileViewModel.profileState

    Box {

//        Scaffold(
//            topBar = {
//                ProfileTopBar()
//            }
//        ) {
            ProfileContent(
                paddingValues = paddingValues
            )
//        }
    }
}

