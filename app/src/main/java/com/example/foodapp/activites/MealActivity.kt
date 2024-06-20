package com.example.foodapp.activites

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.foodapp.R
import com.example.foodapp.databinding.ActivityMealBinding
import com.example.foodapp.fragments.HomeFragment
import com.example.foodapp.pojo.Meal
import com.example.foodapp.viewModel.MealViewModel

@Suppress("DEPRECATION")
class MealActivity : AppCompatActivity() {

    private lateinit var mealId: String
    private lateinit var mealName: String
    private lateinit var mealThumb: String
    private lateinit var binding: ActivityMealBinding
    private lateinit var mealMvvm: MealViewModel
    private lateinit var youtubeLink: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMealBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mealMvvm = ViewModelProvider(this)[MealViewModel::class.java]

        getMealInformationFromIntent()
        setInformationIntentView()

        //call loading case when we call to api
        loadingCase()

        mealMvvm.getMealDetail(mealId)
        observeMealDetailsLiveData()

        //find the toolbar in the layout
        val toolbar: Toolbar = findViewById(R.id.toolBar)
        setSupportActionBar(toolbar)

        //enable the up button
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        //Click image youtube to navigate to youtube
        onYoutubeImgClick()

    }

    private fun onYoutubeImgClick(){
        binding.imgYoutube.setOnClickListener{
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(youtubeLink))
            startActivity(intent)
        }
    }

    //handle clicked on img youtube
    @SuppressLint("SetTextI9n")
    private fun observeMealDetailsLiveData() {
        mealMvvm.observeMealDetailsLiveData().observe(this, object : Observer<Meal> {
            @SuppressLint("SetTextI18n")
            override fun onChanged(value: Meal) {
                respondCase()   //when got respond from API
                val meal = value
                binding.tvCategory.text = "Category : ${meal.strCategory}"
                binding.tvArea.text = "Area : ${meal.strArea}"
                binding.tvInstructionSt.text = meal.strInstructions

                //Assigns the YouTube link from the Meal object to the youtubeLink variable.
                // Update the YouTube link variable with the new link from the meal
                youtubeLink = meal.strYoutube
            }
        })
    }

    private fun setInformationIntentView() {
        Glide.with(applicationContext)
            .load(mealThumb)
            .into(binding.imgMealDetail)

        binding.collapsingToolbar.title = mealName
        binding.collapsingToolbar.setCollapsedTitleTextColor(resources.getColor(R.color.white))
        binding.collapsingToolbar.setExpandedTitleColor(resources.getColor(R.color.white))
    }

    private fun getMealInformationFromIntent() {
        val intent = intent
        mealId = intent.getStringExtra(HomeFragment.MEAL_ID)!!
        mealName = intent.getStringExtra(HomeFragment.MEAL_NAME)!!
        mealThumb = intent.getStringExtra(HomeFragment.MEAL_THUMB)!!
    }

    // Handle the Up button press
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    //loading case
    private fun loadingCase() {

        binding.progressBar.visibility = View.VISIBLE
        binding.btnAddToFav.visibility = View.INVISIBLE
        binding.tvInstructions.visibility = View.INVISIBLE
        binding.tvCategory.visibility = View.INVISIBLE
        binding.tvArea.visibility = View.INVISIBLE
        binding.imgYoutube.visibility = View.INVISIBLE

    }

    //respond case
    private fun respondCase() {

        binding.progressBar.visibility = View.INVISIBLE
        binding.btnAddToFav.visibility = View.VISIBLE
        binding.tvInstructions.visibility = View.VISIBLE
        binding.tvCategory.visibility = View.VISIBLE
        binding.tvArea.visibility = View.VISIBLE
        binding.imgYoutube.visibility = View.VISIBLE

    }

}