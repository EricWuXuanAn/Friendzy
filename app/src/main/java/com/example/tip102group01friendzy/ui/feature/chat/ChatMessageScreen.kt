package com.example.tip102group01friendzy.ui.feature.chat

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.tip102group01friendzy.R
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


@Composable
fun ChatMessageScreen(
    navController: NavHostController,
    chatMessageViewModel: ChatMessageViewModel = viewModel(),
    chatroomViewModel: ChatroomViewModel = viewModel(),
    roomNo: Int,
    currentUserId: Int
) {
    var chatMessage by remember { mutableStateOf("") }
    val messages by chatMessageViewModel.messages.collectAsState()
    val chatrooms by chatroomViewModel.chatroomState.collectAsState()
    val listState = rememberLazyListState()
    val scope = rememberCoroutineScope()

    val currentChatroom = chatrooms.find { it.room_no == roomNo }

    LaunchedEffect(messages.size) {
        if (messages.isNotEmpty()) {
            listState.animateScrollToItem(messages.size - 1)
        }
    }

    LaunchedEffect(roomNo) {
        chatMessageViewModel.loadMessages(roomNo)
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
        modifier = Modifier.fillMaxSize()
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Image(
                painter = painterResource(id = R.drawable.puzzle),
                contentDescription = "chatroom",
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .border(BorderStroke(1.dp, Color(0x3C645959)), CircleShape),
                contentScale = ContentScale.Crop,
                alignment = Alignment.Center
            )
            Spacer(modifier = Modifier.padding(10.dp))
            currentChatroom?.let {
                Text(
                    text = it.OtherUserName,
                    textAlign = TextAlign.Center,
                    fontSize = 20.sp
                )
            }
        }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Bottom,
            modifier = Modifier
                .fillMaxSize(0.95f)
        ) {
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                state = listState
            ) {
                items(messages) { message ->
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
                            if (chatMessage.isNotBlank()) {
                                chatMessageViewModel.sendMessage(
                                    roomNo = roomNo,
                                    content = chatMessage,
                                    senderId = currentUserId
                                )
                                chatMessage = ""

                            }
                        }
                    )
                },
            )
        }
    }
}

@Composable
fun MessageBubble(
    message: FirebaseMessage,
    isCurrentUser: Boolean
) {
    Column(
        horizontalAlignment = if (isCurrentUser) Alignment.End else Alignment.Start,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Surface(
            shape = RoundedCornerShape(8.dp),
            color = if (isCurrentUser) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary,
            modifier = Modifier
                .padding(horizontal = 8.dp)
        ) {
            Column(modifier = Modifier.padding(8.dp)) {
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
//        ChatMessageScreen(rememberNavController(),ChatMessageViewModel(),roomNo, currentUserId: Int)
//    }
//}