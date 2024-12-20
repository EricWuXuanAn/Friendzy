package com.example.tip102group01friendzy.ui.feature.customer

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.tip102group01friendzy.R
import kotlinx.coroutines.launch


@Composable
fun OrderListScreen(
    orderlistVM: OrderVM,
    navConrollor: NavHostController
) {
    var tabIndex by remember { mutableStateOf(3) }
    val ordeState by orderlistVM.orderList.collectAsState()
    val uncomfirm = ordeState.filter { it.order_Status == 0 }
    val inProfress = ordeState.filter { it.order_Status == 1 }
    val completed = ordeState.filter { it.order_Status == 2 }
    val tab = listOf(
        stringResource(R.string.unconfirm),
        stringResource(R.string.in_progress),
        stringResource(R.string.complete),
        stringResource(R.string.order_List)
    )
    val scope = rememberCoroutineScope()
    var snackbar = remember { SnackbarHostState() }

    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally

    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            ScrollableTabRow(
                selectedTabIndex = tabIndex,
                edgePadding = 0.dp
            ) {
                tab.forEachIndexed { index, title ->
                    Tab(
                        text = { Text(text = title, softWrap = false) },
                        selected = index == tabIndex,
                        onClick = { tabIndex = index },
                        selectedContentColor = colorResource(R.color.teal_700),
                        unselectedContentColor = Color.Gray,
                        icon = {
                            when (index) {
                                0 -> Icon(
                                    painter = painterResource(R.drawable.unconirm),
                                    contentDescription = "unconfirm"
                                )

                                1 -> Icon(
                                    painter = painterResource(R.drawable.inprogress),
                                    contentDescription = "inprogress"
                                )

                                2 -> Icon(
                                    painter = painterResource(R.drawable.check_circle),
                                    contentDescription = "complete"
                                )

                                3 -> Icon(
                                    painter = painterResource(R.drawable.orderlist),
                                    contentDescription = "orderlist"
                                )
                            }
                        }
                    )
                }
            }

        }

        HorizontalDivider(
            modifier = Modifier
                .fillMaxWidth()
        )
        when (tabIndex) {
            0 -> orderList(orders = uncomfirm, onClick = {
                scope.launch {
                    snackbar.showSnackbar("跳到訂單頁面", withDismissAction = true)
                }
            })
            1 -> orderList(orders = inProfress, onClick = {
                scope.launch {
                    snackbar.showSnackbar("跳到訂單頁面", withDismissAction = true)
                }
            })
            2 -> orderList(orders = completed, onClick = {scope.launch {
                snackbar.showSnackbar("跳到訂單頁面", withDismissAction = true)
            }})
            3 -> orderList(orders = ordeState, onClick = {scope.launch {
                snackbar.showSnackbar("跳到訂單頁面", withDismissAction = true)
            }})
        }
        SnackbarHost(hostState = snackbar)
    }
}

@Composable
fun orderList(
    orders: List<OrderList>,
    onClick: (OrderList) -> Unit
) {
    LazyColumn {
        items(orders) { order ->
            ListItem(
                modifier = Modifier.clickable { onClick(order) },
                headlineContent = { Text(text = "order Content: ${order.order_content}") },
                overlineContent = { Text(text = "Order ID: ${order.orderID}", fontSize = 18.sp) },
                supportingContent = { Text(text = "Order Person:${ order.order_Person }") },
                trailingContent = {
                    Text(
                        text = "Order Price: \n${order.order_Pirce.toString()}",
                        fontSize = 14.sp,
                        textAlign = TextAlign.Center
                    )
                }
            )
            HorizontalDivider()
        }
    }
}

@Composable
@Preview(showBackground = true)
fun OrderListScreenPreview() {
    OrderListScreen(orderlistVM = viewModel(), navConrollor = rememberNavController())
}