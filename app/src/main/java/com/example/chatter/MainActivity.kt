package com.example.chatter

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.chatter.feature.auth.signin.SignInViewModel
import com.example.chatter.ui.theme.ChatterTheme
import org.koin.androidx.viewmodel.ext.android.getViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val signInViewModel : SignInViewModel = getViewModel()

        setContent {
            ChatterTheme {
                MainApp()

                }
            }
        }
    }
