package com.koga.poc_native_app_integrations.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.koga.poc_native_app_integrations.ui.theme.PocnativeappintegrationsTheme
import com.koga.poc_native_app_integrations.util.ReactNativeAppLoader

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        ReactNativeAppLoader.preLoad(this, ReactNativeAppLoader.IDENTIFIER)

        setContent {
            PocnativeappintegrationsTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Hello World from Android App!"
                        )

                        Button(onClick = { navigateToReactNativeApp() }) {
                            Text(text = "navigate to react native app")
                        }
                    }
                }
            }
        }
    }

    private fun navigateToReactNativeApp() {
        startActivity(
            Intent(this, ReactNativeAppActivity::class.java)
        )
    }
}