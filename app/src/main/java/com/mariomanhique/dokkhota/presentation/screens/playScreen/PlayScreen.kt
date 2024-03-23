package com.mariomanhique.dokkhota.presentation.screens.playScreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.unit.dp
import com.mariomanhique.dokkhota.data.repository.examsRepository.Questions
import com.mariomanhique.dokkhota.model.Question

@Composable
fun PlayScreen(
    paddingValues: PaddingValues,
){
    PlayContent(
        paddingValues = paddingValues
    )
}

@Composable
fun PlayContent(
     questions: List<Question> = listOf(Question(), Question()),
     paddingValues: PaddingValues
){

 LazyColumn(
     contentPadding = paddingValues
 ) {
     items(items = questions){
         QACard()
     }
 }

}


@Composable
fun QACard(
    choices: List<String> = listOf("Chocie_1", "Choice_2", "Choice_3", "Choice_4"),

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
