package com.example.tip102group01friendzy.ui.feature.chat

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.tip102group01friendzy.R
import com.example.tip102group01friendzy.Screen
import com.example.tip102group01friendzy.TabVM
import com.example.tip102group01friendzy.ui.feature.account.LoginScreen
import com.example.tip102group01friendzy.ui.feature.account.LoginViewModel
import com.example.tip102group01friendzy.ui.feature.account.RegisterScreen
import com.example.tip102group01friendzy.ui.feature.account.RegisterViewModel
import java.text.SimpleDateFormat
import java.util.Locale


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatroomScreen(
    navController: NavHostController,
    chatroomViewModel: ChatroomViewModel = viewModel(),
    tabVM: TabVM = TabVM()
) {

    tabVM.tabBarState(true)
    var searchChatroom by remember { mutableStateOf("") }
    val chatrooms by chatroomViewModel.chatroomState.collectAsState()
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())

    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally

    ) {
        Row {
        Text(text = stringResource(R.string.chatroom))
        Button(onClick = {
            Log.d("tag_", "ChatroomScreen1")
            navController.navigate(Screen.ChatMessageScreen.name)
            Log.d("tag_", "ChatroomScreen2")
        })
         { Text(text = "test") }}
        Column(
            modifier = Modifier
                .fillMaxWidth(0.9f)
        ) {
            OutlinedTextField(
                value = searchChatroom,
                onValueChange = { searchChatroom = it },
                label = { Text(text = stringResource(R.string.search)) },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "search"
                    )
                },
                singleLine = true,
                shape = RoundedCornerShape(15.dp),
                modifier = Modifier
                    .fillMaxWidth()
            )
        }
        Column(
            modifier = Modifier
                .fillMaxWidth(0.9f)
        ) {
            ChatroomLists(
                chatrooms.filter { it.UserTwoName.contains(searchChatroom, true) },
                onClick = {
                    // TODO:跳轉到該聊天室
                }
            )
        }

    }
}

@Composable
fun ChatroomLists(
    chatrooms: List<Chatroom>,
    onClick: (Chatroom) -> Unit
) {
    LazyColumn(
        contentPadding = PaddingValues(),
        modifier = Modifier

    ) {
        items(chatrooms) { chatroom ->
            // 用來建立Lists內容物
            ListItem(
                modifier = Modifier.clickable {
                    onClick(chatroom)
                },
                overlineContent = { Text(text = chatroom.roomNo) },
                headlineContent = { Text(chatroom.UserTwoName) },
                leadingContent = {
                    Image(
                        painter = painterResource(id = chatroom.image),
                        contentDescription = "chatroom",
                        modifier = Modifier
                            .size(50.dp)
                            .clip(CircleShape)
                            .border(BorderStroke(1.dp, Color(0x3C645959)), CircleShape),
                        contentScale = ContentScale.Crop
                    )
                },
                trailingContent = {
                    Text(text = formatDatetime(chatroom.lastMessageTime))
                }
            )
        }
    }
}

fun formatDatetime(datetime: String): String {
    val inputFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
    val outputFormat = SimpleDateFormat("yyyy-MM-dd HH:mm a", Locale.getDefault())

    return try {
        val date = inputFormat.parse(datetime)
        if (date != null) {
            outputFormat.format(date)
        } else {
            datetime
        }
    } catch (e: Exception) {
        datetime
    }
}


//@Preview(showBackground = true)
//@Composable
//fun ChatroomScreenPreview() {
//    TIP102Group01FriendzyTheme {
//        ChatroomScreen(rememberNavController(), chatroomViewModel = ChatroomViewModel(), tabVM=TabVM())
//    }
//}