package com.example.tip102group01friendzy.ui.feature.customer

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
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
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.tip102group01friendzy.R
import com.example.tip102group01friendzy.Screen
import com.example.tip102group01friendzy.TabVM
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomerScreen(
    navController: NavHostController, customerVM: CustomerVM, postVM: PostVM, tabVM:TabVM
) {
    var text by remember { mutableStateOf("") } //使用者的型態文字串
    var accountStatus by remember { mutableStateOf(false) } //設定要顯示什麼身份
//    val dateFormat = ofPattern("YYYY-MM-YY")
//    var selectDate by remember { mutableStateOf(LocalDate.now().format(dateFormat)) }
//    var showDatePickerDialog by remember { mutableStateOf(false) }
    var inputText by remember { mutableStateOf("") } //搜尋功能使用的
    val postListState by customerVM.recommendPostListState.collectAsState()
    val postlists = postListState.filter { it.poster_status == 1 }
    Log.d("tag_custmerScreen","${postlists}")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
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
                if (accountStatus == false) {
                } else {
                    navController.navigate(Screen.CompanionScreen.name)
                }
            }

            Text(text = "Customer")

            IconButton(
                onClick = {

                },
            ) { Icon(Icons.Filled.Notifications, contentDescription = "Notification") }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            OutlinedTextField(value = inputText,
                onValueChange = { inputText = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp),
                leadingIcon = {
                    Icon(imageVector = Icons.Default.Search, contentDescription = "Search")
                },
                trailingIcon = {
                    Icon(imageVector = Icons.Default.Clear,
                        contentDescription = "Cancel",
                        modifier = Modifier.clickable { inputText = "" })
                },
                singleLine = true,
                shape = RoundedCornerShape(15.dp),
                placeholder = { Text(stringResource(R.string.search_special)) }

            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                IconButton(onClick = {
                    navController.navigate(route = Screen.OrderScreen.name)
                }) {
                    Icon(painter = painterResource(R.drawable.orderlist), "order list")
                }
                Text(text = stringResource(R.string.order_manage))
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                IconButton(onClick = {
                    navController.navigate(Screen.Favorite_and_BlackListScreen.name)
                }) {
                    Icon(painter = painterResource(R.drawable.blacklist), "black list")
                }
                Text(text = stringResource(R.string.blackList))
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                IconButton(onClick = {
                    navController.navigate(Screen.Favorite_and_BlackListScreen.name)
                }) {
                    Icon(imageVector = Icons.Default.FavoriteBorder, "favorite list")
                }
                Text(text = stringResource(R.string.collection))
            }
        }

        HorizontalDivider(
            modifier = Modifier.padding(top = 2.dp), color = colorResource(R.color.teal_700)
        )
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp),
            text = stringResource(R.string.recommemd),
            textAlign = TextAlign.Start,
            fontSize = 28.sp
        )
        postList(
            postlist = postlists.filter { it.service.contains(inputText, true) },
            onClick = {
                navController.navigate(route ="${Screen.ReservationScreen.name}/${it.service_id}")
            }
        )
    }


}

@Composable
fun switch(
    check: Boolean,
    onCheckChange: (Boolean) -> Unit,

    ) {
    Switch(
        checked = check, onCheckedChange = onCheckChange, thumbContent = {
            if (check) {
                Icon(
                    Icons.Filled.AccountCircle,
                    contentDescription = "Customer",
                    modifier = Modifier.size(SwitchDefaults.IconSize)
                )
            } else {
                Icon(
                    Icons.Filled.AccountBox,
                    contentDescription = "Companion",
                    modifier = Modifier.size(SwitchDefaults.IconSize)
                )
            }
        }, colors = SwitchDefaults.colors(
            checkedThumbColor = Color.White,
            checkedTrackColor = colorResource(R.color.purple_200),
            uncheckedThumbColor = Color.Gray,
            uncheckedTrackColor = colorResource(R.color.green_200)
        )
    )
}


@Composable
//拿到顧客資訊 目前為假資料 之後要從資料庫抓
fun postList(
    postlist: List<Post>, onClick: (Post) -> Unit
) {
    LazyColumn {
        items(postlist) { post ->
            ListItem(
                modifier = Modifier.clickable { onClick(post) },
                overlineContent = { Text(text = "ServiceID: ${post.service_id.toString()}", fontSize = 18.sp) },
                headlineContent = { Text(text = "Service Title: ${post.service}", fontFamily = FontFamily.Default) },
                supportingContent = {

                    val formattedDate = post.start_time.let { startTime ->
                        Instant.ofEpochMilli(startTime)
                            .atZone(ZoneId.systemDefault())
                            .toLocalDate()
                            .format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
                    }
                    Text(text = "Start at: ${formattedDate}")
                },
                leadingContent = {
                    Image(
                        modifier = Modifier
                            .size(50.dp)
                            .padding(end = 10.dp),
                        painter = painterResource(R.drawable.buddy),
                        contentDescription = "memberPhoto"
                    )
                }

            )
            HorizontalDivider(modifier = Modifier.padding(top = 5.dp))
        }
    }
}


@Composable
@Preview(showBackground = true)
fun CustomerScreenPreview() {
    CustomerScreen(rememberNavController(), customerVM = CustomerVM(), postVM = PostVM(), tabVM = TabVM())
}