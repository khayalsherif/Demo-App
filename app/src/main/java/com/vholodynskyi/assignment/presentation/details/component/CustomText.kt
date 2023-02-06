package com.vholodynskyi.assignment.presentation.details.component

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun TextFieldCustom(title: String, body: String) {
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
    }
}