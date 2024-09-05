package com.example.foodapp.pojo

/**
 * Data class representing a list of food categories.
 *
 * @property categories A list of [Category] objects.
 * This list contains all the food categories available, which can be used to populate UI elements or for other data handling purposes.
 */
data class CategoryList(
    /**
     * List of food categories.
     * Each [Category] object in this list represents a different food category.
     */
    val categories: List<Category>
)
