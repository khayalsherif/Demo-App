package com.vholodynskyi.assignment.presentation.edit.compenent

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.vholodynskyi.assignment.presentation.ui.theme.Green
import com.vholodynskyi.assignment.presentation.ui.theme.GreenLight


@Composable
fun CustomTextField(
    text: String, caption: String = "", modifier: Modifier, onValueChangeUnit: (String) -> Unit
) {
    Column(modifier = Modifier.padding(start = 16.dp, end = 16.dp)) {
        var textState by remember { mutableStateOf(text) }
        val maxLength = 110
        Text(
            text = caption,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 4.dp),
            textAlign = TextAlign.Start,
            color = Green
        )
        TextField(modifier = modifier,
            value = textState,
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = GreenLight,
                cursorColor = Color.Black,
                disabledLabelColor = GreenLight,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            onValueChange = {
                if (it.length <= maxLength) {
                    textState = it
                    onValueChangeUnit.invoke(it)
                }
            },
            shape = RoundedCornerShape(8.dp),
            singleLine = true,
            trailingIcon = {
                if (textState.isNotEmpty()) {
                    IconButton(onClick = { textState = "" }) {
                        Icon(
                            imageVector = Icons.Outlined.Close, contentDescription = null
                        )
                    }
                }
            })
        Text(
            text = "${textState.length} / $maxLength",
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 4.dp),
            textAlign = TextAlign.End,
            color = Green
        )
    }
}

