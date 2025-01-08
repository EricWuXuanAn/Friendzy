package com.example.tip102group01friendzy.ui.feature.customer

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.tip102group01friendzy.R
import com.example.tip102group01friendzy.Screen
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@Composable
fun ReservationScreen(
    navController: NavHostController,
    reservationVM: ReservationVM,
    service_id:Int

) {
    val dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
    val scpoe = rememberCoroutineScope()
    var selectedPost by remember { mutableStateOf<Post?>(null) }
    LaunchedEffect(Unit) {
        scpoe.launch {
             selectedPost = reservationVM.getSelectedPostList( service_id = service_id)
        }
    }
    val startTime:Long? = selectedPost?.start_time
    val finishedTime = selectedPost?.finished_time
    val startTimeFormatter: String? = startTime?.let {
        Instant.ofEpochMilli(it)
            .atZone(ZoneId.systemDefault())
            .toLocalDateTime().format(dateFormat)
    }
    val finishedTimeFormatter: String? = finishedTime?.let {
        Instant.ofEpochMilli(it)
            .atZone(ZoneId.systemDefault())
            .toLocalDateTime().format(dateFormat)
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            modifier = Modifier.padding(10.dp),
            text = "Check Him/Her Out",
            fontSize = 25.sp,
            fontFamily = FontFamily.Cursive
        )
        HorizontalDivider()
        Row(
            Modifier
                .fillMaxWidth()
                .padding(5.dp),
//            horizontalArrangement = Arrangement.Start
        ) {
            Image(
                alignment = Alignment.CenterStart,
                modifier = Modifier
                    .padding(5.dp)
                    .size(90.dp)
                    .clip(CircleShape)
                    .border(width = 2.dp, color = Color.Gray, shape = CircleShape),
                painter = painterResource(R.drawable.friendzy),
                contentDescription = "image"
            )
            Text(
                text = "Name: ${selectedPost?.member_name}",
                modifier = Modifier.padding(10.dp),
                fontSize = 18.sp
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 30.dp),
                horizontalAlignment = Alignment.End
            ) {
                Button(
                    onClick = {navController.navigate(Screen.ChatroomScreen.name)},
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorResource(R.color.purple_200),
                        contentColor = Color.DarkGray
                    )
                ) {
                    Icon(
                        modifier = Modifier.size(25.dp),
                        painter = painterResource(R.drawable.chat),
                        contentDescription = "chat"
                    )
                }
            }
        }
        HorizontalDivider()
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(350.dp)
                .padding(10.dp)
        ) {
            Text(text = "TiTle: \n${selectedPost?.service}", fontSize = 20.sp, modifier = Modifier.padding(10.dp))
            Text(text = "Content: \n${selectedPost?.service_detail}", fontSize = 20.sp, modifier = Modifier.padding(10.dp))
            Text(text = "Available: ", fontSize = 20.sp, modifier = Modifier.padding(10.dp))
            Text(
                text = "Start: ${startTimeFormatter}",
                fontSize = 20.sp,
                modifier = Modifier.padding(10.dp)
            )
            Text(
                text = "End: ${finishedTimeFormatter}",
                fontSize = 20.sp,
                modifier = Modifier.padding(10.dp)
            )
//            Text(text = "Location: $location", fontSize = 20.sp, modifier = Modifier.padding(10.dp))
            Text(text = "Price: ${selectedPost?.service_charge}", fontSize = 20.sp, modifier = Modifier.padding(10.dp))
        }
    }
    Row(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxSize(),
        horizontalArrangement = Arrangement.spacedBy(20.dp),
        verticalAlignment = Alignment.Bottom
    ) {
        Button(
            modifier = Modifier.weight(0.3f),
            colors = ButtonDefaults.buttonColors(
                containerColor = colorResource(R.color.purple_200),
                contentColor = Color.DarkGray
            ),
            onClick = {
                /*
                發請求給後端 叫後端動作 會有修改然後傳到某個table會在頂單管理出現
                應該要用一個coroutineScope包起來 裡面值請求發給後端動 然後回到上一頁
                FCM 連server 發通知
                */
            }) {
            Text(text = "Make a reservation")
        }
        Button(
            modifier = Modifier.weight(0.3f),
            colors = ButtonDefaults.buttonColors(
                containerColor = colorResource(R.color.purple_200),
                contentColor = Color.DarkGray
            ),
            onClick = {
                navController.popBackStack() //回到上一頁
            }
        ) {
            Text(text = stringResource(R.string.cancle))
        }
    }
}


@Composable
@Preview(showBackground = true)
fun ReservationScreenPreview() {
    ReservationScreen(rememberNavController(), ReservationVM(), service_id = 1)
}