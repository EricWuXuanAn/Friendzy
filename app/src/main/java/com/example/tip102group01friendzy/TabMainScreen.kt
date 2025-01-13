package com.example.tip102group01friendzy

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
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
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.tip102group01friendzy.ui.feature.Memberpage.MemberScreen
import com.example.tip102group01friendzy.ui.feature.chat.ChatroomScreen
import com.example.tip102group01friendzy.ui.feature.companion.CompanionMyPublishVM
import com.example.tip102group01friendzy.ui.feature.companion.CompanionPublishScreen
import com.example.tip102group01friendzy.ui.feature.companion.CompanionScreen
import com.example.tip102group01friendzy.ui.feature.companion.CompanionVM
import com.example.tip102group01friendzy.ui.feature.companion.companionScenery
import com.example.tip102group01friendzy.ui.feature.customer.CustomerScreen
import com.example.tip102group01friendzy.ui.feature.customer.CustomerVM
import com.example.tip102group01friendzy.ui.feature.customer.PostScreen
import com.example.tip102group01friendzy.ui.feature.customer.PostVM
import com.example.tip102group01friendzy.ui.feature.search.CompanionInfo
import com.example.tip102group01friendzy.ui.feature.search.SearchWithMapScreen
import com.google.android.gms.maps.model.LatLng

@Composable
fun TabMainScreen(
    navController: NavHostController = rememberNavController(),
    tabVM: TabVM = viewModel()
) {
    var switchState by remember { mutableStateOf(false) }
    val tabBarVisibility = tabVM.tabBarVisibility.collectAsState()
    val showTabIndex by tabVM.showTabIndex.collectAsState()
    val memberStatus = tabVM.memberStatus.collectAsState()

    Log.d("_showTabIndex", "showTabIndex:$showTabIndex")

    val tabs = listOf(
        stringResource(id = R.string.home),
        stringResource(id = R.string.service),
        stringResource(id = R.string.post),
        stringResource(id = R.string.chat),
        stringResource(id = R.string.information)
    )

    LaunchedEffect(Unit) {
        tabVM.tabBarState(true)
    }

    Column(modifier = Modifier.fillMaxWidth()) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = if (memberStatus.value) {
                Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .background(color = companionScenery)
            } else {
                Modifier
                    .fillMaxWidth()
                    .weight(1f)
            }
        ) {

            when (showTabIndex) {
                0 -> SearchWithMapScreen(
                    navController = navController,
                    defaultLocation = LatLng(25.0330, 121.5654),
                    showPopup = true,
                    onMemberSelected = { },
                    CompanionInfo("1", "Johnny", "Love to travel and explore new places", "信義區", LatLng(25.0330, 121.5654), "專長1", R.drawable.avatar3),
                    tabVM = tabVM)

                1 -> if (memberStatus.value) {
                    CompanionScreen(
                        navController = navController,
                        companionVM = CompanionVM(),
                        companionMyPublishVM = CompanionMyPublishVM(),
                        tabVM = tabVM
                    )
                } else {
                    CustomerScreen(
                        navController = navController,
                        tabVM = tabVM,
                        customerVM = CustomerVM(),
                        postVM = PostVM()
                    )
                }

                2 -> if (memberStatus.value) {
                    CompanionPublishScreen(
                        navController = navController,
                        myPublish = CompanionMyPublishVM(),
                        tabVM = tabVM
                    )
                } else {
                    PostScreen(
                        navController = navController,
                        postVM = PostVM(),
                        tabVM = tabVM
                    )
                }

                3 -> {
                    ChatroomScreen(navController = navController, tabVM = tabVM)
                }

                4 -> MemberScreen(
                    navController = navController,
                    memberVM = viewModel()
                )
            }
        }
        if (tabBarVisibility.value) {
            TabRow(
                selectedTabIndex = showTabIndex,
                containerColor = colorResource(id = R.color.green_200)
            ) {
                tabs.forEachIndexed { index, title ->
                    Tab(text = { Text(title) },
                        // 判斷此頁籤是否為選取頁籤
                        selected = index == showTabIndex,
                        // 點擊此頁籤後將選取索引改為此頁籤的索引
                        onClick = {
                            tabVM.setShowTabIndex(index)
                        },
                        // 設定選取顏色
                        selectedContentColor = colorResource(R.color.teal_700),
                        // 設定未選取顏色
                        unselectedContentColor = Color.Gray,
                        icon = {
                            when (index) {
                                0 -> Icon(
                                    painter = painterResource(R.drawable.map),
                                    contentDescription = "Home"
                                )

                                1 -> Icon(
                                    painter = painterResource(R.drawable.contact),
                                    contentDescription = "Service"
                                )

                                2 -> Icon(
                                    imageVector = Icons.Default.AddCircle,
                                    contentDescription = "Post"
                                )

                                3 -> Icon(
                                    painter = painterResource(R.drawable.baseline_chat_24),
                                    contentDescription = "Chat"
                                )

                                4 -> Icon(
                                    imageVector = Icons.Default.Person,
                                    contentDescription = "Info"
                                )
                            }
                        }
                    )
                }
            }
        }
    }
}