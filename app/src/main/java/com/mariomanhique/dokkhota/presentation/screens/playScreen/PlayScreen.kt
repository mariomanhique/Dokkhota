package com.mariomanhique.dokkhota.presentation.screens.playScreen

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonColors
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mariomanhique.dokkhota.model.Question
import com.mariomanhique.dokkhota.model.Result
import com.mariomanhique.dokkhota.util.HandleBackButtonPress
import kotlinx.coroutines.delay

@Composable
fun PlayScreen(
    paddingValues: PaddingValues,
    playViewModel: PlayViewModel = hiltViewModel(),
    onBackToHomeClicked: () -> Unit,
    popBackStack: () -> Unit,
){

    val questions by playViewModel.exams.collectAsStateWithLifecycle()

    when(questions){
        is Result.Success ->{
//            Log.d("Questions", "Play: ${}")

            val examQuestions = (questions as Result.Success<List<Question>>).data

            if (examQuestions.isNotEmpty()){
                PlayContent(
                    questions = examQuestions,
                    paddingValues = paddingValues,
                    onBackToHomeClicked = onBackToHomeClicked,
                    popBackStack = popBackStack
                )
            }else{
                EmptyPage(
                    title = "Empty Exam",
                    subtitle = "This Exam has no questions yet"
                )
            }

        }
        is Result.Error ->{

        }
        else -> {

        }
    }
}

@Composable
fun PlayContent(
     questions: List<Question>,
     paddingValues: PaddingValues,
     onBackToHomeClicked: () -> Unit,
     popBackStack: () -> Unit,
){

    var selectedOptions by remember { mutableStateOf<Map<Int, String>>(emptyMap()) }
    var isExamCompleted by remember { mutableStateOf(false) }
    var timesClicked by remember {
        mutableStateOf(0)
    }
    var value by remember {
        mutableFloatStateOf(0F)
    }

    var onDismissDialod by remember {
        mutableStateOf(false)
    }
    var initialTime by remember { mutableLongStateOf(0L) }
    var currentTime by remember { mutableLongStateOf(0L) }
    var isTimerRunning by remember { mutableStateOf(false) }
    var progress by remember { mutableFloatStateOf(0f) }



 
    HandleBackButtonPress {
        onDismissDialod = true
    }
    
    if (onDismissDialod){
        AlertDialog(
            title = { 
                Text(text = "Quit Exam?")
            },
            text = {
               Text(text = "Are you sure you want to quick this exam?")
            },
            onDismissRequest = {
                       onDismissDialod = false
        }, confirmButton = {
            Button(onClick = {
                onDismissDialod = false
                popBackStack()
            }) {
                Text(text = "Yes")
            }
        },
        dismissButton = {
            Button(onClick = {
                onDismissDialod = false
            }) {
                Text(text = "No")
            }
        }
        )
    }
    

    fun startTimer(initialT: Long) {
        currentTime = initialT
        initialTime = initialT
        isTimerRunning = true
    }

    //The logic behind the timer
    LaunchedEffect(isTimerRunning) {
        if (isTimerRunning) {
            while (currentTime > 0) {
                delay(100L)
                currentTime -= 100L
                progress = 1f - currentTime / initialTime.toFloat()
            }
            isTimerRunning = false  // Update isTimerRunning after the loop
        }

    }

    // The timer starts when the composable is first composed
    LaunchedEffect(Unit) {
        startTimer(10 * 1000L) // Start the timer with 10 seconds (10 * 1000 milliseconds)
    }

    isExamCompleted = !isTimerRunning

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        LinearProgressIndicator(
            modifier = Modifier.fillMaxWidth(),
            progress = { progress }
        )
        if (isExamCompleted){
            Row(
                modifier = Modifier.fillMaxWidth(),
               horizontalArrangement = Arrangement.SpaceBetween
            ) {
                TextButton(onClick = {
                   popBackStack()
                }) {
                    Text(text = "Back")
                }
                TextButton(onClick = {
                    isExamCompleted = false
                }) {
                    Text(text = "Check Score")
                }
            }
        }


        LazyColumn(
            modifier = Modifier
                .fillMaxHeight(0.93F)
                .padding(horizontal = 24.dp)
                .navigationBarsPadding()
                .padding(top = paddingValues.calculateTopPadding())
                .padding(bottom = paddingValues.calculateBottomPadding())
                .padding(start = paddingValues.calculateStartPadding(LayoutDirection.Ltr))
                .padding(end = paddingValues.calculateEndPadding(LayoutDirection.Ltr)),
        ) {
            itemsIndexed(items = questions) { index, item ->
                QACard(
                    index = index,
                    question = item.question,
                    answer = item.answer,
                    selectedOption = selectedOptions[index],
                    onSelectOption = { option ->
                        selectedOptions += index to option
                    },
                    isExamCompleted = isExamCompleted,
                    choices = item.choices,
                )
            }
        }

        if (isExamCompleted){
            Button(
                onClick = {
                    isExamCompleted = true
                    timesClicked +=1
                    onBackToHomeClicked()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                shape = MaterialTheme.shapes.small,
            ) {
                Text(text = if (!isExamCompleted) "Submit" else "Back To Home")
            }
        }

    }
}


@Composable
fun QACard(
    index: Int,
    question: String,
    isExamCompleted: Boolean,
    answer: String,
    selectedOption: String?,
    onSelectOption: (String) -> Unit,
    choices: List<String>,
) {
    Column(Modifier.selectableGroup()) {
        SectionTitle(text = "${index + 1}. $question")

        choices.forEach { choice ->
            ChooserRow(
                text = choice,
                selected = choice == selectedOption,
                isExamCompleted = isExamCompleted,
                isAnswerCorrect = choice == answer,
                onClick = { onSelectOption(choice) }
            )
        }
    }
}


@Composable
private fun SectionTitle(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.titleLarge,
        modifier = Modifier.padding(top = 16.dp, bottom = 8.dp),
    )
}

@Composable
fun EmptyPage(
    title: String = "Empty Diary",
    subtitle: String = "Write Something"
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(all = 24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = title,
            style = TextStyle(
                fontSize = MaterialTheme.typography.titleMedium.fontSize,
                fontWeight = FontWeight.Medium
            )
        )
        Text(
            text = subtitle,
            style = TextStyle(
                fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                fontWeight = FontWeight.Normal
            )
        )
    }
}
@Composable
fun ChooserRow(
    text: String,
    selected: Boolean,
    isExamCompleted: Boolean,
    isAnswerCorrect: Boolean,
    onClick: () -> Unit,
) {

    val checkedValue = checkAnswer(selected, isAnswerCorrect, isExamCompleted)

    Row(
        Modifier
            .fillMaxWidth()
            .selectable(
                selected = selected,
                enabled = !isExamCompleted,
                role = Role.RadioButton,
                onClick = onClick,
            )
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        RadioButton(
            selected = selected,
            onClick = null,
            colors = RadioButtonColors(
                selectedColor = checkedValue,
                disabledSelectedColor = checkedValue,
                unselectedColor = checkedValue,
                disabledUnselectedColor = checkedValue
            )
        )
        Spacer(Modifier.width(8.dp))
        Text(
            text,
            style = MaterialTheme.typography.bodyLarge.copy(
                fontSize = 20.sp
            ),
            color = checkedValue
        )
    }
}

@Composable
fun checkAnswer(
    selected: Boolean,
    isAnswerCorrect: Boolean,
    isExamCompleted: Boolean,
): Color {
    if (isExamCompleted) {
        return if (isAnswerCorrect && selected) {
            Color.Green
        } else if (!isAnswerCorrect && selected) {
            Color.Red
        } else {
            MaterialTheme.colorScheme.secondary
        }
    } else {
        return  MaterialTheme.colorScheme.secondary
    }
}