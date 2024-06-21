package com.example.foodapp.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.foodapp.databinding.PopularItemsBinding
import com.example.foodapp.pojo.MealsByCategory
import kotlin.collections.ArrayList

class MostPopularAdapter(): RecyclerView.Adapter<MostPopularAdapter.PopularMealViewHolder>(){

    lateinit var onItemsClick:(MealsByCategory) -> Unit
    private var mealsList: ArrayList<MealsByCategory> = ArrayList()

    @SuppressLint("NotifyDataSetChanged")
    fun setMeals(mealsList:ArrayList<MealsByCategory>){
        this.mealsList = mealsList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularMealViewHolder {
        return PopularMealViewHolder(PopularItemsBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int {
        return mealsList.size
    }

    override fun onBindViewHolder(holder: PopularMealViewHolder, position: Int) {
        Glide.with(holder.itemView)
            .load(mealsList[position].strMealThumb)
            .into(holder.binding.imgPopularMealItem)

        holder.itemView.setOnClickListener {
            onItemsClick.invoke(mealsList[position])
        }
    }

    class PopularMealViewHolder(val binding: PopularItemsBinding):RecyclerView.ViewHolder(binding.root)

}