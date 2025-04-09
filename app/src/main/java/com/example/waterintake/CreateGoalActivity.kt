package com.example.waterintake

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class CreateGoalActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WaterGoalSettingScreen()
        }
    }
}


@Preview(showBackground = true)
@Composable
fun CreateGoalScreen() {
//    WaterConsumptionEntryScreen()
    WaterGoalSettingScreen()
}

@Composable
fun WaterConsumptionEntryScreen() {
    val currentDateTime = remember {
        SimpleDateFormat("dd MMM yyyy, hh:mm a", Locale.getDefault()).format(Date())
    }

    var waterConsumed by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = colorResource(id = R.color.SkyBlue))
                .padding(vertical = 6.dp, horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.baseline_arrow_back_24),
                contentDescription = "Logo",
                modifier = Modifier
                    .width(36.dp)
                    .height(36.dp)
                    .clickable {
                    }
            )

            Spacer(modifier = Modifier.width(16.dp))

            Text(
                text = "WaterIntake Reminder",
                style = MaterialTheme.typography.titleLarge,
                color = Color.Black,
                fontWeight = FontWeight.Bold
            )

        }

        Column(
            modifier = Modifier.padding(16.dp)
        ) {


            Text(
                text = "Current Date & Time",
                style = MaterialTheme.typography.labelMedium
            )
            Text(
                text = currentDateTime,
                style = MaterialTheme.typography.bodyLarge
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "Total Water Consumed (ml)",
                style = MaterialTheme.typography.labelMedium
            )
            OutlinedTextField(
                value = waterConsumed,
                onValueChange = { waterConsumed = it },
                placeholder = { Text("Enter amount in ml") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )

            Button(
                onClick = {
                    // Handle save or submission here
                },
                modifier = Modifier.align(Alignment.End)
            ) {
                Text("Save")
            }

        }
    }

    @Composable
    fun WaterGoalSettingScreen() {
        var weight by remember { mutableStateOf("") }
        var activityLevel by remember { mutableStateOf("") }
        var customGoal by remember { mutableStateOf("") }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text("Set Your Water Intake Goal", style = MaterialTheme.typography.headlineSmall)

            OutlinedTextField(
                value = weight,
                onValueChange = { weight = it },
                label = { Text("Your Weight (kg)") },
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = activityLevel,
                onValueChange = { activityLevel = it },
                label = { Text("Activity Level (e.g., Low, Moderate, High)") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = customGoal,
                onValueChange = { customGoal = it },
                label = { Text("Custom Goal (ml)") },
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )

            Button(
                onClick = {
                    // Handle goal saving logic here
                },
                modifier = Modifier.align(Alignment.End)
            ) {
                Text("Set Goal")
            }
        }
    }



}

@Composable
fun WaterGoalSettingScreen() {
    var weight by remember { mutableStateOf("") }
    var activityLevel by remember { mutableStateOf("") }
    var customGoal by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = colorResource(id = R.color.SkyBlue))
                .padding(vertical = 6.dp, horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.baseline_arrow_back_24),
                contentDescription = "Logo",
                modifier = Modifier
                    .width(36.dp)
                    .height(36.dp)
                    .clickable {
                    }
            )

            Spacer(modifier = Modifier.width(16.dp))

            Text(
                text = "Set Your Water Intake Goal",
                style = MaterialTheme.typography.titleLarge,
                color = Color.Black,
                fontWeight = FontWeight.Bold
            )

        }
        Column(
            modifier = Modifier.padding(horizontal = 12.dp)
        ) {



            OutlinedTextField(
                value = weight,
                onValueChange = { weight = it },
                label = { Text("Your Weight (kg)") },
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = activityLevel,
                onValueChange = { activityLevel = it },
                label = { Text("Activity Level (e.g., Low, Moderate, High)") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = customGoal,
                onValueChange = { customGoal = it },
                label = { Text("Custom Goal (ml)") },
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )

            Button(
                onClick = {
                    // Handle goal saving logic here
                },
                modifier = Modifier.align(Alignment.End)
            ) {
                Text("Set Goal")
            }
        }
    }
}

