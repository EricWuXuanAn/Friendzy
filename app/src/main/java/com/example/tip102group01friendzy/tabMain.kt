package com.example.tip102group01friendzy

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Icon
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tip102group01friendzy.ui.feature.Memberpage.MemberScreen
import com.example.tip102group01friendzy.ui.feature.Memberpage.memberScreenPreview
import com.example.tip102group01friendzy.ui.feature.chat.ChatroomScreen
import com.example.tip102group01friendzy.ui.feature.customer.CustomerScreen
import com.example.tip102group01friendzy.ui.feature.customer.PostScreen
import com.example.tip102group01friendzy.ui.feature.search.SearchWithMap
import java.lang.reflect.Member

@Composable
fun tabMain(tabVM: TabVM = viewModel()){
    val tabBarVisibility = tabVM.tabBarVisibility.collectAsState()
    var tabIndex by remember { mutableStateOf(0) }
    val tabs = listOf(
        stringResource(id = R.string.home),
        stringResource(id = R.string.service),
        stringResource(id = R.string.post),
        stringResource(id = R.string.chat),
        stringResource(id = R.string.information)
    )

    Column (modifier = Modifier.fillMaxWidth()){
        Column (
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ){
            when(tabIndex){
//                0 -> SearchWithMap(tabVM=tabVM)
//                1 -> CustomerScreen(tabVM=tabVM)
//                2 -> PostScreen(tabVM = tabVM)
                3 -> ChatroomScreen(tabVM = tabVM)
//                4 -> MemberScreen(tabVM = tabVM)
            }
        }
        if (tabBarVisibility.value){
            TabRow(
                selectedTabIndex = tabIndex,
                containerColor = colorResource(id = R.color.green_200) ){
                    tabs.forEachIndexed{ index, title ->
                        Tab(text = { Text(title) },
                            // 判斷此頁籤是否為選取頁籤
                            selected = index == tabIndex,
                            // 點擊此頁籤後將選取索引改為此頁籤的索引
                            onClick = { tabIndex = index },
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
                                    4-> Icon(
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
@Composable
@Preview(showBackground = true)
fun view(){
    tabMain()
}