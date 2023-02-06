package com.vholodynskyi.assignment.presentation

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.*
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.IntOffset
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.vholodynskyi.assignment.presentation.contactslist.component.ContactListScreen
import com.vholodynskyi.assignment.presentation.details.component.DetailsScreen
import com.vholodynskyi.assignment.presentation.edit.compenent.EditScreen
import com.vholodynskyi.assignment.presentation.ui.theme.ContactAppTheme

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ContactAppTheme {
                NavigationComponent()
            }
        }
    }

    @OptIn(ExperimentalAnimationApi::class)
    @Composable
    fun NavigationComponent() {
        Surface {
            val navController = rememberAnimatedNavController()
            val springSpec = spring<IntOffset>(stiffness = Spring.StiffnessMediumLow)
            AnimatedNavHost(
                navController = navController,
                startDestination = Screen.ContactList.route
            ) {
                composable(
                    route = Screen.ContactList.route,
                    enterTransition = {
                        slideInHorizontally(initialOffsetX = { 1000 }, animationSpec = springSpec)
                    },
                    exitTransition = {
                        slideOutHorizontally(targetOffsetX = { -1000 }, animationSpec = springSpec)
                    },
                    popEnterTransition = {
                        slideInHorizontally(initialOffsetX = { -1000 }, animationSpec = springSpec)
                    },
                    popExitTransition = {
                        slideOutHorizontally(targetOffsetX = { 1000 }, animationSpec = springSpec)
                    }
                ) {
                    ContactListScreen(navController)
                }
                composable(
                    route = Screen.ContactDetail.route + "/{contactId}"
                ) {
                    DetailsScreen(navController = navController)
                }
                composable(
                    route = Screen.ContactEdit.route + "/{contactId}"
                ) {
                    EditScreen(navController = navController)
                }
            }
        }
    }
}