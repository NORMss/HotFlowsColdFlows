package com.norm.myhotflowscoldflows

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.ActivityCompat
import androidx.core.app.ComponentActivity

@Composable
fun LocationFlowDemo(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    LaunchedEffect(key1 = true) {
        ActivityCompat.requestPermissions(
            context as ComponentActivity,
            arrayOf(
                android.Manifest.permission.ACCESS_COARSE_LOCATION,
                android.Manifest.permission.ACCESS_FINE_LOCATION,
            ),
            0
        )
    }

    Box(
        modifier = modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        val flow = remember {
            observeLocation(context)
        }
        val location1 by flow.collectAsState(initial = null)
        val location2 by flow.collectAsState(initial = null)

        LaunchedEffect(location1) {
            println("Location1 update ${location1}, ${location1}")
        }

        LaunchedEffect(location2) {
            println("Location2 update ${location2}, ${location2}")
        }
    }

}