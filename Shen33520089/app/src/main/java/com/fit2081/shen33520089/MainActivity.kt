package com.fit2081.shen33520089

import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fit2081.shen33520089.ui.theme.Shen33520089Theme

/**
 * Main entry point of NutriTrack app.
 * This activity displays the login screen with a disclaimer and a button to log in.
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Load saved user session
        AuthManager.loadUserFromPrefs(this)

        // If already logged in, go directly to Home
        if (AuthManager.isLoggedIn()) {
            startActivity(Intent(this, HomeActivity::class.java))
            finish()
            return
        }

        // Load CSV only on first launch (if DB is empty)
        lifecycleScope.launch {
            CSVLoader.loadCsvIntoRoomIfEmpty(applicationContext)
        }
        enableEdgeToEdge()
        setContent {
            Shen33520089Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    LogInActivity(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

/**
 * Composable function to display the login screen.
 * It includes a disclaimer and a button to log in.
 *
 * @param modifier Modifier to be applied to the layout.
 */
@Composable
fun LogInActivity(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val scrollState = rememberScrollState()


    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Spacer(modifier = Modifier.height(72.dp))
        Image(
            painter = painterResource(id = R.drawable.nutritracklogiremovebackground),
            contentDescription = "NutriTrack Logo",
            modifier = Modifier.size(240.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        val disclaimer ="""
            This app provides general health and nutrition information
            for educational purposes only. It is not intended as
            medical advice, diagnosis, or treatment. Always consult a
            qualified healthcare professional before making any
            changes to your diet, exercise, or health regimen.
            Use this app at your own risk.
            If you'd like to see an Accredited Practicing Dietitian (APD),
            please visit the Monash Nutrition/Dietetics Clinic
            (discounted rates for students):
            """.trimIndent()

        val link = "https://www.monash.edu/medicine/scs/nutrition/clinics/nutrition"

        Column(
            modifier = Modifier.padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = disclaimer,
                fontSize = 12.sp,
                fontStyle = FontStyle.Italic,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )

            Text(
                text = link,
                fontSize = 12.sp,
                fontStyle = FontStyle.Italic,
                fontWeight = FontWeight.Bold,
                color = Color.Blue,
                textAlign = TextAlign.Center,
                modifier = Modifier.clickable {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(link))
                    context.startActivity(intent)
                }
            )
        }


        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = { context.startActivity(Intent(context, LogInActivity::class.java)) },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6200EE)),
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .height(50.dp)
        ) {
            Text(text = "Login", fontSize = 16.sp)
        }

        Spacer(modifier = Modifier.height(80.dp))

        Text(
            text = "Designed with ❤️ by Aw Shen Yang (33520089)",
            fontSize = 12.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(top = 8.dp)
        )
    }
}