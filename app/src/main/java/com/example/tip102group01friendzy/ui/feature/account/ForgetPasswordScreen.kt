package com.example.tip102group01friendzy.ui.feature.account

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.tip102group01friendzy.R
import com.example.tip102group01friendzy.ui.theme.TIP102Group01FriendzyTheme
import kotlinx.coroutines.launch

@Composable
fun ForgetPasswordScreen(
    navController: NavHostController,
    forgetPasswordViewModel: ForgetPasswordViewModel
) {
    val snackbarMessage by forgetPasswordViewModel.snackbarMessage.collectAsState()
    val snackbarTrigger by forgetPasswordViewModel.snackbarTrigger.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    val emailEmptyMessage = stringResource(R.string.EmailCanNotEmpty)
    val emailFormatErrorMessage = stringResource(R.string.errorEmail)

    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 100.dp)
        ) {
            Text(
                text = stringResource(R.string.ForgotYourPassword),
                fontSize = 25.sp,
                modifier = Modifier
                    .padding(12.dp)
            )
            Text(
                text = stringResource(R.string.sendLink),
                fontSize = 15.sp,
                modifier = Modifier
                    .padding(12.dp)
            )
            OutlinedTextField(
                value = forgetPasswordViewModel.email.value,
                onValueChange = {
                    forgetPasswordViewModel.email.value = it
                },
                placeholder = { Text(text = stringResource(R.string.email)) },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.MailOutline,
                        contentDescription = "email"
                    )
                },
                singleLine = true,
                isError = forgetPasswordViewModel.email.value.isNotBlank() && !forgetPasswordViewModel.isValidEmail,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = colorResource(R.color.teal_700),
                    unfocusedIndicatorColor = colorResource(R.color.purple_200)
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp)
                    .background(colorResource(R.color.purple_200))
            )

            Button(
                onClick = {
                    forgetPasswordViewModel.onForgetPasswordClecked()
                    //TODO:else{}電子信箱正確且曾註冊過發送郵件
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(R.color.purple_200),
                    contentColor = Color.DarkGray
                )
            ) { Text(text = stringResource(R.string.submit)) }

        }
        LaunchedEffect (snackbarTrigger){
            if (snackbarMessage != null){
                scope.launch {
                    snackbarHostState.showSnackbar(
                        message = when(snackbarMessage){
                            "Email cannot be empty." -> emailEmptyMessage
                            "Email Formatting Error." -> emailFormatErrorMessage
                            else -> ""
                        },
                        duration = SnackbarDuration.Short,
                        withDismissAction = true
                    )
                }
            }
        }

    }
    Box (
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomCenter
    ){
        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier.padding(bottom = 150.dp)
        )
    }

    Column(
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.End,
        modifier = Modifier
            .fillMaxSize()
    ) {
        Image(
            painter = painterResource(R.drawable.text),
            contentDescription = "bottom",
            modifier = Modifier
                .size(200.dp)
        )
    }
}


@Preview(showBackground = true)
@Composable
fun ForgetPasswordScreenPreview() {
    TIP102Group01FriendzyTheme {
        ForgetPasswordScreen(
            rememberNavController(),
            forgetPasswordViewModel = ForgetPasswordViewModel()
        )
    }
}