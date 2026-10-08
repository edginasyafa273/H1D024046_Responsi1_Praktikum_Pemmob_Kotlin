package com.pemmob.resepku

import com.pemmob.resepku.data.model.MealDto
import com.pemmob.resepku.data.model.extractIngredients
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

/**
 * Unit Test untuk memverifikasi logika parsing dan Extension Function Kotlin.
 * Memastikan fitur Null Safety dan konversi 20 bahan TheMealDB berjalan dengan benar.
 */
class MealModelTest {

    @Test
    fun extractIngredients_withValidAndNullFields_returnsCleanList() {
        // Given
        val dummyMeal = MealDto(
            idMeal = "52772",
            strMeal = "Teriyaki Chicken Casserole",
            strCategory = "Chicken",
            strArea = "Japanese",
            strInstructions = "Cook the chicken...",
            strMealThumb = "https://www.themealdb.com/images/media/meals/wvpsxx1468256321.jpg",
            strTags = "Meat,Casserole",
            strYoutube = "https://www.youtube.com/watch?v=4aZr5hZXP_s",
            strIngredient1 = "soy sauce",
            strMeasure1 = "3/4 cup",
            strIngredient2 = "water",
            strMeasure2 = "1/2 cup",
            strIngredient3 = "brown sugar",
            strMeasure3 = "1/4 cup",
            strIngredient4 = "", // Field kosong harus diabaikan
            strMeasure4 = "",
            strIngredient5 = null, // Field null harus diabaikan
            strMeasure5 = null
        )

        // When
        val ingredients = dummyMeal.extractIngredients()

        // Then
        assertEquals(3, ingredients.size)
        assertEquals("soy sauce", ingredients[0].name)
        assertEquals("3/4 cup", ingredients[0].measure)
        assertEquals("water", ingredients[1].name)
        assertEquals("1/2 cup", ingredients[1].measure)
        assertEquals("brown sugar", ingredients[2].name)
        assertEquals("1/4 cup", ingredients[2].measure)
    }

    @Test
    fun extractIngredients_whenAllIngredientsNull_returnsEmptyList() {
        // Given
        val emptyMeal = MealDto(
            idMeal = "99999",
            strMeal = "Empty Dish",
            strCategory = null,
            strArea = null,
            strInstructions = null,
            strMealThumb = null,
            strTags = null,
            strYoutube = null
        )

        // When
        val ingredients = emptyMeal.extractIngredients()

        // Then
        assertTrue(ingredients.isEmpty())
    }
}
