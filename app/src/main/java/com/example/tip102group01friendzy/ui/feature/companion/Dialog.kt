package com.example.tip102group01friendzy.ui.feature.companion

import android.util.Log
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PublishDatePicker(
    onConfirm: (Long?) -> Unit,
    onDismiss: () -> Unit
){
    val datePickerState = rememberDatePickerState(
        // SelectableDates介面用來限制可選擇的日期與年
        selectableDates = object : SelectableDates {
            // 將顯示的日期逐一傳給utcTimeMillis參數，回傳true代表該日可選；false代表該日不可選
            override fun isSelectableDate(utcTimeMillis: Long): Boolean {
                val toDat = LocalDate.now()
                /* 將utcTimeMillis轉成LocalDate物件後取出星期幾的資訊，API 26開始支援Instant */
                val dayOfWeek = Instant.ofEpochMilli(utcTimeMillis).atZone(ZoneId.of("UTC"))
                    .toLocalDate()
                // 設定週六日不可選擇
                return dayOfWeek >= toDat
            }
            // 將顯示的年逐一傳給year參數，回傳true代表該年可選；false代表該年不可選
            override fun isSelectableYear(year: Int): Boolean {
                return year >= LocalDate.now().year
            }
        }
    )
    DatePickerDialog(
        // 點擊對話視窗外部或back按鈕時呼叫，並非點擊dismissButton時呼叫
        onDismissRequest = onDismiss,
        confirmButton = {
            Button(
                // 點擊確定按鈕時呼叫onConfirm(Long?)並將選取日期傳入以回饋給原畫面
                onClick = {
                    onConfirm(datePickerState.selectedDateMillis)
                }
            ) {
                Text("確認")
            }
        },
        // 設定取消按鈕
        dismissButton = {
            Button(onClick = onDismiss) {
                Text("取消")
            }
        }
    ) {
        DatePicker(state = datePickerState)
    }

}

/**JSON時間轉換String*/
fun formatTimestamp(timestamp: Long?): String {
    // 將 Timestamp 轉為 LocalDateTime
    val dateTime = Instant.ofEpochMilli(timestamp ?: return "")
        .atZone(ZoneId.systemDefault())
        .toLocalDateTime()

    // 格式化為 yyyy/MM/dd HH:mm
    val formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd")
    return dateTime.format(formatter)
}
/** 將日期、時間字串轉成毫秒數時間 */
fun combineToEpochMillis(date: String, time: String): Long {
    // 定義日期和時間的格式
    val dateFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd")
    val timeFormatter = DateTimeFormatter.ofPattern("HH:mm")

    // 將字串解析為 LocalDate 和 LocalTime
    val localDate = LocalDate.parse(date, dateFormatter)
    val localTime = LocalTime.parse(time, timeFormatter)
    // 組合成 LocalDateTime
    val localDateTime = LocalDateTime.of(localDate, localTime)

    // 將 LocalDateTime 轉為毫秒數時間戳
    return localDateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
}



//時間的TimePickerDialog 先不使用
/*
fun MyTimePickerDialog(
    onConfirm: (Long?) -> Unit,
    onDismiss: () -> Unit
){

}
 */