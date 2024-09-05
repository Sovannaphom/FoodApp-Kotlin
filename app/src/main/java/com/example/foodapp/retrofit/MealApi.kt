package com.example.foodapp.retrofit

import com.example.foodapp.pojo.CategoryList
import com.example.foodapp.pojo.MealByCategoryList
import com.example.foodapp.pojo.MealList
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Retrofit API interface for interacting with the meal-related endpoints of the food API.
 */
interface MealApi {

    /**
     * Retrieves a random meal from the API.
     *
     * @return A [Call] object that, when executed, returns a [MealList] containing a randomly selected meal.
     */
    @GET("random.php")
    fun getRandomMeal(): Call<MealList>

    /**
     * Retrieves details of a specific meal based on its ID.
     *
     * @param id The unique ID of the meal for which details are to be fetched.
     * @return A [Call] object that, when executed, returns a [MealList] containing the details of the specified meal.
     */
    @GET("lookup.php")
    fun getMealDetails(@Query("i") id: String): Call<MealList>

    /**
     * Retrieves popular meals based on the specified category.
     *
     * @param categoryName The name of the category for which popular meals are to be fetched.
     * @return A [Call] object that, when executed, returns a [MealByCategoryList] containing meals that are popular within the specified category.
     */
    @GET("filter.php?")
    fun getPopularItem(@Query("c") categoryName: String): Call<MealByCategoryList>

    /**
     * Retrieves a list of meal categories.
     *
     * @return A [Call] object that, when executed, returns a [CategoryList] containing all available meal categories.
     */
    @GET("categories.php")
    fun getCategories(): Call<CategoryList>

    /**
     * Retrieves meals based on the specified category name.
     *
     * @param categoryName The name of the category for which meals are to be fetched.
     * @return A [Call] object that, when executed, returns a [MealByCategoryList] containing meals that belong to the specified category.
     */
    @GET("filter.php")
    fun getMealsByCategory(@Query("c") categoryName: String): Call<MealByCategoryList>
}
