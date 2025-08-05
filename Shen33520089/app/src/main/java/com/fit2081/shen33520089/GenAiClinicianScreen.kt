package com.fit2081.shen33520089

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.fit2081.shen33520089.data.PatientViewModel

/**
 * GenAiClinicianSection is a composable function that displays a section for clinicians
 */
@Composable
fun GenAiClinicianSection(
    averageHeifaScoreMale: Float?, averageHeifaScoreFemale: Float?, viewModel: PatientViewModel, genAiViewModel: GenAIViewModel = viewModel()
) {
    val uiState by genAiViewModel.uiState.collectAsState()
    val scrollState = rememberScrollState()


    val maxDiscretionaryScore by viewModel.getMaxDiscretionaryScore().collectAsState(initial = null)
    val minDiscretionaryScore by viewModel.getMinDiscretionaryScore().collectAsState(initial = null)
    val avgDiscretionaryScore by viewModel.getAvgDiscretionaryScore().collectAsState(initial = null)
    val maxVegetablesScore by viewModel.getMaxVegetablesScore().collectAsState(initial = null)
    val minVegetablesScore by viewModel.getMinVegetablesScore().collectAsState(initial = null)
    val avgVegetablesScore by viewModel.getAvgVegetablesScore().collectAsState(initial = null)
    val maxFruitScore by viewModel.getMaxFruitScore().collectAsState(initial = null)
    val minFruitScore by viewModel.getMinFruitScore().collectAsState(initial = null)
    val avgFruitScore by viewModel.getAvgFruitScore().collectAsState(initial = null)
    val maxGrainsScore by viewModel.getMaxGrainsScore().collectAsState(initial = null)
    val minGrainsScore by viewModel.getMinGrainsScore().collectAsState(initial = null)
    val avgGrainsScore by viewModel.getAvgGrainsScore().collectAsState(initial = null)
    val maxWholeGrainsScore by viewModel.getMaxWholeGrainsScore().collectAsState(initial = null)
    val minWholeGrainsScore by viewModel.getMinWholeGrainsScore().collectAsState(initial = null)
    val avgWholeGrainsScore by viewModel.getAvgWholeGrainsScore().collectAsState(initial = null)
    val maxMeatScore by viewModel.getMaxMeatScore().collectAsState(initial = null)
    val minMeatScore by viewModel.getMinMeatScore().collectAsState(initial = null)
    val avgMeatScore by viewModel.getAvgMeatScore().collectAsState(initial = null)
    val maxDairyScore by viewModel.getMaxDairyScore().collectAsState(initial = null)
    val minDairyScore by viewModel.getMinDairyScore().collectAsState(initial = null)
    val avgDairyScore by viewModel.getAvgDairyScore().collectAsState(initial = null)
    val maxSodiumScore by viewModel.getMaxSodiumScore().collectAsState(initial = null)
    val minSodiumScore by viewModel.getMinSodiumScore().collectAsState(initial = null)
    val avgSodiumScore by viewModel.getAvgSodiumScore().collectAsState(initial = null)
    val maxAlcoholScore by viewModel.getMaxAlcoholScore().collectAsState(initial = null)
    val minAlcoholScore by viewModel.getMinAlcoholScore().collectAsState(initial = null)
    val avgAlcoholScore by viewModel.getAvgAlcoholScore().collectAsState(initial = null)
    val maxWaterScore by viewModel.getMaxWaterScore().collectAsState(initial = null)
    val minWaterScore by viewModel.getMinWaterScore().collectAsState(initial = null)
    val avgWaterScore by viewModel.getAvgWaterScore().collectAsState(initial = null)
    val maxSugarScore by viewModel.getMaxSugarScore().collectAsState(initial = null)
    val minSugarScore by viewModel.getMinSugarScore().collectAsState(initial = null)
    val avgSugarScore by viewModel.getAvgSugarScore().collectAsState(initial = null)


    Column(modifier = Modifier.verticalScroll(scrollState)) {
        Button(
            onClick = {
                val prompt = buildClinicianPrompt(
                    averageHeifaScoreMale, averageHeifaScoreFemale,
                    maxDiscretionaryScore, minDiscretionaryScore, avgDiscretionaryScore,
                    maxVegetablesScore, minVegetablesScore, avgVegetablesScore,
                    maxFruitScore, minFruitScore, avgFruitScore,
                    maxGrainsScore, minGrainsScore, avgGrainsScore,
                    maxWholeGrainsScore, minWholeGrainsScore, avgWholeGrainsScore,
                    maxMeatScore, minMeatScore, avgMeatScore,
                    maxDairyScore, minDairyScore, avgDairyScore,
                    maxSodiumScore, minSodiumScore, avgSodiumScore,
                    maxAlcoholScore, minAlcoholScore, avgAlcoholScore,
                    maxWaterScore, minWaterScore, avgWaterScore,
                    maxSugarScore, minSugarScore, avgSugarScore
                )
                genAiViewModel.sendPrompt(prompt)
            },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6200EE)),
            modifier = Modifier.padding(bottom = 8.dp)
        ) {
            Text(text = "Find Data Patterns 🧐")
        }

        when (uiState) {
            is UiState.Loading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally).padding(top = 16.dp))
            }

            is UiState.Error -> {
                PatternInfoBox(
                    color = Color(0xFFFFCDD2),
                    text = (uiState as UiState.Error).errorMessage
                )
            }

            is UiState.Success -> {
                val paragraphs = (uiState as UiState.Success).outputText.split("\n\n")
                DisplayParagraphs(paragraphs)
            }

            else -> {
                DisplayParagraphs(List(3) { "Pattern insights will appear here once generated." })
            }
        }
    }
}

/**
 * DisplayParagraphs is a composable function that displays a list of paragraphs
 */
@Composable
fun DisplayParagraphs(paragraphs: List<String>) {
    val backgroundColors = listOf(
        Color(0xFFE3F2FD),
        Color(0xFFE8F5E9),
        Color(0xFFFFF3E0)
    )

    paragraphs.forEachIndexed { index, paragraph ->
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .defaultMinSize(minHeight = 150.dp) // 👈 Increase box height
                .background(
                    color = backgroundColors.getOrElse(index) { Color.LightGray },
                )
                .padding(16.dp)
        ) {
            Text(
                text = paragraph,
                color = Color.Black,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}


/**
 * PatternInfoBox is a composable function that displays a box with a colored background and text
 */
@Composable
private fun PatternInfoBox(
    color: Color,
    text: String
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp)
            .background(color)
            .padding(12.dp)
    ) {
        Text(
            text = text,
            fontSize = 14.sp
        )
    }
}


/**
 * buildClinicianPrompt is a function that constructs a prompt for the clinician
 */
fun buildClinicianPrompt(
    averageHeifaScoreMale: Float?, averageHeifaScoreFemale: Float?,
    maxDiscretionaryScore: Float?, minDiscretionaryScore: Float?, avgDiscretionaryScore: Float?,
    maxVegetablesScore: Float?, minVegetablesScore: Float?, avgVegetablesScore: Float?,
    maxFruitScore: Float?, minFruitScore: Float?, avgFruitScore: Float?,
    maxGrainsScore: Float?, minGrainsScore: Float?, avgGrainsScore: Float?,
    maxWholeGrainsScore: Float?, minWholeGrainsScore: Float?, avgWholeGrainsScore: Float?,
    maxMeatScore: Float?, minMeatScore: Float?, avgMeatScore: Float?,
    maxDairyScore: Float?, minDairyScore: Float?, avgDairyScore: Float?,
    maxSodiumScore: Float?, minSodiumScore: Float?, avgSodiumScore: Float?,
    maxAlcoholScore: Float?, minAlcoholScore: Float?, avgAlcoholScore: Float?,
    maxWaterScore: Float?, minWaterScore: Float?, avgWaterScore: Float?,
    maxSugarScore: Float?, minSugarScore: Float?, avgSugarScore: Float?
): String {
    return """
        Analyze the following nutritional pattern data from a HEIFA-based assessment system.

        - Gender-based HEIFA averages:
          • Males: ${"%.1f".format(averageHeifaScoreMale ?: 0f)}
          • Females: ${"%.1f".format(averageHeifaScoreFemale ?: 0f)}

        - Category scores:
          Discretionary: max=${maxDiscretionaryScore}, min=${minDiscretionaryScore}, avg=${avgDiscretionaryScore}
          Vegetables: max=${maxVegetablesScore}, min=${minVegetablesScore}, avg=${avgVegetablesScore}
          Fruits: max=${maxFruitScore}, min=${minFruitScore}, avg=${avgFruitScore}
          Grains: max=${maxGrainsScore}, min=${minGrainsScore}, avg=${avgGrainsScore}
          Whole Grains: max=${maxWholeGrainsScore}, min=${minWholeGrainsScore}, avg=${avgWholeGrainsScore}
          Meat: max=${maxMeatScore}, min=${minMeatScore}, avg=${avgMeatScore}
          Dairy: max=${maxDairyScore}, min=${minDairyScore}, avg=${avgDairyScore}
          Sodium: max=${maxSodiumScore}, min=${minSodiumScore}, avg=${avgSodiumScore}
          Alcohol: max=${maxAlcoholScore}, min=${minAlcoholScore}, avg=${avgAlcoholScore}
          Water: max=${maxWaterScore}, min=${minWaterScore}, avg=${avgWaterScore}
          Sugar: max=${maxSugarScore}, min=${minSugarScore}, avg=${avgSugarScore}

        Based on this data:
        1. Identify which categories have the highest and lowest scores and explain what that implies about diet quality.
        2. Comment on overall trends across average scores — which areas consistently need improvement?
        3. Discuss gender differences in HEIFA scores and possible dietary behavior trends.

        Provide a short, professional analysis in 3 paragraphs, each paragraphs 40 words will do, please dont leave empty rows between paragraphs.
    """.trimIndent()
}
