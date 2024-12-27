package com.example.tip102group01friendzy.ui.feature.companion

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.tip102group01friendzy.R
import com.example.tip102group01friendzy.TabVM
import com.example.tip102group01friendzy.ui.feature.customer.OrderListScreen
import com.example.tip102group01friendzy.ui.feature.customer.OrderVM
import com.example.tip102group01friendzy.ui.feature.customer.switch

//tab選項內容格式
class Tabs(var name: String = "",var btIcon: Int = R.drawable.icon,var color:Int = R.color.white)

@Composable
//陪伴者主頁
fun CompanionScreen(
    navController: NavHostController = rememberNavController(),
    companionVM: CompanionVM,
    tabVM: TabVM
    ){
    var inputText by remember { mutableStateOf("") }//搜尋內容
    var tabIndex by remember { mutableIntStateOf(2) }//使用tabs的index編號
    val companionState by companionVM.companionState.collectAsState()
    var testIten by remember { mutableStateOf("") }//測試用

    var accountStatus by remember { mutableStateOf(false) }
    var text by remember { mutableStateOf("") }

    val tabs :List<Tabs> =listOf(//tab選項內容
        Tabs("訂單管理",R.drawable.order_manage,R.color.teal_700),
        Tabs("可約時間",R.drawable.date_range,R.color.teal_700),
        Tabs(),
//        Tabs("申請項目",R.drawable.check_list)
    )
    Column (
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            switch(
                check = accountStatus
            ) {
                accountStatus = it
            }
            text = when(accountStatus){
                true ->"Customer"
                false ->"Companion"
            }
            Text(
                text = text
            )
            IconButton(
                onClick = {},
            ) { Icon(Icons.Filled.Notifications, contentDescription = "Notification") }
        }//↑保留
        Column {
            //tab選項點擊功能
            when(tabIndex){
                0 ->{//訂單管理
                    OrderListScreen(
                        orderlistVM = OrderVM(),
                        navController = rememberNavController(),
                    )
                    testIten = "123"
                }
                1 ->{//可預約時間
                    testIten = "456"
                }
                2 ->{//申請項目
                    testIten = "asd"
                }
            }
        }
        // ↓點tab隱藏
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 16.dp, end = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(//搜尋輸入框
                value = inputText,
                onValueChange = {inputText = it},
                modifier = Modifier
                    .fillMaxWidth(),
                leadingIcon = {
                    Icon(imageVector = Icons.Default.Search,
                        contentDescription = "Search",
                    )
                },
                trailingIcon = {
                    Icon(imageVector = Icons.Default.Clear,
                        contentDescription ="Clear",
                        modifier = Modifier.clickable { inputText = "" }
                    )
                },
                singleLine = true,
                shape = RoundedCornerShape(15.dp),
            )
//＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝
            Spacer(modifier = Modifier.padding(4.dp))
            ScrollableTabRow(//按鈕列
                selectedTabIndex = tabIndex,
                //目前選擇選項的底線
                indicator = {tabPositions ->
                    TabRowDefaults.SecondaryIndicator(
                        Modifier.tabIndicatorOffset(tabPositions[tabIndex]),
                        color = Color.White
                    )
                }, //分隔線屬性
                divider = {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(2.dp)
                    )
                },
            ) {
                tabs.forEachIndexed{index,tabList ->
                    Tab(
                        modifier = Modifier
//                        .wrapContentHeight()
                            .size(width =65.dp , height = 65.dp )//單個選項的格子大小
                            .width(65.dp)
//                        .padding(top = 6.dp)
//                        .fillMaxHeight()
                        ,
                        selected = index == tabIndex,
                        onClick = {tabIndex = index},
                    ){//選項樣式
                        Column (
                            modifier = Modifier
                                .fillMaxWidth(0.9f)//選項和選項之間保留空隙
                                .fillMaxHeight(),//擴到最高才能置頂
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Top,
                        ){
                            Icon(
                                painter = painterResource(tabList.btIcon),
                                contentDescription = tabs[index].name,
                                tint = colorResource(id = tabList.color)
                            )
                            Text(
                                text = tabs[index].name,
                                textAlign = TextAlign.Center, //文字置中
                                modifier = Modifier.wrapContentHeight(),
                                color = colorResource(id = tabList.color)
                            )
                        }
                    }
                }
            }
            HorizontalDivider()//分隔線
//＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.Start
            ) {
                Text("推薦項目", fontSize = 28.sp)
                Spacer(modifier = Modifier.padding(4.dp))
                Text(text = testIten)//測試tab功能用
                ServicList(companions = companionState){
                    companionVM.setCompanion(it)
//                    navController.navigate(Screen.CompanionLookPublish.name)
                }
            }
        }
    }
}
///*
//推薦的顧客項目列表
@Composable
fun ServicList(
    companions:List<Companion>,
    onClick:(Companion) ->Unit
){
    LazyColumn (
        modifier = Modifier.fillMaxSize()
    ){
        items(companions) {companion ->
            ListItem(
                modifier = Modifier.clickable { onClick(companion) },
                headlineContent = { Text(text = companion.serviceTitle)},
                supportingContent = { Text(text = companion.memberName)},
                leadingContent = {
                    Image(
                        modifier = Modifier.size(80.dp),
                        painter = painterResource(id = companion.memberImg),
                        contentDescription = "memberPhoto",
                        contentScale = ContentScale.FillBounds
                    )
                },
//                trailingContent = {
//                    Icon(
//                        painter = painterResource(R.drawable.chat),
//                        contentDescription = "",
//                        modifier = Modifier.padding(8.dp).size(40.dp)
//                    )
//                }
            )
            HorizontalDivider()//分隔線
        }
    }
}
// */


@Composable
@Preview(showBackground = true)
fun PreviewCompanionScreen(){
    CompanionScreen(rememberNavController(), companionVM = CompanionVM(), tabVM = TabVM())
}