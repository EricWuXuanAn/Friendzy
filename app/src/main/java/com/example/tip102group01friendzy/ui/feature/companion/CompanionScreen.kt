package com.example.tip102group01friendzy.ui.feature.companion

import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.tip102group01friendzy.R
import com.example.tip102group01friendzy.Screen
import com.example.tip102group01friendzy.TabVM
import com.example.tip102group01friendzy.ui.feature.customer.switch

//tab選項內容格式
class ScreenTabsButton(var name: String = "", var btIcon: Int = R.drawable.icon, var color:Int = R.color.white)
//背景色
val companionScenery = Color(red = 243, green = 232, blue = 224, alpha = 255)


@Composable
//陪伴者主頁
fun CompanionScreen(
    navController: NavHostController,
    companionVM: CompanionVM,
    companionMyPublishVM: CompanionMyPublishVM,
    tabVM: TabVM
    ){
    var inputText by remember { mutableStateOf("") }//搜尋內容
    var tabIndex by remember { mutableIntStateOf(2) }//使用tabs的index編號
    val companionState by companionVM.companionState.collectAsState()
    val MyPublishState by companionMyPublishVM.publishListState.collectAsState()
    var testIten by remember { mutableStateOf("") }//測試用

    var accountStatus by remember { mutableStateOf(true) }
    var text by remember { mutableStateOf("") }


    val tabs :List<ScreenTabsButton> =listOf(//tab選項內容
        ScreenTabsButton("訂單管理",R.drawable.order_manage,R.color.teal_700),
//        Tabs("可約時間",R.drawable.date_range,R.color.teal_700),
//        Tabs(),
//        Tabs("申請項目",R.drawable.check_list)
    )

    Column (
        modifier = Modifier.fillMaxSize()
            .background(companionScenery)
    ){  }
    Column (
        modifier = Modifier.fillMaxSize().padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        //切換身分列
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            switch(
                check = accountStatus
            ) {
                accountStatus = it
                if (!accountStatus){
                    navController.navigate(Screen.CustomerScreen.name){
                        popUpTo(Screen.CompanionScreen.name){inclusive = true}
                    }
                }


            }
//            text = when(accountStatus){
//                false ->"Customer"
//                true ->"Companion"
//            }
            Text(
                text = "Companion"
            )
            IconButton(
                onClick = {},
            ) { Icon(Icons.Filled.Notifications, contentDescription = "Notification") }
        }
        /*
        //↑保留
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
        */
        //搜尋框
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(//搜尋輸入框
                value = inputText,
                onValueChange = {inputText = it},
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp),
                leadingIcon = {
                    Icon(imageVector = Icons.Default.Search,
                        contentDescription = "Search",
                        modifier = Modifier.clickable {

                        }
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
        }
        //＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝
        HorizontalDivider(
            modifier = Modifier.padding(top = 4.dp ,bottom = 10.dp), color = colorResource(R.color.teal_700)
        )
        Row (//按鈕列
            modifier = Modifier.fillMaxWidth()
                .padding(4.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            tabs.forEachIndexed{index, tabsList ->
                Column (
                    modifier = Modifier
                        .padding(start = 4.dp)
                        .clickable {
                            when(index){
                                0 ->{ navController.navigate(route = Screen.CompanionOrderListScreen.name) }
                            }
                        },
                    horizontalAlignment = Alignment.CenterHorizontally,
                ){
                    Icon(
                        painter = painterResource(tabsList.btIcon),
                        contentDescription = tabs[index].name,
                    )
                    Text(
                        text = tabs[index].name
                    )
                }
            }
        }
        /*
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
 */
        HorizontalDivider(
            modifier = Modifier.padding(top = 10.dp), color = colorResource(R.color.teal_700)
        )
        //＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.Start
        ) {
            Text("我的刊登項目", fontSize = 28.sp)
            Spacer(modifier = Modifier.padding(4.dp))
            //服務項目清單
            MyPublishList(
                publishs = MyPublishState.filter { it.startTime.contains(inputText,true) },
                onClick = {
                    companionMyPublishVM.setMyPublish(it)
//                    navController.navigate(Screen.CompanionLookPublish.name)
                },
                iconOnClick = {

                }
            )
        }
    }
}
///*
//推薦的顧客項目列表
@Composable
fun MyPublishList(
    publishs:List<MyPublish>,
    onClick:(MyPublish) ->Unit,
    iconOnClick:() ->Unit,
){
    LazyColumn (
        modifier = Modifier.fillMaxSize()
    ){
        items(publishs) { publish ->
            ListItem(
                modifier = Modifier.clickable { onClick(publish) },
                overlineContent = { Text(text = publish.serviceTitle, fontSize = 18.sp)},
                headlineContent = { Text(text = publish.serviceDetail, fontFamily = FontFamily.Default)},
                supportingContent = { Text(text = publish.startTime)},
                colors =  ListItemDefaults.colors(
                    containerColor = companionScenery),
//                leadingContent = {
//                    Image(
//                        modifier = Modifier.size(80.dp),
//                        painter = painterResource(id = companion.memberImg),
//                        contentDescription = "memberPhoto",
//                        contentScale = ContentScale.FillBounds
//                    )
//                },
                trailingContent = {
                    Icon(
                        painter = painterResource(R.drawable.delete),
                        contentDescription = "delete",
                        modifier = Modifier.padding(8.dp).size(40.dp)
                            .clickable { iconOnClick }
                    )
                }
            )
            HorizontalDivider()//分隔線
        }
    }
}
// */



@Composable
@Preview(showBackground = true)
fun PreviewCompanionScreen(){
    CompanionScreen(rememberNavController(), companionVM = CompanionVM(), companionMyPublishVM = CompanionMyPublishVM(),tabVM = TabVM())
}