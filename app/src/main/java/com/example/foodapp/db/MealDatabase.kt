package com.example.foodapp.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.foodapp.pojo.Meal

/**
 * MealDatabase is an abstract class that represents the Room database for the app.
 * It defines the database configuration and serves as the main access point for the database.
 *
 * @Database annotation specifies the entities that belong to the database and the version number.
 * @TypeConverters annotation specifies the TypeConverter classes used to handle non-standard data types.
 */
@Database(entities = [Meal::class], version = 1)
@TypeConverters(MealTypeConverter::class)
abstract class MealDatabase : RoomDatabase() {

    /**
     * Abstract method to obtain an instance of the MealDao.
     * @return MealDao instance for accessing meal data.
     */
    abstract fun mealDao(): MealDao

    companion object {
        // Volatile ensures that changes to INSTANCE are immediately visible to other threads.
        @Volatile
        var INSTANCE: MealDatabase? = null

        /**
         * Synchronized function to obtain the singleton instance of MealDatabase.
         * This method ensures that only one instance of the database is created.
         *
         * @param context The context used to build the database.
         * @return The singleton instance of MealDatabase.
         */
        @Synchronized
        fun getInstance(context: Context): MealDatabase {
            // Check if the instance is null and create a new one if necessary
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(
                    context,
                    MealDatabase::class.java,
                    "meal.db" // Name of the database file
                )
                    // Handle schema migrations
                    .fallbackToDestructiveMigration()
                    .build()
            }
            return INSTANCE as MealDatabase
        }
    }
}
