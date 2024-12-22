package com.example.tip102group01friendzy.ui.feature.companion

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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults.TrailingIcon
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.tip102group01friendzy.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
//陪伴者刊登畫面
fun CompanionPublishScreen(
    navController: NavHostController = rememberNavController(),
    skillVM: SkillVM
) {
    //專長文字輸入方塊文字
    var inputSkillText by remember { mutableStateOf("") }
    //開始日期時間
    var inputDateStart by remember { mutableStateOf("") }
    var inputTimeStart by remember { mutableStateOf("") }
    //結束日期時間
    var inputDateEnd by remember { mutableStateOf("") }
    var inputTimeEnd by remember { mutableStateOf("") }
    //預算文字
    var inputBudget by remember { mutableStateOf("") }
    //專長下拉選單文字
    var inputDropdownMenu by remember { mutableStateOf("") }
    var inputLocationText by remember { mutableStateOf("") }
    //下拉選單的顯示
    var skillExpanded by remember { mutableStateOf(false) }
    var locationExpanded by remember { mutableStateOf(false) }

    //呼叫VM
    val skillState by skillVM.skillState.collectAsState()


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 16.dp, end = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "刊登", fontSize = 28.sp)
        HorizontalDivider(modifier = Modifier.padding(top = 6.dp , bottom = 6.dp))
        Column(
            horizontalAlignment = Alignment.Start
        ) {
            //專長
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
            //專長下拉選單
            Spacer(modifier = Modifier.size(8.dp))//間隔
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
//                Text("專長選項：")
                ExposedDropdownMenuBox(
                    expanded = skillExpanded,
                    onExpandedChange = {
                        skillExpanded = it
                        inputDropdownMenu = ""
                    },
                ) {
                    OutlinedTextField(
                        readOnly = true,
                        modifier = Modifier
                            .menuAnchor()
                            .fillMaxWidth(0.75f),
                        value = inputDropdownMenu,
                        onValueChange = {
                            inputDropdownMenu = it
                            skillExpanded = true
                        },
                        placeholder = { Text("專長選項") },
                        shape = RoundedCornerShape(15.dp),
                        trailingIcon = { TrailingIcon(expanded = skillExpanded) },
                        enabled = false
                    )
                    ExposedDropdownMenu(
                        expanded = skillExpanded,
                        onDismissRequest = { skillExpanded = false },
                    ) {
                        skillState.forEach { skill ->
                            DropdownMenuItem(
                                text = { Text(skill.skillName) },
                                onClick = {
                                    inputDropdownMenu = skill.skillName
                                    skillExpanded = false
                                }
                            )
                        }
                    }
                }
                IconButton(onClick = {
                    //判斷文字方塊是否有這個詞，有就不變 沒有就新增
                    when(inputSkillText.contains(inputDropdownMenu)){
                        true -> {}
                        false -> inputSkillText += "$inputDropdownMenu "
                    }
                },) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Add",
                        modifier = Modifier.size(60.dp)
                    )
                }
                IconButton(onClick = {//刪除下拉選單選取的詞
                    inputSkillText = inputSkillText.replace("$inputDropdownMenu ","")
                },) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Minus",
                        modifier = Modifier.size(60.dp)
                    )
                }
            }
            //開始時間
            Spacer(modifier = Modifier.size(8.dp))//間隔
            Text(text = "開始時間：", fontSize = 24.sp)
            Row (
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 2.dp),
                verticalAlignment = Alignment.CenterVertically
            ){
//                Text(text = "日期：", fontSize = 16.sp)
                //開始日期
                OutlinedTextField(
                    value = inputDateStart,
                    onValueChange = {  },
                    modifier = Modifier
                        .fillMaxWidth(0.6f)
                        .padding(end = 6.dp),
//                    singleLine = false,
                    shape = RoundedCornerShape(15.dp),
                    placeholder = { Text("選日期") },
                    enabled = false, //禁止打字
                    trailingIcon = {
                        Icon(painter = painterResource(R.drawable.date_range),
                            contentDescription ="Clear",
                            modifier = Modifier.clickable {  }
                        )
                    }
                )
                //開始時間
                OutlinedTextField(
                    value = inputTimeStart,
                    onValueChange = {  },
                    modifier = Modifier.fillMaxWidth(1f),
//                    singleLine = false,
                    shape = RoundedCornerShape(15.dp),
                    placeholder = { Text("選時間") },
                    enabled = false, //禁止打字
                    trailingIcon = {
                        Icon(painter = painterResource(R.drawable.clock),
                            contentDescription ="Clear",
                            modifier = Modifier.clickable {  }
                        )
                    }
                )
            }
            //結束時間
            Spacer(modifier = Modifier.size(8.dp))//間隔
            Text(text = "結束時間：", fontSize = 24.sp)
            Row (
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 2.dp),
                verticalAlignment = Alignment.CenterVertically
            ){
//                Text(text = "日期：", fontSize = 16.sp)
                //結束日期
                OutlinedTextField(
                    value = inputDateEnd,
                    onValueChange = {  },
                    modifier = Modifier
                        .fillMaxWidth(0.6f)
                        .padding(end = 6.dp),
//                    singleLine = false,
                    shape = RoundedCornerShape(15.dp),
                    placeholder = { Text("選日期") },
                    enabled = false, //禁止打字
                    trailingIcon = {
                        Icon(painter = painterResource(R.drawable.date_range),
                            contentDescription ="Clear",
                            modifier = Modifier.clickable {  }
                        )
                    }
                )
                //結束時間
                OutlinedTextField(
                    value = inputTimeEnd,
                    onValueChange = {  },
                    modifier = Modifier.fillMaxWidth(1f),
//                    singleLine = false,
                    shape = RoundedCornerShape(15.dp),
                    placeholder = { Text("選時間") },
                    enabled = false, //禁止打字
                    trailingIcon = {
                        Icon(painter = painterResource(R.drawable.clock),
                            contentDescription ="Clear",
                            modifier = Modifier.clickable {  }
                        )
                    }
                )
            }
            //地區
            Spacer(modifier = Modifier.size(8.dp))//間隔
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 2.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "地區：", fontSize = 16.sp)
                ExposedDropdownMenuBox(
                    expanded = locationExpanded,
                    onExpandedChange = {
                        locationExpanded = it
                        inputLocationText = ""
                    },
                ) {
                    OutlinedTextField(
                        readOnly = true,
                        modifier = Modifier
                            .menuAnchor()
                            .fillMaxWidth(1f),
                        value = inputLocationText,
                        onValueChange = {
                            inputLocationText = it
                            locationExpanded = true
                        },
                        placeholder = { Text("城市") },
                        shape = RoundedCornerShape(15.dp),
                        trailingIcon = { TrailingIcon(expanded = locationExpanded) },
                        enabled = false,
                    )
                    ExposedDropdownMenu(
                        expanded = locationExpanded,
                        onDismissRequest = { locationExpanded = false },
                    ) {
                        skillState.forEach { skill ->
                            DropdownMenuItem(
                                text = { Text(skill.skillName) },
                                onClick = {
                                    inputLocationText = skill.skillName
                                    locationExpanded = false
                                }
                            )
                        }
                    }
                }
            }
            //金額
            Spacer(modifier = Modifier.size(8.dp))//間隔
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 2.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "金額：", fontSize = 16.sp)
                OutlinedTextField(
                    value = inputBudget,
                    onValueChange = { inputBudget = it },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    shape = RoundedCornerShape(15.dp),
                    placeholder = { Text("輸入金額") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
            }
        }
        Column (
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Bottom
        ) {
            Row (
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            ) {
                Button(
                    modifier = Modifier
                        .fillMaxWidth(0.5f)
                        .padding(end = 6.dp),
                    onClick = {}
                ) { Text("刊登")}
                Button(
                    modifier = Modifier.fillMaxWidth(1f),
                    onClick = {}
                ) { Text("取消")}
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun PreviewCompanionPublishScreen() {
    CompanionPublishScreen(skillVM = SkillVM())
}