package com.vholodynskyi.assignment.presentation.details.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.vholodynskyi.assignment.presentation.ui.theme.Green

@Composable
fun TextField(title: String, body: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, bottom = 12.dp)
    ) {
        Text(
            text = title,
            modifier = Modifier.padding(bottom = 6.dp),
            color = Color.Black
        )
        Text(text = body)
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(Green)
                .padding(top = 6.dp)
        )
    }
}