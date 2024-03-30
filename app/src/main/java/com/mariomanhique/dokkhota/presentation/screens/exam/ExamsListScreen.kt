package com.mariomanhique.dokkhota.presentation.screens.exam

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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

@Composable
fun ExamsListScreen(
    paddingValues: PaddingValues,
    onExamClicked: (String, String) -> Unit,
    examsListViewModel: ExamsListViewModel = hiltViewModel()
){


    val examsList by examsListViewModel.exams.collectAsStateWithLifecycle()

    val category by examsListViewModel.category.collectAsStateWithLifecycle()

    examsList?.let {
        ExamsListContent(
            paddingValues = paddingValues,
            onExamClicked = {examNr->
            category?.let { category -> onExamClicked(examNr, category) }
        }, examsCount = it,
            category = category.toString()
        )
    }

    Log.d("Exams", "ExamsListScreen: $examsList + $category")


}

@Composable
fun ExamsListContent(
    paddingValues: PaddingValues,
    onExamClicked: (String) -> Unit,
    examsCount: List<String>,
    category: String
){

    Column(
        modifier = Modifier
            .fillMaxSize(),
//            .padding(paddingValues),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Text(
//            modifier = Modifier.padding(20.dp),
            text = category,
            style = MaterialTheme.typography.titleLarge.copy(
                fontSize = 30.sp
            ),
//                fontWeight = FontWeight.ExtraBold,
        )
        
        Spacer(modifier = Modifier.height(10.dp))

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp)
//                .navigationBarsPadding()
//                .padding(top = paddingValues.calculateTopPadding())
//                .padding(bottom = paddingValues.calculateBottomPadding())
//                .padding(start = paddingValues.calculateStartPadding(LayoutDirection.Ltr))
//                .padding(end = paddingValues.calculateEndPadding(LayoutDirection.Ltr))
        ) {

            items(items = examsCount){
                ExamCard(
                    onExamClicked = onExamClicked,
                    examN = it
                )
            }
        }
    }
}

@Composable
fun ExamCard(
    modifier: Modifier = Modifier,
    onExamClicked: (String) -> Unit,
    examN: String
){
    Surface(
        onClick = {onExamClicked(examN)},
        modifier = modifier
            .fillMaxWidth()
            .height(80.dp)
            .padding(vertical = 5.dp),
        tonalElevation = 1.dp

    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    vertical = 5.dp
                )
                .padding(horizontal = 5.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(0.2f),
            ) {
                Text(
                    modifier = Modifier.padding(horizontal = 10.dp),
                    text = "BCS - $examN",
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontSize = 20.sp
                    )
                )
            }

            Icon(imageVector = Icons.AutoMirrored.Filled.ArrowForwardIos, contentDescription = "")
        }
    }

}