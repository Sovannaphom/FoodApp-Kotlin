package com.example.foodapp.db

import androidx.room.TypeConverter
import androidx.room.TypeConverters

/**
 * MealTypeConverter is a Room type converter class that helps in converting unsupported data types
 * into supported ones for database storage. In this case, it converts between `Any` and `String` types.
 * Room does not support complex data types, so TypeConverters are used to convert them to a format
 * that Room can handle (e.g., String).
 */

@TypeConverters
class MealTypeConverter {

    /**
     * Converts an `Any?` type (nullable Any) to a `String`.
     * This is used to store complex data types into the database as a `String`.
     *
     * @param attribute The data of type `Any?` that needs to be converted.
     * @return A `String` representation of the input, or an empty string if the input is `null`.
     */
    @TypeConverter
    fun fromAnyToString(attribute: Any?): String {
        if (attribute == null)
            return ""
        return attribute as String
    }

    /**
     * Converts a `String?` (nullable String) back to an `Any` type.
     * This method is used to retrieve data from the database and convert it back to its original type.
     *
     * @param attribute The `String?` representation of the data retrieved from the database.
     * @return The `Any` type or an empty string if the input is `null`.
     */
    @TypeConverter
    fun fromStringToAny(attribute: String?): Any {
        if (attribute == null)
            return ""
        return attribute
    }
}
