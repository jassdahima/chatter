package com.example.chatter.feature.chat

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.chatter.model.Message
import com.example.chatter.ui.theme.DarkGrey
import com.example.chatter.ui.theme.Purple
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreen(navController: NavController,channelId : String,channelName : String){


    Scaffold(
        containerColor = Color.Black
    ) {
        Column(modifier = Modifier.fillMaxSize().padding(it)) {
            val viewModel : ChatViewModel = koinViewModel()
            LaunchedEffect(key1 = true) {
                viewModel.listenForMessages(channelId)
            }
            val messages = viewModel.message.collectAsState()

            ChatMessages(
                messages = messages.value,
                onSendMessage = { message ->
                    viewModel.sendMessage(channelId, message)
                },
                channelName = channelName, navController = navController
            )
        }
    }
}

@Composable
fun ChatMessages(navController : NavController,channelName: String,
    messages : List<Message>,
    onSendMessage : (String) -> Unit,
){
    val hideKeyboardController = LocalSoftwareKeyboardController.current

    val msg = remember {
        mutableStateOf("")
    }

    val listState = rememberLazyListState()



    LaunchedEffect(messages.size) {
        if (messages.isNotEmpty()){
            listState.animateScrollToItem(messages.lastIndex)
        }
    }
    Column(modifier = Modifier.fillMaxSize()) {
        Row { ChannelStatus(channelName, Modifier, navController) { } }
        LazyColumn(modifier = Modifier.weight(1f), state = listState) {
            item {
            }
            items(messages) { message ->
                ChatBubble(message = message, onLongClick = {
                },channelName

                )
            }
        }


        Row(
            modifier = Modifier.fillMaxWidth()
                .background(DarkGrey)
                .padding(3.dp),
            verticalAlignment = Alignment.Bottom,
        ) {

            IconButton(onClick = {
                msg.value = ""
            }, modifier = Modifier.padding(4.dp).weight(0.2f)) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "attach",
                    tint = Color.White
                )

            }

            OutlinedTextField(
                value = msg.value, onValueChange = { msg.value = it },
                modifier = Modifier.weight(1f).clip(RoundedCornerShape(40.dp)),
                placeholder = { Text(text = "Type a message") },
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(
                    onDone = {
                        hideKeyboardController?.hide()
                    }), colors = TextFieldDefaults.colors().copy(
                    focusedContainerColor = DarkGrey,
                    unfocusedContainerColor = Color.DarkGray,
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                    focusedPlaceholderColor = Color.White,
                    unfocusedPlaceholderColor = Color.Gray,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedLabelColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent
                )
            )



            IconButton(onClick = {
                if (msg.value.isNotBlank()) {
                    onSendMessage(msg.value)
                    msg.value = ""
                }
            }, enabled = msg.value.isNotBlank(), modifier = Modifier.padding(4.dp).weight(0.3f)) {
                Icon(
                    imageVector = Icons.Default.Send,
                    tint = Color.White,
                    contentDescription = "send"
                )
            }

        }
    }
}

@Composable
fun ChatBubble(message : Message,
               onLongClick : () -> Unit,channelName : String) {
    val isCurrentUser = message.senderId == Firebase.auth.currentUser?.uid
    val bubbleColor = if (isCurrentUser){
        Purple
    } else{
        DarkGrey
    }

    Box(modifier = Modifier.fillMaxWidth()
        .padding(horizontal = 8.dp)
       ) {
        val alignment = if (isCurrentUser) Alignment.CenterEnd else Alignment.CenterStart
                Row(
                    modifier = Modifier
                        .combinedClickable(
                            onClick = {},
                            onLongClick = onLongClick
                        )
                        .padding(10.dp)
                        .align(alignment),
                    verticalAlignment = Alignment.CenterVertically
            ){
                    if (!isCurrentUser){

                        Box(
                            modifier = Modifier
                                .padding(8.dp)
                                .size(40.dp)
                                .clip(CircleShape)
                                .background(Color.Yellow.copy(alpha = 0.3f))
                        ) {
                            Text(
                                text = channelName[0].uppercase(),
                                color = Color.White,
                                style = TextStyle(fontSize = 35.sp),
                                textAlign = TextAlign.Center,
                                modifier = Modifier.align(Alignment.Center)

                            )
                        }


                    }
                    Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = message.message.trim(),
                    color = Color.White,
                    modifier = Modifier
                        .background(color = bubbleColor, shape = RoundedCornerShape(8.dp))
                        .padding(12.dp)
                )
            }



    }
}







@Composable
fun ChannelStatus(channelName : String,modifier: Modifier,navController : NavController,onClick : () -> Unit){
    Row(modifier = modifier
        .fillMaxWidth()
        .clip(RoundedCornerShape(2.dp))
        .background(DarkGrey)
        .clickable{
            onClick()
        },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Absolute.Left) {


        Row(modifier = modifier.padding(4.dp)){IconButton(onClick = {navController.popBackStack()}) {
            Icon(imageVector = Icons.Default.ArrowBack,
                contentDescription = "back",
                tint = Color.White)
        }}


        Box(
            modifier = Modifier
                .padding(8.dp)
                .size(60.dp)
                .clip(CircleShape)
                .background(Color.Yellow.copy(alpha = 0.3f))
        ) {
            Text(
                text = channelName[0].uppercase(),
                color = Color.White,
                style = TextStyle(fontSize = 35.sp),
                textAlign = TextAlign.Center,
                modifier = Modifier.align(Alignment.Center)

            )
        }


            Text(
                text = channelName,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(8.dp),
                color = Color.White,
            )


    }
}







@Composable
fun DeleteMessageConfirmationDialog(
    onConfirm : () -> Unit,
    onDismiss : () -> Unit
){
    AlertDialog(onDismissRequest = onDismiss,
        title = {Text(text = "Delete Message")},
        text = {Text(text = "Are you you want to delete this message ")},
        confirmButton = {
            TextButton(onClick = {
                onConfirm()
                onDismiss()
            }) {
                Text("Delete")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        })
}