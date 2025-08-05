package com.fit2081.shen33520089

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.fit2081.shen33520089.data.FoodIntakeViewModel
import com.fit2081.shen33520089.data.NutriCoach
import com.fit2081.shen33520089.data.NutriCoachViewModel

/**
 * GenAINutriCoachScreen is a composable function that displays the GenAI NutriCoach screen.
 */
@Composable
fun GenAINutriCoachScreen(
    nutriCoachViewModel: NutriCoachViewModel,
    foodIntakeViewModel: FoodIntakeViewModel,
    heifaScore: Float,
    discretionaryScore: Float,
    vegetablesScore: Float,
    fruitScore: Float,
    grainsScore: Float,
    wholeGrainsScore: Float,
    meatScore: Float,
    dairyScore: Float,
    sodiumScore: Float,
    alcoholScore: Float,
    waterScore: Float,
    sugarScore: Float,
    saturatedFatScore: Float,
    unsaturatedFatScore: Float,
    genAiViewModel: GenAIViewModel = viewModel()
) {
    val uiState by genAiViewModel.uiState.collectAsState()
    val userId = AuthManager.getUserId() ?: "default"
    val userMessages by nutriCoachViewModel.getMessagesByUserId(userId).collectAsState(initial = emptyList())
    var showMessagesDialog by remember { mutableStateOf(false) }
    val foodIntake by foodIntakeViewModel.getFoodIntakeById(userId.toString()).collectAsState(initial = null)
    val fruitsChecked = foodIntake?.fruitsChecked ?: false
    val vegetablesChecked = foodIntake?.vegetablesChecked ?: false
    val grainsChecked = foodIntake?.grainsChecked ?: false
    val redMeatChecked = foodIntake?.redMeatChecked ?: false
    val seafoodChecked = foodIntake?.seafoodChecked ?: false
    val poultryChecked = foodIntake?.poultryChecked ?: false
    val fishChecked = foodIntake?.fishChecked ?: false
    val eggsChecked = foodIntake?.eggsChecked ?: false
    val nutsSeedsChecked = foodIntake?.nutsSeedsChecked ?: false
    val selectedPersona = foodIntake?.selectedPersona
    val mealTime = foodIntake?.mealTime
    val sleepTime = foodIntake?.sleepTime
    val wakeTime = foodIntake?.wakeTime

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(bottom = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start

        ) {
            Button(
                onClick = {
                    val generatedPrompt = """
                        Generate a short encouraging message in around 25 words to help someone improve their health with a few emojis. Below are the scores and dietary intake:
                        
                        Heifa Score: $heifaScore
                        Discretionary Score: $discretionaryScore
                        Vegetables Score: $vegetablesScore
                        Fruit Score: $fruitScore
                        Grains Score: $grainsScore
                        Whole Grains Score: $wholeGrainsScore
                        Meat Score: $meatScore
                        Dairy Score: $dairyScore
                        Sodium Score: $sodiumScore
                        Alcohol Score: $alcoholScore
                        Water Score: $waterScore
                        Sugar Score: $sugarScore
                        Saturated Fat Score: $saturatedFatScore
                        Unsaturated Fat Score: $unsaturatedFatScore
                    
                        Food Intake:
                        - Fruits: $fruitsChecked
                        - Vegetables: $vegetablesChecked
                        - Grains: $grainsChecked
                        - Red Meat: $redMeatChecked
                        - Seafood: $seafoodChecked
                        - Poultry: $poultryChecked
                        - Fish: $fishChecked
                        - Eggs: $eggsChecked
                        - Nuts & Seeds: $nutsSeedsChecked
                    
                        Selected Persona: $selectedPersona
                        Meal Time: $mealTime
                        Sleep Time: $sleepTime
                        Wake Time: $wakeTime
                    """.trimIndent()

                    genAiViewModel.sendPrompt(generatedPrompt)
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6200EE)),
                modifier = Modifier.padding(top = 8.dp)
            ) {
                Text("💬 Motivational Message (AI)")
            }
        }

        if (uiState is UiState.Loading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
        } else {
            var result = ""
            var textColor = MaterialTheme.colorScheme.onSurface

            if (uiState is UiState.Error) {
                textColor = MaterialTheme.colorScheme.error
                result = (uiState as UiState.Error).errorMessage
            } else if (uiState is UiState.Success) {
                result = (uiState as UiState.Success).outputText
                val nutriCoach = NutriCoach(userId = userId, message = result)
                LaunchedEffect(result) {
                    nutriCoachViewModel.insert(nutriCoach)
                }
            }

            val scrollState = rememberScrollState()

            // Stylized message/result box
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp)
                    .heightIn(min = 100.dp)
                    .background(color = Color(0xFFEDE7F6), shape = MaterialTheme.shapes.medium)
                    .padding(8.dp)
            ) {
                Text(
                    text = if (result.isBlank()) "Your motivational message will appear here 😊" else result,
                    color = if (result.isBlank()) MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f) else textColor,
                    textAlign = if (result.isBlank()) TextAlign.Center else TextAlign.Start,
                    modifier = Modifier
                        .fillMaxWidth()
                        .verticalScroll(scrollState)
                )
            }
        }
        // "Show All Tips" button placed outside the message box
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            horizontalArrangement = Arrangement.End
        ) {
            Button(
                onClick = { showMessagesDialog = true },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6200EE)),
            ) {
                Text("📚 Show All Tips")
            }
        }
    }

    if (showMessagesDialog) {
        AlertDialog(
            onDismissRequest = { showMessagesDialog = false },
            title = { Text("All Motivational Tips") },
            text = {
                val scrollState = rememberScrollState()
                Column(
                    modifier = Modifier
                        .heightIn(min = 100.dp, max = 300.dp)
                        .verticalScroll(scrollState)
                ) {
                    userMessages.forEach { message ->
                        Text(
                            text = "• ${message.message}",
                            modifier = Modifier.padding(vertical = 4.dp)
                        )
                        HorizontalDivider()
                    }
                }
            },
            confirmButton = {
                Button(onClick = { showMessagesDialog = false },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6200EE)),
                ) {
                    Text("Close")
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )
    }
}
