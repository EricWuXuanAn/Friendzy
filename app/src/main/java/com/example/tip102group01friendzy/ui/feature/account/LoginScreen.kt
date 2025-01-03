package com.example.tip102group01friendzy.ui.feature.account


import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.tip102group01friendzy.R
import com.example.tip102group01friendzy.RequestVM
import com.example.tip102group01friendzy.Screen
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(
    navController: NavHostController,
    loginViewModel: LoginViewModel,
//    requestVM: RequestVM= viewModel(),
//    onLoginSuccess:(String) -> Unit
) {
//    //設置偏好設定
//    val context = LocalContext.current
//    val preferences = context.getSharedPreferences("setting", Context.MODE_PRIVATE)

    val snackbarMessage by loginViewModel.snackbarMessage.collectAsState()
    val snackbarTrigger by loginViewModel.snackbarTrigger.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    val loginState by loginViewModel.loginState.collectAsState()

    val accountOrPasswordEmptyMessage = stringResource(R.string.acc_pass_empty)
    val emailFormatErrorMessage = stringResource(R.string.errorEmail)

//    LaunchedEffect(Unit) {
//        Log.d(tag,"LaunchedEffect")
//        // 取出當初儲存資料，如果沒有儲存則回傳空字串
//        loginViewModel.email.value = preferences.getString("email", "")!!
//        loginViewModel.mpassword.value = preferences.getString("mpassword", "")!!
//    }

    LaunchedEffect(snackbarMessage) {
        snackbarMessage?.let{
            snackbarHostState.showSnackbar(message = it)
        }
    }

    LaunchedEffect(loginState) {
        if(loginState is LoginResponse.Success &&
            (loginState as LoginResponse.Success).result.statu){
            navController.navigate(Screen.TabMainScreen.name){
                popUpTo(Screen.LoginScreen.name){inclusive = true}
            }
        }
    }

    Log.d("tag_", "LoginScreen")
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .padding(top=150.dp)
    ) {
        Log.d("tag_", "LoginScreen - Column")

        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(6.dp)
        ) {
            Image(
                painter = painterResource(R.drawable.friendzy),
                contentDescription = "friendzy",
                modifier = Modifier
                    .size(150.dp)
                    .clip(CircleShape)
                    .border(BorderStroke(1.dp, Color(0x3C645959)), CircleShape),
                contentScale = ContentScale.Crop
            )
            TextField(
                value = loginViewModel.email.value,
                onValueChange = { loginViewModel.email.value = it },
                label = { Text(text = stringResource(R.string.account)) },
                singleLine = true,
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = colorResource(R.color.teal_700),
                    unfocusedIndicatorColor = colorResource(R.color.purple_200)
                ),
                isError = loginViewModel.email.value.isNotBlank() && !loginViewModel.isValidEmail,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .padding(12.dp)
            )

            TextField(
                value = loginViewModel.mpassword.value,
                onValueChange = { loginViewModel.mpassword.value = it },
                label = { Text(text = stringResource(R.string.password)) },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Lock,
                        contentDescription = "password"
                    )
                },
                isError = loginViewModel.mpassword.value.isNotBlank() && loginViewModel.mpassword.value.count() <8,
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Default.Clear,
                        contentDescription = "clear",
                        modifier = Modifier.clickable {
                            loginViewModel.clearPassword()
                        }
                    )
                },
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                singleLine = true,
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = colorResource(R.color.teal_700),
                    unfocusedIndicatorColor = colorResource(R.color.purple_200)
                ),
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .padding(12.dp)
            )
        }
        Column(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth(0.8f)
        ) {

            Button(
                onClick = {
                    scope.launch {
                        loginViewModel.login()
                    }
//                    coroutineScope.launch {
//                        val logged = requestVM.login(loginViewModel.email.value, loginViewModel.mpassword.value)
//                        if(logged ){
//                            Log.d("tag_","Login1")
//                            loginViewModel.onLoginClicked()
//                            Log.d("tag_","Login2")
//                            onLoginSuccess(loginViewModel.email.value)
//                            Log.d("tag_","Login3")
//                        }else{
//                        }
//                    }

//                    loginViewModel.onLoginClicked()
//                    //TODO: 確認帳號正確才能登入(控制器到首頁)，測試直接跳轉
//                    navController.navigate(Screen.TabMainScreen.name){
//                        popUpTo(Screen.LoginScreen.name){inclusive=true}
//                    }

                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(R.color.purple_200),
                    contentColor = Color.DarkGray
                )
            ) {
                Text(
                    text = stringResource(R.string.logIn)
                )
            }
            Row(
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.End,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                TextButton(
                    onClick = {
                        loginViewModel.mpassword.value = ""
                        navController.navigate(Screen.ForgetPasswordScreen.name)
                    } //跳轉畫面到忘記密碼頁
                ) {
                    Text(
                        text = stringResource(R.string.ForgotYourPassword),
                        color = colorResource(R.color.Gray),
                        textDecoration = TextDecoration.Underline
                    )
                }
                TextButton(
                    onClick = {
                        navController.navigate(Screen.RegisterScreen.name)
                    } //跳轉畫面到註冊
                ) {
                    Text(
                        text = stringResource(R.string.signUp),
                        color = colorResource(R.color.Gray),
                        textDecoration = TextDecoration.Underline
                    )
                }


            }
        }
        LaunchedEffect (snackbarTrigger){
            if (snackbarMessage != null){
                scope.launch {
                    snackbarHostState.showSnackbar(
                        message = when(snackbarMessage){
                            "empty_fields" -> accountOrPasswordEmptyMessage
                            "invalid_email" -> emailFormatErrorMessage
                            else -> snackbarMessage ?: ""
                        },
                        duration = SnackbarDuration.Short,
                        withDismissAction = true
                    )
                }
            }
        }
        Box (
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.BottomCenter
        ){
            SnackbarHost(
                hostState = snackbarHostState,
                modifier = Modifier.padding(bottom = 100.dp)
            )
        }
    }
}


//@Preview(showBackground = true)
//@Composable
//fun LoginScreenPreview() {
//    TIP102Group01FriendzyTheme {
//        LoginScreen(rememberNavController(), loginViewModel = LoginViewModel())
//    }
//}