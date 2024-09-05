package com.example.foodapp.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodapp.db.MealDatabase
import com.example.foodapp.pojo.Meal
import com.example.foodapp.pojo.MealList
import com.example.foodapp.retrofit.RetrofitInstance
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * ViewModel class for managing and providing meal-related data.
 *
 * This ViewModel handles fetching meal details from the API and interacting with the local database
 * to insert or delete meal records. It uses Retrofit for network operations and Room for local data
 * persistence. LiveData is used to observe meal details.
 *
 * @param mealDatabase An instance of [MealDatabase] used for database operations.
 */
class MealViewModel(
    private val mealDatabase: MealDatabase
) : ViewModel() {

    /**
     * LiveData that holds the details of a single meal.
     *
     * This MutableLiveData is updated with the details of a meal retrieved from the API.
     * Observers can subscribe to this LiveData to receive updates when meal details are fetched.
     */
    private var mealDetailsLiveData = MutableLiveData<Meal>()

    /**
     * Fetches details of a meal by its ID from the API and updates [mealDetailsLiveData].
     *
     * This function makes an asynchronous network request to retrieve details of a meal specified by its ID.
     * Upon successful response, it updates the `mealDetailsLiveData` with the retrieved meal. If the request fails,
     * it logs the error.
     *
     * @param id The ID of the meal for which details are to be fetched.
     */
    fun getMealDetail(id: String) {
        RetrofitInstance.api.getMealDetails(id).enqueue(object : Callback<MealList> {
            override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
                if (response.body() != null) {
                    mealDetailsLiveData.value = response.body()!!.meals[0]
                } else {
                    return
                }
            }

            override fun onFailure(call: Call<MealList>, t: Throwable) {
                Log.d("MealActivity", t.message.toString())
            }
        })
    }

    /**
     * Provides a LiveData object to observe meal details.
     *
     * @return A LiveData object containing a single [Meal] representing the meal details.
     */
    fun observeMealDetailsLiveData(): LiveData<Meal> {
        return mealDetailsLiveData
    }

    /**
     * Inserts or updates a meal in the local database.
     *
     * This function launches a coroutine to perform the database operation in a background thread.
     * It uses the [mealDatabase] instance to call the `upsert` method of the `MealDao` to insert or update
     * the meal record in the database.
     *
     * @param meal The [Meal] object to be inserted or updated in the database.
     */
    fun insertMeal(meal: Meal) {
        viewModelScope.launch {
            mealDatabase.mealDao().upsert(meal)
        }
    }

    /**
     * Deletes a meal from the local database.
     *
     * This function launches a coroutine to perform the database operation in a background thread.
     * It uses the [mealDatabase] instance to call the `delete` method of the `MealDao` to remove the
     * meal record from the database.
     *
     * @param meal The [Meal] object to be deleted from the database.
     */
    fun deleteMeal(meal: Meal) {
        viewModelScope.launch {
            mealDatabase.mealDao().delete(meal)
        }
    }
}
