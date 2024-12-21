package com.example.tip102group01friendzy.ui.feature.companion

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.shape.RoundedCornerShape
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
    //專長文字輸入方塊的字
    var inputSkillText by remember { mutableStateOf("") }
    //下拉選單的字
    var inputDropdownMenu by remember { mutableStateOf("") }
    //下拉選單的顯示
    var expanded by remember { mutableStateOf(false) }
    val skillState by skillVM.skillState.collectAsState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "刊登", fontSize = 28.sp)
        HorizontalDivider()
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.Start
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "專長：", fontSize = 24.sp)
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
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
//                Text("專長選項：")
                ExposedDropdownMenuBox(
                    expanded = expanded,
                    onExpandedChange = { expanded = it },
                ) {
                    TextField(
                        readOnly = true,
                        modifier = Modifier
                            .menuAnchor()
                            .fillMaxWidth(0.75f),
                        value = inputDropdownMenu,
                        onValueChange = {
                            inputDropdownMenu = it
                            expanded = true
                        },
                        label = { Text(text = "專長選項") },
                        trailingIcon = { TrailingIcon(expanded = expanded) }
                    )
                    ExposedDropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false },
                    ) {
                        skillState.forEach { skill ->
                            DropdownMenuItem(
                                text = { Text(skill.skillName) },
                                onClick = {
                                    inputDropdownMenu = skill.skillName
                                    expanded = false
                                }
                            )
                        }
                    }
                }
                IconButton(onClick = {
                    //判斷文字方塊是否有這個詞，有就不便 沒有就新增
                    when(inputSkillText.contains(inputDropdownMenu)){
                        true -> inputSkillText
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
            Spacer(modifier = Modifier.size(4.dp))
            Text(text = "開始時間：", fontSize = 24.sp)
            Row (
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ){

            }

        }
    }
}

@Composable
@Preview(showBackground = true)
fun PreviewCompanionPublishScreen() {
    CompanionPublishScreen(skillVM = SkillVM())
}