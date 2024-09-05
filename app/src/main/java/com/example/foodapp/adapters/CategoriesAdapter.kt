package com.example.foodapp.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.foodapp.databinding.CategoryItemBinding
import com.example.foodapp.pojo.Category

/**
 * CategoriesAdapter is a RecyclerView adapter that handles the display of food categories.
 * It binds each category item (image and name) to a view in the RecyclerView and allows for item click handling.
 */
class CategoriesAdapter : RecyclerView.Adapter<CategoriesAdapter.CategoryViewHolder>() {

    // List to hold food categories
    private var categoriesList = ArrayList<Category>()

    // Lambda function that triggers when a category item is clicked
    var onItemClick: ((Category) -> Unit)? = null

    /**
     * Sets the list of categories and notifies the adapter to update the RecyclerView.
     *
     * @param categoriesList A list of Category objects representing food categories.
     */
    @SuppressLint("NotifyDataSetChanged")
    fun setCategoriesList(categoriesList: List<Category>) {
        // Cast the input list to an ArrayList and update the internal list
        this.categoriesList = categoriesList as ArrayList<Category>
        notifyDataSetChanged() // Notify the adapter to refresh the displayed data
    }

    /**
     * ViewHolder class that binds data from the Category object to the view via CategoryItemBinding.
     */
    inner class CategoryViewHolder(val binding: CategoryItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    /**
     * Called when RecyclerView needs a new ViewHolder to represent a category item.
     *
     * @param parent The ViewGroup into which the new view will be added after it is bound to an adapter position.
     * @param viewType The view type of the new view.
     * @return A new ViewHolder that holds a view of a category item.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        // Inflate the layout for the category item and return a new ViewHolder instance
        return CategoryViewHolder(
            CategoryItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    /**
     * Returns the total number of items (categories) in the dataset held by the adapter.
     *
     * @return The size of the categories list.
     */
    override fun getItemCount(): Int {
        return categoriesList.size
    }

    /**
     * Binds data from the Category object to the ViewHolder at the specified position.
     * This method loads the category image using Glide and sets the category name for each item.
     *
     * @param holder The ViewHolder which should be updated to represent the contents of the item at the given position.
     * @param position The position of the item within the adapter's data set.
     */
    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        // Load the category image into the ImageView using Glide
        Glide.with(holder.itemView).load(categoriesList[position].strCategoryThumb)
            .into(holder.binding.imgCategory)

        // Set the category name in the TextView
        holder.binding.tvCategoryName.text = categoriesList[position].strCategory

        // Set a click listener to trigger the onItemClick lambda when the item is clicked
        holder.itemView.setOnClickListener {
            onItemClick?.invoke(categoriesList[position])
        }
    }
}
