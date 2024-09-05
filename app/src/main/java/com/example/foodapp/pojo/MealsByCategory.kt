package com.example.foodapp.pojo

/**
 * Data class representing a meal with minimal details, typically used for displaying meals by category.
 *
 * @property idMeal The unique identifier for the meal.
 * @property strMeal The name of the meal.
 * @property strMealThumb A URL to a thumbnail image of the meal.
 */
data class MealsByCategory(
    val idMeal: String,
    val strMeal: String,
    val strMealThumb: String
)
