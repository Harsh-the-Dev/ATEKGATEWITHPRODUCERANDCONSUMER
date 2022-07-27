package com.atek.gate.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.time.LocalDateTime

@Composable
fun Footer(modifier: Modifier) {
    Row(
        modifier = modifier.fillMaxSize().background(Color.Blue),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly,
    ) {
        Text(
            text = "ENTRY",
            color = Color.White,
            fontSize = 20.sp,
            modifier = modifier.padding(10.dp),
        )
        Text(
            text = "10.13.3.235",
            color = Color.White,
            fontSize = 20.sp,
            modifier = modifier.padding(10.dp),
        )
        Text(
            text = "1234",
            color = Color.White,
            fontSize = 20.sp,
            modifier = modifier.padding(10.dp),
        )
        Text(
            text = "Ghatkopar",
            color = Color.White,
            fontSize = 20.sp,
            modifier = modifier.padding(10.dp),
        )
        Text(
            text = LocalDateTime.now().toLocalDate().toString(),
            color = Color.White,
            fontSize = 20.sp,
            modifier = modifier.padding(10.dp),
        )
    }
}