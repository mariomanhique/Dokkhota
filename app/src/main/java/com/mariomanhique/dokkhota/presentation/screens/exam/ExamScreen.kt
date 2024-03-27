package com.mariomanhique.dokkhota.presentation.screens.exam

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFromBaseline
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.mariomanhique.dokkhota.R
import java.util.Locale

@Composable
fun ExamScreen(
    onCategoryClicked: (String) -> Unit,
    examsViewModel: ExamsViewModel = hiltViewModel(),
    paddingValues: PaddingValues
){
    
    val categories by examsViewModel.categories.collectAsStateWithLifecycle()
    
    
    if (categories.isNotEmpty()){
        ExamContent(
            categories = categories,
            onCategoryClicked = onCategoryClicked,
            paddingValues = paddingValues
        )
    }
    
}

@Composable
fun ExamContent(
    categories: List<String>,
    onCategoryClicked: (String) -> Unit,
    paddingValues: PaddingValues
){
    LazyColumn(
        modifier = Modifier.fillMaxSize()
            .padding(horizontal = 24.dp)
            .navigationBarsPadding()
//            .padding(top = paddingValues.calculateTopPadding())
            .padding(bottom = paddingValues.calculateBottomPadding())
            .padding(start = paddingValues.calculateStartPadding(LayoutDirection.Ltr))
            .padding(end = paddingValues.calculateEndPadding(LayoutDirection.Ltr)),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            Text(
                modifier = Modifier.paddingFromBaseline(top = 20.dp, bottom = 60.dp),
                text = "CATEGORIES",
                style = MaterialTheme.typography.titleLarge.copy(
                    fontSize = 30.sp
                ),
//                fontWeight = FontWeight.ExtraBold,
                )

        }
        
        items(items = categories){category->
            CategoryCard(category = category.replaceFirstChar {
                if (it.isLowerCase()) it.titlecase(
                    Locale.ROOT
                ) else it.toString()
            },
                onCategoryClicked = onCategoryClicked
            )
        }
        
    }
}


@Composable
fun CategoryCard(
    modifier: Modifier = Modifier,
    onCategoryClicked: (String) -> Unit,
    category: String
){
    Surface(
        onClick = {onCategoryClicked(category)},
        modifier = modifier
            .fillMaxWidth()
            .height(80.dp)
            .padding(vertical = 5.dp),
        tonalElevation = 1.dp

    ) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(vertical = 5.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                modifier = Modifier
                    .size(80.dp),
                model = R.drawable.profile,
                contentDescription =""

            )
            Column(
                modifier = Modifier.weight(0.2f),
            ) {
             Text(
                 text = category,
                 style = MaterialTheme.typography.bodyLarge.copy(
                     fontSize = 20.sp
                 )
             )
            }

            Icon(imageVector = Icons.AutoMirrored.Filled.ArrowForwardIos, contentDescription = "")
        }
    }

}