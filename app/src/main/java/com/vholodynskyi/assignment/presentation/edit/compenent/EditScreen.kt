package com.vholodynskyi.assignment.presentation.edit.compenent

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.vholodynskyi.assignment.R
import com.vholodynskyi.assignment.domain.model.Contact
import com.vholodynskyi.assignment.presentation.edit.EditViewModel
import com.vholodynskyi.assignment.presentation.ui.theme.Green
import kotlinx.coroutines.delay
import org.koin.androidx.compose.koinViewModel

@Composable
fun EditScreen(
    navController: NavController, viewModel: EditViewModel = koinViewModel()
) {
    val state by lazy { viewModel.state.value }
    val focusRequester = remember { FocusRequester() }
    val context = LocalContext.current

    LaunchedEffect(key1 = Unit) {
        delay(400)
        focusRequester.requestFocus()
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .background(Green)

    ) {
        Image(painter = painterResource(id = R.drawable.ic_back),
            contentDescription = "Back",
            modifier = Modifier
                .align(Alignment.CenterStart)
                .padding(start = 8.dp, end = 8.dp)
                .size(20.dp)
                .clickable {
                    navController.popBackStack()
                })

        Text(
            text = "Edit".uppercase(),
            modifier = Modifier.align(Alignment.Center),
            color = Color.White
        )

    }

    state.contact?.let { contact ->
        var emailText by rememberSaveable { mutableStateOf(contact.email!!) }
        var fullNameText by rememberSaveable { mutableStateOf("${contact.firstName} ${contact.lastName}") }

        Column(modifier = Modifier.padding(top = 80.dp)) {

            CustomTextField(text = fullNameText,
                caption = "Full Name",
                modifier = Modifier
                    .fillMaxWidth()
                    .focusRequester(focusRequester),
                onValueChangeUnit = {
                    fullNameText = it
                })

            CustomTextField(text = emailText,
                caption = "Email",
                modifier = Modifier.fillMaxWidth(),
                onValueChangeUnit = {
                    emailText = it
                })

            Button(
                onClick = {
                    val updatedContact = Contact(
                        contact.id,
                        fullNameText.split(" ")[0],
                        fullNameText.split(" ")[1],
                        emailText,
                        contact.photo
                    )
                    viewModel.updateContact(updatedContact)
                    Toast.makeText(context, "Changed contact item", Toast.LENGTH_LONG).show()
                },
                modifier = Modifier
                    .align(alignment = Alignment.CenterHorizontally)
                    .padding(top = 16.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = Green)

            ) {
                Text(text = "Save", color = Color.White)
            }

        }

    }

}
