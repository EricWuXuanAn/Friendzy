package com.example.tip102group01friendzy.ui.feature.customer

import android.service.controls.Control
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.tip102group01friendzy.R
import java.security.AccessController

@Composable
fun CustomerScreen(
    navController:NavHostController = rememberNavController(),
    customerVM: CustomerVM
){
    var inputText by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
       Row(modifier =Modifier.fillMaxWidth().padding(5.dp),
           horizontalArrangement = Arrangement.Center)
       {
           OutlinedTextField(
               value = inputText,
               onValueChange = {inputText = it},
               modifier = Modifier.fillMaxWidth().padding(5.dp),
               label = { Text(text = stringResource(R.string.search)) },
               leadingIcon = {
                   Icon(imageVector = Icons.Default.Search, contentDescription = "Search")
               }
           )

       }
        //kokokokokokokko
        //jjijijijijii
    }
}








@Composable
@Preview(showBackground = true)
fun CustomerScreenPreview(){
    CustomerScreen(rememberNavController(), customerVM = CustomerVM())
}