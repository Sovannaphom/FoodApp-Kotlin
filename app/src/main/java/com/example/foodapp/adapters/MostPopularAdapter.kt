package com.example.foodapp.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.foodapp.databinding.PopularItemsBinding
import com.example.foodapp.pojo.MealsByCategory
import kotlin.collections.ArrayList

/**
 * MostPopularAdapter is a RecyclerView adapter responsible for displaying a list of popular meal items.
 * It binds data to the RecyclerView and handles interactions with individual items in the list.
 * It uses Glide to load images and employs data binding with the PopularItemsBinding class.
 */
class MostPopularAdapter : RecyclerView.Adapter<MostPopularAdapter.PopularMealViewHolder>() {

    // A lambda function to handle item clicks, passing the clicked MealsByCategory object
    lateinit var onItemsClick: (MealsByCategory) -> Unit

    // List that holds the meals to be displayed
    private var mealsList: ArrayList<MealsByCategory> = ArrayList()

    /**
     * Sets the list of meals and refreshes the RecyclerView.
     * This method is called when the data source is updated, ensuring the UI reflects the new data.
     *
     * @param mealsList A list of MealsByCategory objects representing popular meals.
     */
    @SuppressLint("NotifyDataSetChanged")
    fun setMeals(mealsList: ArrayList<MealsByCategory>) {
        this.mealsList = mealsList
        notifyDataSetChanged() // Notify the adapter to refresh the UI
    }

    /**
     * Called to create a new ViewHolder object that will hold the view for each meal item.
     *
     * @param parent The ViewGroup into which the new view will be added after it is bound to an adapter position.
     * @param viewType The type of the new view.
     * @return A new PopularMealViewHolder that holds the view for each popular meal item.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularMealViewHolder {
        return PopularMealViewHolder(
            PopularItemsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    /**
     * Returns the total number of items in the meals list.
     *
     * @return The size of the meals list.
     */
    override fun getItemCount(): Int {
        return mealsList.size
    }

    /**
     * Called to bind data to the ViewHolder for a given position in the RecyclerView.
     * This method is responsible for displaying the meal image using Glide and setting up the click listener.
     *
     * @param holder The ViewHolder which should be updated to represent the contents of the item at the given position.
     * @param position The position of the item within the adapter's data set.
     */
    override fun onBindViewHolder(holder: PopularMealViewHolder, position: Int) {
        // Load the meal thumbnail into the ImageView using Glide
        Glide.with(holder.itemView)
            .load(mealsList[position].strMealThumb)
            .into(holder.binding.imgPopularMealItem)

        // Set up the click listener for the item
        holder.itemView.setOnClickListener {
            onItemsClick.invoke(mealsList[position])
        }
    }

    /**
     * ViewHolder class that holds the view for each popular meal item.
     * It uses PopularItemsBinding for data binding.
     */
    class PopularMealViewHolder(val binding: PopularItemsBinding) : RecyclerView.ViewHolder(binding.root)
}
