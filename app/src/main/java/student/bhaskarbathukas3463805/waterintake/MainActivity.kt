package student.bhaskarbathukas3463805.waterintake

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EntryScreenMA()
        }
    }
}

@Composable
fun EntryScreenMA() {
    val context = LocalContext.current as Activity

    LaunchedEffect(Unit) {
        delay(3000)

        if(HydrationPrefs.isUserLoggedIn(context))
        {
            context.startActivity(Intent(context, HomeActivity::class.java))
        }else{
            context.startActivity(Intent(context, AccessActivity::class.java))
        }


    }

    EntryScreen()
}

@Composable
fun EntryScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = colorResource(id = R.color.color_deep_blue)),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            Spacer(modifier = Modifier.weight(1f))


            Image(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                painter = painterResource(id = R.drawable.app_logo),
                contentDescription = "WaterIntake Reminder App by Bhaskar",
            )

            Spacer(modifier = Modifier.weight(1f))

            Text(
                text = "Welcome To",
                fontWeight = FontWeight.Bold,
                color = colorResource(id = R.color.color_light_sky_blue), // Green color similar to the design
                fontSize = 26.sp,
                style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
                modifier = Modifier.align(Alignment.CenterHorizontally),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = "WaterIntake Reminder App\nby Bhaskar",
                color = colorResource(id = R.color.color_light_sky_blue), // Green color similar to the design
                style = MaterialTheme.typography.headlineLarge.copy(fontWeight = FontWeight.Bold),
                modifier = Modifier.align(Alignment.CenterHorizontally),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.weight(1f))


        }
    }

}