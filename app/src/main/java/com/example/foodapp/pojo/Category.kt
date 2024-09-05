package com.example.foodapp.pojo

/**
 * Data class representing a food category.
 *
 * @property idCategory A unique identifier for the category.
 * @property strCategory The name of the category.
 * @property strCategoryDescription A brief description of the category.
 * @property strCategoryThumb A URL to an image thumbnail representing the category.
 */
data class Category(
    /**
     * Unique identifier for the category.
     * This can be used to differentiate between different categories in the application.
     */
    val idCategory: String,

    /**
     * Name of the category.
     * This is the main label or title of the category that will be displayed in the UI.
     */
    val strCategory: String,

    /**
     * Description of the category.
     * Provides additional information or context about the category, useful for displaying more details in the UI.
     */
    val strCategoryDescription: String,

    /**
     * URL to the category's image thumbnail.
     * This image is used to visually represent the category in the application's UI.
     */
    val strCategoryThumb: String
)
