package com.example.foodapp.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Object that provides a singleton instance of the Retrofit API client.
 *
 * This object uses the Retrofit library to create and configure an instance of the [MealApi] interface,
 * which allows for network operations to be performed against the meal-related endpoints of the API.
 */
object RetrofitInstance {

    /**
     * Singleton instance of the [MealApi] interface.
     *
     * This instance is lazily initialized, meaning it will only be created when first accessed.
     * The instance is created using Retrofit's builder, which is configured with:
     * - The base URL of the API
     * - A Gson converter factory for parsing JSON responses into Kotlin data classes.
     */
    val api: MealApi by lazy {

        Retrofit.Builder()
            .baseUrl("https://www.themealdb.com/api/json/v1/1/") // Base URL of the API
            .addConverterFactory(GsonConverterFactory.create()) // Converter for JSON to Kotlin objects
            .build() // Build the Retrofit instance
            .create(MealApi::class.java) // Create an implementation of the [MealApi] interface
    }

}
