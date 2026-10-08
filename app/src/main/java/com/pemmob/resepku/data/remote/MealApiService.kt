package com.pemmob.resepku.data.remote

import com.pemmob.resepku.data.model.MealResponse
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Interface Retrofit API Service untuk berkomunikasi dengan TheMealDB API.
 * Sesuai persyaratan modul:
 * 1. Search Recipe: search.php?s={nama_makanan}
 * 2. Detail Recipe: lookup.php?i={id_recipe}
 */
interface MealApiService {

    @GET("search.php")
    suspend fun searchRecipes(
        @Query("s") query: String
    ): MealResponse

    @GET("lookup.php")
    suspend fun getRecipeDetail(
        @Query("i") id: String
    ): MealResponse
}
