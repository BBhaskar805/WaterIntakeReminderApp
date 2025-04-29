package student.bhaskarbathukas3463805.waterintake

import android.app.Activity
import android.graphics.drawable.GradientDrawable
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import student.bhaskarbathukas3463805.waterintake.storagedata.WaterViewModel
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class OverallActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WaterIntakeFourWeeksScreen(viewModel = WaterViewModel(application))
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun WaterIntakeFourWeeksScreen(viewModel: WaterViewModel) {
    val allEntries by viewModel.allEntries.collectAsState()
    val context = LocalContext.current

    val weeksWithLabels = remember {
        getLastFourWeeksWithLabels()
    }

    var selectedWeek by remember { mutableStateOf(weeksWithLabels.first()) }

    val entriesForSelectedWeek = allEntries.filter {
        val date = LocalDate.parse(it.date)
        date in selectedWeek.first..selectedWeek.second
    }

    val grouped = entriesForSelectedWeek.groupBy { it.date }
        .mapValues { it.value.sumOf { entry -> entry.amountMl }.toFloat() }

    val weekDates = getDatesBetween(selectedWeek.first, selectedWeek.second)

    val displayDates = weekDates.map { it }
    val dailyValues = displayDates.map { grouped[it.toString()] ?: 0f }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(WindowInsets.systemBars.asPaddingValues())
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
                text = "Weekly Summary",
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

            Spacer(modifier = Modifier.height(8.dp))

//        DropdownMenuBox(
//            options = weeksWithLabels.map { it.third },
//            selectedOption = selectedWeek.third,
//            onOptionSelected = { label ->
//                selectedWeek = weeksWithLabels.first { it.third == label }
//            }
//        )

            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            ) {
                items(weeksWithLabels) { week ->
                    val isSelected = selectedWeek == week

                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(20.dp))
                            .background(if (isSelected) Color(0xFF4FC3F7) else Color.LightGray)
                            .clickable {
                                selectedWeek = week
                            }
                            .padding(horizontal = 16.dp, vertical = 8.dp)
                    ) {
                        Text(
                            text = week.third,
                            color = if (isSelected) Color.White else Color.Black,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }


            Spacer(modifier = Modifier.height(24.dp))

            Text( modifier = Modifier.align(Alignment.CenterHorizontally),
                text="Water Intake for ${selectedWeek.third}",
                style = MaterialTheme.typography.titleMedium
            )

            BarChartComposable(
                labels = displayDates.map { it.format(DateTimeFormatter.ofPattern("MMM d")) },
                values = dailyValues
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                text = "Graph analysis of last four weeks",
                style = MaterialTheme.typography.titleMedium
            )

            val weeklyConsumption = viewModel.getLast4WeeksConsumption()
            WeeklyWaveGraph(weeklyData = weeklyConsumption)
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
fun getDatesBetween(start: LocalDate, end: LocalDate): List<LocalDate> {
    val dates = mutableListOf<LocalDate>()
    var current = start
    while (!current.isAfter(end)) {
        dates.add(current)
        current = current.plusDays(1)
    }
    return dates
}


@RequiresApi(Build.VERSION_CODES.O)
fun getLastFourWeeksWithLabels(): List<Triple<LocalDate, LocalDate, String>> {
    val today = LocalDate.now()
    val startOfWeek = today.with(DayOfWeek.MONDAY)

    return (0 until 4).map { i ->
        val start = startOfWeek.minusWeeks(i.toLong())
        val end = start.plusDays(6)
        Triple(
            start,
            end,
            "${start.format(DateTimeFormatter.ofPattern("MMM d"))} - ${
                end.format(
                    DateTimeFormatter.ofPattern("MMM d")
                )
            }"
        )
    }.reversed() // So current week is at top
}

@Composable
fun DropdownMenuBox(
    options: List<String>,
    selectedOption: String,
    onOptionSelected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Box {
        OutlinedButton(onClick = { expanded = true }) {
            Text(selectedOption)
        }

        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            options.forEach { label ->
                DropdownMenuItem(
                    text = { Text(label) },
                    onClick = {
                        onOptionSelected(label)
                        expanded = false
                    }
                )
            }
        }
    }
}

@Composable
fun BarChartComposable(labels: List<String>, values: List<Float>) {
    AndroidView(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp),
        factory = { context ->
            BarChart(context).apply {
                description.isEnabled = false
                axisRight.isEnabled = false
                xAxis.position = XAxis.XAxisPosition.BOTTOM
                xAxis.setDrawGridLines(false)
                axisLeft.setDrawGridLines(false)
                legend.isEnabled = false
            }
        },
        update = { chart ->
            val entries = values.mapIndexed { index, value ->
                BarEntry(index.toFloat(), value)
            }

            val dataSet = BarDataSet(entries, "Water Intake").apply {
                color = Color(0xFF4FC3F7).toArgb()
                valueTextSize = 12f
            }

            val barData = BarData(dataSet)
            chart.data = barData
            chart.xAxis.valueFormatter = IndexAxisValueFormatter(labels)
            chart.invalidate()
        }
    )
}


@Composable
fun WeeklyWaveGraph(weeklyData: List<Float>) {
    AndroidView(
        factory = { context ->
            LineChart(context).apply {
                description.isEnabled = false
                legend.isEnabled = false

                axisRight.isEnabled = false
                axisLeft.apply {
                    axisMinimum = 0f
                    textColor = Color.Black.toArgb()
                }

                xAxis.apply {
                    position = XAxis.XAxisPosition.BOTTOM
                    granularity = 1f
                    setDrawGridLines(false)
                    valueFormatter = IndexAxisValueFormatter(
                        listOf("Week 1", "Week 2", "Week 3", "Week 4")
                    )
                    textColor = Color.Black.toArgb()
                }

                setTouchEnabled(false)
                setScaleEnabled(false)
            }
        },
        update = { chart ->
            val entries = weeklyData.mapIndexed { index, value ->
                Entry(index.toFloat(), value)
            }

            val dataSet = LineDataSet(entries, "Water Intake").apply {
                color = android.graphics.Color.parseColor("#2196F3")
                valueTextColor = android.graphics.Color.BLACK
                setDrawFilled(true)
                fillDrawable = GradientDrawable(
                    GradientDrawable.Orientation.TOP_BOTTOM,
                    intArrayOf(
                        android.graphics.Color.parseColor("#BBDEFB"),
                        android.graphics.Color.TRANSPARENT
                    )
                )
                mode = LineDataSet.Mode.CUBIC_BEZIER
                setDrawCircles(true)
                setDrawValues(true)
                circleRadius = 5f
                circleHoleRadius = 2f
                lineWidth = 2f
            }

            chart.data = LineData(dataSet)
            chart.invalidate()
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp)
            .padding(8.dp)
    )
}
