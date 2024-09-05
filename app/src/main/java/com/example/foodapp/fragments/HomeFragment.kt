package com.example.foodapp.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.foodapp.activites.CategoryMealsActivity
import com.example.foodapp.activites.MealActivity
import com.example.foodapp.adapters.CategoriesAdapter
import com.example.foodapp.adapters.MostPopularAdapter
import com.example.foodapp.databinding.FragmentHomeBinding
import com.example.foodapp.pojo.MealsByCategory
import com.example.foodapp.pojo.Meal
import com.example.foodapp.viewModel.HomeViewModel

/**
 * HomeFragment is responsible for displaying the main content of the home screen.
 * It retrieves data through HomeViewModel, such as random meals, popular meals,
 * and meal categories, and displays them in RecyclerViews.
 */
class HomeFragment : Fragment() {

    // Binding object to access the views in the fragment's layout
    private lateinit var _binding: FragmentHomeBinding

    // ViewModel for interacting with the data and handling business logic
    private lateinit var homeMvvm: HomeViewModel

    // Holds the randomly selected meal to display in the UI
    private lateinit var randomMeal: Meal

    // Adapters for popular meals and categories RecyclerViews
    private lateinit var popularItemsAdapter: MostPopularAdapter
    private lateinit var categoriesAdapter: CategoriesAdapter

    companion object {
        // Constants used for passing data between fragments and activities
        const val MEAL_ID = "com.example.foodapp.fragments.idMeal"
        const val MEAL_NAME = "com.example.foodapp.fragments.nameMeal"
        const val MEAL_THUMB = "com.example.foodapp.fragments.thumbMeal"
        const val CATEGORY_NAME = "com.example.foodapp.fragments.categoryName"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initializing the ViewModel for fetching data
        homeMvvm = ViewModelProvider(this)[HomeViewModel::class.java]

        // Initializing the adapter for the popular items RecyclerView
        popularItemsAdapter = MostPopularAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout using ViewBinding
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Set up RecyclerView for popular meals
        preparePopularItemsRecyclerView()

        // Fetch and observe random meal data
        homeMvvm.getRandomMeal()
        observeRandomMeal()
        onRandomMealClick()

        // Fetch and observe popular meals data
        homeMvvm.getPopularItem()
        observePopularItemsLiveData()
        onPopularItemClick()

        // Fetch and observe meal categories data
        homeMvvm.getCategories()
        observeCategoriesLiveData()
        prepareCategoriesRecyclerView()
        onCategoryClick()
    }

    /**
     * Handle click events for meal categories, launching CategoryMealsActivity
     * when a category is clicked.
     */
    private fun onCategoryClick() {
        categoriesAdapter.onItemClick = { category ->
            val intent = Intent(activity, CategoryMealsActivity::class.java)
            intent.putExtra(CATEGORY_NAME, category.strCategory)
            startActivity(intent)
        }
    }

    /**
     * Set up the RecyclerView for displaying meal categories with a grid layout.
     */
    private fun prepareCategoriesRecyclerView() {
        categoriesAdapter = CategoriesAdapter()
        _binding.recViewCategory.apply {
            layoutManager = GridLayoutManager(context, 3, GridLayoutManager.VERTICAL, false)
            adapter = categoriesAdapter
        }
    }

    /**
     * Observe the categories live data from ViewModel and update the RecyclerView
     * when new data is available.
     */
    private fun observeCategoriesLiveData() {
        homeMvvm.observeCategoriesLiveData().observe(viewLifecycleOwner, Observer { categories ->
            categoriesAdapter.setCategoriesList(categories)
        })
    }

    /**
     * Handle click events for popular meal items, launching MealActivity
     * when a meal is clicked.
     */
    private fun onPopularItemClick() {
        popularItemsAdapter.onItemsClick = { meal ->
            val intent = Intent(activity, MealActivity::class.java)
            intent.putExtra(MEAL_ID, meal.idMeal)
            intent.putExtra(MEAL_NAME, meal.strMeal)
            intent.putExtra(MEAL_THUMB, meal.strMealThumb)
            startActivity(intent)
        }
    }

    /**
     * Set up the RecyclerView for displaying popular meal items with a horizontal
     * linear layout.
     */
    private fun preparePopularItemsRecyclerView() {
        _binding.recViewMealsPopular.apply {
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
            adapter = popularItemsAdapter
        }
    }

    /**
     * Observe the popular items live data from ViewModel and update the RecyclerView
     * when new data is available.
     */
    private fun observePopularItemsLiveData() {
        homeMvvm.observePopularItemsLiveData().observe(viewLifecycleOwner) { mealsList ->
            popularItemsAdapter.setMeals(mealsList = mealsList as ArrayList<MealsByCategory>)
        }
    }

    /**
     * Handle click events for the random meal card, launching MealActivity
     * when the card is clicked.
     */
    private fun onRandomMealClick() {
        _binding.randomMealCard.setOnClickListener {
            val intent = Intent(activity, MealActivity::class.java)
            intent.putExtra(MEAL_ID, randomMeal.idMeal)
            intent.putExtra(MEAL_NAME, randomMeal.strMeal)
            intent.putExtra(MEAL_THUMB, randomMeal.strMealThumb)
            startActivity(intent)
        }
    }

    /**
     * Observe the random meal live data from ViewModel and display it
     * using Glide for image loading.
     */
    private fun observeRandomMeal() {
        homeMvvm.observeRandomMealLiveData().observe(viewLifecycleOwner) { meal ->
            Glide.with(this@HomeFragment)
                .load(meal!!.strMealThumb)
                .into(_binding.imgRandomMeal)

            this.randomMeal = meal
        }
    }
}
