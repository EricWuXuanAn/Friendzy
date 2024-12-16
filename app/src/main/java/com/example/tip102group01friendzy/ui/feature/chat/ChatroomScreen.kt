package com.example.tip102group01friendzy.ui.feature.chat

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.tip102group01friendzy.R
import com.example.tip102group01friendzy.ui.theme.TIP102Group01FriendzyTheme


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatroomScreen(
    navController: NavController
) {
    var searchchatroom by remember { mutableStateOf("") }
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())

    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Column {
            OutlinedTextField(
                value = searchchatroom,
                onValueChange = { searchchatroom = it },
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
        ChatroomLists(fetchChatroom(), innerPadding()) { }
    }
}

@Composable
fun ChatroomLists(
    chatrooms: List<Chatroom>,
    innerPadding: PaddingValues,
    onItemClick: (Chatroom) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
    ) {
        items(chatrooms) { chatroom ->
            // 用來建立Lists內容物
            ListItem(
                modifier = Modifier.clickable {
                    onItemClick(chatroom)
                },
                overlineContent = { Text(text = chatroom.id) },
                headlineContent = { Text(chatroom.name) },
                leadingContent = {
                    Image(
                        painter = painterResource(id = chatroom.image),
                        contentDescription = "book",
                        modifier = Modifier.padding(16.dp)
                    )
                },
                trailingContent = {
                    Icon(
                        imageVector = Icons.Filled.Favorite,
                        contentDescription = "shoppingCart",
                        modifier = Modifier.padding(16.dp)
                    )
                }
            )
            HorizontalDivider()
        }
    }
}

/**
 * 載入測試需要資料
 * @return 多本書資訊
 */
fun fetchChatroom(): List<Chatroom> {
    return listOf(
        Chatroom("0001", "James Smith", R.drawable.chatroom1),
        Chatroom("0002", "Emily Johnson", R.drawable.chatroom2),
        Chatroom("0003", "Michael Brown", R.drawable.chatroom3)

    )
}

@Preview(showBackground = true)
@Composable
fun ChatroomScreenPreview() {
    TIP102Group01FriendzyTheme {
        ChatroomScreen(rememberNavController())
    }
}