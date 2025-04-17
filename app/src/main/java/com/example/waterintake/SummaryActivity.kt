package com.example.waterintake

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

class SummaryActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WaterIntakeDashboardScreen()
        }
    }
}

@Composable
fun WaterIntakeDashboardScreen() {
    val dailyGoal = 3000f // in ml
    val todayIntake = 1800f
    val weekData = listOf(2500f, 2200f, 3000f, 2800f, 1900f, 3100f, todayIntake)
    val days = listOf("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Today")

    val dailyPercentage = (todayIntake / dailyGoal * 100).coerceIn(0f, 100f)

    Column(
        modifier = Modifier
            .fillMaxSize()
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
                text = "Summary",
                style = MaterialTheme.typography.titleLarge,
                color = Color.Black,
                fontWeight = FontWeight.Bold
            )

        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp)
        ) {


            Text(
                "Daily Water Intake Goal: ${dailyGoal.toInt()} ml",
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Progress Bar
            Text(
                "Today's Progress: ${"%.1f".format(dailyPercentage)}%",
                style = MaterialTheme.typography.bodyLarge
            )
            LinearProgressIndicator(
                progress = dailyPercentage / 100,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(12.dp)
                    .clip(RoundedCornerShape(8.dp)),
                color = Color(0xFF4FC3F7),
                trackColor = Color(0xFFE0E0E0)
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Weekly Summary
            Text("Weekly Water Intake Comparison", style = MaterialTheme.typography.titleMedium)

            Spacer(modifier = Modifier.height(16.dp))

            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(weekData.size) { index ->
                    val intake = weekData[index]
                    val isToday = index == weekData.size - 1
                    val achieved = intake >= dailyGoal

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .width(70.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(if (achieved) Color(0xFFA5D6A7) else Color(0xFFFFCDD2))
                            .padding(8.dp)
                    ) {
                        Text(
                            days[index],
                            style = MaterialTheme.typography.bodySmall,
                            color = Color.Black
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            "${intake.toInt()} ml",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.Black
                        )
                        if (isToday) {
                            Text(
                                "Today",
                                style = MaterialTheme.typography.labelSmall,
                                color = Color.DarkGray
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Summary
            val weeklyTotal = weekData.sum()
            val weeklyTarget = dailyGoal * 7
            val weeklyProgress = (weeklyTotal / weeklyTarget * 100).coerceIn(0f, 100f)

            Text("Weekly Summary", style = MaterialTheme.typography.titleMedium)
            Text("Total Intake: ${weeklyTotal.toInt()} ml / ${weeklyTarget.toInt()} ml")
            Text("Completed: ${"%.1f".format(weeklyProgress)}%")
        }
    }
}
