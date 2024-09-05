package com.example.foodapp.pojo

/**
 * Data class representing a list of meals.
 *
 * @property meals A list of [Meal] objects. This contains the details of multiple meals.
 */
data class MealList(
    val meals: List<Meal>
)
