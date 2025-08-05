package com.fit2081.shen33520089

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.fit2081.shen33520089.data.FoodIntakeViewModel
import com.fit2081.shen33520089.data.PatientViewModel
import com.fit2081.shen33520089.ui.theme.Shen33520089Theme
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class LogInActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val viewModel: PatientViewModel = ViewModelProvider(
            this,
            PatientViewModel.patientViewModelFactory(this)
        )[PatientViewModel::class.java]

        setContent {
            Shen33520089Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    LogInScreen(viewModel)
                }
            }
        }
    }
}

/**
 * LogInScreen is a composable function that displays the login screen for the app.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LogInScreen(viewModel: PatientViewModel, modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val users by viewModel.allPatients.collectAsState(initial = emptyList())

    var password by remember { mutableStateOf("") }
    var passwordError by remember { mutableStateOf(false) }
    var expanded by remember { mutableStateOf(false) }
    var selectedId by remember { mutableStateOf("") }
    var storedPassword by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var idError by remember { mutableStateOf(false) }
    var loginErrorMessage by remember { mutableStateOf("") }
    val scrollState = rememberScrollState()


    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Spacer(modifier = Modifier.height(100.dp))
        Text(
            text = "Log in",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded },
            modifier = Modifier.fillMaxWidth()
        ) {
            OutlinedTextField(
                value = selectedId,
                onValueChange = { },
                label = { Text("My ID (Provided by your Clinician)") },
                readOnly = true,
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                isError = idError,
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor()
            )

            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                users.forEach { user ->
                    DropdownMenuItem(
                        text = { Text(user.userId) },
                        onClick = {
                            selectedId = user.userId
                            storedPassword = user.password
                            expanded = false
                            idError = false
                            loginErrorMessage = ""
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // --- Password Entry ---
        OutlinedTextField(
            value = password,
            onValueChange = {
                password = it
                passwordError = false
            },
            label = { Text("Password") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                val iconText = if (passwordVisible) "Hide" else "Show"
                Text(
                    text = iconText,
                    modifier = Modifier.padding(end = 8.dp)
                        .clickable { passwordVisible = !passwordVisible }
                )
            },
            isError = passwordError,
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        if (loginErrorMessage.isNotBlank()) {
            Text(
                text = loginErrorMessage,
                color = MaterialTheme.colorScheme.error,
                fontSize = 14.sp,
                modifier = Modifier
                    .padding(top = 4.dp)
                    .fillMaxWidth()
            )
        }

        Spacer(modifier = Modifier.height(16.dp))
        Text(
            "This app is only for pre-registered users. Please enter your ID and password or Register to claim your account on your first visit.",
            fontSize = 14.sp
        )

        Spacer(modifier = Modifier.height(16.dp))

        val foodIntakeViewModel = ViewModelProvider(
            context as ComponentActivity,
            FoodIntakeViewModel.FoodIntakeViewModelFactory(context)
        )[FoodIntakeViewModel::class.java]

        Button(
            onClick = {
                when {
                    selectedId.isEmpty() -> {
                        idError = true
                        loginErrorMessage = "Please select your ID"
                    }
                    password.isEmpty() -> {
                        passwordError = true
                        loginErrorMessage = "Password cannot be empty"
                    }
                    else -> {
                        val user = users.find { it.userId == selectedId }
                        if (user == null || user.password.isBlank()) {
                            loginErrorMessage = "Login failed. User not registered."
                        } else if (password != user.password) {
                            passwordError = true
                            loginErrorMessage = "Incorrect password."
                        } else {
                            (context as? ComponentActivity)?.lifecycleScope?.launch {
                                val foodIntake = foodIntakeViewModel.getFoodIntakeById(selectedId).first()
                                AuthManager.login(context, selectedId)
                                if (foodIntake?.questionnaireCompleted == true) {
                                    context.startActivity(Intent(context, HomeActivity::class.java))
                                    Toast.makeText(context, "Welcome back, ${user.name}!", Toast.LENGTH_LONG).show()
                                } else {
                                    context.startActivity(Intent(context, QuestionnaireActivity::class.java))
                                    Toast.makeText(context, "Login Successful", Toast.LENGTH_LONG).show()
                                }
                            }
                        }
                    }
                }
            },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6200EE)),
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .height(48.dp)
        ) {
            Text("Continue")
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = {
                context.startActivity(Intent(context, RegisterActivity::class.java))
            },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6200EE)),
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .height(48.dp)
        ) {
            Text("Register")
        }
    }
}
