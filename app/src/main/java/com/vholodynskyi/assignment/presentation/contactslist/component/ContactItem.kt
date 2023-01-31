package com.vholodynskyi.assignment.presentation.contactslist.component

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.vholodynskyi.assignment.domain.model.Contact

@Composable
fun ContactItem(
    contact: Contact,
    onItemClick: (Contact) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, top = 32.dp, end = 16.dp)
            .border(shape = CircleShape, width = 1.dp, color = Color.Black)
            .clickable {
                onItemClick(contact)
            },
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "${contact.firstName} ${contact.lastName}", modifier = Modifier.padding(20.dp)
        )
    }
}