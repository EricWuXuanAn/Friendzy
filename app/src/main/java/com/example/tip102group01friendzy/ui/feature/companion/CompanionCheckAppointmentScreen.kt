package com.example.tip102group01friendzy.ui.feature.companion

import android.content.Context
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.tip102group01friendzy.R
import com.example.tip102group01friendzy.Screen
import com.example.tip102group01friendzy.TabVM


@Composable
//陪伴者確認預約(確認訂單)
fun CompanionCheckAppointmentScreen(
    navController: NavHostController,
    applicantVM: CompanionApplicantVM,
    comOrderVM: CompanionOrderVM,
    tabVM: TabVM,
    account: Int,
    serviceId: Int
    ) {
    val context = LocalContext.current
    val preferences = context.getSharedPreferences("settings", Context.MODE_PRIVATE)
    val memberNo = preferences.getInt("member_no", 0)

    val myAccount by applicantVM.applicantSelectState.collectAsState()
//    val order by comOrderVM.orderDetailsSelectState.collectAsState()
//    val selectOrder by remember { mutableStateOf<Applicant?>(null) }
//    val scpoe = rememberCoroutineScope()
//    LaunchedEffect(Unit) {
//        scpoe.launch {
//            companionApplicantVM.getApplicantSelect()
//        }
//    }

    LaunchedEffect(Unit) {
        applicantVM.getApplicantSelect(memberNo,account,serviceId)
    }

    Column (
        modifier = Modifier.fillMaxSize()
            .background(companionScenery)
    ){  }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        //預約人的資訊
        //預約人
        Text(
            text = "預約人",
            fontSize = 24.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            textAlign = TextAlign.Center
        )
        Row(
            modifier = Modifier
//                .fillMaxHeight(0.15f)
                .padding(top = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                alignment = Alignment.CenterStart,
                modifier = Modifier
                    .padding(5.dp)
                    .size(90.dp)
                    .clip(CircleShape)
                    .border(width = 2.dp, color = Color.Gray, shape = CircleShape),
                painter = painterResource(R.drawable.friendzy),
                contentDescription = "Image"
            )
            Column(
                modifier = Modifier
//                    .fillMaxSize()
                    .padding(start = 8.dp),
                horizontalAlignment = Alignment.End,
            ) {
                Text(
                    text = "名字：${myAccount?.accountName}",
                    fontSize = 24.sp,
                    modifier = Modifier
                        .fillMaxWidth()
//                        .fillMaxHeight(0.5f)
                        .padding(bottom = 20.dp)
                )
//                /*
                Button(
                    onClick = {
                        navController.navigate(Screen.ChatroomScreen.name)
                    },
                    modifier = Modifier
                        .fillMaxWidth(0.5f)
//                        .fillMaxHeight()
                        .padding(8.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Transparent,//背景顏色
                        contentColor = Color.Black//內容顏色
                    ),
                    border = BorderStroke(1.dp, Color.Black)//外框樣式
                ) {
                    Row(
//                        modifier = Modifier.fillMaxSize(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(text = "聊聊", modifier = Modifier.padding(end = 2.dp))
                        Icon(
                            modifier = Modifier.size(20.dp),
                            painter = painterResource(R.drawable.chat),
                            contentDescription = "聊聊",
                        )
                    }
                }
//               */
            }
        }
        HorizontalDivider(modifier = Modifier.padding(6.dp))//分隔線
        Column (
            modifier = Modifier.fillMaxWidth().padding(top = 4.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ){
            Text(text = "刊登資訊", fontSize = 28.sp, modifier = Modifier.fillMaxWidth())
            Text(text = "標題：${myAccount?.service}", fontSize = 20.sp)
            Text(text = "開始時間：${formatTimestamp(myAccount?.startTime)}", fontSize = 20.sp)
            Text(text = "結束時間：${formatTimestamp(myAccount?.endTime)}", fontSize = 20.sp)
            Text(text = "服務地區：${myAccount?.area}", fontSize = 20.sp)
            Text(text = "刊登人：${myAccount?.orderPosterName}", fontSize = 20.sp)

        }
        Column (
            modifier = Modifier.fillMaxSize().padding(4.dp),
            verticalArrangement = Arrangement.Top
        ){
            Row(
                modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                Button(
                    modifier = Modifier.padding(end = 4.dp).fillMaxWidth(0.5f),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorResource(R.color.purple_200),
                        contentColor = Color.DarkGray
                    ),
                    onClick = {
                        applicantVM.setApplicantStatus(
                            serviceId =  myAccount?.serviceId!!,
                            account = myAccount?.accountId!!,
                            reject = 0,
                        )
                        navController.popBackStack()
                    }
                ) { Text("確認") }
                Button(
                    modifier = Modifier.fillMaxWidth(1f),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorResource(R.color.purple_200),
                        contentColor = Color.DarkGray
                    ),
                    onClick = {
                        applicantVM.setApplicantStatus(
                            serviceId =  myAccount?.serviceId!!,
                            account = myAccount?.accountId!!,
                            reject = 1,
                        )
                        navController.popBackStack()
                    },
                ) { Text("拒絕") }
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun PreviewCompanionCheckAppointmentScreen(
) {
    CompanionCheckAppointmentScreen(
        navController = rememberNavController(),
        applicantVM = CompanionApplicantVM(),
        tabVM = TabVM(),
        comOrderVM = CompanionOrderVM(),
        account = 1,
        serviceId = 1,
    )
}