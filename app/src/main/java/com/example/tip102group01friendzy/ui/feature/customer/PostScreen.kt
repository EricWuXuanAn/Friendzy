package com.example.tip102group01friendzy.ui.feature.customer

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults.TrailingIcon
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.tip102group01friendzy.R
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter.ofPattern

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PostScreen(
    navController: NavHostController,
    postVM: PostVM
) {
    var startExpendText by remember { mutableStateOf("HH") }
    var endExpendText by remember { mutableStateOf("HH") }

    var startExpended by remember { mutableStateOf(false) }
    var endExpended by remember { mutableStateOf(false) }

    val options = listOf(
        "00:00", "01:00", "02:00", "03:00", "04:00", "05:00", "06:00", "07:00", "08:00", "09:00",
        "10:00", "11:00", "12:00", "13:00", "14:00", "15:00", "16:00", "17:00", "18:00", "19:00",
        "20:00", "21:00", "22:00", "23:00", "24:00"
    )

    val dateFormat = ofPattern("YYYY-MM-dd") //設定日期格式
    var startSelectDate by remember {
        mutableStateOf(
            LocalDate.now().format(dateFormat)
        )
    }
    var endSelectDate by remember {
        mutableStateOf(
            LocalDate.now().format(dateFormat)
        )
    }

    //日期對話選擇後會在Text上顯示的日期
    var showStartDatePickerDialog by remember { mutableStateOf(false) } //預設日期對話匡要顯示還是不顯示，預設為不顯示
    var showEndtDatePickerDialog by remember { mutableStateOf(false) }
    var inputTitle by remember { mutableStateOf("") }
    var scpoe = rememberCoroutineScope()
    val snackBar = remember { SnackbarHostState() }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp),
            text = "Post Your Event!",
            textAlign = TextAlign.Center,
            fontFamily = FontFamily.Cursive,
            fontSize = 25.sp
        )
        HorizontalDivider(modifier = Modifier.padding(bottom = 10.dp, top = 10.dp))
        Column(
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp),
                text = "Event Title:",
                fontSize = 17.sp
            )
            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 15.dp)
                    .height(28.dp),
                value = inputTitle,
                onValueChange = { inputTitle = it },
                label = { Text(text = "Type your title!", fontSize = 13.sp) }
            )
            Spacer(
                Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
            )
            Text("Start Time:")
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp),
                horizontalArrangement = Arrangement.Start
            ) {
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(0.64f),
                    value = "Date: ${startSelectDate}",
                    onValueChange = { startSelectDate = it },
                    shape = RoundedCornerShape(15.dp),
                    trailingIcon = {
                        IconButton(onClick = {
                            showStartDatePickerDialog = true

                        }) {
                            Icon(
                                painter = painterResource(R.drawable.date_range),
                                contentDescription = "Date select"
                            )
                        }
                        if (showStartDatePickerDialog) {
                            getDatePicker(
                                onDismiss = {
                                    showStartDatePickerDialog = false
                                },
                                onClick = { startutcTimeMillis ->
                                    startSelectDate = startutcTimeMillis?.let {
                                        Instant.ofEpochMilli(it).atZone(ZoneId.of("UTC"))
                                            .toLocalDate()
                                            .format(dateFormat)
                                    } ?: startSelectDate
                                    showStartDatePickerDialog = false
                                },
                                onDismissRequest = {
                                    showStartDatePickerDialog = false
                                }
                            )
                        }
                    }
                )
                ExposedDropdownMenuBox(
                    onExpandedChange = {
                        startExpended = it
                        startExpended = true
                    },
                    expanded = startExpended
                ) {
                    OutlinedTextField(
                        value = startExpendText,
                        onValueChange = { startExpendText = it },
                        singleLine = true,
                        modifier = Modifier
                            .menuAnchor()
                            .padding(start = 10.dp),
                        shape = RoundedCornerShape(15.dp),
                        trailingIcon = { TrailingIcon(expanded = startExpended) },
                        readOnly = true
                    )
                    ExposedDropdownMenu(
                        expanded = startExpended,
                        onDismissRequest = { startExpended = false }
                    ) {
                        options.forEach { startoption ->
                            DropdownMenuItem(
                                text = { Text(startoption) },
                                onClick = {
                                    startExpendText = startoption
                                    startExpended = false
                                }
                            )
                        }
                    }
                }

            }
            Text(text = "End Time:", modifier = Modifier.padding(top = 10.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp),
                horizontalArrangement = Arrangement.Start
            ) {
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(0.64f),
                    value = "Date: ${endSelectDate}",
                    onValueChange = { endSelectDate = it },
                    shape = RoundedCornerShape(15.dp),
                    trailingIcon = {
                        IconButton(onClick = {
                            showEndtDatePickerDialog = true

                        }) {
                            Icon(
                                painter = painterResource(R.drawable.date_range),
                                contentDescription = "Date select"
                            )
                        }
                        if (showEndtDatePickerDialog) {
                            getDatePicker(
                                onDismiss = {
                                    showEndtDatePickerDialog = false
                                },
                                onClick = { endutcTimeMillis ->
                                    endSelectDate = endutcTimeMillis?.let {
                                        Instant.ofEpochMilli(it).atZone(ZoneId.of("UTC"))
                                            .toLocalDate()
                                            .format(dateFormat)
                                    } ?: endSelectDate
                                    showEndtDatePickerDialog = false
                                },
                                onDismissRequest = {
                                    showEndtDatePickerDialog = false
                                }
                            )
                        }
                    }
                )
                ExposedDropdownMenuBox(
                    onExpandedChange = {
                        endExpended = it
                        endExpended = true
                    },
                    expanded = endExpended
                ) {
                    OutlinedTextField(
                        value = endExpendText,
                        onValueChange = { endExpendText = it },
                        singleLine = true,
                        modifier = Modifier
                            .menuAnchor()
                            .padding(start = 10.dp),
                        shape = RoundedCornerShape(15.dp),
                        trailingIcon = { TrailingIcon(expanded = endExpended) },
                        readOnly = true
                    )
                    ExposedDropdownMenu(
                        expanded = endExpended,
                        onDismissRequest = { endExpended = false }
                    ) {
                        options.forEach { endopiton ->
                            DropdownMenuItem(
                                text = { Text(endopiton) },
                                onClick = {
                                    endExpendText = endopiton
                                    endExpended = false
                                }
                            )
                        }
                    }
                }

            }

        }
    }
    Column(
        horizontalAlignment = Alignment.End,
        verticalArrangement = Arrangement.Bottom,
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        OutlinedButton(
            modifier = Modifier
                .padding(10.dp)
                .height(110.dp),
            shape = RoundedCornerShape(10.dp),
            onClick = {}
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(
                    Icons.Default.Add,
                    contentDescription = "upload photo"
                )
                Text("Upload Photo")
            }
        }
        Button(
            colors = ButtonDefaults.buttonColors(
                containerColor = colorResource(R.color.purple_200),
                contentColor = Color.DarkGray
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp),
            onClick = {
                scpoe.launch {
                    snackBar.showSnackbar(
                        "Successfully Post Your Event!",
                        withDismissAction = true
                    )
                    navController.popBackStack()
                }
            }
        ) {
            Text("Post")
        }
    }

}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
//做一個日期選擇對話筐
fun getDatePicker(
    onClick: (Long?) -> Unit,
    onDismiss: () -> Unit,
    onDismissRequest: () -> Unit
) {
    var datePickerState = rememberDatePickerState(selectableDates = object : SelectableDates {
        override fun isSelectableYear(year: Int): Boolean {
            return year >= 2024
        }
    })

    DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            Button(onClick = {
                onClick(datePickerState.selectedDateMillis)
            }) {
                Text(text = stringResource(R.string.Confirm))
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) { Text(text = stringResource(R.string.Cancel)) }
        }
    )
    { DatePicker(state = datePickerState) }
}


@Composable
@Preview(showBackground = true)
fun PostScreenPreview() {
    PostScreen(rememberNavController(), postVM = PostVM())
}