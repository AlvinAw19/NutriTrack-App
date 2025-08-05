package com.fit2081.shen33520089

import android.content.Intent
import android.os.Bundle
import androidx.compose.material3.OutlinedTextField
import android.widget.Toast
import androidx.compose.material3.Button
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.navigation.compose.composable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.sp
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextButton
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import coil3.compose.AsyncImage
import com.fit2081.shen33520089.data.FoodIntakeViewModel
import com.fit2081.shen33520089.data.NutriCoachViewModel
import com.fit2081.shen33520089.data.PatientViewModel
import com.fit2081.shen33520089.data.network.FruitResponse
import com.fit2081.shen33520089.data.network.Nutritions
import com.fit2081.shen33520089.data.repository.FruitsRepository
import com.fit2081.shen33520089.ui.theme.Shen33520089Theme
import kotlinx.coroutines.launch

/**
 * Main activity that serves as the entry point for the app
 * It sets up the navigation and view models for the app.
 */
class HomeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val patientViewModel: PatientViewModel = ViewModelProvider(
            this,
            PatientViewModel.patientViewModelFactory(this))[PatientViewModel::class.java]
        val nutriCoachViewModel: NutriCoachViewModel = ViewModelProvider(
            this,
            NutriCoachViewModel.nutriCoachViewModelFactory(this))[NutriCoachViewModel::class.java]

        val foodIntakeViewModel = ViewModelProvider(
            this,
            FoodIntakeViewModel.FoodIntakeViewModelFactory(this))[FoodIntakeViewModel::class.java]
        setContent {
            Shen33520089Theme {
                // Navigation controller for handling screen transitions
                val navController: NavHostController = rememberNavController()
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = {MyBottomAppBar(navController)}
                ) { innerPadding ->
                    // Navigation host that manages screen content
                    MyNavHost(
                        innerPadding = innerPadding,
                        navController = navController,
                        patientViewModel = patientViewModel,
                        nutriCoachViewModel = nutriCoachViewModel,
                        foodIntakeViewModel = foodIntakeViewModel
                    )
                }
            }
        }
    }
}


/**
 * Composable for the bottom navigation bar
 *
 * @param navController Navigation controller for handling item clicks
 */
@Composable
fun MyBottomAppBar(navController: NavHostController) {
    // Get current route to highlight active item
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    // Navigation items with their labels and icons
    val items = listOf("Home", "Insights", "NutriCoach", "Health", "Settings")
    var selectedItem by remember { mutableIntStateOf(0) }

    NavigationBar {
        items.forEachIndexed { index, item ->
            NavigationBarItem(
                icon = {
                    when (item) {
                        "Home" -> Icon(Icons.Default.Home, contentDescription = "Home")
                        "Insights" -> Icon(Icons.Default.Face, contentDescription = "Insights")
                        "NutriCoach" -> Icon(Icons.Default.Person, contentDescription = "NutriCoach")
                        "Health" -> Icon(Icons.Default.Info, contentDescription = "Health")
                        "Settings" -> Icon(Icons.Default.Settings, contentDescription = "Settings")
                    }
                },
                label = { Text(item)},
                selected = currentRoute == item,
                onClick = {
                    selectedItem = index
                    // Only navigate if not already on this screen
                    if (currentRoute != item) {
                        navController.navigate(item) {
                            // Clear back stack to avoid multiple instances
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                }
            )
        }
    }
}

/**
 * Composable function that sets up the navigation host
 */
@Composable
fun MyNavHost(innerPadding: PaddingValues, navController: NavHostController, patientViewModel: PatientViewModel, nutriCoachViewModel: NutriCoachViewModel, foodIntakeViewModel: FoodIntakeViewModel) {
    val context = LocalContext.current
    NavHost(
        navController = navController,
        startDestination = "Home", // Default starting screen
        modifier = Modifier.padding(innerPadding)
    ) {
        // Define all composable destinations
        composable("cliniciandashboard") { ClinicianDashboardScreen(navController, patientViewModel) }
        composable("Clinician") { ClinicianLoginScreen(navController) }
        composable ("Health"){HealthScreen(patientViewModel)}
        composable("Home") { HomeScreen(navController, patientViewModel) }
        composable("Insights") { InsightsScreen(navController, patientViewModel) }
        composable("NutriCoach") { NutriCoachScreen(nutriCoachViewModel, patientViewModel, foodIntakeViewModel) }
        composable("Settings") { SettingScreen(onLogout = { AuthManager.logout(context)
            context.startActivity(Intent(context, LogInActivity::class.java)) },
            onClinicianLogin = { navController.navigate("clinician") }, patientViewModel
        ) }
    }
}

/**
 * Composable function for the Health screen
 * Displays HEIFA score comparison and individual nutrition scores
 *
 * @param viewModel ViewModel for managing patient data
 */
@Composable
fun HealthScreen(viewModel: PatientViewModel) {
    val avgDiscretionaryScore by viewModel.getAvgDiscretionaryScore().collectAsState(initial = null)
    val avgVegetablesScore by viewModel.getAvgVegetablesScore().collectAsState(initial = null)
    val avgFruitScore by viewModel.getAvgFruitScore().collectAsState(initial = null)
    val avgGrainsScore by viewModel.getAvgGrainsScore().collectAsState(initial = null)
    val avgWholeGrainsScore by viewModel.getAvgWholeGrainsScore().collectAsState(initial = null)
    val avgMeatScore by viewModel.getAvgMeatScore().collectAsState(initial = null)
    val avgDairyScore by viewModel.getAvgDairyScore().collectAsState(initial = null)
    val avgSodiumScore by viewModel.getAvgSodiumScore().collectAsState(initial = null)
    val avgAlcoholScore by viewModel.getAvgAlcoholScore().collectAsState(initial = null)
    val avgWaterScore by viewModel.getAvgWaterScore().collectAsState(initial = null)
    val avgSugarScore by viewModel.getAvgSugarScore().collectAsState(initial = null)
    val userId = AuthManager.getUserId()
    val user by viewModel.getPatientById(userId.toString()).collectAsState(initial = null)
    val averageHeifaScore by if (user?.gender == "Male") {
        viewModel.getAverageHeifaScoreMale().collectAsState(initial = null)
    } else {
        viewModel.getAverageHeifaScoreFemale().collectAsState(initial = null)
    }
    val heifaScore = user?.heifaTotalScore?: 0f
    val discretionaryScore = user?.discretionaryScore ?: 0f
    val vegetablesScore = user?.vegetablesScore ?: 0f
    val fruitScore = user?.fruitScore ?: 0f
    val grainsScore = user?.grainsScore ?: 0f
    val wholeGrainsScore = user?.wholeGrainsScore ?: 0f
    val meatScore = user?.meatScore ?: 0f
    val dairyScore = user?.dairyScore ?: 0f
    val sodiumScore = user?.sodiumScore ?: 0f
    val alcoholScore = user?.alcoholScore ?: 0f
    val waterScore = user?.waterScore ?: 0f
    val sugarScore = user?.sugarScore ?: 0f

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "HEIFA Score Comparison",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(24.dp))

        if (averageHeifaScore != null) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(270.dp)
                    .background(Color(0xFFEDE7F6), RoundedCornerShape(16.dp))
                    .padding(24.dp),
                contentAlignment = Alignment.BottomCenter
            ) {
                GroupedVerticalBarChart(
                    heifaScore = heifaScore.toFloat(),
                    averageHeifaScore = averageHeifaScore!!.toFloat(),
                    maxScore = 100,
                    barWidth = 60.dp,
                    barHeight = 150.dp
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            val diff = heifaScore - averageHeifaScore!!
            val percentage = if (averageHeifaScore != 0f) String.format("%.2f", (diff / averageHeifaScore!!) * 100) else "0.00"

            val text = when {
                diff > 0 -> "Your average is $percentage% higher than the average user."
                diff < 0 -> "Your average is ${percentage.trimStart('-')}% lower than the average user."
                else -> "Your average matches the overall average."
            }


            Text(text = text, fontSize = 14.sp, fontStyle = FontStyle.Italic)
        } else {
            CircularProgressIndicator()
        }
        Spacer(modifier = Modifier.height(32.dp))

        Text("Your Scores vs. Average Users", fontSize = 18.sp, fontWeight = FontWeight.SemiBold)

        Spacer(modifier = Modifier.height(16.dp))

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            if (avgDiscretionaryScore != null)
                item {
                    CategoryBarCard("Discretionary", discretionaryScore, avgDiscretionaryScore!!)
                }
            if (avgVegetablesScore != null)
                item {
                    CategoryBarCard("Vegetables", vegetablesScore, avgVegetablesScore!!)
                }
            if (avgFruitScore != null)
                item {
                    CategoryBarCard("Fruits", fruitScore, avgFruitScore!!)
                }
            if (avgGrainsScore != null)
                item {
                    CategoryBarCard("Grains", grainsScore, avgGrainsScore!!)
                }
            if (avgWholeGrainsScore != null)
                item {
                    CategoryBarCard("Whole Grains", wholeGrainsScore, avgWholeGrainsScore!!)
                }
            if (avgMeatScore != null)
                item {
                    CategoryBarCard("Meat", meatScore, avgMeatScore!!)
                }
            if (avgDairyScore != null)
                item {
                    CategoryBarCard("Dairy", dairyScore, avgDairyScore!!)
                }
            if (avgSodiumScore != null)
                item {
                    CategoryBarCard("Sodium", sodiumScore, avgSodiumScore!!)
                }
            if (avgAlcoholScore != null)
                item {
                    CategoryBarCard("Alcohol", alcoholScore, avgAlcoholScore!!)
                }
            if (avgWaterScore != null)
                item {
                    CategoryBarCard("Water", waterScore, avgWaterScore!!)
                }
            if (avgSugarScore != null)
                item {
                    CategoryBarCard("Sugar", sugarScore, avgSugarScore!!)
                }
        }

    }
}

/**
 * Composable function that displays a card for each nutrition category
 */
@Composable
fun CategoryBarCard(category: String, userScore: Float, avgScore: Float) {
    val diff = userScore - avgScore
    val percentage = if (avgScore != 0f) String.format("%.2f", (diff / avgScore) * 100) else "0.00"
    val text = when {
        diff > 0 -> "$percentage% higher than average user."
        diff < 0 -> "${percentage.trimStart('-')}% lower than the average user."
        else -> "Matches the overall average."
    }

    Card(
        modifier = Modifier
            .width(160.dp)
            .height(300.dp)
            .padding(4.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFD1C4E9))

    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(category, style = MaterialTheme.typography.labelLarge)
            Spacer(Modifier.height(8.dp))

            // Set fixed chart height to avoid stretching
            Box(modifier = Modifier.height(180.dp)) {
                GroupedVerticalBarChart(
                    heifaScore = userScore,
                    averageHeifaScore = avgScore,
                    maxScore = 10,
                    barWidth = 36.dp,
                    barHeight = 120.dp
                )
            }

            Text(
                text = text,
                fontSize = 12.sp,
                fontStyle = FontStyle.Italic,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 4.dp)
            )
        }
    }
}


/**
 * Composable function that displays a grouped vertical bar chart
 */
@Composable
fun GroupedVerticalBarChart(
    heifaScore: Float,
    averageHeifaScore: Float,
    maxScore: Int,
    barWidth: Dp,
    barHeight: Dp
) {
    val groupSpacing = 8.dp  // Smaller spacing between groups

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .wrapContentHeight()
            .padding(horizontal = groupSpacing)
    ) {
        // Bars
        Row(
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier
                .height(barHeight)  // Full height given to bars
        ) {
            Box(
                modifier = Modifier
                    .width(barWidth)
                    .fillMaxHeight(heifaScore / maxScore)
                    .background(
                        Color(0xFF2196F3),
                        RoundedCornerShape(topStart = 6.dp, topEnd = 6.dp)
                    )
            )

            Box(
                modifier = Modifier
                    .width(barWidth)
                    .fillMaxHeight(averageHeifaScore / maxScore)
                    .background(
                        Color(0xFFE91E63),
                        RoundedCornerShape(topStart = 6.dp, topEnd = 6.dp)
                    )
            )
        }

        // Shrink the spacing and text size below
        Spacer(modifier = Modifier.height(6.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(28.dp)) {
            Text("You", fontSize = 10.sp)
            Text("Average", fontSize = 10.sp)
        }

        Spacer(modifier = Modifier.height(2.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(25.dp)) {
            Text("%.1f".format(heifaScore), fontSize = 10.sp, fontWeight = FontWeight.Bold)
            Text("%.1f".format(averageHeifaScore), fontSize = 10.sp, fontWeight = FontWeight.Bold)
        }
    }
}


/**
 * Composable function that displays the clinician dashboard screen
 */
@Composable
fun ClinicianDashboardScreen(navController: NavHostController, viewModel: PatientViewModel) {
    // Collect all the states
    val averageHeifaScoreMale by viewModel.getAverageHeifaScoreMale().collectAsState(initial = null)
    val averageHeifaScoreFemale by viewModel.getAverageHeifaScoreFemale().collectAsState(initial = null)

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .padding(bottom = 64.dp), // Leave space for bottom button
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = "Clinician Dashboard",
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                ),
                modifier = Modifier.padding(bottom = 24.dp)
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Average HEIFA (Male):",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Medium),
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.weight(1f)
                    )
                    Text(
                        text = averageHeifaScoreMale?.let { String.format("%.1f", it) } ?: "Loading...",
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.SemiBold
                    )
                }

                Spacer(modifier = Modifier.height(6.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Average HEIFA (Female):",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Medium),
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.weight(1f)
                    )
                    Text(
                        text = averageHeifaScoreFemale?.let { String.format("%.1f", it) } ?: "Loading...",
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }


            HorizontalDivider(
                color = Color.Gray,
                thickness = 1.dp,
                modifier = Modifier.padding(vertical = 8.dp)
            )

            GenAiClinicianSection(
                averageHeifaScoreMale = averageHeifaScoreMale,
                averageHeifaScoreFemale = averageHeifaScoreFemale,
                viewModel = viewModel
            )
        }

        // Bottom-aligned "Done" button
        Button(
            onClick = { navController.navigate("settings") },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6200EE)),
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
        ) {
            Text("Done")
        }
    }
}


/**
 * Composable function that displays the GenAI Clinician section
 */
@Composable
fun ClinicianLoginScreen(
    navController: NavHostController
) {
    var clinicianKey by remember { mutableStateOf("") }
    var showError by remember { mutableStateOf(false) }
    var clinicianKeyVisible by remember { mutableStateOf(false) }
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Header
        Text(
            text = "Clinician Login",
            style = MaterialTheme.typography.headlineLarge.copy(fontWeight = FontWeight.Bold),
            modifier = Modifier.padding(bottom = 32.dp)
        )

        // Clinician Key Label
        Text(
            text = "Clinician Key",
            style = MaterialTheme.typography.labelLarge,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            textAlign = TextAlign.Start
        )

        // Clinician Key Input with visibility toggle
        OutlinedTextField(
            value = clinicianKey,
            onValueChange = {
                clinicianKey = it
                showError = false
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = if (showError) 8.dp else 24.dp),
            placeholder = { Text("Enter your clinician key") },
            isError = showError,
            singleLine = true,
            visualTransformation = if (clinicianKeyVisible) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                val toggleText = if (clinicianKeyVisible) "Hide" else "Show"
                Text(
                    text = toggleText,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .clickable { clinicianKeyVisible = !clinicianKeyVisible }
                        .padding(end = 8.dp)
                )
            }
        )

        // Error Message
        if (showError) {
            Text(
                text = "Invalid Clinician Key",
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            )
        }

        // Login Button
        Button(
            onClick = {
                if (clinicianKey == "dollar-entry-apples") {
                    navController.navigate("cliniciandashboard")
                    Toast.makeText(context, "Login successful", Toast.LENGTH_LONG).show()
                } else {
                    showError = true
                }
            },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6200EE)),
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .height(48.dp)
        ) {
            Text("Clinician Login")
        }
    }
}


/**
 * Composable function that displays the home screen
 */
@Composable
fun HomeScreen(navController: NavHostController, viewModel: PatientViewModel) {
    val context = LocalContext.current
    val userId = AuthManager.getUserId()
    val user by viewModel.getPatientById(userId.toString()).collectAsState(initial = null)
    val heifaTotalScore = user?.heifaTotalScore?: 0f

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Greeting
        Text(
            text = "Hello,",
            fontSize = 18.sp,
            color = Color.Gray,
            modifier = Modifier.align(Alignment.Start)
        )
        Text(
            text = user?.name ?: "User",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(Alignment.Start)
        )
        Spacer(modifier = Modifier.height(4.dp))

        // Edit questionnaire button row
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                "You've already filled in your Food Intake Questionnaire, but you can change details here: ",
                fontSize = 14.sp,
                color = Color.Gray,
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(8.dp))

            Button(
                onClick = {
                    context.startActivity(Intent(context, QuestionnaireActivity::class.java))
                },
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6200EE)),
                contentPadding = PaddingValues(horizontal = 12.dp, vertical = 8.dp)
            ) {
                Icon(Icons.Default.Create, contentDescription = "Edit", modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.width(4.dp))
                Text("Edit", fontSize = 14.sp)
            }
        }
        Spacer(modifier = Modifier.height(16.dp))

        // Food plate image
        Image(
            painter = painterResource(id = R.drawable.balance_diet),
            contentDescription = "Food Plate",
            modifier = Modifier
                .size(240.dp)
                .fillMaxWidth()
                .height(180.dp)
                .clip(RoundedCornerShape(8.dp))
                .padding(vertical = 8.dp),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Score section
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("My Score", fontSize = 20.sp, fontWeight = FontWeight.Bold)

            // Button to view all scores
            TextButton(
                onClick = {
                    navController.navigate("insights") {
                        // Clear entire back stack up to start destination
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                    }
                },
                modifier = Modifier.padding(horizontal = 4.dp)
            ) {
                Text(
                    "See all scores",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Gray,
                )
                Icon(
                    imageVector = Icons.Default.PlayArrow,
                    contentDescription = "See all",
                    tint = Color.Gray,
                    modifier = Modifier.size(16.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Food Quality Score display
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Upward Trend",
                tint = Color.Gray
            )

            Spacer(modifier = Modifier.width(8.dp))

            Text("Your Food Quality Score", fontSize = 16.sp)

            Spacer(modifier = Modifier.weight(1f))

            Text(
                "${heifaTotalScore.toInt()}/100",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF008000)
            )
        }
        Spacer(modifier = Modifier.height(24.dp))

        // Explanation section
        Text("What is the Food Quality Score?",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(Alignment.Start))

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            "Your Food Quality Score provides a snapshot of how well your eating patterns align with established food guidelines, helping you identify both strengths and opportunities for improvement in your diet.\n" +
                    "This personalized measurement considers various food groups including vegetables, fruits, whole grains, and proteins to give you practical insights for making healthier food choices.",
            fontSize = 14.sp,
            color = Color.Gray,
            lineHeight = 20.sp
        )
    }
}


/**
 * Composable function that displays the insights screen
 */
@Composable
fun InsightsScreen(navController: NavHostController, viewModel: PatientViewModel) {
    val context = LocalContext.current
    val userId = AuthManager.getUserId()
    val user by viewModel.getPatientById(userId.toString()).collectAsState(initial = null)
    // User data states
    val heifaTotalScore = user?.heifaTotalScore?: 0f


    // Individual nutrition score states
    val discretionaryScore = user?.discretionaryScore?: 0f
    val vegetablesScore = user?.vegetablesScore?: 0f
    val fruitScore = user?.fruitScore?: 0f
    val grainsScore = user?.grainsScore?: 0f
    val wholeGrainsScore = user?.wholeGrainsScore?: 0f
    val meatScore = user?.meatScore?: 0f
    val dairyScore = user?.dairyScore?: 0f
    val sodiumScore = user?.sodiumScore?: 0f
    val alcoholScore = user?.alcoholScore?: 0f
    val waterScore = user?.waterScore?: 0f
    val sugarScore = user?.sugarScore?: 0f
    val saturatedFatScore = user?.saturatedFatScore?: 0f
    val unsaturatedFatScore = user?.unsaturatedFatScore?: 0f


    // Build shareable text with all scores
    val shareText = buildString {
        append("My Food Quality Score: ${heifaTotalScore}/100\n\n")
        append("Detailed Scores:\n")
        append("Discretionary: ${discretionaryScore}\n")
        append("Vegetables: ${vegetablesScore}\n")
        append("Fruit: ${fruitScore}\n")
        append("Grains & Cereals: ${grainsScore}\n")
        append("Whole Grains: ${wholeGrainsScore}\n")
        append("Meat & Alternatives: ${meatScore}\n")
        append("Dairy: ${dairyScore}\n")
        append("Water: ${waterScore}\n")
        append("Unsaturated Fats: ${unsaturatedFatScore}\n")
        append("Saturated Fats: ${saturatedFatScore}\n")
        append("Sodium: ${sodiumScore}\n")
        append("Sugar: ${sugarScore}\n")
        append("Alcohol: ${alcoholScore}\n")
        append("\nShared via NutriTrack App")
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        // Screen title
        Text(
            "Insights: Food Score",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Display all score categories
        FoodScoreCategory("Vegetables", vegetablesScore.toString(), maxScore = 10)
        FoodScoreCategory("Fruits", fruitScore.toString(), maxScore = 10)
        FoodScoreCategory("Grains & Cereals", grainsScore.toString(), maxScore = 5)
        FoodScoreCategory("Whole Grains", wholeGrainsScore.toString(), maxScore = 5)
        FoodScoreCategory("Meat & Alternatives", meatScore.toString(), maxScore = 10)
        FoodScoreCategory("Dairy", dairyScore.toString(), maxScore = 10)
        FoodScoreCategory("Water", waterScore.toString(), maxScore = 5)
        FoodScoreCategory("Unsaturated Fats", unsaturatedFatScore.toString(), maxScore = 5)
        FoodScoreCategory("Saturated Fats", saturatedFatScore.toString(), maxScore = 5)
        FoodScoreCategory("Sodium", sodiumScore.toString(), maxScore = 10)
        FoodScoreCategory("Sugar", sugarScore.toString(), maxScore = 10)
        FoodScoreCategory("Alcohol", alcoholScore.toString(), maxScore = 5)
        FoodScoreCategory("Discretionary Foods", discretionaryScore.toString(), maxScore = 10)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            LegendDot(Color(0xFF4CAF50), "Excellent")
            Spacer(modifier = Modifier.width(12.dp))
            LegendDot(Color(0xFFFFC107), "Good")
            Spacer(modifier = Modifier.width(12.dp))
            LegendDot(Color(0xFFF44336), "Needs Improvement")
        }
        Spacer(modifier = Modifier.height(4.dp))
        // Total score display
        Text("Total Food Quality Score", fontSize = 20.sp, fontWeight = FontWeight.Bold)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Progress bar showing total score
            LinearProgressIndicator(
                progress = { (heifaTotalScore) / 100f },
                modifier = Modifier
                    .weight(0.5f)
                    .padding(end = 8.dp)
                    .height(6.dp),
                color = when {
                    (heifaTotalScore) >= 100f -> Color(0xFF4CAF50) // Green
                    (heifaTotalScore) >= 50f -> Color(0xFFFFC107) // Amber
                    else -> Color(0xFFF44336) // Red
                },
                trackColor = Color.LightGray
            )

            // Score text
            Text(
                text = "${heifaTotalScore}/100",
                modifier = Modifier.width(60.dp),
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.End
            )
        }

        // Action buttons row
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // Share Button
            Button(
                onClick = {
                    val shareIntent = Intent(Intent.ACTION_SEND)
                    shareIntent.type = "text/plain"
                    shareIntent.putExtra(Intent.EXTRA_TEXT, shareText)
                    context.startActivity(Intent.createChooser(shareIntent, "Share your food score"))
                },
                modifier = Modifier
                    .weight(1f)
                    .height(48.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF6200EE)
                )
            ) {
                Icon(
                    imageVector = Icons.Default.Share,
                    contentDescription = "Share",
                    modifier = Modifier.padding(end = 8.dp)
                )
                Text("Share")
            }

            // Improve button (navigates to NutriCoach)
            Button(
                onClick = {
                    navController.navigate("nutriCoach") {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                    }
                },
                modifier = Modifier
                    .weight(1f)
                    .height(48.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF6200EE)
                )
            ) {
                Icon(
                    imageVector = Icons.Default.ThumbUp,
                    contentDescription = "Improve",
                    modifier = Modifier.padding(end = 8.dp)
                )
                Text("Improve")
            }
        }
    }
}

/**
 * Composable function that displays a single food score category
 */
@Composable
fun LegendDot(color: Color, label: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(
            modifier = Modifier
                .size(8.dp)
                .background(color = color)
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(text = label, fontSize = 12.sp)
    }
}

/**
 * Composable function that displays a single food score category
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NutriCoachScreen(nutriCoachViewModel: NutriCoachViewModel, patientViewModel: PatientViewModel, foodIntakeViewModel: FoodIntakeViewModel) {
    val repository = remember { FruitsRepository() }
    var fruitName by remember { mutableStateOf("") }
    var fruitData by remember {
        mutableStateOf(
            FruitResponse(
                name = "-",
                id = 0,
                family = "-",
                order = "-",
                genus = "-",
                nutritions = Nutritions(calories = 0.0, fat = 0.0, sugar = 0.0, carbohydrates = 0.0, protein = 0.0
                )
            )
        )
    }

    var isLoading by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    val coroutineScope = rememberCoroutineScope()
    val userId = AuthManager.getUserId()
    val user by patientViewModel.getPatientById(userId.toString()).collectAsState(initial = null)
    val heifaScore = user?.heifaTotalScore?: 0f
    val discretionaryScore = user?.discretionaryScore?: 0f
    val vegetablesScore = user?.vegetablesScore?: 0f
    val fruitScore = user?.fruitScore?: 0f
    val grainsScore = user?.grainsScore?: 0f
    val wholeGrainsScore = user?.wholeGrainsScore?: 0f
    val meatScore = user?.meatScore?: 0f
    val dairyScore = user?.dairyScore?: 0f
    val sodiumScore = user?.sodiumScore?: 0f
    val alcoholScore = user?.alcoholScore?: 0f
    val waterScore = user?.waterScore?: 0f
    val sugarScore = user?.sugarScore?: 0f
    val saturatedFatScore = user?.saturatedFatScore?: 0f
    val unsaturatedFatScore = user?.unsaturatedFatScore?: 0f
    val fruitServeSize = user?.fruitServeSize?: 0f
    val fruitVariationScore = user?.fruitVariationScore?: 0f

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        // Title
        Text(
            text = "NutriCoach",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 24.dp)
        )
        if (fruitServeSize > 0f && fruitVariationScore > 0f) {
            val imageUrl = "https://picsum.photos/600/300"
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .background(color = Color(0xFFEDE7F6), shape = MaterialTheme.shapes.medium)
                ,
                elevation = CardDefaults.cardElevation(4.dp),
                shape = RoundedCornerShape(8.dp)
            ) {
                AsyncImage(
                    model = imageUrl,
                    contentDescription = "Image",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .background(color = Color(0xFFEDE7F6), shape = MaterialTheme.shapes.medium)
                )
            }
        } else {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                OutlinedTextField(
                    value = fruitName,
                    onValueChange = { fruitName = it },
                    label = {
                        Text(
                            text = "Fruit Name",
                            fontSize = 12.sp
                        )
                    },
                    placeholder = {
                        Text(
                            text = "Enter fruit name",
                            fontSize = 12.sp
                        )
                    },
                    textStyle = LocalTextStyle.current.copy(fontSize = 12.sp),
                    modifier = Modifier
                        .weight(1f)
                        .height(60.dp)
                    ,
                    shape = RoundedCornerShape(56),
                    singleLine = true
                )

                Button(
                    onClick = {
                        if (fruitName.isBlank()) {
                            errorMessage = "Please enter a fruit name"
                            return@Button
                        }

                        coroutineScope.launch {
                            isLoading = true
                            errorMessage = null
                            try {
                                repository.getFruitInfo(fruitName)?.let { data ->
                                    fruitData = data
                                } ?: run {
                                    errorMessage = "Fruit not found"
                                }
                            } catch (e: Exception) {
                                errorMessage = "Error: ${e.localizedMessage}"
                            } finally {
                                isLoading = false
                            }
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6200EE)),
                    shape = RoundedCornerShape(40),
                    modifier = Modifier.height(40.dp)
                ) {
                    Icon(Icons.Default.Search, contentDescription = "Search", tint = Color.White)
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Details", color = Color.White)
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            errorMessage?.let { message ->
                Text(
                    text = message,
                    color = MaterialTheme.colorScheme.error,
                    fontSize = 16.sp
                )
            }

            if (isLoading) {
                CircularProgressIndicator()
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                NutritionItem(label = "family", value = fruitData.family)
                NutritionItem(label = "calories", value = "${fruitData.nutritions.calories}")
                NutritionItem(label = "fat", value = "${fruitData.nutritions.fat}")
                NutritionItem(label = "sugar", value = "${fruitData.nutritions.sugar}")
                NutritionItem(label = "carbohydrates", value = "${fruitData.nutritions.carbohydrates}")
                NutritionItem(label = "protein", value = "${fruitData.nutritions.protein}")
            }
        }

        GenAINutriCoachScreen(nutriCoachViewModel = nutriCoachViewModel, foodIntakeViewModel = foodIntakeViewModel,
            heifaScore = heifaScore,
            discretionaryScore = discretionaryScore,
            vegetablesScore = vegetablesScore,
            fruitScore = fruitScore,
            grainsScore = grainsScore,
            wholeGrainsScore = wholeGrainsScore,
            meatScore = meatScore,
            dairyScore = dairyScore,
            sodiumScore = sodiumScore,
            alcoholScore = alcoholScore,
            waterScore = waterScore,
            sugarScore = sugarScore,
            saturatedFatScore = saturatedFatScore,
            unsaturatedFatScore = unsaturatedFatScore
        )


    }
}

/**
 * Composable function that displays the nutrition item with label and value
 */
@Composable
fun NutritionItem(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFEDE7F6), shape = RoundedCornerShape(8.dp))
            .padding(horizontal = 16.dp, vertical = 10.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            fontWeight = FontWeight.Medium,
            fontSize = 12.sp  // smaller font size
        )
        Text(
            text = ":  $value",
            fontSize = 12.sp  // smaller font size
        )
    }
}

/**
 * Composable function that displays the settings screen
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingScreen(
    onLogout: () -> Unit,
    onClinicianLogin: () -> Unit,
    viewModel: PatientViewModel
) {
    val userId = AuthManager.getUserId()
    val user by viewModel.getPatientById(userId.toString()).collectAsState(initial = null)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        Text(
            "Settings",
            style = MaterialTheme.typography.headlineLarge.copy(fontWeight = FontWeight.Bold),
            color = MaterialTheme.colorScheme.primary
        )

        // Account Info Section
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                "Account Information",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold),
                color = MaterialTheme.colorScheme.primary
            )

            AccountInfoSection(
                userName = user?.name ?: "N/A",
                phoneNumber = user?.phoneNumber ?: "N/A",
                userId = user?.userId ?: "N/A"
            )
        }

        Divider(thickness = 1.dp, color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f))

        // Other Settings Section
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                "Other Settings",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold),
                color = MaterialTheme.colorScheme.primary
            )

            Button(
                onClick = onLogout,
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = MaterialTheme.colorScheme.error
                ),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.error)
            ) {
                Icon(Icons.Default.ExitToApp, contentDescription = "Logout", tint = MaterialTheme.colorScheme.error)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Logout")
            }

            Button(
                onClick = onClinicianLogin,
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = MaterialTheme.colorScheme.primary
                ),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary)
            ) {
                Icon(Icons.Default.AccountCircle, contentDescription = "Clinician Login")
                Spacer(modifier = Modifier.width(8.dp))
                Text("Clinician Login")
            }
        }
    }
}


/**
 * Composable function that displays the account information section
 */
@Composable
fun AccountInfoSection(userName: String, phoneNumber: String, userId: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 24.dp)
    ) {
        InfoRow("Name:", userName)
        InfoRow("Phone:", phoneNumber)
        InfoRow("User ID:", userId)
    }
}

/**
 * Composable function that displays a row with label and value
 */
@Composable
fun InfoRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.width(80.dp)
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.weight(1f)
        )
    }
}


/**
 * Composable for displaying a single food score category
 *
 * @param categoryName Name of the nutrition category
 * @param score String score value
 * @param maxScore Maximum possible score for this category
 */
@Composable
fun FoodScoreCategory(categoryName: String, score: String, maxScore: Int) {
    // Calculate progress value (0-1)
    val scoreValue = (score.toFloat() / maxScore.toFloat())

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 3.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Category name
        Text(
            text = categoryName,
            modifier = Modifier.width(120.dp),
            fontSize = 12.sp
        )

        // Progress bar showing score
        LinearProgressIndicator(
            progress = { scoreValue },
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 8.dp)
                .height(12.dp),
            color = when {
                scoreValue >= 0.8f -> Color(0xFF4CAF50) // Green
                scoreValue >= 0.5f -> Color(0xFFFFC107) // Amber
                else -> Color(0xFFF44336) // Red
            },
            trackColor = Color.LightGray
        )

        // Score text
        Text(
            text = "${(scoreValue * maxScore)}/$maxScore",
            modifier = Modifier.width(50.dp),
            textAlign = TextAlign.End,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

