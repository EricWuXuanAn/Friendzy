package com.example.tip102group01friendzy.ui.feature.companion

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults.TrailingIcon
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.tip102group01friendzy.R
import com.example.tip102group01friendzy.TabVM
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter.ofPattern

@OptIn(ExperimentalMaterial3Api::class)
@Composable
//陪伴者刊登畫面
fun CompanionPublishScreen(
    navController: NavHostController,
    myPublish: CompanionMyPublishVM,
    tabVM: TabVM
) {
    //呼叫VM
    val skillState by myPublish.publishSkillState.collectAsState()
    val areaState by myPublish.publishAreaState.collectAsState()
    //標題輸入文字
    var inputTitleText by remember { mutableStateOf("") }
    //專長輸入文字
    var inputSkillText by remember { mutableStateOf("") }
    //開始日期時間輸入文字
    var inputDateStart by remember { mutableStateOf("") }
    var inputTimeStart by remember { mutableStateOf("") }
    //結束日期時間輸入文字
    var inputDateEnd by remember { mutableStateOf("") }
    var inputTimeEnd by remember { mutableStateOf("") }
    //設定日期格式
    val dateFormat = ofPattern("yyyy-MM-dd")
    //DatePickDialog顯示控制
    var shortStartDatePick by remember { mutableStateOf(false) }
    var shortEndDatePick by remember { mutableStateOf(false) }
    //預算輸入文字
    var inputBudget by remember { mutableStateOf("") }
    //下拉選單輸入文字
    var inputDropdownMenu by remember { mutableStateOf("") }//專長
    var inputCityText by remember { mutableStateOf("") }//城市
    var inputDistrictText by remember { mutableStateOf("") }//區
    //下拉選單的顯示
    var skillExpanded by remember { mutableStateOf(false) }//專長
    var cityExpanded by remember { mutableStateOf(false) }//城市
    var districtExpanded by remember { mutableStateOf(false) }//區
    var timeStartExpanded by remember { mutableStateOf(false) }//開始時間
    var timeEndExpanded by remember { mutableStateOf(false) }//結束時間
    //選時間用的小時數
    val timeList = listOf(
        "00:00", "01:00", "02:00", "03:00", "04:00", "05:00", "06:00", "07:00", "08:00", "09:00",
        "10:00", "11:00", "12:00", "13:00", "14:00", "15:00", "16:00", "17:00", "18:00", "19:00",
        "20:00", "21:00", "22:00", "23:00", "24:00"
    )
    //過濾掉重複的城市
    val cityList = areaState.map { it.areaCity }.distinct()
    //過濾出所選城市的所有區
    val districtList = areaState.filter { it.areaCity == inputCityText }.map { it.areaDistricy }
    //專長文字搜尋過濾
    val skillFiltered = skillState.filter { it.expertiseLabel!!.startsWith(inputDropdownMenu, true) }
    skillExpanded = skillExpanded && skillFiltered.isNotEmpty()

    LaunchedEffect(Unit) {
        myPublish.getAreaState()
        myPublish.getSkillState()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 16.dp, end = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "刊登", fontSize = 28.sp)
        HorizontalDivider(modifier = Modifier.padding(top = 6.dp, bottom = 6.dp))
        Column(
            horizontalAlignment = Alignment.Start
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 6.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "標題：", fontSize = 16.sp)
                OutlinedTextField(
                    value = inputTitleText,
                    onValueChange = {inputTitleText = it},
                    shape = RoundedCornerShape(15.dp),
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = { Text("輸入標題") },
                )
            }
            Spacer(modifier = Modifier.size(8.dp))//間隔
            //專長下拉選單
            ExposedDropdownMenuBox(
                expanded = skillExpanded,
                onExpandedChange = {
                    skillExpanded = it
                    inputDropdownMenu = ""
                },
            ) {
                OutlinedTextField(
                    readOnly = false,
                    modifier = Modifier
                        .menuAnchor()
                        .fillMaxWidth(),
                    value = inputDropdownMenu,
                    onValueChange = {
                        Log.e("tag", it)
                        inputDropdownMenu = it
                        skillExpanded = true
                    },
                    placeholder = { Text("專長選項") },
                    shape = RoundedCornerShape(15.dp),
                    trailingIcon = { TrailingIcon(expanded = skillExpanded) },
//                    enabled = false
                )
                ExposedDropdownMenu(
                    expanded = skillExpanded,
                    onDismissRequest = { skillExpanded = false },
                ) {
                    skillFiltered.forEach { skill ->
                        DropdownMenuItem(
                            text = { Text(skill.expertiseLabel!!) },
                            onClick = {
                                inputDropdownMenu = skill.expertiseLabel!!
                                skillExpanded = false
                            }
                        )
                    }
                }
            }
            //新增刪除專長按鈕
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(6.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorResource(R.color.purple_200),
                        contentColor = Color.DarkGray
                    ),
                    modifier = Modifier
                        .fillMaxWidth(0.5f)
                        .padding(end = 6.dp),
                    onClick = {
                        //判斷文字方塊是否有這個詞，有就不變 沒有就新增
                        when (inputSkillText.contains(inputDropdownMenu)) {
                            true -> {}
                            false -> inputSkillText += "$inputDropdownMenu "
                        }
                    }
                ) { Text("加入選擇專長") }
                Button(
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorResource(R.color.purple_200),
                        contentColor = Color.DarkGray
                    ),
                    modifier = Modifier.fillMaxWidth(1f),
                    onClick = {
                        inputSkillText = inputSkillText.replace("$inputDropdownMenu ", "")
                    }
                ) { Text("刪除選擇專長") }
            }
            Spacer(modifier = Modifier.size(8.dp))//間隔
            //專長顯示
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 2.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "專長：", fontSize = 16.sp)
                OutlinedTextField(
                    value = inputSkillText,
                    onValueChange = { inputSkillText = it },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = false,
                    shape = RoundedCornerShape(15.dp),
                    placeholder = { Text("透過選項輸入專長 限30個字") },
                    enabled = false //禁止打字
                )
            }
            //開始時間
            Spacer(modifier = Modifier.size(8.dp))//間隔
            Text(text = "開始時間：", fontSize = 24.sp)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 2.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                //開始日期輸入方塊
                OutlinedTextField(
                    value = inputDateStart,
                    onValueChange = { inputDateStart = it },
                    modifier = Modifier
                        .fillMaxWidth(0.6f)
                        .padding(end = 6.dp)
                        .clickable { shortStartDatePick = true },
//                    singleLine = false,
                    shape = RoundedCornerShape(15.dp),
                    placeholder = { Text("選日期") },
                    enabled = false, //禁止打字
                    trailingIcon = {
                        Icon(
                            painter = painterResource(R.drawable.date_range),
                            contentDescription = "Clear",
                        )
                    }
                )
                //開始的DatePickDialog
                if (shortStartDatePick) {
                    PublishDatePicker(
                        onConfirm = { startUtcTimeMillis ->
                            inputDateStart = startUtcTimeMillis?.let {
                                Instant.ofEpochMilli(it).atZone(ZoneId.of("UTC"))
                                    .toLocalDate()
                                    .format(dateFormat)
                            } ?: inputDateStart
                            shortStartDatePick = false
                        },
                        onDismiss = {
                            shortStartDatePick = false
                        }
                    )
                }
                //開始時段選單
                ExposedDropdownMenuBox(
                    expanded = timeStartExpanded,
                    onExpandedChange = {
                        timeStartExpanded = it
                        inputTimeStart = ""
                    },
                ) {
                    OutlinedTextField(
                        readOnly = true,
                        modifier = Modifier
                            .menuAnchor()
                            .fillMaxWidth(),
                        value = inputTimeStart,
                        onValueChange = {
                            inputTimeStart = it
                            timeStartExpanded = true
                        },
                        placeholder = { Text("選時間") },
                        shape = RoundedCornerShape(15.dp),
                        trailingIcon = { TrailingIcon(expanded = timeStartExpanded) },
                        enabled = false
                    )
                    ExposedDropdownMenu(
                        expanded = timeStartExpanded,
                        onDismissRequest = { timeStartExpanded = false },
                    ) {
                        timeList.forEach { time ->
                            DropdownMenuItem(
                                text = { Text(time) },
                                onClick = {
                                    inputTimeStart = time
                                    timeStartExpanded = false
                                }
                            )
                        }
                    }
                }
                /*
                OutlinedTextField(
                    value = inputTimeStart,
                    onValueChange = {  },
                    modifier = Modifier.fillMaxWidth(1f),
//                    singleLine = false,
                    shape = RoundedCornerShape(15.dp),
                    placeholder = { Text("選時間") },
                    enabled = false, //禁止打字
//                    trailingIcon = {
//                        Icon(painter = painterResource(R.drawable.clock),
//                            contentDescription ="Clear",
//                            modifier = Modifier.clickable {  }
//                        )
//                    }
                )
                 */
            }
            //結束時間
            Spacer(modifier = Modifier.size(8.dp))//間隔
            Text(text = "結束時間：", fontSize = 24.sp)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 2.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
//                Text(text = "日期：", fontSize = 16.sp)
                //結束日期選擇
                OutlinedTextField(
                    value = inputDateEnd,
                    onValueChange = { inputDateEnd = it },
                    modifier = Modifier
                        .fillMaxWidth(0.6f)
                        .padding(end = 6.dp)
                        .clickable { shortEndDatePick = true },
//                    singleLine = false,
                    shape = RoundedCornerShape(15.dp),
                    placeholder = { Text("選日期") },
                    enabled = false, //禁止打字
                    trailingIcon = {
                        Icon(
                            painter = painterResource(R.drawable.date_range),
                            contentDescription = "Clear",
                        )
                    }
                )
                if (shortEndDatePick) {
                    PublishDatePicker(
                        onConfirm = { startUtcTimeMillis ->
                            inputDateEnd = startUtcTimeMillis?.let {
                                Instant.ofEpochMilli(it).atZone(ZoneId.of("UTC"))
                                    .toLocalDate()
                                    .format(dateFormat)
                            } ?: inputDateEnd
                            shortEndDatePick = false
                        },
                        onDismiss = {
                            shortEndDatePick = false
                        }
                    )
                }
                //結束時段選擇
                ExposedDropdownMenuBox(
                    expanded = timeEndExpanded,
                    onExpandedChange = {
                        timeEndExpanded = it
                        inputTimeEnd = ""
                    },
                ) {
                    OutlinedTextField(
                        readOnly = true,
                        modifier = Modifier
                            .menuAnchor()
                            .fillMaxWidth(),
                        value = inputTimeEnd,
                        onValueChange = {
                            inputTimeEnd = it
                            timeEndExpanded = true
                        },
                        placeholder = { Text("選時間") },
                        shape = RoundedCornerShape(15.dp),
                        trailingIcon = { TrailingIcon(expanded = timeEndExpanded) },
                        enabled = false
                    )
                    ExposedDropdownMenu(
                        expanded = timeEndExpanded,
                        onDismissRequest = { timeEndExpanded = false },
                    ) {
                        timeList.forEach { time ->
                            DropdownMenuItem(
                                text = { Text(time) },
                                onClick = {
                                    inputTimeEnd = time
                                    timeEndExpanded = false
                                }
                            )
                        }
                    }
                }
                /*
                OutlinedTextField(
                    value = inputTimeEnd,
                    onValueChange = {  },
                    modifier = Modifier.fillMaxWidth(1f),
//                    singleLine = false,
                    shape = RoundedCornerShape(15.dp),
                    placeholder = { Text("選時間") },
                    enabled = false, //禁止打字
//                    trailingIcon = {
//                        Icon(painter = painterResource(R.drawable.clock),
//                            contentDescription ="Clear",
//                            modifier = Modifier.clickable {  }
//                        )
//                    }
                )
                 */
            }
            //選地區
            Spacer(modifier = Modifier.size(8.dp))//間隔
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 2.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                //選城市
                Text(text = "城市：", fontSize = 16.sp)
                ExposedDropdownMenuBox(
                    expanded = cityExpanded,
                    onExpandedChange = {
                        cityExpanded = it
                        inputCityText = ""
                        inputDistrictText = ""
                    },
                ) {
                    //選城市
                    OutlinedTextField(
                        readOnly = true,
                        modifier = Modifier
                            .menuAnchor()
                            .fillMaxWidth(0.43f)
                            .padding(end = 4.dp),
                        value = inputCityText,
                        onValueChange = {
                            inputCityText = it
                            inputDistrictText = ""
                            cityExpanded = true
                        },
                        placeholder = { Text("城市") },
                        shape = RoundedCornerShape(15.dp),
                        trailingIcon = { TrailingIcon(expanded = cityExpanded) },
                        enabled = false,
                    )
                    ExposedDropdownMenu(
                        expanded = cityExpanded,
                        onDismissRequest = { cityExpanded = false },
                    ) {
                        cityList.forEach { citys ->
                            DropdownMenuItem(
                                text = { Text(citys!!) },
                                onClick = {
                                    inputCityText = citys!!
                                    inputDistrictText = ""
                                    cityExpanded = false
                                }
                            )
                        }
                    }
                }
                //選地區
                Text(text = "地區：", fontSize = 16.sp)
                ExposedDropdownMenuBox(
                    expanded = districtExpanded,
                    onExpandedChange = {
                        districtExpanded = it
                        inputDistrictText = ""
                    },
                ) {
                    OutlinedTextField(
                        readOnly = true,
                        modifier = Modifier
                            .menuAnchor()
                            .fillMaxWidth(1f),
                        value = inputDistrictText,
                        onValueChange = {
                            inputDistrictText = it
                            districtExpanded = true
                        },
                        placeholder = { Text("區") },
                        shape = RoundedCornerShape(15.dp),
                        trailingIcon = { TrailingIcon(expanded = districtExpanded) },
                        enabled = false,
                    )
                    ExposedDropdownMenu(
                        expanded = districtExpanded,
                        onDismissRequest = { districtExpanded = false },
                    ) {
                        districtList.forEach { district ->
                            DropdownMenuItem(
                                text = { Text(district!!) },
                                onClick = {
                                    inputDistrictText = district!!
                                    districtExpanded = false
                                }
                            )
                        }
                    }
                }
            }
            //金額
//            /*
            Spacer(modifier = Modifier.size(8.dp))//間隔
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 2.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "價格：", fontSize = 16.sp)
                OutlinedTextField(
                    value = inputBudget,
                    onValueChange = { inputBudget = it },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    shape = RoundedCornerShape(15.dp),
                    placeholder = { Text("輸入價格 最少0元") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
            }
//             */
        }
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Top
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp)
            ) {
                Button(
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorResource(R.color.purple_200),
                        contentColor = Color.DarkGray
                    ),
                    modifier = Modifier
                        .fillMaxWidth(1f)
                        .padding(end = 6.dp),
                    onClick = {
                        val area =areaState.filter {
                            it.areaCity == inputCityText && it.areaDistricy == inputDistrictText
                        }
                        val Publish = MyPublish(
                        )
                    }
                ) { Text("刊登") }
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun PreviewCompanionPublishScreen() {
    CompanionPublishScreen(
        rememberNavController(),
        CompanionMyPublishVM(),
        tabVM = TabVM()
    )
}