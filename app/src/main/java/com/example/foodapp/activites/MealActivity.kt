package com.example.foodapp.activites

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.foodapp.R
import com.example.foodapp.databinding.ActivityMealBinding
import com.example.foodapp.db.MealDatabase
import com.example.foodapp.fragments.HomeFragment
import com.example.foodapp.pojo.Meal
import com.example.foodapp.viewModel.MealViewModel
import com.example.foodapp.viewModel.MealViewModelFactory

/**
 * MealActivity displays detailed information about a selected meal.
 * It retrieves meal details from a ViewModel and updates the UI accordingly.
 */
@Suppress("DEPRECATION")
class MealActivity : AppCompatActivity() {

    // Variables to store meal details and UI components
    private lateinit var mealId: String
    private lateinit var mealName: String
    private lateinit var mealThumb: String
    private lateinit var binding: ActivityMealBinding
    private lateinit var mealMvvm: MealViewModel
    private lateinit var youtubeLink: String

    /**
     * Initializes the activity, sets up the ViewModel, retrieves meal data from the Intent,
     * configures the UI, and sets up event listeners.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMealBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize the ViewModel with a ViewModelFactory
        val mealDatabase = MealDatabase.getInstance(this)
        val viewModelFactory = MealViewModelFactory(mealDatabase)
        mealMvvm = ViewModelProvider(this, viewModelFactory).get(MealViewModel::class.java)

        // Retrieve and set meal information
        getMealInformationFromIntent()
        setInformationIntentView()

        // Display loading UI until meal details are fetched
        loadingCase()

        // Fetch meal details from the ViewModel
        mealMvvm.getMealDetail(mealId)
        observeMealDetailsLiveData()

        // Set up the toolbar and enable the back navigation button
        val toolbar: Toolbar = findViewById(R.id.toolBar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Set up a click listener for the YouTube image to open the YouTube video
        onYoutubeImgClick()

        // Set up a click listener for the Button Favorite to open save the save the meals
        onFavoriteClick()

    }

    /**
     * Sets up a click listener for the "Add to Favorites" button.
     *
     * When the button is clicked:
     * - Checks if `mealToSave` is not null. This variable holds the details of the meal that the user wants to save.
     * - If `mealToSave` is not null, it proceeds to save the meal details to the database using the `insertMeal` method of the `mealMvvm` ViewModel.
     * - Displays a Toast message to notify the user that the meal has been successfully saved.
     */
    private fun onFavoriteClick() {
        binding.btnAddToFav.setOnClickListener {
            mealToSave?.let {
                mealMvvm.insertMeal(it)
                Toast.makeText(this, "Meal Saved", Toast.LENGTH_SHORT).show()
            }
        }
    }

    /**
     * Sets up a click listener on the YouTube image to open the meal's YouTube video.
     */
    private fun onYoutubeImgClick() {
        binding.imgYoutube.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(youtubeLink))
            startActivity(intent)
        }
    }

    // Nullable variable to temporarily hold a reference to a Meal object that
    // the user might want to save or operate on. Initially set to null until
    // a Meal object is assigned.
    private var mealToSave: Meal? = null

    /**
     * Observes the LiveData from the ViewModel for meal details and updates the UI when data is received.
     */
    @SuppressLint("SetTextI18n")
    private fun observeMealDetailsLiveData() {
        // Observes the LiveData from the ViewModel for changes in meal details.
        mealMvvm.observeMealDetailsLiveData().observe(this, object : Observer<Meal> {
            @SuppressLint("SetTextI18n")
            override fun onChanged(value: Meal) {
                // Called when the LiveData emits a new value (Meal object).
                // Update UI to reflect the fetched meal details.
                respondCase()

                // Assign the received Meal object to the mealToSave variable for future use.
                // This allows for saving or operating on this meal later in the application.
                mealToSave = value

                // Update the UI components with the details of the fetched meal.
                // Set the text of the category TextView to display the meal's category.
                binding.tvCategory.text = "Category : ${value.strCategory}"
                // Set the text of the area TextView to display the meal's area.
                binding.tvArea.text = "Area : ${value.strArea}"
                // Set the text of the instructions TextView to display the meal's instructions.
                binding.tvInstructionSt.text = value.strInstructions

                // Store the YouTube link from the meal object for future use.
                // This link can be used later to open the associated YouTube video.
                youtubeLink = value.strYoutube
            }
        })
    }

    /**
     * Sets up the UI with information received via Intent.
     */
    private fun setInformationIntentView() {
        // Load the meal thumbnail image into the ImageView using Glide.
        // Glide is an image loading library that efficiently handles image loading
        // and caching.
        Glide.with(applicationContext)
            .load(mealThumb)  // The URL or resource ID of the meal thumbnail image
            .into(binding.imgMealDetail)  // The ImageView where the image will be displayed

        // Set the title of the CollapsingToolbar to the name of the meal.
        // CollapsingToolbar is used to provide a scrolling effect for the title
        // when the user scrolls the content.
        binding.collapsingToolbar.title = mealName

        // Set the text color of the title when the toolbar is collapsed.
        // `resources.getColor(R.color.white)` retrieves the color resource for the text.
        binding.collapsingToolbar.setCollapsedTitleTextColor(resources.getColor(R.color.white))

        // Set the text color of the title when the toolbar is expanded.
        // This ensures that the title is visible and readable in both collapsed
        // and expanded states.
        binding.collapsingToolbar.setExpandedTitleColor(resources.getColor(R.color.white))
    }

    /**
     * Retrieves meal information (ID, name, and thumbnail) from the Intent extras.
     */
    private fun getMealInformationFromIntent() {
        // Retrieve the Intent that started this activity.
        val intent = intent

        // Extract the meal ID from the Intent's extras. The `getStringExtra` method
        // retrieves the value associated with the specified key (MEAL_ID). If the
        // value is null, an empty string is used as a fallback.
        mealId = intent.getStringExtra(HomeFragment.MEAL_ID) ?: ""

        // Extract the meal name from the Intent's extras. The `getStringExtra` method
        // retrieves the value associated with the specified key (MEAL_NAME). If the
        // value is null, an empty string is used as a fallback.
        mealName = intent.getStringExtra(HomeFragment.MEAL_NAME) ?: ""

        // Extract the meal thumbnail URL from the Intent's extras. The `getStringExtra`
        // method retrieves the value associated with the specified key (MEAL_THUMB).
        // If the value is null, an empty string is used as a fallback.
        mealThumb = intent.getStringExtra(HomeFragment.MEAL_THUMB) ?: ""
    }

    /**
     * Handles Up button press to navigate back to the previous screen.
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            // Handle the Up button (home button) in the action bar.
            // This button is typically used to navigate back to the previous screen
            // or the parent activity in the app's navigation hierarchy.
            android.R.id.home -> {
                // Call the onBackPressed method to navigate back to the previous screen.
                // This action simulates the behavior of pressing the device's back button.
                onBackPressed()
                true  // Return true to indicate that the event was handled.
            }

            // For other menu items, use the default behavior provided by the superclass.
            // This allows for handling any additional menu items if present.
            else -> super.onOptionsItemSelected(item)
        }
    }


    /**
     * Displays loading UI components while fetching meal details.
     */
    private fun loadingCase() {
        // Show the progress bar to indicate that data is being loaded or fetched.
        // This provides a visual cue to the user that loading is in progress.
        binding.progressBar.visibility = View.VISIBLE

        // Hide the "Add to Favorites" button as it should not be available
        // until the meal details have been successfully loaded.
        binding.btnAddToFav.visibility = View.INVISIBLE

        // Hide the TextView that displays instructions as it should only be visible
        // once the meal details are fully loaded and available.
        binding.tvInstructions.visibility = View.INVISIBLE

        // Hide the TextView that displays the meal's category since this information
        // will only be shown after the meal details have been fetched.
        binding.tvCategory.visibility = View.INVISIBLE

        // Hide the TextView that displays the meal's area as it is not relevant
        // during the loading phase and will be shown once the data is ready.
        binding.tvArea.visibility = View.INVISIBLE

        // Hide the ImageView for the YouTube link because it should be visible only
        // when meal details are available and the YouTube link is ready to be accessed.
        binding.imgYoutube.visibility = View.INVISIBLE
    }

    /**
     * Displays UI components with fetched meal details.
     */
    private fun respondCase() {
        // Hide the progress bar once the meal details have been successfully loaded.
        // This indicates to the user that the loading process is complete.
        binding.progressBar.visibility = View.INVISIBLE

        // Show the "Add to Favorites" button, making it available for the user to interact with.
        // This button allows users to add the meal to their favorites once the meal details are visible.
        binding.btnAddToFav.visibility = View.VISIBLE

        // Show the TextView that displays the meal's instructions, making it visible to the user
        // once the meal details have been loaded.
        binding.tvInstructions.visibility = View.VISIBLE

        // Show the TextView that displays the meal's category, making it visible to the user
        // along with other details.
        binding.tvCategory.visibility = View.VISIBLE

        // Show the TextView that displays the meal's area, making it visible to the user
        // along with other details.
        binding.tvArea.visibility = View.VISIBLE

        // Show the ImageView for the YouTube link, making it available for the user to interact with
        // once the meal details have been fetched and are ready to be displayed.
        binding.imgYoutube.visibility = View.VISIBLE
    }

}
