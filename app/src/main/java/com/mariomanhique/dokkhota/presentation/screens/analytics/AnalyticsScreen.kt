package com.mariomanhique.dokkhota.presentation.screens.analytics

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFromBaseline
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.firebase.auth.FirebaseAuth
import com.mariomanhique.dokkhota.R
import com.mariomanhique.dokkhota.model.Result
import com.mariomanhique.dokkhota.model.Score
import com.mariomanhique.dokkhota.presentation.components.CustomButton
import com.mariomanhique.dokkhota.presentation.components.EmptyPage

@Composable
fun AnalyticsScreen(
    paddingValues: PaddingValues,
    navigateToSignIn: () -> Unit,
    analyticsViewModel: AnalyticsViewModel = hiltViewModel()
){



    if (FirebaseAuth.getInstance().currentUser != null){
        val scores by analyticsViewModel.scores!!.collectAsStateWithLifecycle()
        when(scores){
            is Result.Success -> {
                AnalyticsContent(
                    paddingValues = paddingValues,
                    scores = (scores as Result.Success<List<Score>>).data

                )
            }
            else -> {

            }
        }
    }else{

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally

        ) {
            EmptyPage(
                title = "No account",
                subtitle = "You don't yet have an account, SignUp/SignIn to see your performance!"

            )

            CustomButton(
                modifier = Modifier.padding(horizontal = 40.dp),
                buttonText = R.string.sign_In,
                buttonEnabled = true,
                onClicked = navigateToSignIn
                )
        }

    }




}

@Composable
fun AnalyticsContent(
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues,
    scores: List<Score>
){
    Column(
        modifier = modifier
            .fillMaxSize(),
//            .padding(paddingValues),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            modifier = Modifier.paddingFromBaseline(top = 30.dp, bottom = 30.dp),
            text = "Individual Results",
            style = MaterialTheme.typography.headlineLarge.copy(
                fontSize = 30.sp,
//                fontWeight = FontWeight.Bold
            )
            )

        LazyColumn {
            items(items = scores){
                AnalyticsCard(
                    percentage = it.percentage,
                    examNr = it.examNr
                )
            }
        }

    }
}

@Composable
fun AnalyticsCard(
    modifier: Modifier = Modifier,
    percentage: Float,
    examNr: String

){
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(4.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {

        Text(
            modifier = Modifier
                .weight(0.7F),
            text = "BCS-$examNr")

        LinearProgressIndicator(
            modifier = Modifier
                .fillMaxWidth()
                .weight(2.6F)
                .heightIn(min = 10.dp, max = 15.dp),
            strokeCap = ProgressIndicatorDefaults.LinearStrokeCap,
            progress = {
                percentage
        })

        Text(
            modifier = Modifier
                .weight(0.5F),
            text = "${percentage*100}%"
        )
    }

}