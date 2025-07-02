package com.earlybird.earlybirdcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.earlybird.earlybirdcompose.presentation.screen.main.MainScreen
import com.earlybird.earlybirdcompose.ui.theme.EarlyBirdComposeTheme
import kotlinx.coroutines.MainScope

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val startIntent = intent

        setContent {
            EarlyBirdComposeTheme {
                AppNavigation(startIntent)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    EarlyBirdComposeTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            MainScreen()
        }
    }
}