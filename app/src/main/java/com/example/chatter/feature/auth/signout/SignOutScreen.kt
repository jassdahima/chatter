package com.example.chatter.feature.auth.signout

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import org.koin.androidx.compose.koinViewModel
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.chatter.ui.theme.DarkGrey

@Composable
fun SignOut(modifier: Modifier = Modifier,navController: NavController){
    val viewModel : SignOutViewModel = koinViewModel()
    val state = viewModel.state.collectAsState()


    LaunchedEffect(state.value) {
        if (state.value is SignOutState.Success){
            navController.navigate("signin"){
                popUpTo("signin"){
                    inclusive = true
                }
            }
        }
    }



    Scaffold(modifier = Modifier.fillMaxSize(), containerColor = Color.Black) {
        Column(modifier = Modifier.fillMaxSize().padding(it), verticalArrangement = Arrangement.Top, horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "Signout Page", style = TextStyle(color = Color.White, fontSize = 32.sp, fontWeight = FontWeight.Bold))
            Spacer(modifier = Modifier.height(40.dp))

            Button(onClick = {viewModel.signOut()}, colors = ButtonDefaults.buttonColors(containerColor = DarkGrey)) {
                Text(text = "SignOut", color = Color.White)
            }
        }
    }
}