package com.example.tip102group01friendzy.ui.feature.chat

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.tip102group01friendzy.R
import com.example.tip102group01friendzy.ui.theme.TIP102Group01FriendzyTheme
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


@Composable
fun ChatMessageScreen(
    navController: NavHostController,
    chatMessageViewModel: ChatMessageViewModel = viewModel(),
    roomNo: Int,
    currentUserId: Int
) {
    var chatMessage by remember { mutableStateOf("") }
    val messages by chatMessageViewModel.messages.collectAsState()

    LaunchedEffect(roomNo) {
        chatMessageViewModel.loadMessages(roomNo)
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom,
        modifier = Modifier
            .fillMaxSize()
    ) {
        LazyColumn (
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(horizontal = 8.dp)
        ){
            items(messages){ message ->
                val isCurrentUser = message.senderId == currentUserId
                MessageBubble(
                    message = message,
                    isCurrentUser = isCurrentUser
                )

            }
        }
            OutlinedTextField(
                value = chatMessage,
                onValueChange = { chatMessage = it },
                label = { Text(text = stringResource(R.string.input)) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                trailingIcon = {
                    Icon(
                        painter = painterResource(R.drawable.send),
                        contentDescription = "send",
                        modifier = Modifier.clickable {
                            if (chatMessage.isNotBlank()){
                                chatMessageViewModel.sendMessage(
                                    roomNo = roomNo,
                                    content = chatMessage,
                                    senderId = currentUserId
                                )
                                chatMessage=""
                            }
                        }
                    )
                },
            )
    }
}

@Composable
fun MessageBubble(
    message: FirebaseMessage,
    isCurrentUser: Boolean
){
    Column (
        horizontalAlignment = if (isCurrentUser) Alignment.End else Alignment.Start,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ){
        Surface (
            shape = RoundedCornerShape(8.dp),
            color = if(isCurrentUser) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary,
            modifier = Modifier
                .padding(horizontal = 8.dp)
        ){
            Column (modifier = Modifier.padding(8.dp)) {
                Text(
                    text = message.content,
                    color = Color.White,
                    modifier = Modifier
                        .padding(8.dp)
                )
                Text(
                    text = SimpleDateFormat("HH:mm", Locale.getDefault())
                        .format(Date(message.timestamp)),
                    color = Color.White.copy(alpha = 0.7f),
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier
                        .padding(top = 4.dp)
                )
            }
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun ChatMessageScreenPreview() {
//    TIP102Group01FriendzyTheme {
//        ChatMessageScreen(rememberNavController())
//    }
//}