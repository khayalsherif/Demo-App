package com.vholodynskyi.assignment.presentation

sealed class Screen(val route: String) {
    object ContactList : Screen("contact_list_screen")
    object ContactDetail : Screen("contact_detail_screen")
    object ContactEdit :Screen("contact_edit_screen")
}