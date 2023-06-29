package com.example.jetsubmission

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.jetsubmission.ui.navigation.NavigationItem
import com.example.jetsubmission.ui.navigation.Screen
import com.example.jetsubmission.ui.screen.detail.DetailScreen
import com.example.jetsubmission.ui.screen.favorite.FavoriteScreen
import com.example.jetsubmission.ui.screen.home.HomeScreen
import com.example.jetsubmission.ui.screen.profile.ProfileScreen
import com.example.jetsubmission.ui.theme.JetSubmissionTheme


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JetBookApp(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        bottomBar = {
            if (currentRoute  != Screen.DetailBook.route) {
                BottomBar(navController = navController)
            }
        },
        modifier = modifier
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.Home.route) {
                HomeScreen(
                    navigateToDetail = { bookId ->
                        navController.navigate(Screen.DetailBook.createRoute(bookId))
                    },
                    modifier = modifier
                )
            }
            composable(Screen.Favorite.route) {
                FavoriteScreen(navigateToDetail = {bookId ->
                    navController.navigate(Screen.DetailBook.createRoute(bookId))
                })
            }
            composable(Screen.Profile.route) {
                ProfileScreen()
            }
            composable(
                route = Screen.DetailBook.route,
                arguments = listOf(navArgument("id") { type = NavType.IntType }),
            ) {
                val id = it.arguments?.getInt("id") ?: -1
                DetailScreen(bookId = id, navigateBack = { navController.navigateUp() })
            }
        }

    }

}

@Composable
fun BottomBar(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavigationBar(modifier) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        val navigationItem = listOf(
            NavigationItem(
                title = stringResource(id = R.string.home),
                icon = Icons.Default.Home,
                screen = Screen.Home,
                contentDescription = stringResource(id = R.string.home_page)
            ),
            NavigationItem(
                title = stringResource(id = R.string.fav),
                icon = Icons.Default.List,
                screen = Screen.Favorite,
                contentDescription = stringResource(id = R.string.favorite_page)
            ),
            NavigationItem(
                title = stringResource(id = R.string.profile),
                icon = Icons.Default.AccountCircle,
                screen = Screen.Profile,
                contentDescription = stringResource(id = R.string.about_page)
            ),
        )
        NavigationBar() {
            navigationItem.map { item ->
                NavigationBarItem(
                    icon = {
                        Icon(
                            imageVector = item.icon,
                            contentDescription = item.contentDescription
                        )
                    },
                    label = { Text(text = item.title) },
                    selected = currentRoute == item.screen.route,
                    onClick = {
                        navController.navigate(item.screen.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            restoreState = true
                            launchSingleTop = true
                        }
                    }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun JetBookAppPreview() {
    JetSubmissionTheme {
        JetBookApp()
    }
}