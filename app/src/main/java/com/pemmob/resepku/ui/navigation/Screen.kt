package com.pemmob.resepku.ui.navigation

/**
 * Definisi rute navigasi aplikasi ResepKu.
 */
sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Detail : Screen("detail/{mealId}") {
        fun createRoute(mealId: String): String = "detail/$mealId"
    }
}
