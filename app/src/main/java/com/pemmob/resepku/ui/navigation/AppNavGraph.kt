package com.pemmob.resepku.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.pemmob.resepku.ui.detail.RecipeDetailScreen
import com.pemmob.resepku.ui.home.HomeScreen

/**
 * NavGraph untuk mengatur alur navigasi dari Home Screen ke Recipe Detail Screen.
 * Sesuai persyaratan Modul Poin 7: Aplikasi minimal memiliki 2 screen.
 */
@Composable
fun AppNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route,
        modifier = modifier
    ) {
        // 1. Home Screen
        composable(route = Screen.Home.route) {
            HomeScreen(
                onRecipeClick = { mealId ->
                    navController.navigate(Screen.Detail.createRoute(mealId))
                }
            )
        }

        // 2. Recipe Detail Screen
        composable(
            route = Screen.Detail.route,
            arguments = listOf(
                navArgument("mealId") {
                    type = NavType.StringType
                }
            )
        ) { backStackEntry ->
            val mealId = backStackEntry.arguments?.getString("mealId").orEmpty()
            RecipeDetailScreen(
                mealId = mealId,
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }
    }
}
