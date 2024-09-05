package com.example.foodapp.activites

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.foodapp.adapters.CategoryMealsAdapter
import com.example.foodapp.databinding.ActivityCategoryMealsBinding
import com.example.foodapp.fragments.HomeFragment
import com.example.foodapp.viewModel.CategoryMealsViewModel

/**
 * CategoryMealsActivity displays a list of meals for a selected category.
 * It uses a ViewModel to fetch meal data and a RecyclerView with a GridLayoutManager
 * to display the meals in a grid format.
 */
class CategoryMealsActivity : AppCompatActivity() {

    // Binding object for accessing views
    private lateinit var binding: ActivityCategoryMealsBinding

    // ViewModel for managing meal data
    private lateinit var categoryMealsViewModel: CategoryMealsViewModel

    // Adapter for displaying meals in the RecyclerView
    private lateinit var categoryMealsAdapter: CategoryMealsAdapter

    /**
     * Called when the activity is first created. This method initializes the UI components,
     * sets up the RecyclerView, and observes changes in the meal data.
     *
     * @param savedInstanceState A Bundle containing the activity's previously saved state, or null if there is no state.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Inflate the layout for this activity
        binding = ActivityCategoryMealsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Prepare and configure the RecyclerView
        prepareRecyclerView()

        // Initialize the ViewModel
        categoryMealsViewModel = ViewModelProvider(this).get(CategoryMealsViewModel::class.java)

        // Get the category name from the intent and request meals for that category
        val categoryName = intent.getStringExtra(HomeFragment.CATEGORY_NAME)!!
        categoryMealsViewModel.getMealsByCategory(categoryName)

        // Observe the LiveData for meal updates and update the RecyclerView
        categoryMealsViewModel.observeMealsLiveData().observe(this, Observer { mealsList ->
            // Update the count of meals in the TextView
            binding.tvCategoryCount.text = mealsList.size.toString()
            // Update the adapter with the new list of meals
            categoryMealsAdapter.setMealsList(mealsList)
        })
    }

    /**
     * Initializes and configures the RecyclerView with a GridLayoutManager and an adapter.
     */
    private fun prepareRecyclerView() {
        // Create an instance of the adapter
        categoryMealsAdapter = CategoryMealsAdapter()

        // Configure the RecyclerView with a GridLayoutManager and the adapter
        binding.rvMeals.apply {
            layoutManager = GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
            adapter = categoryMealsAdapter
        }
    }
}
