package com.example.waterintake

import android.app.Activity
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.waterintake.storagedata.WaterViewModel
import java.util.Calendar

class AddConsumationActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WaterInputScreen(WaterViewModel(application))
        }
    }
}


@Composable
fun WaterInputScreen(viewModel: WaterViewModel) {
    var amount by remember { mutableStateOf("") }
    var date by remember { mutableStateOf("") }
    var time by remember { mutableStateOf("") }

    val context = LocalContext.current
    val calendar = Calendar.getInstance()

    val datePicker = DatePickerDialog(
        context,
        { _, y, m, d ->
            date = String.format("%04d-%02d-%02d", y, m + 1, d)
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )

    val timePicker = TimePickerDialog(context, { _, h, min ->
        time = String.format("%02d:%02d", h, min)
    }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true)

    Column(
        modifier = Modifier.fillMaxSize()
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
                text = "Add Intake",
                style = MaterialTheme.typography.titleLarge,
                color = Color.Black,
                fontWeight = FontWeight.Bold
            )

        }

        Column(
            Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = amount,
                onValueChange = { amount = it },
                label = { Text("Water (in ml)") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )

            Spacer(Modifier.height(8.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Box(
                    modifier = Modifier
                        .weight(1f)
                        .padding(vertical = 8.dp)
                        .height(50.dp)
                        .clickable {
                            // Handle the click event, e.g., show a date picker
                        }
                        .background(Color.LightGray, MaterialTheme.shapes.medium)
                        .padding(horizontal = 16.dp),
                    contentAlignment = Alignment.CenterStart
                ) {
                    Text(
                        text = date.ifEmpty { "Date" },
                        color = if (date.isEmpty()) Color.Gray else Color.Black
                    )
                    Icon(
                        imageVector = Icons.Default.DateRange, // Replace with your desired icon
                        contentDescription = "Calendar Icon",
                        modifier = Modifier
                            .align(Alignment.CenterEnd)
                            .size(24.dp)
                            .clickable {
                                datePicker.show()
                            },
                        tint = Color.DarkGray
                    )
                }

                Spacer(modifier = Modifier.width(16.dp))

                Box(
                    modifier = Modifier
                        .weight(1f)
                        .padding(vertical = 8.dp)
                        .height(50.dp)
                        .clickable {
                            // Handle the click event, e.g., show a date picker
                        }
                        .background(Color.LightGray, MaterialTheme.shapes.medium)
                        .padding(horizontal = 16.dp),
                    contentAlignment = Alignment.CenterStart
                ) {
                    Text(
                        text = time.ifEmpty { "Time" },
                        color = if (time.isEmpty()) Color.Gray else Color.Black
                    )
                    Icon(
                        imageVector = Icons.Default.DateRange, // Replace with your desired icon
                        contentDescription = "Calendar Icon",
                        modifier = Modifier
                            .align(Alignment.CenterEnd)
                            .size(24.dp)
                            .clickable {
                                timePicker.show()
                            },
                        tint = Color.DarkGray
                    )
                }

            }


            Spacer(Modifier.height(16.dp))

            Button(
                modifier = Modifier.width(200.dp),
                onClick = {
                    if (amount.isNotEmpty() && date.isNotEmpty() && time.isNotEmpty()) {
                        viewModel.insert(amount.toInt(), date, time)
                        amount = ""
                        date = ""
                        time = ""

                        Toast.makeText(context, "Added Successfully", Toast.LENGTH_SHORT).show()
                        (context as Activity).finish()
                    }
                }
            ) {
                Text("Save Entry")
            }
        }

    }
}
