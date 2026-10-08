package com.pemmob.resepku.data.repository

import com.pemmob.resepku.data.model.MealDto
import com.pemmob.resepku.data.remote.MealApiService
import com.pemmob.resepku.data.remote.RetrofitClient

/**
 * Interface Repository untuk mengabstraksi pemanggilan data API dari TheMealDB.
 * Sesuai persyaratan arsitektur MVVM:
 * Pengelolaan data tidak boleh dilakukan langsung di Composable.
 */
interface MealRepository {
    suspend fun searchRecipes(query: String): Result<List<MealDto>>
    suspend fun getRecipeDetail(id: String): Result<MealDto?>
}

/**
 * Implementasi dari MealRepository.
 * Menghubungkan Retrofit API Service dengan ViewModel serta menangani pengecualian (Exception) jaringan.
 */
class MealRepositoryImpl(
    private val apiService: MealApiService = RetrofitClient.apiService
) : MealRepository {

    override suspend fun searchRecipes(query: String): Result<List<MealDto>> {
        return runCatching {
            val response = apiService.searchRecipes(query)
            response.meals.orEmpty()
        }
    }

    override suspend fun getRecipeDetail(id: String): Result<MealDto?> {
        return runCatching {
            val response = apiService.getRecipeDetail(id)
            response.meals?.firstOrNull()
        }
    }
}
