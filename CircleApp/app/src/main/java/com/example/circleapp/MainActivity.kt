package com.example.circleapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.PI

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CircleAppScreen()
        }
    }
}

@Composable
fun CircleAppScreen() {
    var radiusInputText by remember { mutableStateOf("") }
    var inputErrorMessage by remember { mutableStateOf<String?>(null) }
    var areaResultText by remember { mutableStateOf<String?>(null) }
    val authorName = stringResource(id = R.string.author_name)

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color(0xFFFFF8E1)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween // keeps footer at bottom
        ) {
            // Main Part
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "CircleApp",
                    fontSize = 32.sp,
                    color = Color(0xFF5E35B1)
                )

                Spacer(modifier = Modifier.height(16.dp))

                Image(
                    painter = painterResource(id = R.drawable.circle),
                    contentDescription = "Circle picture",
                    modifier = Modifier
                        .size(140.dp)
                        .padding(8.dp)
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = radiusInputText,
                    onValueChange = {
                        radiusInputText = it
                        inputErrorMessage = null
                    },
                    label = { Text(text = "Radius (r)") },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Number
                    ),
                    modifier = Modifier.fillMaxWidth()
                )

                if (!inputErrorMessage.isNullOrEmpty()) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = inputErrorMessage!!, color = Color(0xFFB00020))
                }

                Spacer(modifier = Modifier.height(12.dp))

                Button(
                    onClick = {
                        val raw = radiusInputText.trim().replace(',', '.')
                        if (raw.isEmpty()) {
                            inputErrorMessage = "Please enter the radius."
                            areaResultText = null
                            return@Button
                        }

                        val radius = raw.toDoubleOrNull()
                        if (radius == null) {
                            inputErrorMessage = "Invalid number. Use digits like 3.5"
                            areaResultText = null
                            return@Button
                        }

                        if (radius < 0.0) {
                            inputErrorMessage = "Radius cannot be negative."
                            areaResultText = null
                            return@Button
                        }

                        val area = PI * radius * radius
                        areaResultText = "Area = %.4f".format(area)
                        inputErrorMessage = null
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00796B)),
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(text = "Calculate Area", color = Color.White)
                }

                Spacer(modifier = Modifier.height(20.dp))

                if (!areaResultText.isNullOrEmpty()) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color.White, RoundedCornerShape(12.dp))
                            .padding(16.dp)
                    ) {
                        Text(text = areaResultText!!, fontSize = 18.sp, color = Color(0xFF212121))
                    }
                }
            }

            Text(
                text = "Developed by $authorName",
                fontSize = 14.sp,
                color = Color.Black,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
            )
        }
    }
}
