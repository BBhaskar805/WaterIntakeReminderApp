package com.example.waterintake

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview

class WaterIntakeHomeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WaterIntakeHome()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun WaterIntakeHomePreview() {
    WaterIntakeHome()
}

@Composable
fun WaterIntakeHome() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = colorResource(id = R.color.app_bg_c1))
    ) {

        Row {
            Image(painter = painterResource(id = R.drawable.water_intake), contentDescription = "")
        }
    }
}