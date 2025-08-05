package com.fit2081.shen33520089

import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.ViewModelProvider
import com.fit2081.shen33520089.data.FoodIntake
import com.fit2081.shen33520089.data.FoodIntakeViewModel
import com.fit2081.shen33520089.ui.theme.Shen33520089Theme
import kotlinx.coroutines.launch
import java.util.Calendar

/**
 * QuestionnaireActivity is the main activity for the food intake questionnaire.
 */
class QuestionnaireActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            Shen33520089Theme {
                val foodIntakeViewModel: FoodIntakeViewModel = ViewModelProvider(
                    this,
                    FoodIntakeViewModel.FoodIntakeViewModelFactory(this)
                )[FoodIntakeViewModel::class.java]

                val userID = AuthManager.getUserId()

                // Collect the FoodIntake data as nullable
                val foodIntake by foodIntakeViewModel.getFoodIntakeById(userID.toString()).collectAsState(initial = null)

                val fruitsChecked = remember { mutableStateOf(false) }
                val vegetablesChecked = remember { mutableStateOf(false) }
                val grainsChecked = remember { mutableStateOf(false) }
                val redMeatChecked = remember { mutableStateOf(false) }
                val seafoodChecked = remember { mutableStateOf(false) }
                val poultryChecked = remember { mutableStateOf(false) }
                val fishChecked = remember { mutableStateOf(false) }
                val eggsChecked = remember { mutableStateOf(false) }
                val nutsSeedsChecked = remember { mutableStateOf(false) }

                val selectedPersona = remember { mutableStateOf("") }
                val mealTime = remember { mutableStateOf("") }
                val sleepTime = remember { mutableStateOf("") }
                val wakeTime = remember { mutableStateOf("") }

                val scrollState = rememberScrollState()

                LaunchedEffect(foodIntake) {
                        fruitsChecked.value = foodIntake?.fruitsChecked == true
                        vegetablesChecked.value = foodIntake?.vegetablesChecked == true
                        grainsChecked.value = foodIntake?.grainsChecked == true
                        redMeatChecked.value = foodIntake?.redMeatChecked == true
                        seafoodChecked.value = foodIntake?.seafoodChecked == true
                        poultryChecked.value = foodIntake?.poultryChecked == true
                        fishChecked.value = foodIntake?.fishChecked == true
                        eggsChecked.value = foodIntake?.eggsChecked == true
                        nutsSeedsChecked.value = foodIntake?.nutsSeedsChecked == true

                        // Safe handling of nullable strings
                        selectedPersona.value = foodIntake?.selectedPersona.takeIf { !it.isNullOrEmpty() } ?: "Select your persona"
                        mealTime.value = foodIntake?.mealTime.takeIf { !it.isNullOrEmpty() && it != "00:00" } ?: "12:00"
                        sleepTime.value = foodIntake?.sleepTime.takeIf { !it.isNullOrEmpty() && it != "00:00" } ?: "22:00"
                        wakeTime.value = foodIntake?.wakeTime.takeIf { !it.isNullOrEmpty() && it != "00:00" } ?: "07:00"

                }

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = {
                        // Fixed save button at bottom of screen
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 56.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            SaveButton(
                                fruitsChecked = fruitsChecked,
                                vegetablesChecked = vegetablesChecked,
                                grainsChecked = grainsChecked,
                                redMeatChecked = redMeatChecked,
                                seafoodChecked = seafoodChecked,
                                poultryChecked = poultryChecked,
                                fishChecked = fishChecked,
                                eggsChecked = eggsChecked,
                                nutsSeedsChecked = nutsSeedsChecked,
                                selectedPersona = selectedPersona,
                                mealTime = mealTime,
                                sleepTime = sleepTime,
                                wakeTime = wakeTime,
                                userID = userID ?: "User ID not found",
                                foodIntakeViewModel = foodIntakeViewModel
                            )
                        }
                    }
                ) { innerPadding ->
                    Column(
                        modifier = Modifier
                            .padding(innerPadding)
                            .fillMaxWidth()
                            .verticalScroll(scrollState)
                    ) {
                        // Food intake questionnaire section
                        FoodIntakeQuestionnaire(
                            fruitsChecked = fruitsChecked,
                            vegetablesChecked = vegetablesChecked,
                            grainsChecked = grainsChecked,
                            redMeatChecked = redMeatChecked,
                            seafoodChecked = seafoodChecked,
                            poultryChecked = poultryChecked,
                            fishChecked = fishChecked,
                            eggsChecked = eggsChecked,
                            nutsSeedsChecked = nutsSeedsChecked
                        )

                        // Persona selection section
                        PersonaModalScreen()

                        // Persona dropdown label
                        Text("Which persona best fits you?",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)
                        )

                        // Persona dropdown selector
                        PersonaSelectionDropdown(selectedPersona)

                        Spacer(modifier = Modifier.height(16.dp))

                        // Timing section header
                        Text("Timings",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 16.dp)
                        )

                        // Time selection controls
                        TimeSelections(mealTime, sleepTime, wakeTime)
                    }
                }
            }
        }
    }
}

/**
 * Composable for the food intake questionnaire section
 *
 * @param fruitsChecked State for fruits checkbox
 * @param vegetablesChecked State for vegetables checkbox
 * @param grainsChecked State for grains checkbox
 * @param redMeatChecked State for red meat checkbox
 * @param seafoodChecked State for seafood checkbox
 * @param poultryChecked State for poultry checkbox
 * @param fishChecked State for fish checkbox
 * @param eggsChecked State for eggs checkbox
 * @param nutsSeedsChecked State for nuts/seeds checkbox
 */
@Composable
fun FoodIntakeQuestionnaire(
    fruitsChecked: MutableState<Boolean>,
    vegetablesChecked: MutableState<Boolean>,
    grainsChecked: MutableState<Boolean>,
    redMeatChecked: MutableState<Boolean>,
    seafoodChecked: MutableState<Boolean>,
    poultryChecked: MutableState<Boolean>,
    fishChecked: MutableState<Boolean>,
    eggsChecked: MutableState<Boolean>,
    nutsSeedsChecked: MutableState<Boolean>
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        // Title section
        Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
            Text(
                "Food Intake Questionnaire",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(16.dp))
        }

        Spacer(modifier = Modifier.height(2.dp))

        // Instruction text
        Text(
            "Tick all the food categories you can eat",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        // Checkbox grid
        Column(modifier = Modifier.padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.Start) {
            // First row of checkboxes
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(1f)) {
                    Checkbox(checked = fruitsChecked.value, onCheckedChange = { fruitsChecked.value = it },  colors = CheckboxDefaults.colors(
                        checkedColor = Color(0xFF6200EE),
                        uncheckedColor = Color(0xFF6200EE).copy(alpha = 0.6f)
                    ))
                    Text("Fruits", fontSize = 14.sp)
                }
                Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(1f)) {
                    Checkbox(checked = vegetablesChecked.value, onCheckedChange = { vegetablesChecked.value = it },  colors = CheckboxDefaults.colors(
                        checkedColor = Color(0xFF6200EE),
                        uncheckedColor = Color(0xFF6200EE).copy(alpha = 0.6f)
                    ))
                    Text("Vegetables", fontSize = 14.sp)
                }
                Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(1f)) {
                    Checkbox(checked = grainsChecked.value, onCheckedChange = { grainsChecked.value = it },  colors = CheckboxDefaults.colors(
                        checkedColor = Color(0xFF6200EE),
                        uncheckedColor = Color(0xFF6200EE).copy(alpha = 0.6f)
                    ))
                    Text("Grains", fontSize = 14.sp)
                }
            }

            // Second row of checkboxes
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(1f)) {
                    Checkbox(checked = redMeatChecked.value, onCheckedChange = { redMeatChecked.value = it },  colors = CheckboxDefaults.colors(
                        checkedColor = Color(0xFF6200EE),
                        uncheckedColor = Color(0xFF6200EE).copy(alpha = 0.6f)
                    ))
                    Text("Red Meat", fontSize = 14.sp)
                }
                Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(1f)) {
                    Checkbox(checked = seafoodChecked.value, onCheckedChange = { seafoodChecked.value = it },  colors = CheckboxDefaults.colors(
                        checkedColor = Color(0xFF6200EE),
                        uncheckedColor = Color(0xFF6200EE).copy(alpha = 0.6f)
                    ))
                    Text("Seafood", fontSize = 14.sp)
                }
                Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(1f)) {
                    Checkbox(checked = poultryChecked.value, onCheckedChange = { poultryChecked.value = it },  colors = CheckboxDefaults.colors(
                        checkedColor = Color(0xFF6200EE),
                        uncheckedColor = Color(0xFF6200EE).copy(alpha = 0.6f)
                    ))
                    Text("Poultry", fontSize = 14.sp)
                }
            }

            // Third row of checkboxes
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(1f)) {
                    Checkbox(checked = fishChecked.value, onCheckedChange = { fishChecked.value = it },  colors = CheckboxDefaults.colors(
                        checkedColor = Color(0xFF6200EE),
                        uncheckedColor = Color(0xFF6200EE).copy(alpha = 0.6f)
                    ))
                    Text("Fish", fontSize = 14.sp)
                }
                Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(1f)) {
                    Checkbox(checked = eggsChecked.value, onCheckedChange = { eggsChecked.value = it },  colors = CheckboxDefaults.colors(
                        checkedColor = Color(0xFF6200EE),
                        uncheckedColor = Color(0xFF6200EE).copy(alpha = 0.6f)
                    ))
                    Text("Eggs", fontSize = 14.sp)
                }
                Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(1f)) {
                    Checkbox(checked = nutsSeedsChecked.value, onCheckedChange = { nutsSeedsChecked.value = it },  colors = CheckboxDefaults.colors(
                        checkedColor = Color(0xFF6200EE),
                        uncheckedColor = Color(0xFF6200EE).copy(alpha = 0.6f)
                    ))
                    Text("Nuts/Seeds", fontSize = 14.sp)
                }
            }
        }
    }
}

/**
 * Composable for the persona modal selection screen
 */
@Composable
fun PersonaModalScreen() {
    // State for tracking which persona is selected (if any)
    var selectedPersona by remember { mutableStateOf<String?>("Please select an option") }

    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        Text(
            "Your Persona",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            """
            People can be broadly classified into 6 different types based on their eating preferences. Click on each button below to find out the different types, and select the type that best fits you!
            """.trimIndent(),
            fontSize = 12.sp,
            modifier = Modifier.padding(top = 8.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        // First row of buttons
        Row(modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly) {
            PersonaButton("Health Devotee") { selectedPersona = it }
            PersonaButton("Mindful Eater") { selectedPersona = it }
            PersonaButton("Wellness Striver") { selectedPersona = it }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Second row of buttons
        Row(modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly) {
            PersonaButton("Balance Seeker") { selectedPersona = it }
            PersonaButton("Health Procrastinator") { selectedPersona = it }
            PersonaButton("Food Carefree") { selectedPersona = it }
        }
    }

    // Show modal when a persona is selected
    if (selectedPersona != null) {
        when (selectedPersona) {
            "Health Devotee" -> PersonaModal("Health Devotee", "I’m passionate about healthy eating & health plays a big part in my life. I use social media to follow active lifestyle personalities or get new recipes/exercise ideas. I may even buy superfoods or follow a particular type of diet. I like to think I am super healthy.", R.drawable.persona_1) { selectedPersona = null }
            "Mindful Eater" -> PersonaModal("Mindful Eater", "I’m health-conscious and being healthy and eating healthy is important to me. Although health means different things to different people, I make conscious lifestyle decisions about eating based on what I believe healthy means. I look for new recipes and healthy eating information on social media.", R.drawable.persona_2) { selectedPersona = null }
            "Wellness Striver" -> PersonaModal("Wellness Striver", "I aspire to be healthy (but struggle sometimes). Healthy eating is hard work! I’ve tried to improve my diet, but always find things that make it difficult to stick with the changes. Sometimes I notice recipe ideas or healthy eating hacks, and if it seems easy enough, I’ll give it a go.", R.drawable.persona_3) { selectedPersona = null }
            "Balance Seeker" -> PersonaModal("Balance Seeker", "I try and live a balanced lifestyle, and I think that all foods are okay in moderation. I shouldn’t have to feel guilty about eating a piece of cake now and again. I get all sorts of inspiration from social media like finding out about new restaurants, fun recipes and sometimes healthy eating tips.", R.drawable.persona_4) { selectedPersona = null }
            "Health Procrastinator" -> PersonaModal("Health Procrastinator", "I’m contemplating healthy eating but it’s not a priority for me right now. I know the basics about what it means to be healthy, but it doesn’t seem relevant to me right now. I have taken a few steps to be healthier but I am not motivated to make it a high priority because I have too many other things going on in my life.", R.drawable.persona_5) { selectedPersona = null }
            "Food Carefree" -> PersonaModal("Food Carefree", "I’m not bothered about healthy eating. I don’t really see the point and I don’t think about it. I don’t really notice healthy eating tips or recipes and I don’t care what I eat.", R.drawable.persona_6) { selectedPersona = null }
        }
    }
}

/**
 * Composable for a single persona selection button
 */
@Composable
fun PersonaButton(name: String, onClick: (String) -> Unit) {
    Button(
        onClick = { onClick(name) },
        modifier = Modifier
            .height(40.dp)
            .padding(2.dp),
        shape = RoundedCornerShape(8.dp),
        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6200EE))
    ) {
        Text(
            name,
            fontSize = 12.sp,
        )
    }
}

/**
 * Composable for displaying persona details in a modal dialog
 */
@Composable
fun PersonaModal(persona: String, description: String, imageRes: Int, onDismiss: () -> Unit) {
    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = Modifier.padding(16.dp),
            shape = RoundedCornerShape(12.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = imageRes),
                    contentDescription = persona,
                    modifier = Modifier.size(160.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Persona title
                Text(
                    text = persona,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Persona description
                Text(
                    text = description,
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center,
                    lineHeight = 20.sp
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Dismiss button
                Button(
                    onClick = onDismiss,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF6200EE),
                        contentColor = Color.White
                    ),
                    modifier = Modifier.width(120.dp)
                ) {
                    Text("Dismiss", fontSize = 16.sp)
                }
            }
        }
    }
}
/**
 * Composable for the persona selection dropdown
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PersonaSelectionDropdown(selectedPersona: MutableState<String>) {
    // List of available persona options
    val personas = listOf(
        "Health Devotee", "Mindful Eater", "Wellness Striver",
        "Balance Seeker", "Health Procrastinator", "Food Carefree"
    )

    // State for dropdown menu expansion
    var expanded by remember { mutableStateOf(false) }
    val context = LocalContext.current

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp)
    ) {
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = it }
        ) {
            // Text field that shows the selected persona
            OutlinedTextField(
                modifier = Modifier
                    .menuAnchor()
                    .fillMaxWidth()
                    .height(54.dp),
                readOnly = true,
                value = if (false) "Select option" else selectedPersona.value,
                onValueChange = {},
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors(),
                singleLine = true,
                textStyle = LocalTextStyle.current.copy(
                    fontSize = 14.sp,
                )
            )

            // Dropdown menu with all persona options
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier.heightIn(max = 240.dp)
            ) {
                personas.forEach { persona ->
                    DropdownMenuItem(
                        modifier = Modifier.height(32.dp),
                        text = {
                            Text(
                                text = persona,
                                fontSize = 14.sp,
                            )
                        },
                        onClick = {
                            selectedPersona.value = persona
                            expanded = false
                            Toast.makeText(context, "$persona selected", Toast.LENGTH_LONG).show()
                        }
                    )
                }
            }
        }
    }
}

/**
 * Composable for the time selection section
 */
@Composable
fun TimeSelections(mealTime: MutableState<String>,
                   sleepTime: MutableState<String>,
                   wakeTime: MutableState<String>) {

    Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
        TimePickerRow("What time of day approx. do you normally eat your biggest meal?", mealTime)
        Spacer(modifier = Modifier.height(2.dp))
        TimePickerRow("What time of day approx. do you go to sleep at night?", sleepTime)
        Spacer(modifier = Modifier.height(2.dp))
        TimePickerRow("What time of day approx. do you wake up in the morning?", wakeTime)
    }
}

/**
 * Composable for a single time picker row
 */
@Composable
fun TimePickerRow(label: String, timeState: MutableState<String>) {
    // Get the current context
    val mContext = LocalContext.current
    // Get the calandar instance
    val mCalendar = Calendar.getInstance()

    val timePickerDialog = remember {
        TimePickerDialog(
            mContext,
            { _, selectedHour: Int, selectedMinute: Int ->
                // Format to always show 2 digits for hour and minute
                timeState.value = String.format("%02d:%02d", selectedHour, selectedMinute)
            },
            // Parse current time or use current hour/minute if parsing fails
            timeState.value.substringBefore(":").toIntOrNull() ?: mCalendar.get(Calendar.HOUR_OF_DAY),
            timeState.value.substringAfter(":").toIntOrNull() ?: mCalendar.get(Calendar.MINUTE),
            true // Set to true for 24-hour format
        )
    }

    // Row containing label and time picker button
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(text = label, modifier = Modifier.weight(1f))

        // Time picker button
        Button(
            onClick = { timePickerDialog.show() },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6200EE))
        ) {
            Icon(
                imageVector = Icons.Default.DateRange,
                contentDescription = "Date Range icon",
                modifier = Modifier.size(18.dp)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(text = timeState.value) // Display current time
        }
    }
}

/**
 * Composable for the save button that save all data
 */
@Composable
fun SaveButton(
    fruitsChecked: MutableState<Boolean>,
    vegetablesChecked: MutableState<Boolean>,
    grainsChecked: MutableState<Boolean>,
    redMeatChecked: MutableState<Boolean>,
    seafoodChecked: MutableState<Boolean>,
    poultryChecked: MutableState<Boolean>,
    fishChecked: MutableState<Boolean>,
    eggsChecked: MutableState<Boolean>,
    nutsSeedsChecked: MutableState<Boolean>,
    selectedPersona: MutableState<String>,
    mealTime: MutableState<String>,
    sleepTime: MutableState<String>,
    wakeTime: MutableState<String>,
    userID: String,
    foodIntakeViewModel: FoodIntakeViewModel
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    Button(
        onClick = {
            // Create the FoodIntake object with all data
            val foodIntake = FoodIntake(
                userId = userID,
                fruitsChecked = fruitsChecked.value,
                vegetablesChecked = vegetablesChecked.value,
                grainsChecked = grainsChecked.value,
                redMeatChecked = redMeatChecked.value,
                seafoodChecked = seafoodChecked.value,
                poultryChecked = poultryChecked.value,
                fishChecked = fishChecked.value,
                eggsChecked = eggsChecked.value,
                nutsSeedsChecked = nutsSeedsChecked.value,
                selectedPersona = selectedPersona.value,
                mealTime = mealTime.value,
                sleepTime = sleepTime.value,
                wakeTime = wakeTime.value,
                questionnaireCompleted = true
            )


            coroutineScope.launch {
                try {
                    foodIntakeViewModel.insert(foodIntake)
                    Toast.makeText(context, "Saved successfully!", Toast.LENGTH_LONG).show()
                    context.startActivity(Intent(context, HomeActivity::class.java))
                } catch (e: Exception) {
                    Toast.makeText(context, "Error saving data: ${e.message}", Toast.LENGTH_LONG).show()
                }
            }
        },
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6200EE)),
        modifier = Modifier
            .fillMaxWidth(0.5f)
            .height(48.dp)
    ) {
        Text("Save")
    }
}