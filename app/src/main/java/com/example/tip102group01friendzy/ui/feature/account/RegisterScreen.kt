package com.example.tip102group01friendzy.ui.feature.account

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.tip102group01friendzy.R
import com.example.tip102group01friendzy.Screen
import com.example.tip102group01friendzy.ui.theme.TIP102Group01FriendzyTheme
import kotlinx.coroutines.launch

//@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(
    navController: NavHostController,
    registerViewModel: RegisterViewModel
) {
    val snackbarMessage by registerViewModel.snackbarMessage.collectAsState()
    val snackberTrigger by registerViewModel.snackbarTrigger.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    val fieldEmptyMessage = stringResource(R.string.columnIsEmpty)
    val emailFormatErrorMessage = stringResource(R.string.errorEmail)
    val passwordLengthMessage = stringResource(R.string.passwordRule)
    val passwordDifferent = stringResource(R.string.passwordDifferent)

    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(12.dp, 40.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(6.dp)
        ) {
            Text(
                text = stringResource(R.string.signUp),
                fontSize = 20.sp,
                modifier = Modifier
                    .padding(6.dp)
            )
        }
        OutlinedTextField(
            value = registerViewModel.account.value,
            onValueChange = { registerViewModel.account.value = it },
            placeholder = { Text(text = stringResource(R.string.account)) },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.MailOutline,
                    contentDescription = "account"
                )
            },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            isError = registerViewModel.account.value.isNotBlank() && !registerViewModel.isValidEmail,
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = colorResource(R.color.teal_700),
                unfocusedIndicatorColor = colorResource(R.color.purple_200),
                errorIndicatorColor = Color.Red
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(18.dp, 12.dp)
                .background(colorResource(R.color.purple_200))
        )
        OutlinedTextField(
            value = registerViewModel.password.value,
            onValueChange = {
                Log.d("tag","New password value: $it")
                registerViewModel.password.value = it
                            },
            placeholder = { Text(text = stringResource(R.string.password)) },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Lock,
                    contentDescription = "password"
                )
            },
            trailingIcon = {
                Icon(
                    imageVector = Icons.Default.Clear,
                    contentDescription = "clear",
                    modifier = Modifier.clickable {
                        registerViewModel.password.value = ""
                    }
                )
            },
            visualTransformation = PasswordVisualTransformation(),
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            isError = registerViewModel.password.value.isNotBlank() && registerViewModel.password.value.count() < 8,
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = colorResource(R.color.teal_700),
                unfocusedIndicatorColor = colorResource(R.color.purple_200),
                errorIndicatorColor = Color.Red
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(18.dp, 12.dp)
                .background(colorResource(R.color.purple_200))
        )
        OutlinedTextField(
            value = registerViewModel.confirmPassword.value,
            onValueChange = { registerViewModel.confirmPassword.value = it },
            placeholder = { Text(text = stringResource(R.string.confirmPassword)) },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Lock,
                    contentDescription = "confirmPassword"
                )
            },
            trailingIcon = {
                Icon(
                    imageVector = Icons.Default.Clear,
                    contentDescription = "clear",
                    modifier = Modifier.clickable {
                        registerViewModel.confirmPassword.value = ""
                    }
                )
            },
            visualTransformation = PasswordVisualTransformation(),
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = colorResource(R.color.teal_700),
                unfocusedIndicatorColor = colorResource(R.color.purple_200)
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(18.dp, 12.dp)
                .background(colorResource(R.color.purple_200))
        )
        OutlinedTextField(
            value = registerViewModel.username.value,
            onValueChange = { registerViewModel.username.value = it },
            placeholder = { Text(text = stringResource(R.string.name)) },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "name"
                )
            },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = colorResource(R.color.teal_700),
                unfocusedIndicatorColor = colorResource(R.color.purple_200)
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(18.dp, 12.dp)
                .background(colorResource(R.color.purple_200))
        )

        Button(
            onClick = {
                if(snackberTrigger==0) {
                    registerViewModel.onRegesterClicked()
                }else if(snackbarMessage != null){
                    registerViewModel.onRegesterClicked()
                }
                else{
                    //TODO:資料都輸入且符合規格回到登入頁
                    navController.navigate(Screen.LoginScreen.name)
                }
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = colorResource(R.color.purple_200),
                contentColor = Color.DarkGray
            )

        ) {
            Text(
                text = stringResource(R.string.submit)
            )
        }
        LaunchedEffect(snackberTrigger) {
            if (snackbarMessage != null) {
                scope.launch {
                    snackbarHostState.showSnackbar(
                        message = when (snackbarMessage) {
                            "Field cannot be empty." -> fieldEmptyMessage
                            "Email Formatting Error." -> emailFormatErrorMessage
                            "Password(at least 8 characters)" -> passwordLengthMessage
                            "Password do not match." -> passwordDifferent
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
fun RegisterScreenPreview() {
    TIP102Group01FriendzyTheme {
        RegisterScreen(rememberNavController(), registerViewModel = RegisterViewModel())
    }
}