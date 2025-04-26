package com.example.waterintake


import android.app.Activity
import android.os.Build
import android.os.Bundle
import android.view.ViewGroup
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
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
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.example.waterintake.storagedata.WaterViewModel
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class SummaryActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WaterIntakeDashboardScreen(WaterViewModel(application))
        }
    }
}


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun WaterIntakeDashboardScreen(viewModel: WaterViewModel) {

    val entries by viewModel.last7Days.collectAsState()

    val dailyGoal = HydrationPrefs.getDailyWaterGoal(LocalContext.current)

//    val grouped = entries.groupBy { it.date }
//        .mapValues { it.value.sumOf { entry -> entry.amountMl }.toFloat() }
//
//    val sortedDays = grouped.keys.sorted()
//    val weekData = sortedDays.map { grouped[it] ?: 0f }

    val today = LocalDate.now()
    val last7Days = (0..6).map { today.minusDays((6 - it).toLong()) } // Mon to Sun

    val grouped = entries.groupBy { it.date }
        .mapValues { it.value.sumOf { entry -> entry.amountMl }.toFloat() }

    val weekData = last7Days.map { date ->
        grouped[date.toString()] ?: 0f
    }

    val formatter = DateTimeFormatter.ofPattern("MMM d") // Example: May 20
    val days = last7Days.map { it.format(formatter) }


//    val days = last7Days.map { date ->
//        if (date == today) "Today" else date.dayOfWeek.name.take(3)
//    }


    val todayIntake = grouped[LocalDate.now().toString()] ?: 0f
    val dailyPercentage = (todayIntake / dailyGoal * 100).coerceIn(0f, 100f)

//    val days = sortedDays.map {
//        if (it == LocalDate.now()
//                .toString()
//        ) "Today" else LocalDate.parse(it).dayOfWeek.name.take(3)
//    }

    val context = LocalContext.current

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
                        (context as Activity).finish()
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
                .verticalScroll(rememberScrollState())
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

            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.height(300.dp) // adjust height as needed
            ) {
                items(weekData.size) { index ->
                    val intake = weekData[index]
                    val isToday = last7Days[index] == LocalDate.now()
                    val achieved = intake >= dailyGoal

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .clip(RoundedCornerShape(12.dp))
                            .background(if (achieved) Color(0xFFA5D6A7) else Color(0xFFFFCDD2))
                            .padding(8.dp)
                    ) {
                        Text(
                            days[index], // "May 20"
                            style = MaterialTheme.typography.bodySmall,
                            color = Color.Black
                        )
                        Spacer(modifier = Modifier.height(4.dp))
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

            WeeklyWaterBarChart(last7Days, weekData)

//            LazyRow(
//                horizontalArrangement = Arrangement.spacedBy(12.dp)
//            ) {
//                items(weekData.size) { index ->
//                    val intake = weekData[index]
//                    val isToday = index == weekData.size - 1
//                    val achieved = intake >= dailyGoal
//
//                    Column(
//                        horizontalAlignment = Alignment.CenterHorizontally,
//                        modifier = Modifier
//                            .width(70.dp)
//                            .clip(RoundedCornerShape(12.dp))
//                            .background(if (achieved) Color(0xFFA5D6A7) else Color(0xFFFFCDD2))
//                            .padding(8.dp)
//                    ) {
//                        Text(
//                            days[index],
//                            style = MaterialTheme.typography.bodySmall,
//                            color = Color.Black
//                        )
//                        Spacer(modifier = Modifier.height(8.dp))
//                        Text(
//                            "${intake.toInt()} ml",
//                            style = MaterialTheme.typography.bodyMedium,
//                            color = Color.Black
//                        )
//                        if (isToday) {
//                            Text(
//                                "Today",
//                                style = MaterialTheme.typography.labelSmall,
//                                color = Color.DarkGray
//                            )
//                        }
//                    }
//                }
//            }

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

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun WeeklyWaterBarChart(dates: List<LocalDate>, data: List<Float>) {
    val formatter = DateTimeFormatter.ofPattern("MMM d")
    val context = LocalContext.current

    AndroidView(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp),
        factory = {
            BarChart(context).apply {
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
                setDrawValueAboveBar(true)
                setFitBars(true)
                description.isEnabled = false
                legend.isEnabled = false
                axisRight.isEnabled = false
                axisLeft.granularity = 1f
                xAxis.position = XAxis.XAxisPosition.BOTTOM
                xAxis.granularity = 1f
                xAxis.setDrawGridLines(false)
            }
        },
        update = { barChart ->
            val entries = data.mapIndexed { index, value ->
                BarEntry(index.toFloat(), value)
            }

            val dataSet = BarDataSet(entries, "Water Intake (ml)").apply {
                color = Color(0xFF4FC3F7).toArgb()
                valueTextColor = Color.Black.toArgb()
                valueTextSize = 12f
            }

            barChart.data = BarData(dataSet)

            barChart.xAxis.valueFormatter = IndexAxisValueFormatter(dates.map {
                it.format(formatter)
            })

            barChart.invalidate()
        }
    )
}
