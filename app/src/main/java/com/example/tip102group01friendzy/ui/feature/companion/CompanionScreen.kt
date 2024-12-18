package com.example.tip102group01friendzy.ui.feature.companion

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.TabRow
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController


@Composable
fun CompanionRoute(
    navController: NavHostController = rememberNavController(),
    companionVM: CompanionVM
){
    CompanionScreen(

    )
}


@Composable
fun CompanionScreen(
    items: List<Any> = listOf()

    ){
    var inputText by remember { mutableStateOf("") }
    var tabIndex by remember { mutableIntStateOf(0) }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = inputText,
            onValueChange = {inputText = it},
            modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp),
            leadingIcon = {
                Icon(imageVector = Icons.Default.Search,
                    contentDescription = "Search",
                )
            },
            trailingIcon = {
                Icon(imageVector = Icons.Default.Clear,
                    contentDescription ="Clear",
                    modifier = Modifier.clickable { inputText = "" }
                )
            },
            singleLine = true,
            shape = RoundedCornerShape(15.dp),
        )
        HorizontalDivider()
        TabRow(
            selectedTabIndex = tabIndex,

        ) { }
    }
}



@Composable
@Preview(showBackground = true)
fun PreviewCompanionScreen(){
    CompanionScreen()
}