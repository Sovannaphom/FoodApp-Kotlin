package com.example.foodapp.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.foodapp.db.MealDatabase
import com.example.foodapp.pojo.Category
import com.example.foodapp.pojo.CategoryList
import com.example.foodapp.pojo.MealByCategoryList
import com.example.foodapp.pojo.MealsByCategory
import com.example.foodapp.pojo.Meal
import com.example.foodapp.pojo.MealList
import com.example.foodapp.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * ViewModel class for managing and providing data related to the home screen of the app.
 *
 * This ViewModel is responsible for handling and providing data for the home screen, including a random meal,
 * popular items, and categories. It communicates with the Retrofit API service to fetch data and exposes this
 * data to the UI through LiveData.
 */
class HomeViewModel(
    private val mealDatabase: MealDatabase
) : ViewModel() {

    /**
     * LiveData that holds a single random meal.
     *
     * This MutableLiveData is updated with a random meal retrieved from the API.
     * Observers can subscribe to this LiveData to receive updates when a new random meal is fetched.
     */
    private var randomMealLiveData = MutableLiveData<Meal>()

    /**
     * LiveData that holds a list of popular items.
     *
     * This MutableLiveData is updated with a list of popular meals from a specific category (e.g., Seafood).
     * Observers can subscribe to this LiveData to receive updates when the list of popular items changes.
     */
    private var popularItemLiveData = MutableLiveData<List<MealsByCategory>>()

    /**
     * LiveData that holds a list of categories.
     *
     * This MutableLiveData is updated with a list of meal categories retrieved from the API.
     * Observers can subscribe to this LiveData to receive updates when the list of categories changes.
     */
    private var categoriesLiveData = MutableLiveData<List<Category>>()

    // Initializes a LiveData object that observes and holds a list of all meals from the meal database
    private var favoritesMealLiveData = mealDatabase.mealDao().getAllMeals()

    /**
     * Fetches a random meal from the API and updates the [randomMealLiveData].
     *
     * This function makes an asynchronous network request to retrieve a random meal. Upon successful response,
     * it updates the `randomMealLiveData` with the retrieved meal. If the request fails, it logs the error.
     */
    fun getRandomMeal() {
        RetrofitInstance.api.getRandomMeal().enqueue(object : Callback<MealList> {
            override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
                if (response.body() != null) {
                    val randomMeal: Meal = response.body()!!.meals[0]
                    randomMealLiveData.value = randomMeal
                } else {
                    return
                }
            }

            override fun onFailure(call: Call<MealList>, t: Throwable) {
                Log.d("HomeFragment", t.message.toString())
            }
        })
    }

    /**
     * Fetches popular items from a specific category (e.g., Seafood) and updates the [popularItemLiveData].
     *
     * This function makes an asynchronous network request to retrieve popular meals for a specific category.
     * Upon successful response, it updates the `popularItemLiveData` with the list of popular meals. If the
     * request fails, it logs the error.
     */
    fun getPopularItem() {
        RetrofitInstance.api.getPopularItem("Seafood")
            .enqueue(object : Callback<MealByCategoryList> {
                override fun onResponse(
                    call: Call<MealByCategoryList>,
                    response: Response<MealByCategoryList>
                ) {
                    if (response.body() != null) {
                        popularItemLiveData.value = response.body()!!.meals
                    }
                }

                override fun onFailure(call: Call<MealByCategoryList>, t: Throwable) {
                    Log.d("HomeFragment", t.message.toString())
                }
            })
    }

    /**
     * Fetches the list of categories from the API and updates the [categoriesLiveData].
     *
     * This function makes an asynchronous network request to retrieve meal categories. Upon successful response,
     * it updates the `categoriesLiveData` with the list of categories. If the request fails, it logs the error.
     */
    fun getCategories() {
        RetrofitInstance.api.getCategories().enqueue(object : Callback<CategoryList> {
            override fun onResponse(call: Call<CategoryList>, response: Response<CategoryList>) {
                response.body()?.let { categoryList ->
                    categoriesLiveData.postValue(categoryList.categories)
                }
            }

            override fun onFailure(call: Call<CategoryList>, t: Throwable) {
                Log.d("HomeViewModel", t.message.toString())
            }
        })
    }

    /**
     * Provides a LiveData object to observe the random meal data.
     *
     * @return A LiveData object containing a single [Meal] representing the random meal.
     */
    fun observeRandomMealLiveData(): LiveData<Meal> {
        return randomMealLiveData
    }

    /**
     * Provides a LiveData object to observe the list of popular items.
     *
     * @return A LiveData object containing a list of [MealsByCategory] representing the popular items.
     */
    fun observePopularItemsLiveData(): LiveData<List<MealsByCategory>> {
        return popularItemLiveData
    }

    /**
     * Provides a LiveData object to observe the list of categories.
     *
     * @return A LiveData object containing a list of [Category] representing the meal categories.
     */
    fun observeCategoriesLiveData(): LiveData<List<Category>> {
        return categoriesLiveData
    }
}