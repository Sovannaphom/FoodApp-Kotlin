package com.example.foodapp.pojo

/**
 * Data class representing a list of meals categorized by a specific category.
 *
 * @property meals A list of [MealsByCategory] objects, each representing a meal with minimal details,
 * such as its ID, name, and thumbnail image. This list is used to represent multiple meals under a category.
 */
data class MealByCategoryList(
    val meals: List<MealsByCategory>
)
