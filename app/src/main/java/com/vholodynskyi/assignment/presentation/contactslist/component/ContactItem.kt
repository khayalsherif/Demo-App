package com.vholodynskyi.assignment.presentation.contactslist.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import com.vholodynskyi.assignment.domain.model.Contact

@Composable
@OptIn(ExperimentalMaterialApi::class)
fun ContactItem(
    contact: Contact,
    onItemClick: (Contact) -> Unit
) {
    ListItem(
        text = { Text(text = "${contact.firstName} ${contact.lastName}") },
        overlineText = { Text(text = "Full Name") },
        icon = {
            Card(
                shape = CircleShape,
                elevation = 2.dp
            ) {
                SubcomposeAsyncImage(
                    model = contact.photo, loading = {
                        CircularProgressIndicator()
                    }, contentDescription = "Profile Photo"
                )
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colors.surface, shape = RectangleShape)
            .clickable {
                onItemClick(contact)
            }
    )
}