package com.example.tip102group01friendzy.ui.feature.companion

import android.content.Context
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.tip102group01friendzy.R
import com.example.tip102group01friendzy.Screen
import com.example.tip102group01friendzy.TabVM

class OrderTabsButton(
    var name: String = "",
    var btIcon: Int = R.drawable.icon,
    var title: String = ""
)
//陪伴者訂單管理
@Composable
fun CompanionOrderListScreen(
    navController: NavController,
    companionOrderVM: CompanionOrderVM,
    companionApplicantVM: CompanionApplicantVM,
    tabVM: TabVM
) {
    val context = LocalContext.current
    val preferences = context.getSharedPreferences("settings", Context.MODE_PRIVATE)
    val memberNo = preferences.getInt("member_no", 0)
    Log.d("_tab","${memberNo}")

    var tabIndex by remember { mutableIntStateOf(0) }
    var testText by remember { mutableStateOf("") }
    val appointmentState by companionApplicantVM.appointmentState.collectAsState()
    val orderState by companionOrderVM.orderListState.collectAsState()
//    val uncomfirm = orderState.filter { it.orderStatus == 0 && it.serviceStatus == 0}
    val uncomfirm = orderState.filter { it.orderStatus == 0 }
    val inProfress = orderState.filter { it.orderStatus == 1 }
    val completed = orderState.filter { it.orderStatus == 2 || it.orderStatus == 3 }
//    val myReservation = appoState.filter { it.accountId == memberNo && it.applyStatus == 0}
    val myReservation = appointmentState.filter { it.accountId == memberNo}
//    val myRequest = appoState.filter { it.orderPoster ==  memberNo && it.applyStatus == 0}
    val myRequest = appointmentState.filter { it.orderPoster ==  memberNo}

    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        Log.d("TAG","getOrderList")
        companionOrderVM.getOrderList(memberNo)
        companionOrderVM.reSelectOrder()
        companionApplicantVM.getApplicantList(memberNo)
    }


    //tab列資訊
    val tabList = listOf(
        OrderTabsButton(stringResource(R.string.order_List), R.drawable.order_manage, "全部"),
        OrderTabsButton(stringResource(R.string.unconfirm), R.drawable.unconirm, "待確認"),
        OrderTabsButton(stringResource(R.string.in_progress), R.drawable.inprogress, "進行中"),
        OrderTabsButton(stringResource(R.string.complete), R.drawable.check_circle, "已完成"),
        OrderTabsButton(stringResource(R.string.myreservation), R.drawable.date_range, "來應徵"),
        OrderTabsButton(stringResource(R.string.my_request), R.drawable.request_24, "我應徵"),//要再改
    )

    Column (
        modifier = Modifier
            .fillMaxSize()
            .background(companionScenery)
    ) {}
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(companionScenery),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 8.dp)
                .background(companionScenery)
        ) {
            //tab列
            ScrollableTabRow(
                selectedTabIndex = tabIndex,
                containerColor = companionScenery,
            ) {
                tabList.forEachIndexed { index, tabs ->
                    Tab(
                        modifier = Modifier
                            .padding(4.dp)
                            .background(companionScenery),
                        selected = index == tabIndex,
                        onClick = { tabIndex = index },
                    ) {
                        Column(
                            modifier = Modifier.padding(4.dp),
                            verticalArrangement = Arrangement.SpaceBetween,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Icon(
                                painter = painterResource(tabs.btIcon),
                                contentDescription = tabs.name
                            )
                            Text(
                                text = tabs.name,
                                textAlign = TextAlign.Center,

                                )
                        }
                    }
                }
            }
        }
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(testText, fontSize = 24.sp)
            when (tabIndex) {
                0 -> {//全部
                    if(orderState.isEmpty()){
                        testText = "此欄位沒有訂單"
                    }else{
                        testText = ""
                    }

                    CompanionOrderLazy(
//                        orders = orderState.filter { it.serviceStatus != 0},
                        orders = orderState,
                        onClick = {
                            /*
                            //要再判斷我預約和預約我的到另一頁
//                            if (it.orderStatus!! == 1 && it.serviceStatus == 0){
                            if (it.orderStatus!! == 1){
//                                    companionOrderVM.getSelectOrder(memberNo,it.orderPoster!!,it.orderId!!)
//                                    Log.d("_OrderDt","Details：$it")
//                                    Log.d("_OrderMember","MemberNo：$memberNo")
                                    navController.navigate(Screen.CompanionCheckAppointmentScreen.name+"/${it.orderPoster}/${it.orderId}")
                            }else{
//                                    companionOrderVM.getSelectOrder(memberNo,it.orderPoster!!,it.orderId!!)
//                                    Log.d("_OrderDt","Details：$it")
//                                    Log.d("_OrderMember","MemberNo：$memberNo")
                                    navController.navigate(Screen.CompanionOrderDetailsScreen.name+"/${it.orderPoster}/${it.orderId}")
                            }
                            */
                            navController.navigate(Screen.CompanionOrderDetailsScreen.name+"/${it.orderPoster}/${it.orderId}")
                        }
                    )
                }

                1 -> {//待確認
                    if(uncomfirm.isEmpty()){
                        testText = "此欄位沒有訂單"
                    }else{
                        testText = ""
                    }
                    CompanionOrderLazy(
                        orders = uncomfirm,
                        onClick = {
//                            companionOrderVM.getSelectOrder(memberNo,it.orderPoster!!,it.orderId!!)
                            navController.navigate(Screen.CompanionOrderDetailsScreen.name+"/${it.orderPoster}/${it.orderId}")
                        }
                    )
                }

                2 -> {//進行中
                    if(inProfress.isEmpty()){
                        testText = "此欄位沒有訂單"
                    }else{
                        testText = ""
                    }
                    CompanionOrderLazy(
                        orders = inProfress,
                        onClick = {
//                            Log.d("_tag","memberNo：$memberNo , orderPoster： ${it.orderPoster} , orderId：${it.orderId}")
//                            companionOrderVM.getSelectOrder(memberNo,it.orderPoster!!,it.orderId!!)
                            navController.navigate(Screen.CompanionOrderDetailsScreen.name+"/${it.orderPoster}/${it.orderId}")
                        }
                    )
                }

                3 -> {//已完成&取消
                    if(completed.isEmpty()){
                        testText = "此欄位沒有訂單"
                    }else{
                        testText = ""
                    }
                    CompanionOrderLazy(
                        orders = completed,
                        onClick = {
//                            companionOrderVM.getSelectOrder(memberNo,it.orderPoster!!,it.orderId!!)
                            navController.navigate(Screen.CompanionOrderDetailsScreen.name+"/${it.orderPoster}/${it.orderId}")
                        }
                    )
                }

                4 -> {//來應徵
                    if(myReservation.isEmpty()){
                        testText = "此欄位沒有訂單"
                    }else{
                        testText = ""
                    }
                    CompanionApplicantLazy(
                        orders = myReservation,
                        onClick = {
//                            companionApplicantVM.getApplicantSelect(memberNo,it.accountId!!,it.serviceId!!)
                            navController.navigate(Screen.CompanionOrderDetailsScreen.name+"/${it.orderPoster}/${it.orderId}")
                        }
                    )
                }

                5 -> {//我應徵
                    if(myRequest.isEmpty()){
                        testText = "此欄位沒有訂單"
                    }else{
                        testText = ""
                    }
                    CompanionApplicantLazy(
                        orders = myRequest,
                        onClick = {
//                            companionApplicantVM.getApplicantSelect(memberNo,it.accountId!!,it.serviceId!!)
                            navController.navigate(Screen.CompanionCheckAppointmentScreen.name+"/${it.accountId}/${it.serviceId}")
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun CompanionOrderLazy(
    orders: List<CompanionOrder>,
    onClick: (CompanionOrder) -> Unit
) {
    LazyColumn {
        items(orders) { order ->
            ListItem(
                modifier = Modifier.clickable { onClick(order) },
                colors = ListItemDefaults.colors(containerColor = companionScenery),
                overlineContent = {
                    Row (
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ){
                        Text(text = "訂單編號: ${order.orderId}", fontSize = 18.sp)
//                        Text(text = "金額：${order.orderPrice}",fontSize = 18.sp,)
                        Text(text = "狀態：${
                            when(order.orderStatus){
                                0->"待確認"
                                1->"進行中"
                                2->"已完成"
                                3->"已取消"
                                else ->"null"
                            }}",
                            fontSize = 18.sp,
                        )
                    }
                },
                headlineContent = { Text(text = "訂單標題: ${order.service}") },
                supportingContent = {
                    Row (
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ){
                        Text(text = "訂購人:${order.orderPersonName}")
                        Text(text = "開始時間：${formatTimestamp(order.startTime)}")
                    }
                },
            )
            HorizontalDivider()
        }
    }
}
@Composable
fun CompanionApplicantLazy(
    orders: List<Applicant>,
    onClick: (Applicant) -> Unit
) {
    LazyColumn {
        items(orders) { order ->
            ListItem(
                modifier = Modifier.clickable { onClick(order) },
                colors = ListItemDefaults.colors(containerColor = companionScenery),
                headlineContent = { Text(text = "訂單標題: ${order.service}") },
                overlineContent = {
                    Row (
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ){
                        Text(text = "訂單編號: ${order.orderId}", fontSize = 18.sp)
//                        Text(text = "狀態：${
//                            when(order.orderStatus){
//                                0->"待確認"
//                                1->"進行中"
//                                2->"已完成"
//                                3->"已取消"
//                                else ->"null"
//                            }}",
//                            fontSize = 18.sp,
//                        )
                    }
                },
                supportingContent = {
                    Row (
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ){
                        Text(text = "預約人:${order.accountName}")
                        Text(text = "開始時間：${formatTimestamp(order.startTime!!)}")
                    }
                },
            )
            HorizontalDivider()
        }
    }
}


@Composable
@Preview(showBackground = true)
fun PreviewCompanionOrderListScreen() {
    CompanionOrderListScreen(
        navController = rememberNavController(),
        companionOrderVM = CompanionOrderVM(),
        companionApplicantVM = CompanionApplicantVM(),
        TabVM()
    )
}