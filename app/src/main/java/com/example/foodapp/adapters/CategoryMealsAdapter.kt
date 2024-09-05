package com.example.foodapp.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.foodapp.databinding.MealItemBinding
import com.example.foodapp.pojo.MealsByCategory

/**
 * CategoryMealsAdapter is a RecyclerView adapter that displays a list of meals within a specific category.
 * It binds the meal data to the view and uses Glide for image loading.
 */
class CategoryMealsAdapter : RecyclerView.Adapter<CategoryMealsAdapter.CategoryMealsViewModel>() {

    // List to hold the meals by category
    private var mealsList = ArrayList<MealsByCategory>()

    /**
     * Updates the current list of meals and notifies the adapter to refresh the data displayed in the RecyclerView.
     *
     * @param mealsList A list of MealsByCategory objects representing meals in a category.
     */
    @SuppressLint("NotifyDataSetChanged")
    fun setMealsList(mealsList: List<MealsByCategory>) {
        // Cast the input list to an ArrayList and update the internal list
        this.mealsList = mealsList as ArrayList<MealsByCategory>
        notifyDataSetChanged() // Notify the adapter to refresh the displayed data
    }

    /**
     * ViewHolder class that holds the view for each meal item.
     * It binds data from MealsByCategory to the layout via MealItemBinding.
     */
    inner class CategoryMealsViewModel(val binding: MealItemBinding) : RecyclerView.ViewHolder(binding.root)

    /**
     * Called when RecyclerView needs a new ViewHolder of the given type to represent an item.
     *
     * @param parent The ViewGroup into which the new view will be added after it is bound to an adapter position.
     * @param viewType The view type of the new View.
     * @return A new ViewHolder that holds a view of a meal item.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryMealsViewModel {
        return CategoryMealsViewModel(
            MealItemBinding.inflate(
                LayoutInflater.from(parent.context)
            )
        )
    }

    /**
     * Returns the total number of items (meals) in the dataset held by the adapter.
     *
     * @return The size of the meals list.
     */
    override fun getItemCount(): Int {
        return mealsList.size
    }

    /**
     * Binds data to the ViewHolder at the specified position. This method loads the meal image
     * using Glide and sets the meal name for each item.
     *
     * @param holder The ViewHolder which should be updated to represent the contents of the item at the given position.
     * @param position The position of the item within the adapter's data set.
     */
    override fun onBindViewHolder(holder: CategoryMealsViewModel, position: Int) {
        // Load the meal image into the ImageView using Glide
        Glide.with(holder.itemView)
            .load(mealsList[position].strMealThumb)
            .into(holder.binding.imgMeal)

        // Set the meal name in the TextView
        holder.binding.tvMealName.text = mealsList[position].strMeal
    }
}
