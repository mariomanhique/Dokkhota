package com.mariomanhique.dokkhota.presentation.screens.playScreen

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mariomanhique.dokkhota.data.repository.examsRepository.Questions
import com.mariomanhique.dokkhota.model.Question
import com.mariomanhique.dokkhota.model.Result

@Composable
fun PlayScreen(
    paddingValues: PaddingValues,
    playViewModel: PlayViewModel = hiltViewModel()
){

    val questions by playViewModel.exams.collectAsStateWithLifecycle()

    when(questions){
        is Result.Success ->{
            Log.d("Questions", "Play: ${(questions as Result.Success<List<Question>>).data}")
            PlayContent(
                questions = (questions as Result.Success<List<Question>>).data,
                paddingValues = paddingValues,
            )
        }
        else -> {

        }
    }
}

@Composable
fun PlayContent(
     questions: List<Question>,
     paddingValues: PaddingValues
){

 LazyColumn(
     modifier = Modifier
         .padding(horizontal = 24.dp)
         .navigationBarsPadding()
         .padding(top = paddingValues.calculateTopPadding())
         .padding(bottom = paddingValues.calculateBottomPadding())
         .padding(start = paddingValues.calculateStartPadding(LayoutDirection.Ltr))
         .padding(end = paddingValues.calculateEndPadding(LayoutDirection.Ltr)),
     contentPadding = paddingValues
 ) {
     items(items = questions){
         QACard(
             choices = it.choices
         )
     }
 }

}


@Composable
fun QACard(
    choices: List<String>,

){

    var selectedOption by remember { mutableStateOf("") }
    Column(Modifier.selectableGroup()) {
        
        SectionTitle(text = "What what what what? Ham?")

        choices.forEach {choice->
            ChooserRow(
                text = choice,
                selected = choice == selectedOption,
                onClick = {
                    selectedOption = choice
                }
            )
        }



    }
}

@Composable
private fun SectionTitle(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.titleMedium,
        modifier = Modifier.padding(top = 16.dp, bottom = 8.dp),
    )
}

@Composable
fun ChooserRow(
    text: String,
    selected: Boolean,
    onClick: () -> Unit,
) {
    Row(
        Modifier
            .fillMaxWidth()
            .selectable(
                selected = selected,
                role = Role.RadioButton,
                onClick = onClick,
            )
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        RadioButton(
            selected = selected,
            onClick = null,
        )
        Spacer(Modifier.width(8.dp))
        Text(text)
    }
}
