package com.wilsonhernandez.interrrapidisimo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.wilsonhernandez.interrrapidisimo.navigation.AppNavigation
import com.wilsonhernandez.interrrapidisimo.ui.theme.InterrrapidisimoTheme
import com.wilsonhernandez.interrrapidisimo.ui.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val viewModel : HomeViewModel by viewModels()
        super.onCreate(savedInstanceState)
        setContent {
            AppNavigation(viewModel)
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {

    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    InterrrapidisimoTheme {
        Greeting("Android")
    }
}

