package com.vholodynskyi.assignment.presentation.details.component

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.SubcomposeAsyncImage
import com.vholodynskyi.assignment.presentation.Screen
import com.vholodynskyi.assignment.presentation.details.DetailsViewModel
import com.vholodynskyi.assignment.presentation.ui.theme.Green
import com.vholodynskyi.assignment.presentation.ui.theme.GreenDark
import org.koin.androidx.compose.koinViewModel

@Composable
fun DetailsScreen(
    viewModel: DetailsViewModel = koinViewModel(), navController: NavController
) {
    val state by lazy { viewModel.state.value }

    state.contact?.let { contact ->
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

                        Image(painter = painterResource(id = com.vholodynskyi.assignment.R.drawable.ic_edit),
                            contentDescription = "Edit",
                            modifier = Modifier
                                .align(Alignment.CenterStart)
                                .padding(end = 32.dp)
                                .size(18.dp)
                                .clickable {
                                    navController.navigate(Screen.ContactEdit.route + "/${contact.id}")
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