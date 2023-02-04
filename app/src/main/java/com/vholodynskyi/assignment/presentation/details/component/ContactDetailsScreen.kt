package com.vholodynskyi.assignment.presentation.details.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.SubcomposeAsyncImage
import com.vholodynskyi.assignment.domain.model.Contact
import com.vholodynskyi.assignment.presentation.details.DetailsViewModel
import com.vholodynskyi.assignment.presentation.ui.theme.Green
import com.vholodynskyi.assignment.presentation.ui.theme.GreenDark
import org.koin.androidx.compose.koinViewModel

@Composable
fun DetailsScreen(
    viewModel: DetailsViewModel = koinViewModel(), navController: NavController
) {
    val state = viewModel.state.value
    var isEditMode by rememberSaveable { mutableStateOf(false) }

    state.contact?.let { contact ->
        var emailText by rememberSaveable { mutableStateOf(contact.email!!) }
        var fullNameText by rememberSaveable { mutableStateOf("${contact.firstName} ${contact.lastName}") }

        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
                    .background(Green)
            ) {
                Spacer(
                    modifier = Modifier.fillMaxSize()
                )
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(75.dp)
                        .align(Alignment.BottomCenter)
                        .background(Color.White)
                )
                Box(
                    modifier = Modifier
                        .padding(top = 16.dp, start = 8.dp, end = 8.dp)
                        .fillMaxWidth()

                ) {
                    Image(painter = painterResource(id = com.vholodynskyi.assignment.R.drawable.ic_back),
                        contentDescription = "Back",
                        modifier = Modifier
                            .align(Alignment.CenterStart)
                            .size(20.dp)
                            .clickable {
                                navController.popBackStack()
                            })

                    Text(
                        text = "Profile".uppercase(),
                        modifier = Modifier.align(Alignment.Center),
                        color = Color.White
                    )
                    Box(modifier = Modifier.align(Alignment.CenterEnd)) {
                        if (isEditMode) {
                            Image(painter = painterResource(id = com.vholodynskyi.assignment.R.drawable.ic_done),
                                contentDescription = "Done",
                                modifier = Modifier
                                    .align(Alignment.CenterStart)
                                    .padding(end = 32.dp)
                                    .size(18.dp)
                                    .clickable {
                                        viewModel.updateContact(
                                            Contact(
                                                contact.id,
                                                fullNameText.split(" ")[0],
                                                fullNameText.split(" ")[1],
                                                emailText,
                                                contact.photo
                                            )
                                        )
                                        isEditMode = isEditMode.not()
                                    })
                        } else {
                            Image(painter = painterResource(id = com.vholodynskyi.assignment.R.drawable.ic_edit),
                                contentDescription = "Edit",
                                modifier = Modifier
                                    .align(Alignment.CenterStart)
                                    .padding(end = 32.dp)
                                    .size(18.dp)
                                    .clickable {
                                        isEditMode = isEditMode.not()
                                    })
                        }
                        Image(painter = painterResource(id = com.vholodynskyi.assignment.R.drawable.ic_bin),
                            contentDescription = "Remove",
                            modifier = Modifier
                                .align(Alignment.CenterEnd)
                                .size(20.dp)
                                .clickable {
                                    viewModel.deleteById(contact.id)
                                    navController.popBackStack()
                                })
                    }

                }
                Card(
                    modifier = Modifier
                        .size(150.dp)
                        .align(Alignment.BottomCenter),
                    shape = CircleShape,
                    elevation = 2.dp,
                    border = BorderStroke(3.dp, GreenDark)
                ) {
                    SubcomposeAsyncImage(
                        model = contact.photo, loading = {
                            CircularProgressIndicator()
                        }, contentDescription = "Profile Photo"
                    )
                }
            }
            if (isEditMode) {
                TextField(
                    value = fullNameText,
                    onValueChange = {
                        fullNameText = it
                    },
                    modifier = Modifier
                        .padding(top = 8.dp, bottom = 16.dp)
                        .align(Alignment.CenterHorizontally)
                        .widthIn(min = 40.dp),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    ),
                    textStyle = TextStyle.Default.copy(
                        fontWeight = FontWeight.Bold, fontSize = 22.sp
                    )
                )

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, end = 16.dp, bottom = 12.dp)
                ) {
                    Text(
                        text = "Email", modifier = Modifier, color = Color.Black
                    )
                    TextField(
                        value = emailText,
                        onValueChange = {
                            emailText = it
                        },
                        modifier = Modifier
                            .align(Alignment.Start)
                            .widthIn(min = 40.dp)
                            .heightIn(min = 40.dp),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                        colors = TextFieldDefaults.textFieldColors(
                            backgroundColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent
                        )
                    )
                }

            } else {
                Text(
                    text = "${contact.firstName} ${contact.lastName}",
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp, bottom = 16.dp),
                    fontWeight = FontWeight.Bold,
                    fontSize = 22.sp
                )
                TextFieldCustom("Email", contact.email!!)
            }
        }
    }
}