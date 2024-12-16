package com.example.tip102group01friendzy.ui.feature.customer

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.tip102group01friendzy.R

@Composable
fun CustomerScreen(
    navController: NavHostController = rememberNavController(),
    customerVM: CustomerVM
) {
    var inputText by remember { mutableStateOf("") }
    val customerState by customerVM.memberState.collectAsState()
    var scope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp),
            horizontalArrangement = Arrangement.Center
        )
        {
            OutlinedTextField(
                value = inputText,
                onValueChange = { inputText = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp),
//               label = { Text(text = stringResource(R.string.search)) },
                leadingIcon = {
                    Icon(imageVector = Icons.Default.Search, contentDescription = "Search")
                },
                trailingIcon = {
                    Icon(imageVector = Icons.Default.Clear,
                        contentDescription = "Cancel",
                        modifier = Modifier.clickable { inputText = "" }
                    )
                },
                singleLine = true,
                shape = RoundedCornerShape(15.dp),
                placeholder = { Text(stringResource(R.string.search_special)) }

            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        )
        {
            Button(
                modifier = Modifier.weight(1f),
                onClick = {},
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(R.color.purple_200),
                    contentColor = Color.DarkGray
                )
            ) { Text(text = stringResource(R.string.order_manage)) }
            Button(
                modifier = Modifier.weight(1f),
                onClick = {},
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(R.color.purple_200),
                    contentColor = Color.DarkGray
                )
            ) { Text(text = stringResource(R.string.collection)) }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Button(
                modifier = Modifier.weight(1f),
                onClick = {},
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(R.color.purple_200),
                    contentColor = Color.DarkGray
                )
            ) { Text(text = stringResource(R.string.post_setting)) }
            Button(
                modifier = Modifier.weight(1f),
                onClick = {},
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(R.color.purple_200),
                    contentColor = Color.DarkGray
                )
            ) { Text(text = stringResource(R.string.my_applaction)) }
        }
        HorizontalDivider(
            modifier = Modifier.padding(top = 10.dp),
            color = colorResource(R.color.teal_700)
        )
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp),
            text = stringResource(R.string.recommemd),
            textAlign = TextAlign.Start,
            fontSize = 28.sp
        )
        customerList(
            customers = customerState,
            onClick = {
                //要跳到會員資料
            }
        )
        BottomAppBar(){}

    }
}


@Composable
fun customerList(
    customers: List<Customer>,
    onClick: (Customer) -> Unit
) {
    LazyColumn {
        items(customers) { customer ->
            ListItem(
                modifier = Modifier.clickable { onClick(customer) },
                headlineContent = { Text(text = customer.memberName) },
                supportingContent = { Text(text = "I am good at  ${customer.memberSpecialty}") },
                leadingContent = {
                    Image(
                        painter = painterResource(id = customer.memberImg),
                        contentDescription = "memberPhoto"
                    )
                },
            )
            HorizontalDivider(modifier = Modifier.padding(top = 5.dp))
        }
    }
}


@Composable
@Preview(showBackground = true)
fun CustomerScreenPreview() {
    CustomerScreen(rememberNavController(), customerVM = CustomerVM())
}