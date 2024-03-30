package com.mariomanhique.dokkhota.presentation.screens.playScreen

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DoneAll
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonColors
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.firebase.auth.FirebaseAuth
import com.mariomanhique.dokkhota.R
import com.mariomanhique.dokkhota.model.Question
import com.mariomanhique.dokkhota.model.Result
import com.mariomanhique.dokkhota.presentation.components.CustomDialog
import com.mariomanhique.dokkhota.presentation.components.EmptyPage
import com.mariomanhique.dokkhota.presentation.screens.analytics.AnalyticsViewModel
import com.mariomanhique.dokkhota.ui.theme.AppColors
import com.mariomanhique.dokkhota.util.HandleBackButtonPress
import com.mariomanhique.dokkhota.util.formatSecondsToTime
import kotlinx.coroutines.delay

@Composable
fun PlayScreen(
    paddingValues: PaddingValues,
    onBackToHomeClicked: () -> Unit,
    navigateToSignIn: () -> Unit,
    popBackStack: () -> Unit,
    playViewModel: PlayViewModel = hiltViewModel(),
    analyticsViewModel: AnalyticsViewModel = hiltViewModel()
){
    val questions by playViewModel.exams.collectAsStateWithLifecycle()
    var totalQuestions by remember {
        mutableIntStateOf(0)
    }

    var rightAnswers by remember {
        mutableIntStateOf(0)
    }

    var wrongAnswers by remember {
        mutableIntStateOf(0)
    }
    var currentTime by remember {
        mutableLongStateOf(0L)
    }
    val examNr = playViewModel._examNr.value
    val attempts = playViewModel._attempts.value
    val category = playViewModel._category.value
    var resultSheetState by remember {
        mutableStateOf(false)
    }

    var submissionSheetState by remember {
        mutableStateOf(false)
    }

    val context = LocalContext.current

    
    if (resultSheetState){
        ResultSheet(
            onSheetDismissed = {
                resultSheetState = false
            },
            totalQuestions = totalQuestions,
            correctAnswers = rightAnswers,
            wrongAnswers = wrongAnswers,
            attempts = attempts
        )
    }

    if (submissionSheetState){
        SubmissionSheet(
            onSheetDismissed = {
               submissionSheetState = false
            },
            answeredQuestions = rightAnswers+wrongAnswers,
            totalQuestions = totalQuestions,
            timeLeft = "00:00:${currentTime/1000L}",
            onSubmitClicked = { rating, comment->

                if (FirebaseAuth.getInstance().currentUser != null){
                    playViewModel.saveRating(
                        stars = rating,
                        comment = comment,
                        onSuccess = {},
                        onFailure = {}
                    )

                    resultSheetState = true
                    submissionSheetState = false

                    playViewModel.saveOrUpdateScore(
                        category = category,
                        examNr = examNr,
                        percentage = ((rightAnswers*100)/totalQuestions).toLong(),
                        onSuccess = {},
                        onFailure = {}
                    )
                }
            }
        )
    }

    when(questions){
        is Result.Success ->{

            val examQuestions = (questions as Result.Success<List<Question>>).data

            if (examQuestions.isNotEmpty()){
                totalQuestions = examQuestions.count()
                PlayContent(
                    questions = examQuestions,
                    paddingValues = paddingValues,
                    onBackToHomeClicked = onBackToHomeClicked,
                    popBackStack = popBackStack,
                    navigateToSignIn = navigateToSignIn,
                    onScoreSaved = { rAnswers, wAnswers, cTime ->
                        Log.d("CorrectAnswers", "PlayScreen:$rAnswers")
                        rightAnswers = rAnswers
                        wrongAnswers = wAnswers
                        currentTime = cTime

                        if (FirebaseAuth.getInstance().currentUser != null){
                            submissionSheetState = true
                        }else{
                            resultSheetState = true
                        }

                    }
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

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun PlayContent(
     questions: List<Question>,
     paddingValues: PaddingValues,
     onScoreSaved: (Int,Int,Long) -> Unit,
     onBackToHomeClicked: () -> Unit,
     navigateToSignIn: () -> Unit,
     popBackStack: () -> Unit,
){

    var correctSelectedCount by remember { mutableIntStateOf(0) }
    var wrongSelectedCount by remember { mutableIntStateOf(0) }
    var selectedOptions by remember { mutableStateOf<Map<Int, String>>(emptyMap()) }
    var isExamCompleted by remember { mutableStateOf(false) }
    var dialogState by remember {
        mutableStateOf(false)
    }
    var isScoreSaved by remember { mutableStateOf(false) }
    var initialTime by remember { mutableLongStateOf(0L) }
    var currentTime by remember { mutableLongStateOf(0L) }
    var isTimerRunning by remember { mutableStateOf(true) }
    var progress by remember { mutableFloatStateOf(0f) }



 
    HandleBackButtonPress {
        dialogState = true
    }
    
    if (dialogState){
       CustomDialog(
           title = R.string.examQuit,
           text = R.string.examQuitText ,
           onDismissDialog = {
               dialogState = false
           },
           confirmButton = {
               dialogState = false
               popBackStack()
           }
       )
    }

    fun startTimer(initialT: Long) {
        currentTime = initialT
        initialTime = initialT
        isTimerRunning = true
    }

    //The logic behind the timer
    LaunchedEffect(key1 = isTimerRunning, key2 = currentTime) {
        if (currentTime>0 && isTimerRunning) {
                delay(100L)
                currentTime -= 100L
                progress = 1f - currentTime / initialTime.toFloat()
        } else {
            isTimerRunning = false
        }
    }

    // The timer starts when the composable is first composed
    LaunchedEffect(Unit) {
        startTimer(10 * 10000L) // Start the timer with 10 seconds (10 * 1000 milliseconds)
    }

    if (!isTimerRunning){
        isExamCompleted = true
    }

    if (isExamCompleted && !isScoreSaved) {
//        if (FirebaseAuth.getInstance().currentUser != null) {
            onScoreSaved(correctSelectedCount, wrongSelectedCount, currentTime)
            isScoreSaved = true
//        }
    }

    Scaffold(
        topBar = {
         PlayTopBar(
             examCategory = "",
             timer = formatSecondsToTime(seconds = currentTime/1000L),
             popBackStack = {
                 popBackStack()
             }
         )
        },
        floatingActionButton = {
            FloatingActionButton(
                modifier = Modifier
                    .padding(4.dp)
                    .clip(CircleShape),
                onClick ={
                    isExamCompleted = true
                    isTimerRunning = false
                },
                containerColor = AppColors.AppBlue
            ) {
                Box(
                    modifier = Modifier
                ) {
                    Icon(
                        modifier = Modifier,
                        imageVector = Icons.Default.DoneAll,
                        contentDescription = ""
                    )
                }
            }
        }
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(it)
        ) {
                    LinearProgressIndicator(
                        modifier = Modifier.fillMaxWidth(),
                        progress = { progress }
                    )
//                if (isExamCompleted) {
//                    Row(
//                        modifier = Modifier.fillMaxWidth(),
//                        horizontalArrangement = Arrangement.SpaceBetween
//                    ) {
//                        TextButton(onClick = {
//                            popBackStack()
//                        }) {
//                            Text(text = "Back")
//                        }
//                        TextButton(onClick = {
//                            navigateToSignIn()
//                        }) {
//                            Text(text = "Check Performance")
//                        }
//                    }
//                }

                LazyColumn (
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 24.dp)
//                        .navigationBarsPadding()
//                        .padding(top = it.calculateTopPadding())
//                        .padding(bottom = it.calculateBottomPadding())
//                        .padding(start = it.calculateStartPadding(LayoutDirection.Ltr))
//                        .padding(end = it.calculateEndPadding(LayoutDirection.Ltr)),
            ) {
                itemsIndexed(items = questions) { index, item ->
                    QACard(
                        index = index,
                        question = item.question,
                        answer = item.answer,
                        selectedOption = selectedOptions[index],
                        onSelectOption = { option ->
                            selectedOptions += index to option
                            Log.d("Correct", "PlayContent:$option ")
                            Log.d("Correct", "PlayContent:${item.answer} ")

                            if (option == item.answer) {
                                Log.d("Correct", "PlayContent:$option ")
                                correctSelectedCount++ // Increment count if the selected option is correct
                            } else{
                                wrongSelectedCount++
                            }
                        },
                        isExamCompleted = isExamCompleted,
                        choices = item.choices,
                    )
                }
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