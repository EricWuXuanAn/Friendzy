package com.example.tip102group01friendzy.ui.feature.companion

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
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
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.tip102group01friendzy.R
import com.example.tip102group01friendzy.Screen
import com.example.tip102group01friendzy.TabVM
import com.example.tip102group01friendzy.ui.feature.customer.OrderList

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
    tabVM: TabVM
) {
    var tabIndex by remember { mutableIntStateOf(0) }
    var testText by remember { mutableStateOf("") }
    val orderState by companionOrderVM.orderList.collectAsState()
    val uncomfirm = orderState.filter { it.order_Status == 0 }
    val inProfress = orderState.filter { it.order_Status == 1 }
    val completed = orderState.filter { it.order_Status == 2 }
    val reservation = orderState.filter { it.reservation == true }

    //tab列資訊
    val tabList = listOf(
        OrderTabsButton("orderlist", R.drawable.order_manage, "全部"),
        OrderTabsButton("unconfirm", R.drawable.unconirm, ""),
        OrderTabsButton("inprogress", R.drawable.inprogress, "全部"),
        OrderTabsButton("complete", R.drawable.check_circle, "全部"),
        OrderTabsButton("Reservation", R.drawable.date_range, "全部"),
        OrderTabsButton("待更新", R.drawable.icon, "全部"),//要再改
    )
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 8.dp)
        ) {
            //tab列
            ScrollableTabRow(
                selectedTabIndex = tabIndex,
            ) {
                tabList.forEachIndexed { index, tabs ->
                    Tab(
                        modifier = Modifier.padding(4.dp),
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
            modifier = Modifier.fillMaxSize()
        ) {

            Text(testText, fontSize = 24.sp)
            when (tabIndex) {
                0 -> {//全部
                    testText = "0000"
                    CompanionOrderList(
                        orders = orderState,
                        onClick = {
                            //要再判斷我預約和預約我的到另一頁

                        }
                    )
                }

                1 -> {//待確認
                    testText = "1111"
                    CompanionOrderList(
                        orders = uncomfirm,
                        onClick = {
                            navController.navigate(Screen.CompanionOrderDetailsScreen.name)
                        }
                    )
                }

                2 -> {//進行中
                    testText = "2222"
                    CompanionOrderList(
                        orders = inProfress,
                        onClick = {
                            navController.navigate(Screen.CompanionOrderDetailsScreen.name)
                        }
                    )
                }

                3 -> {//已完成
                    testText = "3333"
                    CompanionOrderList(
                        orders = completed,
                        onClick = {
                            navController.navigate(Screen.CompanionOrderDetailsScreen.name)
                        }
                    )
                }

                4 -> {//來應徵
                    testText = "4444"
                    CompanionOrderList(
                        orders = reservation,
                        onClick = {
                            navController.navigate(Screen.CompanionCheckAppointmentScreen.name)
                        }
                    )
                }

                5 -> {//我應徵
                    testText = "5555"
//                    CompanionOrderList(
//                        orders = reservation,
//                        onClick = {
//
//                        }
//                    )
                }
            }
        }
    }
}

@Composable
fun CompanionOrderList(
    orders: List<CompanionOrderList>,
    onClick: (CompanionOrderList) -> Unit
) {
    LazyColumn {
        items(orders) { order ->
            ListItem(
                modifier = Modifier.clickable { onClick(order) },
                headlineContent = { Text(text = "訂單標題: ${order.order_content}") },
                overlineContent = { Text(text = "訂單編號: ${order.orderID}", fontSize = 18.sp) },
                supportingContent = {
                    Row (
                        modifier = Modifier.fillMaxWidth()
                    ){
                        //0:訂單待確認 1:訂單進行中  2:訂單已完成  3:訂單取消
                        Text(text = "開始時間:${order.order_Person}", modifier = Modifier.fillMaxWidth())
                    }
                },
                trailingContent = {
                    Column {
                        Text(
                            text = "價格: \n${order.order_Pirce.toString()}",
                            fontSize = 14.sp,
                            textAlign = TextAlign.Center
                        )
                        Text(text = "狀態：${
                            when(order.order_Status){
                                0->"待確認"
                                1->"進行中"
                                2->"已完成"
                                3->"已取消"
                                else ->"null"
                            }}",
                            modifier = Modifier.padding(top = 12.dp)
                        )
                    }


                }
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
        companionOrderVM = CompanionOrderVM(),TabVM()
    )
}