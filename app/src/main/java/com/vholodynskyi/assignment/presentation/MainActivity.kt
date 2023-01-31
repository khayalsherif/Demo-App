package com.vholodynskyi.assignment.presentation

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.Surface
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.vholodynskyi.assignment.presentation.contactslist.component.ContactListScreen
import com.vholodynskyi.assignment.presentation.details.component.DetailsScreen
import com.vholodynskyi.assignment.presentation.ui.theme.ContactAppTheme

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ContactAppTheme {
                Surface {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = Screen.ContactList.route
                    ) {
                        composable(
                            route = Screen.ContactList.route
                        ) {
                            ContactListScreen(navController)
                        }
                        composable(
                            route = Screen.ContactDetail.route + "/{contactId}"
                        ) {
                            DetailsScreen(navController = navController)
                        }
                    }
                }
            }
        }
    }
}