package com.example.chatter.feature.home

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
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.chatter.feature.auth.signout.SignOutViewModel
import com.example.chatter.model.Channel
import com.example.chatter.ui.theme.DarkGrey
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController){
    val viewModel : HomeViewModel = koinViewModel()
    val channels = viewModel.channels.collectAsState()
    val addChannel = remember {
        mutableStateOf(false)
    }
    val signOutViewModel : SignOutViewModel = koinViewModel()
    val signOutState = signOutViewModel.state.collectAsState()
    val sheetState = rememberModalBottomSheetState()

    var channelToDelete by remember { mutableStateOf<Channel?>(null) }



    Scaffold(
        bottomBar = {
            HomeBottomBar(onSignOut = {
                navController.navigate("signout")
            },navController)
        },
        floatingActionButton = {
            Box(
                modifier = Modifier
                    .padding(16.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(DarkGrey)
                    .clickable {
                        addChannel.value = true
                    }
            ){
                Text(
                    text = "Add Group",
                    modifier = Modifier.padding(16.dp),
                    color = Color.White
                )
            }
        },
        containerColor = Color.Black
    ) {
        Box(modifier = Modifier
            .padding(it)
            .fillMaxSize()
        ){
            LazyColumn{
                item {
                    Text(text = "Message", color = Color.Gray, style = TextStyle(fontSize = 32.sp, fontWeight = FontWeight.Black), modifier = Modifier.padding(16.dp))
                }

                item {
                    TextField(value = "", onValueChange = {},
                        placeholder = {Text(text = "Search")},
                        modifier = Modifier.fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 8.dp)
                            .clip(RoundedCornerShape(40.dp)
                            ),
                        textStyle = TextStyle(color = Color.LightGray),
                        colors = TextFieldDefaults.colors().copy(
                            focusedContainerColor = DarkGrey,
                            unfocusedContainerColor = DarkGrey,
                            focusedTextColor = Color.Gray,
                            unfocusedTextColor = Color.Gray,
                            focusedPlaceholderColor = Color.Gray,
                            unfocusedPlaceholderColor = Color.Gray,
                            focusedIndicatorColor = Color.Gray
                        ),
                        trailingIcon = { Icon(imageVector = Icons.Filled.Search, contentDescription = null) }
                        )
                }

                item { Spacer(modifier = Modifier.padding(vertical = 8.dp)) }

                items(channels.value) { channel ->
                    Column {
                        ChannelItem(channel.name,
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp),
                            onClick = {navController.navigate("chat/${channel.id}&${channel.name}")},
                            onLongClick = {channelToDelete = channel}
                        )

                    }

                }
                item { Spacer(modifier = Modifier.padding(30.dp)) }
            }

        }
    }
    if (addChannel.value){
        ModalBottomSheet(onDismissRequest = {addChannel.value = false},sheetState = sheetState) {
            AddChannelDialog {
                viewModel.addChannel(it)
                addChannel.value = false
            }
        }
    }

    channelToDelete?.let { channel ->
        DeleteConfirmationDialog(
            channelName = channel.name,
            onConfirm = {
                viewModel.deleteChannel(channel.id)},
            onDismiss = {channelToDelete = null}
        )
    }
}



@Composable
fun ChannelItem(channelName : String,modifier: Modifier,onClick : () -> Unit,onLongClick : () -> Unit){
    Row(modifier = modifier
        .fillMaxWidth()
        .clip(RoundedCornerShape(16.dp))
        .background(DarkGrey)
        .combinedClickable(
            onClick= onClick,
            onLongClick = onLongClick
        ),
        verticalAlignment = Alignment.CenterVertically) {


        Box(modifier = Modifier
                .padding(8.dp)
            .size(70.dp)
            .clip(CircleShape)
            .background(Color.Yellow.copy(alpha = 0.3f))
        ){
            Text(text = channelName[0].uppercase(),
                color = Color.White,
                style = TextStyle(fontSize = 35.sp),
                textAlign = TextAlign.Center,
                modifier = Modifier.align(Alignment.Center)

                )
        }

        Text(text = channelName, fontWeight = FontWeight.SemiBold, modifier = Modifier.padding(8.dp), color = Color.White)

    }
}

@Composable
fun AddChannelDialog(onAddChannel : (String) -> Unit){
    val channelName = remember {
        mutableStateOf("")
    }
    Column(modifier = Modifier.padding(8.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = "Add Channel")
        Spacer(modifier = Modifier.padding(8.dp))
        TextField(value = channelName.value, onValueChange = {
            channelName.value = it
        }, label = { Text(text = "Channel Name")}, singleLine = true)
        Spacer(modifier = Modifier.padding(8.dp))
        Button(onClick = { onAddChannel(channelName.value)}, modifier = Modifier.fillMaxWidth()) {
            Text(text = "Add")
        }
    }
}


@Composable
fun HomeBottomBar(onSignOut : () -> Unit,navController : NavController){
    Row(modifier = Modifier
        .fillMaxWidth()
        .height(120.dp)
        .clip(RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp))
        .background(DarkGrey)
        .padding(horizontal = 4.dp),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically) {

        Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.clickable{
            navController.navigate("home"){launchSingleTop = true}
        }) {
            Icon(imageVector = Icons.Default.Home, contentDescription = "home", tint = Color.White)
        }


        Column(horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.clickable {  }
        ) {
            Icon(imageVector = Icons.Default.Settings, contentDescription = "settings",tint = Color.White)
        }

        Column(horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.clickable(onClick = onSignOut)
        ) {
            Icon(imageVector = Icons.Default.ExitToApp,
                contentDescription = "signout",
                tint = Color.White
            )

        }

    }
}



@Composable
fun DeleteConfirmationDialog(
    channelName: String,
    onConfirm : () -> Unit,
    onDismiss : () -> Unit
){
    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = DarkGrey,
        title = {Text(text = "Delete Channel", color = Color.White)},
        text = {Text(text = "Are you sure you want to delete the Group $channelName")},
        confirmButton = {
            TextButton(onClick = {
                onConfirm()
                onDismiss()
            }) {
                Text("Delete")
            }
        }, dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}

