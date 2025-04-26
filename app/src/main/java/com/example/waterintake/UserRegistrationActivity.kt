package com.example.waterintake

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.util.Base64
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import coil.compose.rememberAsyncImagePainter
import com.google.firebase.database.FirebaseDatabase
import java.io.ByteArrayOutputStream
import java.io.File

class UserRegistrationActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            UserRegistrationActivityScreen()
        }
    }
}

@Composable
fun UserRegistrationActivityScreen() {
    var hydraFullName by remember { mutableStateOf("") }
    var hydraAge by remember { mutableStateOf("") }
    var hydraEmail by remember { mutableStateOf("") }
    var hydraPassword by remember { mutableStateOf("") }
    var hydraConfirmPassword by remember { mutableStateOf("") }

    val context = LocalContext.current as Activity

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = colorResource(id = R.color.color_deep_blue))
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.height(32.dp))

        Image(
            painter = painterResource(id = R.drawable.water_intake), // Replace with your drawable resource
            contentDescription = "App Logo",
            modifier = Modifier
                .size(64.dp)
                .align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.height(32.dp))


        Card(
            modifier = Modifier
                .fillMaxSize()
                .clip(
                    RoundedCornerShape(
                        topStart = 36.dp,
                        topEnd = 36.dp,
                        bottomStart = 0.dp,
                        bottomEnd = 0.dp
                    )
                ) // Round only top corners
        ) {

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "Create New Account",
                color = colorResource(id = R.color.color_deep_blue),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(12.dp))

            Card(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(
                        RoundedCornerShape(
                            topStart = 36.dp,
                            topEnd = 36.dp,
                            bottomStart = 0.dp,
                            bottomEnd = 0.dp
                        )
                    ),
                colors = CardDefaults.cardColors(containerColor = colorResource(id = R.color.color_light_sky_blue))
            ) {
                Spacer(modifier = Modifier.height(32.dp))

                UploadUserImage()


                Text(
                    modifier = Modifier.padding(start = 12.dp),
                    text = "Enter FullName"
                )

                Spacer(modifier = Modifier.height(6.dp))

                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp),
                    value = hydraFullName,
                    onValueChange = { hydraFullName = it },
                    colors = TextFieldDefaults.colors(
                        unfocusedContainerColor = Color.White,
                        focusedContainerColor = Color.White,
                    )
                )
                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    modifier = Modifier.padding(start = 12.dp),
                    text = "Enter Age"
                )

                Spacer(modifier = Modifier.height(6.dp))

                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp),
                    value = hydraAge,
                    onValueChange = { hydraAge = it },
                    colors = TextFieldDefaults.colors(
                        unfocusedContainerColor = Color.White,
                        focusedContainerColor = Color.White,
                    )
                )


                Spacer(modifier = Modifier.height(8.dp))


                Text(
                    modifier = Modifier.padding(start = 12.dp),
                    text = "Enter Email"
                )

                Spacer(modifier = Modifier.height(6.dp))

                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp),
                    value = hydraEmail,
                    onValueChange = { hydraEmail = it },
                    colors = TextFieldDefaults.colors(
                        unfocusedContainerColor = Color.White,
                        focusedContainerColor = Color.White,
                    )
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    modifier = Modifier.padding(start = 12.dp),
                    text = "Enter Password"
                )

                Spacer(modifier = Modifier.height(6.dp))

                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp),
                    value = hydraPassword,
                    onValueChange = { hydraPassword = it },
                    colors = TextFieldDefaults.colors(
                        unfocusedContainerColor = Color.White,
                        focusedContainerColor = Color.White,
                    )
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    modifier = Modifier.padding(start = 12.dp),
                    text = "Confirm Password"
                )

                Spacer(modifier = Modifier.height(6.dp))

                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp),
                    value = hydraConfirmPassword,
                    onValueChange = { hydraConfirmPassword = it },
                    colors = TextFieldDefaults.colors(
                        unfocusedContainerColor = Color.White,
                        focusedContainerColor = Color.White,
                    )
                )

                Spacer(modifier = Modifier.height(32.dp))

                Button(
                    onClick = {
                        when {
                            hydraEmail.isEmpty() -> {
                                Toast.makeText(context, "Uh-oh! Email field can’t be empty", Toast.LENGTH_SHORT).show()
                            }
                            hydraFullName.isEmpty() -> {
                                Toast.makeText(context, " Uh-oh! FullName field can’t be empty", Toast.LENGTH_SHORT).show()
                            }

                            hydraAge.isEmpty() -> {
                                Toast.makeText(context, "Uh-oh! Age field can’t be empty", Toast.LENGTH_SHORT).show()
                            }
                            hydraPassword.isEmpty() -> {
                                Toast.makeText(context, "Uh-oh! Password field can’t be empty", Toast.LENGTH_SHORT).show()
                            }

                            else -> {

                                val inputStream =
                                    context.contentResolver.openInputStream(UserPhotoData.selImageUri)
                                val bitmap = BitmapFactory.decodeStream(inputStream)
                                val outputStream = ByteArrayOutputStream()
                                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
                                val base64Image =
                                    Base64.encodeToString(
                                        outputStream.toByteArray(),
                                        Base64.DEFAULT
                                    )

//                                donorFormData.imageUrl = base64Image

                                val userData = UserData(
                                    hydraFullName,
                                    hydraEmail,
                                    hydraAge,
                                    hydraPassword,
                                    base64Image
                                )
                                registerUser(userData,context);


                            }

                        }
                    },
                    modifier = Modifier
                        .width(200.dp)
                        .align(Alignment.CenterHorizontally),
                    contentPadding = PaddingValues(vertical = 12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorResource(id = R.color.color_deep_blue),
                        contentColor = colorResource(
                            id = R.color.color_light_sky_blue
                        )
                    )
                ) {
                    Text(
                        text = "Register",
                        style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                ) {
                    Text(text = "I'm an old user !", fontSize = 14.sp)
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "Login",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = colorResource(id = R.color.color_deep_blue), // Blue text color for "Sign Up"
                        modifier = Modifier.clickable {
                            context.startActivity(Intent(context, AccessActivity::class.java))
                            context.finish()
                        }
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

            }
        }
    }
}

fun registerUser(userData: UserData, context: Context) {

    val firebaseDatabase = FirebaseDatabase.getInstance()
    val databaseReference = firebaseDatabase.getReference("UserData")

    databaseReference.child(userData.emailid.replace(".", ","))
        .setValue(userData)
        .addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Toast.makeText(context, "You Registered Successfully", Toast.LENGTH_SHORT)
                    .show()
                context.startActivity(Intent(context, AccessActivity::class.java))
                (context as Activity).finish()

            } else {
                Toast.makeText(
                    context,
                    "Registration Failed",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        .addOnFailureListener { _ ->
            Toast.makeText(
                context,
                "Something went wrong",
                Toast.LENGTH_SHORT
            ).show()
        }
}

data class UserData(
    var name : String = "",
    var emailid : String = "",
    var area : String = "",
    var password: String = "",
    var imageUrl: String = ""
)

@Composable
fun UploadUserImage() {
    val activityContext = LocalContext.current

    var imageUri by remember { mutableStateOf<Uri?>(null) }

    val captureImageLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture(),
        onResult = { success ->
            if (success) {
                imageUri = getImageUri(activityContext)
                UserPhotoData.selImageUri = imageUri as Uri
                UserPhotoData.isImageSelected=true
            } else {
                UserPhotoData.isImageSelected=false
                Toast.makeText(activityContext, "Capture Failed", Toast.LENGTH_SHORT).show()
            }
        }
    )

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->
            if (isGranted) {
                Toast.makeText(activityContext, "Permission Granted", Toast.LENGTH_SHORT).show()
                captureImageLauncher.launch(getImageUri(activityContext)) // Launch the camera
            } else {
                Toast.makeText(activityContext, "Permission Denied", Toast.LENGTH_SHORT).show()
            }
        }
    )

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = if (imageUri != null) {
                rememberAsyncImagePainter(model = imageUri)
            } else {
                painterResource(id = R.drawable.ic_add_image)
            },
            contentDescription = "Captured Image",
            modifier = Modifier
                .width(100.dp)
                .height(100.dp)
                .clickable {
                    if (ContextCompat.checkSelfPermission(
                            activityContext,
                            Manifest.permission.CAMERA
                        ) == PackageManager.PERMISSION_GRANTED
                    ) {
                        captureImageLauncher.launch(getImageUri(activityContext))
                    } else {
                        permissionLauncher.launch(Manifest.permission.CAMERA)
                    }
                }
        )
        Spacer(modifier = Modifier.height(16.dp))
        if (imageUri == null) {
            Text(text = "Tap the image to capture")
        }
    }
}

fun getImageUri(activityContext: Context): Uri {
    val file = File(activityContext.filesDir, "captured_image.jpg")
    return FileProvider.getUriForFile(
        activityContext,
        "${activityContext.packageName}.fileprovider",
        file
    )
}


object UserPhotoData {
    lateinit var selImageUri: Uri
    var isImageSelected = false
}
