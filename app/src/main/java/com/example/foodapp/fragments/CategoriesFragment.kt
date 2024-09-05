package com.example.foodapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.foodapp.R

// Constant keys for fragment arguments, used to pass data when initializing the fragment
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * CategoriesFragment is a simple fragment that displays a list of meal categories.
 * It can be initialized with two optional parameters (param1 and param2) for customization.
 */
class CategoriesFragment : Fragment() {
    // Optional parameters to configure the fragment instance
    private var param1: String? = null
    private var param2: String? = null

    /**
     * Called when the fragment is first created.
     * Retrieves the arguments passed to the fragment, if any, and stores them in param1 and param2.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    /**
     * Called to inflate the layout for this fragment.
     * Returns the root view of the fragment's layout hierarchy.
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment from the XML file
        return inflater.inflate(R.layout.fragment_categories, container, false)
    }

    companion object {
        /**
         * Factory method to create a new instance of CategoriesFragment.
         * This method allows for the fragment to be initialized with optional parameters.
         *
         * @param param1 A string parameter to pass data to the fragment.
         * @param param2 A string parameter to pass data to the fragment.
         * @return A new instance of CategoriesFragment initialized with the provided parameters.
         */
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            CategoriesFragment().apply {
                // Bundle is used to pass parameters to the fragment
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
