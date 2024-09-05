package com.example.foodapp.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.foodapp.db.MealDatabase

/**
 * Factory class for creating instances of [MealViewModel].
 *
 * This factory class is used to create instances of [MealViewModel] with a [MealDatabase] instance
 * that is required for database operations. It implements the [ViewModelProvider.Factory] interface
 * to provide a way to create a [MealViewModel] with custom parameters.
 *
 * @param mealDatabase An instance of [MealDatabase] used for database operations in [MealViewModel].
 */
class MealViewModelFactory(
    private val mealDatabase: MealDatabase
) : ViewModelProvider.Factory {

    /**
     * Creates an instance of [MealViewModel] with the provided [MealDatabase].
     *
     * This method creates a new [MealViewModel] instance, passing the [MealDatabase] to it. It is called
     * by the [ViewModelProvider] to obtain a [MealViewModel] instance with the necessary dependencies.
     *
     * @param modelClass The class of the ViewModel to be created.
     * @return An instance of [MealViewModel] with the provided [MealDatabase].
     * @throws IllegalArgumentException If the given model class cannot be instantiated.
     */
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MealViewModel::class.java)) {
            return MealViewModel(mealDatabase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}