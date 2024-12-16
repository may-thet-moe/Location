package com.example.location

import LocationScreen
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.example.location.ui.theme.LocationTheme
import eu.tutorials.locationapp.LocationUtils

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val locationViewModel = LocationViewModel()
            LocationTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MyApp(viewModel = locationViewModel, modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun MyApp(viewModel: LocationViewModel, modifier: Modifier = Modifier){
    val context = LocalContext.current
    val locationUtils = LocationUtils(context)
    LocationScreen(locationUtils = locationUtils, viewModel, context = context)
}

