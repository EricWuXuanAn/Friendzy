package com.example.tip102group01friendzy.ui.feature.Memberpage

import android.content.Context
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.navigation.compose.rememberNavController
import com.example.tip102group01friendzy.R
import com.example.tip102group01friendzy.Screen
import com.example.tip102group01friendzy.TabVM


@Composable
fun SettingPage(
    navController: NavHostController,
    settingVM: SettingViewModel,
    tabVM: TabVM
) {
    // 觀察 ViewModel 的會員資料
    val memberInfo by settingVM.memberInfo.collectAsState()

    // 編輯狀態與輸入框的值
    var password by remember { mutableStateOf("**********") } // 顯示用的密碼
    var nickname by remember { mutableStateOf("") } // 暱稱
    var phoneNumber by remember { mutableStateOf("") } // 手機號碼
    var newPassword by remember { mutableStateOf("") } // 新密碼輸入框的值
    var confirmPassword by remember { mutableStateOf("") } // 確認密碼輸入框的值
    var passwordError by remember { mutableStateOf(false) } // 密碼錯誤狀態
    // 登出按鈕
    var showLogoutDialog by remember { mutableStateOf(false) }
    // 編輯狀態的控制變數
    var isEditingPassword by remember { mutableStateOf(false) }
    var isEditingNickname by remember { mutableStateOf(false) }
    var isEditingPhoneNumber by remember { mutableStateOf(false) }
    val context = LocalContext.current
    LaunchedEffect(Unit) {
        // 初始化時從後端抓取會員資料
        settingVM.fetchMemberInfo(context)
    }
    LaunchedEffect(memberInfo) {
        password = "*".repeat(memberInfo.password?.length ?: 0) // 根據資料長度更新靜態密碼顯示
        nickname = memberInfo.nickname ?: ""
        phoneNumber = memberInfo.phone ?: ""
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // 返回按鈕與標題
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            IconButton(
                onClick = { navController.popBackStack() }
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back"
                )
            }
            Text(
                text = "設定",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 8.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // 會員帳號
        Text(text = "會員帳號", fontWeight = FontWeight.Bold)
        Text(text = memberInfo.email ?: "", modifier = Modifier.padding(vertical = 8.dp))
        CustomDivider() // 使用自定義的分隔線

        // 姓名
        Text(text = "姓名", fontWeight = FontWeight.Bold)
        Text(text = memberInfo.name ?: "", modifier = Modifier.padding(vertical = 8.dp))
        CustomDivider()

        // 密碼部分
        Text(text = "會員密碼", fontWeight = FontWeight.Bold)
        if (isEditingPassword) {
            // 顯示編輯密碼的輸入框
            TextField(
                value = newPassword,
                onValueChange = { newPassword = it },
                label = { Text("新密碼") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            )
            TextField(
                value = confirmPassword,
                onValueChange = { confirmPassword = it },
                label = { Text("確認密碼") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            )
            if (passwordError) {
                // 如果密碼不一致，顯示錯誤訊息
                Text(
                    text = "密碼不一致，請重新輸入",
                    color = Color.Red,
                    fontSize = 14.sp,
                    modifier = Modifier.padding(vertical = 4.dp)
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                TextButton(
                    onClick = {
                        if (newPassword == confirmPassword) {
                            password = "*".repeat(newPassword.length) // 更新靜態密碼顯示
                            isEditingPassword = false
                            passwordError = false
                            // 確保傳遞的 context 和 newPassword 已經是最新的
                            settingVM.updatePassword(context, newPassword) // 呼叫 ViewModel 更新密碼
                        } else {
                            passwordError = true
                        }
                    }
                ) {
                    Text("變更密碼")
                }
            }
        } else {
            // 顯示靜態密碼
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            ) {
                Text(text = password, modifier = Modifier.weight(1f))
                IconButton(
                    onClick = { isEditingPassword = true }
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.edit),
                        contentDescription = "Edit"
                    )
                }
            }
        }
        CustomDivider()

        // 使用 EditableRow 顯示暱稱部分
        EditableRow(
            label = "暱稱",
            value = nickname,
            isEditing = isEditingNickname,
            onEditClick = { isEditingNickname = true },
            onSaveClick = {
                isEditingNickname = false
                // 確保在保存時傳遞最新的暱稱
                settingVM.updateNickname(context, nickname) // 呼叫 ViewModel 更新暱稱
            },
            onValueChange = { nickname = it }
        )
        CustomDivider()

        // 使用 EditableRow 顯示手機號碼部分
        EditableRow(
            label = "手機號碼",
            value = phoneNumber,
            isEditing = isEditingPhoneNumber,
            onEditClick = { isEditingPhoneNumber = true },
            onSaveClick = {
                isEditingPhoneNumber = false
                // 確保在保存時傳遞最新的手機號碼
                settingVM.updatePhoneNumber(context, phoneNumber) // 呼叫 ViewModel 更新手機號碼
            },
            onValueChange = { phoneNumber = it }
        )
        CustomDivider()

        Spacer(modifier = Modifier.weight(1f))

        // 登出按鈕
        Button(
            onClick = { showLogoutDialog = true }, // 點擊按鈕時顯示確認窗口
            colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "登出帳號", color = Color.Red, fontWeight = FontWeight.Bold)
        }
        // 清除登入資料的方法
        fun clearLoginData(context: Context) {
            val preferences = context.getSharedPreferences("settings", Context.MODE_PRIVATE)
            preferences.edit().clear().apply() // 清除 SharedPreferences 中的所有資料
        }
        // 彈出確認窗口
        if (showLogoutDialog) {
            AlertDialog(
                onDismissRequest = { showLogoutDialog = false }, // 點擊窗口外或返回鍵時關閉窗口
                title = { Text(text = "確認登出") },
                text = { Text(text = "您確定要登出帳號嗎？") },
                confirmButton = {
                    TextButton(
                        onClick = {
                            // 清除登入資料
                            clearLoginData(context)
                            tabVM.tabBarState(true)
                            tabVM.setMemberStatus(false)
                            tabVM.setShowTabIndex(0)
                            // 導航到 Login 頁面
                            navController.navigate(Screen.LoginScreen.name) {
                                popUpTo(0) // 清空導航堆疊，防止返回到其他頁面
                            }
                        }
                    ) {
                        Text(text = "確認", color = Color.Red)
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showLogoutDialog = false }) {
                        Text(text = "取消")
                    }
                }
            )
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

