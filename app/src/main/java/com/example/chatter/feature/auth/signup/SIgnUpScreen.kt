package com.example.chatter.feature.auth.signup

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
import androidx.compose.material3.ButtonColors
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.chatter.R
import com.example.chatter.ui.theme.DarkGrey
import org.koin.androidx.compose.koinViewModel

@Composable
fun SignUpScreen(navController: NavController){

    val viewModel : SignUpViewModel = koinViewModel()
    val uiState = viewModel.state.collectAsState()

    var email by remember {
        mutableStateOf("")
    }

    var password by remember {
        mutableStateOf("")
    }

    var name by remember {
        mutableStateOf("")
    }

    var confirm by remember {
        mutableStateOf("")
    }

    val context = LocalContext.current

    LaunchedEffect(uiState.value) {
        when(uiState.value){
            is SignUpState.Success -> {
                navController.navigate("home")
            }

            is SignUpState.Error -> {
                Toast.makeText(context,"Sign in failed", Toast.LENGTH_SHORT).show()

            }
            else -> Unit
        }
    }

    Scaffold(modifier = Modifier.fillMaxSize(), containerColor = Color.Black) {
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

            Text(text = "Chatter", fontSize = 32.sp, color = Color.Gray, fontWeight = FontWeight.SemiBold)

           Spacer(modifier = Modifier.height(24.dp))


            OutlinedTextField(value = name, onValueChange = {name = it},
                modifier = Modifier.fillMaxWidth()
                    .padding(horizontal = 36.dp),
                label = {Text(text = "Full Name")},
                shape = RoundedCornerShape(16.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.Transparent,
                    unfocusedBorderColor = Color.Transparent,
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                    focusedLabelColor = Color.White,
                    unfocusedLabelColor = Color.Gray,
                    cursorColor = Color.White,
                    focusedContainerColor = DarkGrey,
                    unfocusedContainerColor = DarkGrey
                ),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(8.dp))



            OutlinedTextField(value = email, onValueChange = {email = it},
                modifier = Modifier.fillMaxWidth()
                    .padding(horizontal = 36.dp),
                label = {Text(text = "Email")},
                shape = RoundedCornerShape(16.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.Transparent,
                    unfocusedBorderColor = Color.Transparent,
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                    focusedLabelColor = Color.White,
                    unfocusedLabelColor = Color.Gray,
                    cursorColor = Color.White,
                    focusedContainerColor = DarkGrey,
                    unfocusedContainerColor = DarkGrey
                ),
                singleLine = true
            )


            Spacer(modifier = Modifier.height(8.dp))


            OutlinedTextField(value = password, onValueChange = {
                password = it
            },
                modifier = Modifier.fillMaxWidth()
                    .padding(horizontal = 36.dp),
                label = {Text(text = "Password")},
                visualTransformation = PasswordVisualTransformation(),
                shape = RoundedCornerShape(16.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.Transparent,
                    unfocusedBorderColor = Color.Transparent,
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                    focusedLabelColor = Color.White,
                    unfocusedLabelColor = Color.Gray,
                    cursorColor = Color.White,
                    focusedContainerColor = DarkGrey,
                    unfocusedContainerColor = DarkGrey
                ),
                singleLine = true)

            Spacer(modifier = Modifier.height(8.dp))


            OutlinedTextField(value = confirm, onValueChange = {
                confirm = it
            },
                modifier = Modifier.fillMaxWidth()
                    .padding(horizontal = 36.dp),
                label = {Text(text = "Confirm Password")},
                shape = RoundedCornerShape(16.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.Transparent,
                    unfocusedBorderColor = Color.Transparent,
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                    focusedLabelColor = Color.White,
                    unfocusedLabelColor = Color.Gray,
                    cursorColor = Color.White,
                    focusedContainerColor = DarkGrey,
                    unfocusedContainerColor = DarkGrey
                ),
                singleLine = true,
                isError = password.isNotEmpty() && confirm.isNotEmpty() && password != confirm)

            Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = {
                        viewModel.signUp(name, email, password)
                    },
                    enabled = name.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty() && confirm.isNotEmpty() && password == confirm,
                    modifier = Modifier.width(100.dp)
                        .height(50.dp),
                    colors = ButtonColors(
                        containerColor = DarkGrey,
                        contentColor = Color.White,
                        disabledContainerColor = DarkGrey,
                        disabledContentColor = Color.White
                    )
                ) {
                    Text(text = "SignUp",
                        fontSize = 14.sp,
                        textAlign = TextAlign.Center,
                        maxLines = 1)
                }

                Spacer(modifier = Modifier.height(40.dp))

                TextButton(onClick = { navController.popBackStack() }) {
                   Text(text = buildAnnotatedString {
                       withStyle(style = SpanStyle(color = Color.Gray)){
                           append("Already have an account? ")
                       }
                       withStyle(style = SpanStyle(color = Color.LightGray, fontWeight = FontWeight.ExtraBold)){
                           append("Sign In")
                       }
                   }, style = MaterialTheme.typography.titleSmall)

                }
            }

        }

    }
