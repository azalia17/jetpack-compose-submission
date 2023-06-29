package com.example.jetsubmission.ui.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Favorite : Screen("favorite")
    object Profile : Screen("profile")
    object DetailBook : Screen("home/{id}") {
        fun createRoute(id: Int) = "home/$id"
    }
}