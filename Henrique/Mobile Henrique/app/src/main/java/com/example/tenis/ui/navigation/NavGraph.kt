package com.example.tenis.ui.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.tenis.ui.elements.account.AccountScreen
import com.example.tenis.ui.elements.add.AddScreen
import com.example.tenis.ui.elements.details.DetailsScreen
import com.example.tenis.ui.elements.edit.EditScreen
import com.example.tenis.ui.elements.home.HomeScreen
import com.example.tenis.ui.elements.login.loginScreen
import com.example.tenis.ui.elements.register.registerScreen

@Composable
fun FinNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {

    NavHost(
        navController = navController,
        startDestination = "login",
        modifier = modifier
    ){
        composable(
            route = "login",
            exitTransition = {
                slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Left,
                    tween(1000)
                )
            },
            enterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Right,
                    tween(1000)
                )
            }
        ) {
            loginScreen(
                navigateToHome = {navController.navigate("home")},
                onNavigateClick = {navController.navigate("register")}
            )
        }
        composable(
            route = "register",
            exitTransition = {
                slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Right,
                    tween(1000)
                )
            },
            enterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Left,
                    tween(1000)
                )
            }
        ) {
            registerScreen(
                navigateToHome = {navController.navigate("home")},
                onNavigateClick = {navController.navigate("login")}
            )
        }
        composable(
            route = "home",
        ){
            HomeScreen(
                navigateToAdd = {navController.navigate("add")},
                navController = navController
            )
        }
        composable(
            route = "add",
        ){
            AddScreen(
                navigateBack = {navController.navigate("home")}
            )
        }
        composable(
            route = "details/{id}",
            arguments = listOf(navArgument("id") { type = NavType.StringType })
        ){  backStackEntry ->
            val id = backStackEntry.arguments?.getString("id") ?: ""
            DetailsScreen(
                id = id,
                navigateToEditItem = {navController.navigate("edit/$id")},
                navigateBack = {navController.navigate("home")}
            )
        }
        composable(
            route = "edit/{id}",
            arguments = listOf(navArgument("id") { type = NavType.StringType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id") ?: ""
            EditScreen(
                id = id,
                navigateBack = {navController.navigate("details/$id")}
            )
        }
        composable(
            route = "account"
        ){
            AccountScreen(
                onNavigateBack = {navController.navigate("home")},
                onLogout = {navController.navigate("login")}
            )
        }
    }

}