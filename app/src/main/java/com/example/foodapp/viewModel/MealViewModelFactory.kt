package com.example.foodapp.viewModel

import androidx.lifecycle.ViewModelProvider
import com.example.foodapp.db.MealDatabase

class MealViewModelFactory(
     val mealDatabase: MealDatabase
) : ViewModelProvider.Factory{
}