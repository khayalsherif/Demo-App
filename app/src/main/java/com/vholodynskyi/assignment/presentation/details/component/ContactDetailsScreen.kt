package com.vholodynskyi.assignment.presentation.details.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
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
import com.vholodynskyi.assignment.presentation.details.DetailsViewModel
import com.vholodynskyi.assignment.presentation.ui.theme.Green
import org.koin.androidx.compose.koinViewModel

@Composable
fun DetailsScreen(
    viewModel: DetailsViewModel = koinViewModel(), navController: NavController
) {
    val state = viewModel.state.value

    state.contact?.let {
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

                Row(
                    modifier = Modifier
                        .padding(top = 16.dp, start = 8.dp, end = 8.dp)
                        .fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween

                ) {
                    Image(painter = painterResource(id = com.vholodynskyi.assignment.R.drawable.ic_back),
                        contentDescription = "Back",
                        modifier = Modifier
                            .size(20.dp)
                            .clickable {
                                navController.popBackStack()
                            })

                    Text(
                        text = "Profile".uppercase(),
                        modifier = Modifier,
                        color = Color.White,
                    )

                    Image(painter = painterResource(id = com.vholodynskyi.assignment.R.drawable.ic_bin),
                        contentDescription = "Remove",
                        modifier = Modifier
                            .size(20.dp)
                            .clickable {
                                viewModel.deleteById(state.contact.id)
                                navController.popBackStack()
                            })
                }

                Card(
                    modifier = Modifier
                        .size(150.dp)
                        .align(Alignment.BottomCenter),
                    shape = CircleShape,
                    elevation = 2.dp,
                    border = BorderStroke(3.dp, Color.White)
                ) {
                    SubcomposeAsyncImage(
                        model = state.contact.photo, loading = {
                            CircularProgressIndicator()
                        }, contentDescription = "Profile Photo"
                    )
                }
            }

            Text(
                text = "${state.contact.firstName} ${state.contact.lastName}",
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                fontWeight = FontWeight.Bold,
                fontSize = 22.sp
            )
            TextField("Email", state.contact.email!!)
        }
    }

}