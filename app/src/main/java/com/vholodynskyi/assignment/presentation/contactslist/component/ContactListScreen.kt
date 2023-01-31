package com.vholodynskyi.assignment.presentation.contactslist.component

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.github.fengdai.compose.pulltorefresh.PullToRefresh
import com.github.fengdai.compose.pulltorefresh.PullToRefreshState
import com.vholodynskyi.assignment.presentation.Screen
import com.vholodynskyi.assignment.presentation.contactslist.ContactListViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun ContactListScreen(
    navController: NavController,
    viewModel: ContactListViewModel = koinViewModel()
) {
    val isRefreshing = rememberSaveable { mutableStateOf(false) }
    val context = LocalContext.current
    val state = viewModel.contactResponse.value

    PullToRefresh(state = PullToRefreshState(isRefreshing.value),
        onRefresh = {
            isRefreshing.value = true
            viewModel.syncData()
        }
    ) {
        if (state.contact.isNotEmpty()) {
            if (isRefreshing.value) isRefreshing.value = false
            Box(modifier = Modifier.fillMaxSize()) {
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(state.contact) { contact ->
                        ContactItem(
                            contact = contact,
                            onItemClick = {
                                navController.navigate(Screen.ContactDetail.route + "/${contact.id}")
                            }
                        )
                    }
                }
            }
            if (state.error.isNotEmpty()) {
                Toast.makeText(context, state.error, Toast.LENGTH_LONG).show()
            }
        }
    }

}