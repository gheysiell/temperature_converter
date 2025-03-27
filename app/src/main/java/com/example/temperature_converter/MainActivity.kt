package com.example.temperature_converter

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.compose.material3.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TemperatureConverterApp()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TemperatureConverterApp() {
    var input by remember { mutableStateOf(TextFieldValue("")) }
    var selectedUnit by remember { mutableStateOf("Celsius") }

    val temperature = input.text.toFloatOrNull() ?: 0f
    val celsius = when (selectedUnit) {
        "Fahrenheit" -> (temperature - 32) * 5 / 9
        "Kelvin" -> temperature - 273.15f
        else -> temperature
    }
    val fahrenheit = celsius * 9 / 5 + 32
    val kelvin = celsius + 273.15f

    Scaffold(
        topBar = {
            Surface(shadowElevation = 10.dp) {
                TopAppBar(
                    title = {
                        Text(
                            "Conversor de Temperatura",
                        )
                    },
                    colors = TopAppBarDefaults.mediumTopAppBarColors(
                        containerColor = Color(0xFF6200EE),
                        titleContentColor = Color.White,
                    ),
                )
            }
        },
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp),
        ) {
            Spacer(
                modifier = Modifier.height(16.dp),
            )
            TextField(
                modifier = Modifier
                    .fillMaxWidth(),
                value = input,
                onValueChange = {
                    input = it
                },
                textStyle = TextStyle(
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 17.sp,
                ),
                placeholder = {
                    Text(
                        text = "Temperatura",
                    )
                }
            )
            Spacer(
                modifier = Modifier.height(16.dp),
            )
            DropdownMenu(
                selectedUnit,
                onUnitSelected = { selectedUnit = it },
            )
            Spacer(
                modifier = Modifier.height(16.dp),
            )
            Text(
                "Celsius: $celsius °C",
                style = TextStyle(
                    fontSize = 18.sp,
                ),
            )
            Text(
                "Fahrenheit: $fahrenheit °F",
                style = TextStyle(
                    fontSize = 18.sp,
                ),
            )
            Text(
                "Kelvin: $kelvin K",
                style = TextStyle(
                    fontSize = 18.sp,
                ),
            )
        }
    }
}

@Composable
fun DropdownMenu(
    selectedUnit: String,
    onUnitSelected: (String) -> Unit,
) {
    var expanded by remember { mutableStateOf(false) }
    val units = listOf(
        "Celsius",
        "Fahrenheit",
        "Kelvin",
    )

    Box {
        Button(onClick = { expanded = true }) {
            Text(selectedUnit)
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
        ) {
            units.forEach { unit ->
                DropdownMenuItem(
                    text = { Text(unit) },
                    onClick = {
                        onUnitSelected(unit)
                        expanded = false
                    },
                )
            }
        }
    }
}
