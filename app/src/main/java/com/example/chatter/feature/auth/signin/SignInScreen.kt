package com.example.chatter.feature.auth.signin

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.chatter.R
import com.example.chatter.ui.theme.DarkGrey
import org.koin.androidx.compose.koinViewModel

@Composable
fun SignInScreen(navController: NavController){

    val viewModel : SignInViewModel = koinViewModel()

    val uiState = viewModel.state.collectAsState()

    var email by remember {
        mutableStateOf("")
    }

    var password by remember {
        mutableStateOf("")
    }

    val context = LocalContext.current

    LaunchedEffect(uiState.value) {
        when(uiState.value){
            is SignInState.Success -> {
                navController.navigate("home")
            }

            is SignInState.Error -> {
                Toast.makeText(context,"Sign in failed", Toast.LENGTH_SHORT).show()

            }
            else -> Unit
        }
    }





    Scaffold (containerColor = Color.Black,
        modifier = Modifier.fillMaxSize()
    ) {
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(it)
            .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally) {
            Image(painter = painterResource(id = R.drawable.chatlogo1), contentDescription = null,
                modifier = Modifier
                    .size(200.dp)
            )


            Text(text = "Chatter", fontSize = 32.sp, color = Color.Gray, fontWeight = FontWeight.SemiBold
            )



            Spacer(modifier = Modifier.height(60.dp))

           OutlinedTextField(value = email, onValueChange = {email = it},
                modifier = Modifier
                    .fillMaxWidth()
                    .height(70.dp)
                    .padding(horizontal = 36.dp),
                label = {Text(text = "Email")},
               shape = RoundedCornerShape(16.dp),
               colors = OutlinedTextFieldDefaults.colors(
                   focusedBorderColor = Color.Transparent,
                   unfocusedBorderColor = Color.Transparent,
                   focusedLabelColor = Color.White,
                   unfocusedLabelColor = Color.Gray,
                   cursorColor = Color.White,
                   focusedTextColor = Color.White,
                   unfocusedTextColor = Color.White,
                   focusedContainerColor = DarkGrey,
                   unfocusedContainerColor = DarkGrey
               ),
               singleLine = true
            )


            Spacer(modifier = Modifier.height(12.dp))


            OutlinedTextField(value = password, onValueChange = {
                password = it
            },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(70.dp)
                    .padding(horizontal = 36.dp),
                label = {Text(text = "Password")},
                shape = RoundedCornerShape(16.dp),
                visualTransformation = PasswordVisualTransformation(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.Transparent,
                    unfocusedBorderColor = Color.Transparent,
                    focusedLabelColor = Color.Gray,
                    unfocusedLabelColor = Color.Gray,
                    cursorColor = Color.White,
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                    focusedContainerColor = DarkGrey,
                    unfocusedContainerColor = DarkGrey
                ),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(30.dp))


           if (uiState.value == SignInState.Loading){
               CircularProgressIndicator()
           }
            else {
               Button(
                   onClick = { viewModel.signIn(email, password) },
                   modifier = Modifier
                       .width(90.dp)
                       .height(45.dp),
                   shape = RoundedCornerShape(40.dp),
                   colors = ButtonDefaults.buttonColors(
                       containerColor = DarkGrey,
                       disabledContainerColor = DarkGrey,

                   ),
                   enabled = email.isNotEmpty() && password.isNotEmpty() && uiState.value == SignInState.Nothing || uiState.value == SignInState.Error
               ) {
                   Text(text = "Login", fontSize = 16.sp,color = Color.LightGray, maxLines = 1)
               }

               Spacer(modifier = Modifier.padding(vertical = 50.dp))

               TextButton(onClick = { navController.navigate("signup") }) {
                   Text(text = buildAnnotatedString {
                       withStyle(style = SpanStyle(color = Color.Gray)){
                           append("Don't have an account? ")
                       }
                       withStyle(style = SpanStyle(color = Color.LightGray, fontWeight = FontWeight.ExtraBold)){
                           append("Login")
                       }
                   },
                       style = MaterialTheme.typography.titleSmall)
               }
           }

        }

    }

}


