package com.vholodynskyi.assignment.presentation.contactslist.component

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.github.fengdai.compose.pulltorefresh.PullToRefresh
import com.github.fengdai.compose.pulltorefresh.PullToRefreshState
import com.vholodynskyi.assignment.domain.model.Contact
import com.vholodynskyi.assignment.presentation.Screen
import com.vholodynskyi.assignment.presentation.contactslist.ContactListViewModel
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ContactListScreen(
    navController: NavController, viewModel: ContactListViewModel = koinViewModel()
) {
    val isRefreshing = rememberSaveable { mutableStateOf(false) }
    val context = LocalContext.current
    val state = viewModel.contactResponse.value

    PullToRefresh(state = PullToRefreshState(isRefreshing.value), onRefresh = {
        isRefreshing.value = true
        viewModel.syncData()
    }) {
        if (state.contact.isNotEmpty()) {
            if (isRefreshing.value) isRefreshing.value = false
            Box(
                modifier = Modifier.fillMaxSize()
            ) {

                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    itemsIndexed(items = state.contact, key = { _: Int, item: Contact ->
                        item.hashCode()
                    }) { index: Int, item: Contact ->
                        val itemState = rememberDismissState(confirmStateChange = {
                            if (it == DismissValue.DismissedToStart) {
                                viewModel.deleteById(state.contact[index].id)
                            }
                            true
                        })
                        SwipeToDismiss(
                            state = itemState,
                            background = {
                                val color = when (itemState.dismissDirection) {
                                    DismissDirection.StartToEnd -> Color.Transparent
                                    DismissDirection.EndToStart -> Color.Red
                                    null -> Color.Transparent
                                }

                                Box(
                                    modifier = Modifier
                                        .background(color)
                                        .fillMaxSize()
                                        .padding(end = 8.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Delete,
                                        contentDescription = "Delete", tint = Color.White,
                                        modifier = Modifier.align(Alignment.CenterEnd)
                                    )
                                }
                            },
                            dismissContent = {
                                ContactItem(contact = item, onItemClick = {
                                    navController.navigate(Screen.ContactDetail.route + "/${item.id}")
                                })
                            },
                            directions = setOf(DismissDirection.EndToStart)
                        )
                        Divider()
                    }
                }
            }

            LaunchedEffect(key1 = true) {
                viewModel.baseEffect.collect {
                    Toast.makeText(context, it, Toast.LENGTH_LONG).show()
                }
            }
        }
    }

}