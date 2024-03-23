package com.mariomanhique.dokkhota.presentation.screens.exam

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.mariomanhique.dokkhota.R

@Composable
fun ExamsListScreen(
    onExamClicked: (String) -> Unit,
    examsListViewModel: ExamsListViewModel = hiltViewModel()
){


    val examsList by examsListViewModel.exams.collectAsStateWithLifecycle()

    examsList?.let {
        ExamsListContent(onExamClicked = onExamClicked, examsCount = it)
    }

    Log.d("Exams", "ExamsListScreen: $examsList")


}

@Composable
fun ExamsListContent(
    onExamClicked: (String) -> Unit,
    examsCount: List<String>
){


  LazyColumn {

      items(items = examsCount){
          ExamCard(
              onExamClicked = onExamClicked,
              examN = it)
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
            modifier = modifier
                .fillMaxWidth()
                .padding(
                    vertical = 5.dp
                ).padding(horizontal = 5.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(0.2f),
            ) {
                Text(
                    text = "BCS - $examN"
                )
            }

            Icon(imageVector = Icons.AutoMirrored.Filled.ArrowForwardIos, contentDescription = "")
        }
    }

}