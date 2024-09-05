package com.example.foodapp.activites

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.example.foodapp.R
import com.google.android.material.bottomnavigation.BottomNavigationView

/**
 * MainActivity serves as the entry point for the application and sets up the bottom navigation for the app.
 * It uses Android's Navigation Component to handle fragment navigation.
 */
class MainActivity : AppCompatActivity() {

    /**
     * Called when the activity is first created. This method sets up the content view,
     * initializes the BottomNavigationView and sets up navigation between fragments.
     *
     * @param savedInstanceState A Bundle containing the activity's previously saved state, or null if there is no state.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Find the BottomNavigationView from the layout
        val bottomNavigation = findViewById<BottomNavigationView>(R.id.btm_nav)

        // Find the NavController associated with the host fragment
        val navController = Navigation.findNavController(this, R.id.host_fragment)

        // Set up the BottomNavigationView with the NavController
        // This allows navigation between fragments using the bottom navigation bar
        NavigationUI.setupWithNavController(bottomNavigation, navController)
    }
}
