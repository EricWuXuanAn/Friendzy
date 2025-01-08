package com.example.tip102group01friendzy.ui.feature.customer

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.tip102group01friendzy.R
import com.example.tip102group01friendzy.Screen

@Composable
fun Favorite_and_BlackListScreen(
    navController: NavHostController,
    favorite_and_blacklistVM: Favorite_and_Black_ListVM
) {
    var tabIndex by remember { mutableStateOf(0) }
    val tab = listOf(
        stringResource(R.string.favotite),
        stringResource(R.string.blackList)
    )
    val favListState by favorite_and_blacklistVM.favoriteListState.collectAsState()
    val blackListState by favorite_and_blacklistVM.blackListState.collectAsState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TabRow(
            selectedTabIndex = tabIndex
        ) {
            tab.forEachIndexed { index, title ->
                Tab(
                    text = { Text(text = title, softWrap = false) },
                    selected = index == tabIndex,
                    onClick = { tabIndex = index },
                    selectedContentColor = colorResource(R.color.teal_700),
                    unselectedContentColor = Color.Gray
                )
            }

        }
        when (tabIndex) {
            0 -> getFavList(favaLists = favListState, onClick = {Screen.MemberScreen.name}, navController = navController)
            1 -> getBlackList(blackLists = blackListState, onClick = {/*發請求叫後端做動作刪除 VM要多刪除方法*/})
        }
    }
}

//建立一個函式來去VM中拿取最新資料
@Composable
fun getFavList(
    favaLists: List<Favorite_List>,
    onClick: (Favorite_List) -> Unit,
    navController:NavHostController
) {
    LazyColumn {
        items(favaLists) { favaList ->
            ListItem(
                modifier = Modifier.clickable { onClick(favaList) },
                headlineContent = {
                    Text(
                        text = "memberID: ${favaList.be_hunted}\n member Name: ${favaList.be_hunted_name}",
                        fontSize = 14.sp
                    )
                },
                leadingContent = {
                    Image(
                        modifier = Modifier.size(70.dp),
                        painter = painterResource(R.drawable.ic_launcher_foreground),
                        contentDescription = "Image"
                    )
                },
                trailingContent = {
                    IconButton(
                        modifier = Modifier.size(20.dp),
                        onClick = {
//                            navController.navigate("${Screen.ChatMessageScreen.name}/${chatroom.room_no}")
                        }
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.chat),
                            contentDescription = "chat"
                        )
                    }
//                    Icon(
//                        modifier = Modifier
//                            .size(20.dp)
//                            .clickable { navController.navigate(Screen.ChatroomScreen.name) },
//                        painter = painterResource(id = R.drawable.chat),
//                        contentDescription = "chat"
//                    )
                }
            )

        }
    }
}


//建立一個函式去VM中拿取資料
@Composable
fun getBlackList(
    blackLists: List<Black_List>,
    onClick: (Black_List) -> Unit
) {
    LazyColumn {
        items(blackLists) { balckList ->

            ListItem(
                modifier = Modifier.clickable { onClick(balckList) },
                headlineContent = {
                    Text(
                        text = "memberID: ${balckList.user_id} \n Being block reason: \n${balckList.blacklist_reason}",
                        fontSize = 14.sp
                    )
                },
                leadingContent = {
                   Image(
                       modifier = Modifier.size(70.dp),
                       painter = painterResource(R.drawable.ic_launcher_foreground),
                       contentDescription = "image"
                   )
                },
                trailingContent = {
                    Column(
                        //為何不會靠底下？
                        verticalArrangement = Arrangement.Bottom
                    ) {
                        IconButton(onClick = {}) {
                            Icon(
                                Icons.Default.Delete,
                                "remove block"
                            )
                        }
                        Text("Unblock")

                    }
                }
            )
            HorizontalDivider()
        }

    }
}


@Composable
@Preview(showBackground = true)
fun Favorite_and_BlackListScreenPreview() {
    Favorite_and_BlackListScreen(rememberNavController(), Favorite_and_Black_ListVM())
}