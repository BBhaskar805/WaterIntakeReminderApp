package com.example.waterintake

import android.app.Activity
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

class TipsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HydrationTipsScreen()
        }
    }
}

@Composable
fun HydrationTipsScreen() {
    val tips = listOf(
        "Set a Reminder" to listOf(
            "Drink Regularly (Not Just When Thirsty)",
            "In colder months or busy days, a phone reminder or hydration app can help."
        ),
        "Carry a Reusable Water Bottle" to listOf(
            "Invest in an insulated bottle to keep water cool or warm."
        ),
        "Watch Out for Dehydrating Drinks" to listOf(
            "Tea and coffee are staples in the UK, but caffeine can be mildly dehydrating.",
            "For every caffeinated drink, match it with a glass of water."
        ),
        "Eat Hydrating Foods" to listOf(
            "Soups, stews, apples, oranges, and berries all help with hydration."
        ),
        "Stay Hydrated When Active" to listOf(
            "If walking in Stewart Park or Roseberry Topping, carry water — even if it’s chilly."
        ),
        "Mind Your Salt Intake" to listOf(
            "Fish & chips or meat pies are salty — drink water to balance.",
            "Pair with water-rich meals when possible."
        ),
        "Pre-Hydrate for Outdoor Adventures" to listOf(
            "Heading to North York Moors or Saltburn? Drink before and during activity.",
            "Hydrate the night before hikes or runs."
        ),
        "Hydrate in Dry Indoor Air" to listOf(
            "Heating dries indoor air — sip water regularly, especially in offices or at Teesside Uni’s library."
        )
    )

    val context = LocalContext.current

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
                text = "Tips For Staying Hydrated",
                style = MaterialTheme.typography.titleMedium,
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

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {


                items(tips) { (title, bullets) ->
                    Card(
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFE1F5FE)),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                text = title,
                                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold),
                                color = Color(0xFF0277BD)
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            bullets.forEach { point ->
                                Row(
                                    verticalAlignment = Alignment.Top,
                                    modifier = Modifier.padding(bottom = 4.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.CheckCircle,
                                        contentDescription = null,
                                        tint = Color(0xFF4FC3F7),
                                        modifier = Modifier
                                            .size(18.dp)
                                            .padding(end = 6.dp)
                                    )
                                    Text(
                                        text = point,
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = Color.Black
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
