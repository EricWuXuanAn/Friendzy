
package com.example.tip102group01friendzy.ui.feature.Memberpage

import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.navigation.compose.rememberNavController
import com.example.tip102group01friendzy.R
import com.example.tip102group01friendzy.Screen



@Composable
fun Settingpage(
    navController: NavHostController,
    settingVM: SettingVM
) {
// 狀態變數，用於管理各個可編輯內容的值
    var password by remember { mutableStateOf("**********") } // 預設密碼顯示為星號
    var nickname by remember { mutableStateOf("暱稱是這裡") } // 預設暱稱
    var phoneNumber by remember { mutableStateOf("+886 912345678") } // 預設手機號碼
    var newPassword by remember { mutableStateOf("") } // 新密碼
    var confirmPassword by remember { mutableStateOf("") } // 確認密碼
    var passwordError by remember { mutableStateOf(false) } // 是否有密碼不一致錯誤

    // 狀態變數，用於控制是否處於編輯模式
    var isEditingPassword by remember { mutableStateOf(false) } // 密碼是否處於編輯模式
    var isEditingNickname by remember { mutableStateOf(false) } // 暱稱是否處於編輯模式
    var isEditingPhoneNumber by remember { mutableStateOf(false) } // 手機號碼是否處於編輯模式

    Column(
        modifier = Modifier
            .fillMaxSize() // 填滿可用空間
            .padding(16.dp) // 設置整體內邊距
    ) {
        // 返回按鈕與標題
        Row(
            verticalAlignment = Alignment.CenterVertically, // 垂直方向置中
            modifier = Modifier.fillMaxWidth() // 填滿寬度
        ) {
            IconButton(
                onClick = {
                    navController.navigate(Screen.MemberScreen.name) // 返回 MemberScreen
                }
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack, // 使用返回箭頭圖標
                    contentDescription = "Back" // 無障礙描述文字
                )
            }
            Text(
                text = "設定", // 設定標題文字
                fontSize = 20.sp, // 字體大小
                fontWeight = FontWeight.Bold, // 粗體字
                modifier = Modifier.padding(start = 8.dp) // 與圖標間距
            )
        }

        Spacer(modifier = Modifier.height(16.dp)) // 添加垂直間距
       

        // 會員帳號部分
        Text(text = "會員帳號", fontWeight = FontWeight.Bold) // 顯示標籤文字
        Text(text = "angelhuang@cw.com.tw", modifier = Modifier.padding(vertical = 8.dp)) // 顯示帳號內容
        CustomDivider()

        // 姓名部分
        Text(text = "姓名", fontWeight = FontWeight.Bold) // 顯示標籤文字
        Text(text = "Angle Huang", modifier = Modifier.padding(vertical = 8.dp)) // 顯示姓名內容
        CustomDivider()

        Text(text = "會員密碼", fontWeight = FontWeight.Bold)
        if (isEditingPassword) {
            // 新密碼輸入框
            TextField(
                value = newPassword,
                onValueChange = { newPassword = it },
                label = { Text("新密碼") },
                modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
            )
            // 確認密碼輸入框
            TextField(
                value = confirmPassword,
                onValueChange = { confirmPassword = it },
                label = { Text("確認密碼") },
                modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
            )
            // 錯誤訊息
            if (passwordError) {
                Text(
                    text = "密碼不一致，請重新輸入",
                    color = Color.Red,
                    fontSize = 14.sp,
                    modifier = Modifier.padding(vertical = 4.dp)
                )
            }
            // 儲存按鈕（文字按鈕）
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End // 將按鈕對齊到右側
            ) {
                TextButton(
                    onClick = {
                        if (newPassword == confirmPassword) {
                            password = newPassword // 更新密碼
                            isEditingPassword = false // 結束編輯模式
                            passwordError = false // 清除錯誤狀態
                        } else {
                            passwordError = true // 顯示錯誤訊息
                        }
                    }
                ) {
                    Text("變更密碼") // 按鈕文字
                }
            }
        } else {
            // 顯示密碼與編輯按鈕
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
            ) {
                // 將密碼顯示為與字數相同的星號
                Text(
                    text = "*".repeat(password.length), // 根據密碼長度生成星號
                    modifier = Modifier.weight(1f)
                )
                IconButton(
                    onClick = { isEditingPassword = true }
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.edit), // 編輯按鈕圖示
                        contentDescription = "Edit"
                    )
                }
            }
        }
        CustomDivider()


        // 暱稱部分
        EditableRow(
            label = "暱稱", // 暱稱標籤
            value = nickname, // 顯示的暱稱
            isEditing = isEditingNickname, // 是否處於編輯模式
            onEditClick = { isEditingNickname = true }, // 點擊進入編輯模式
            onSaveClick = { isEditingNickname = false }, // 點擊完成保存
            onValueChange = { nickname = it } // 更新暱稱值
        )
        CustomDivider()

        // 手機號碼部分
        EditableRow(
            label = "手機號碼", // 手機號碼標籤
            value = phoneNumber, // 顯示的手機號碼
            isEditing = isEditingPhoneNumber, // 是否處於編輯模式
            onEditClick = { isEditingPhoneNumber = true }, // 點擊進入編輯模式
            onSaveClick = { isEditingPhoneNumber = false }, // 點擊完成保存
            onValueChange = { phoneNumber = it } // 更新手機號碼值
        )
        CustomDivider()

        Spacer(modifier = Modifier.weight(1f)) // 添加一個可彈性填充的空間

        // 登出按鈕
        Button(
            onClick = {
                navController.navigate(Screen.LoginScreen.name) // 跳轉到 LoginScreen
            },
            colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent), // 設置按鈕為透明背景
            modifier = Modifier.fillMaxWidth() // 按鈕填滿寬度
        ) {
            Text(text = "登出帳號", color = Color.Red, fontWeight = FontWeight.Bold) // 顯示登出文字，紅色加粗
        }
    }
}

//分隔線
@Composable
fun CustomDivider(
    color: Color = Color.Gray, // 分隔線顏色，預設為灰色
    thickness: Dp = 1.dp,     // 分隔線厚度，預設為 1dp
    padding: PaddingValues = PaddingValues(0.dp) // 分隔線上下的邊距
) {
    Box(
        modifier = Modifier
            .fillMaxWidth() // 分隔線寬度佔滿整行
            .padding(padding) // 設定上下邊距
            .height(thickness) // 設定分隔線的厚度
            .background(color) // 設定分隔線的顏色
    )
}

// 可編輯行的通用組件
@Composable
fun EditableRow(
    label: String, // 行標籤
    value: String, // 當前顯示的值
    isEditing: Boolean, // 是否處於編輯模式
    onEditClick: () -> Unit, // 進入編輯模式的回調
    onSaveClick: () -> Unit, // 保存內容的回調
    onValueChange: (String) -> Unit // 更新值的回調
) {
    Row(
        verticalAlignment = Alignment.CenterVertically, // 垂直方向置中
        modifier = Modifier
            .fillMaxWidth() // 填滿寬度
            .padding(vertical = 8.dp) // 添加上下內邊距
    ) {
        // 標籤與內容
        Column(modifier = Modifier.weight(1f)) { // 左側占據一部分空間
            Text(text = label, fontWeight = FontWeight.Bold) // 顯示標籤文字
            if (isEditing) {
                // 如果處於編輯模式，顯示文字輸入框
                TextField(
                    value = value, // 輸入框內的文字
                    onValueChange = onValueChange, // 更新文字的回調
                    modifier = Modifier.fillMaxWidth() // 輸入框填滿寬度
                )
            } else {
                // 非編輯模式顯示靜態文字
                Text(text = value, modifier = Modifier.padding(top = 4.dp)) // 添加頂部間距
            }
        }

        Spacer(modifier = Modifier.width(8.dp)) // 左右間距

        // 編輯或完成按鈕
        IconButton(
            onClick = {
                if (isEditing) {
                    onSaveClick() // 如果處於編輯模式，點擊保存
                } else {
                    onEditClick() // 否則進入編輯模式
                }
            }
        ) {
            Icon(
                painter = painterResource(id = if (isEditing) R.drawable.check else R.drawable.edit),
                contentDescription = if (isEditing) "Save" else "Edit"
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewSettingsScreen() {
    Settingpage(rememberNavController(), settingVM = SettingVM())
}