package com.mariomanhique.dokkhota.presentation.screens.profile

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.StringRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFromBaseline
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.icons.rounded.ExitToApp
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.core.net.toUri
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.mariomanhique.diaryapp.presentation.screens.profile.GalleryImage
import com.mariomanhique.dokkhota.R
import com.mariomanhique.dokkhota.presentation.components.ZoomableImage
import com.mariomanhique.dokkhota.ui.theme.DokkhotaTheme

@Composable
fun ProfileContent(
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues
){

    val scrollState = rememberScrollState()

    Column(

        modifier.fillMaxSize()
            .verticalScroll(scrollState)
            .padding(paddingValues)
            .padding(horizontal = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        UserDetailsCard(
            imageProfile = null,
            username = "",
            onSelectImage = {}) {

        }
    }
}

@Composable
fun UserDetailsCard(
    imageProfile: Uri?,
    username: String,
    onSelectImage: (Uri) -> Unit,
    onImageUpdated: () -> Unit
){

    var imageSelected by remember { mutableStateOf(Uri.EMPTY) }
    var imagePreviewState by remember {
        mutableStateOf(false)
    }

    Box(modifier = Modifier,
        contentAlignment = Alignment.BottomEnd

    ){

        val imageUri by remember {
            mutableStateOf(
                if (imageProfile.toString().isEmpty()) R.drawable.profile
                else imageProfile
            )
        }



        val multiplePhotoPicker = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.PickVisualMedia(),
        ) { image ->
            if (image != null) {
                onSelectImage(image)
                imageSelected = image
                imagePreviewState = true
            }
        }

        AsyncImage(
            modifier = Modifier
                .clip(CircleShape)
                .size(150.dp)
                .border(
                    width = 1.dp,
                    brush = Brush.linearGradient(colors = listOf(Color.Red, Color.Cyan)),
                    shape = CircleShape
                ),
            model = ImageRequest.Builder(LocalContext.current)
                .data(imageUri)
                .crossfade(true)
                .build(),
            contentDescription = "",
            contentScale = ContentScale.Crop
        )

        Box(
            modifier = Modifier
                .border(
                    width = 1.dp,
                    color = Color.White,
                    shape = CircleShape
                )
                .clip(CircleShape)
                .padding(1.dp)
                .clickable {
                    multiplePhotoPicker.launch(
                        PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                    )
                }
        ){
            Box(
                modifier = Modifier
                    .border(
                        width = 1.dp,
                        brush = Brush.linearGradient(colors = listOf(Color.Red, Color.Cyan)),
                        shape = CircleShape
                    )
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.surface)
                    .padding(5.dp),
            ){

                Icon(
                    modifier = Modifier
                        .clip(CircleShape)
                        .align(Alignment.Center),
                    tint = MaterialTheme.colorScheme.secondary,
                    imageVector = Icons.Default.PhotoCamera,
                    contentDescription = ""
                )

            }
        }

    }

    AnimatedVisibility(
        visible = imagePreviewState) {
        Dialog(
            onDismissRequest = {
                imagePreviewState = false
            }) {
            ZoomableImage(
                actionButton = R.string.chooseAction,
                selectedGalleryImage = GalleryImage(image = imageSelected),
                onCloseClicked = {
                    imagePreviewState = false
                },
                onActionClicked = {
                    onImageUpdated()
                    imagePreviewState = false
                }
            )
        }

    }


    Text(text = username,
        modifier = Modifier.paddingFromBaseline(top = 24.dp, bottom = 24.dp)
    )
}








@Preview(showBackground = true, widthDp = 320)
@Composable
fun ProfileCardInfoPreview(
){
    DokkhotaTheme {

    }
}



