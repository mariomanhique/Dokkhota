package com.mariomanhique.dokkhota.presentation.screens.exam

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mariomanhique.dokkhota.model.Result
import com.mariomanhique.dokkhota.ui.theme.AppColors
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConfirmSheet(
    onSheetDismissed: () -> Unit,
    onStartClicked: () -> Unit,
    onCancelClicked: () -> Unit,
    examN: String,
    category: String,
    totalQuestions: Int,
    examsViewModel: ExamsListViewModel = hiltViewModel()
){

//    val examUiState by examsViewModel.examUiState.collectAsStateWithLifecycle()
    val exam = examsViewModel.examDetails.collectAsStateWithLifecycle().value

    var totalTime by remember {
        mutableLongStateOf(0L)
    }

    var description by remember {
        mutableStateOf("")
    }

    var description2 by remember {
        mutableStateOf("")
    }

    Log.d("ExamDetail", "ConfirmSheet: $exam ")


    when(exam){
        is Result.Success ->{
            Log.d("ExamDetails", "ConfirmSheet: ${(exam as Result.Success).data}")
            description = exam.data.description
            description2 = exam.data.descriptionTwo
            totalTime = exam.data.time

        }
        is Result.Error ->{
            Log.d("ExamDetails", "ConfirmSheet: Error")
        }
        is Result.Loading ->{
            Log.d("ExamDetails", "ConfirmSheet: Loading")
        }

    }


    ModalBottomSheet(
        modifier = Modifier
            .fillMaxSize()
            .heightIn(max = Int.MAX_VALUE.dp),
        onDismissRequest = {
            onSheetDismissed()
        },
    ) {
        Box {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 40.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top

            ) {
                Text(
                    text = description,
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontSize = 20.sp,
                        textAlign = TextAlign.Center
                    ),
                )
                Spacer(modifier = Modifier.height(10.dp))

                Text(text = "$totalQuestions questions of $totalTime minutes")
                Spacer(modifier = Modifier.height(10.dp))

                Text(text = "1.0 marks per question and 0.5 will be deducted for wrong answers")

                Spacer(modifier = Modifier.height(30.dp))

                Text(text = "Syllabus")
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = category.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString() } +
                            " - 0$examN - $description2",
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontSize = 18.sp,
                        color = Color.Gray,
                        textAlign = TextAlign.Center
                    ),
                )
                Spacer(modifier = Modifier.height(10.dp))
                Button(
                    modifier = Modifier
                        .fillMaxWidth(),
                    shape = MaterialTheme.shapes.small,
                    onClick = {
                          onStartClicked()
                    },
                    colors = ButtonColors(
                        containerColor = AppColors.AppBlue,
                        contentColor = MaterialTheme.colorScheme.secondary,
                        disabledContainerColor = Color.Gray,
                        disabledContentColor = MaterialTheme.colorScheme.secondary
                    )
                ) {
                    Text(
                        color = MaterialTheme.colorScheme.onSurface,
                        text = "Get Started"
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    modifier = Modifier.clickable {
                           onCancelClicked()
                    },
                    color = Color.Red,
                    text = "Cancel"
                )
            }

        }

    }

}