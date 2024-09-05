package com.example.foodapp.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.foodapp.pojo.Meal

/**
 * MealDao is an interface that defines the Data Access Object (DAO) for the `Meal` entity.
 * It provides the methods needed to interact with the meal data in the database.
 */
@Dao
interface MealDao {

    /**
     * Inserts a meal into the database. If a meal with the same primary key already exists,
     * it replaces the existing meal with the new one.
     *
     * @param meal The meal object to be inserted or updated.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(meal: Meal)

    /**
     * Deletes a specific meal from the database.
     *
     * @param meal The meal object to be deleted.
     */
    @Delete
    suspend fun delete(meal: Meal)

    /**
     * Retrieves all meals from the database.
     * The result is returned as a LiveData object, which allows observing changes in the data.
     *
     * @return A LiveData list of Meal objects.
     */
    @Query("SELECT * FROM mealInformation")
    fun getAllMeals(): LiveData<List<Meal>>
}
