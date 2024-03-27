package com.mariomanhique.dokkhota.presentation.components

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.mariomanhique.dokkhota.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeTopBar(
    modifier: Modifier = Modifier,
    @StringRes title: Int,
    scrollBehavior: TopAppBarScrollBehavior,
    onMenuClicked: () -> Unit
){
    TopAppBar(
        title = {
            Text(
                text = stringResource(id = title),
//                fontFamily = ,
                fontSize = 20.sp
            )
        },
        modifier = modifier,
        scrollBehavior = scrollBehavior,
        actions = {
            IconButton(onClick = onMenuClicked) {
                Icon(
                    imageVector =Icons.Rounded.Settings,
                    contentDescription = stringResource(id = R.string.menu),
                )
            }
//            AsyncImage(
//                modifier = Modifier
//                    .size(40.dp)
//                    .clip(CircleShape),
//                model = ImageRequest.Builder(LocalContext.current)
//                    .data("https://i.ibb.co/jgc9BQw/cover-book.jpg")
//                    .crossfade(true)
//                    .build(),
//                contentScale = ContentScale.Crop,
//                contentDescription = "Gallery Image"
//            )
        },
        colors = TopAppBarDefaults.largeTopAppBarColors(
            containerColor = Color.Unspecified
        )

    )
}