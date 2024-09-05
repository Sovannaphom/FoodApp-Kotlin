package com.example.foodapp.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.foodapp.pojo.MealByCategoryList
import com.example.foodapp.pojo.MealsByCategory
import com.example.foodapp.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * ViewModel class for managing and providing data related to meals by category.
 *
 * This ViewModel is responsible for handling the data related to meals within a specific category.
 * It communicates with the Retrofit API service to fetch meal data and exposes this data to the UI.
 */
class CategoryMealsViewModel : ViewModel() {

    /**
     * LiveData that holds a list of [MealsByCategory].
     *
     * This MutableLiveData is updated with the meal data retrieved from the API.
     * Observers can subscribe to this LiveData to receive updates when the meal data changes.
     */
    val mealsLiveData = MutableLiveData<List<MealsByCategory>>()

    /**
     * Fetches meals by category from the API and updates the [mealsLiveData].
     *
     * This function makes an asynchronous network request to retrieve a list of meals based on the provided
     * category name. Upon successful response, it updates the `mealsLiveData` with the list of meals.
     *
     * @param categoryName The name of the category for which meals are to be fetched.
     */
    fun getMealsByCategory(categoryName: String) {
        RetrofitInstance.api.getMealsByCategory(categoryName)
            .enqueue(object : Callback<MealByCategoryList> {
                override fun onResponse(
                    call: Call<MealByCategoryList>,
                    response: Response<MealByCategoryList>
                ) {
                    response.body()?.let { mealsList ->
                        // Update the LiveData with the list of meals from the response
                        mealsLiveData.postValue(mealsList.meals)
                    }
                }

                override fun onFailure(call: Call<MealByCategoryList>, t: Throwable) {
                    // Log the error message if the request fails
                    Log.e("CategoryMealsViewModel", t.message.toString())
                }
            })
    }

    /**
     * Provides a LiveData object to observe meal data.
     *
     * This function returns the [mealsLiveData] so that UI components can observe changes to the meal data.
     *
     * @return A LiveData object containing a list of [MealsByCategory].
     */
    fun observeMealsLiveData(): LiveData<List<MealsByCategory>> {
        return mealsLiveData
    }
}