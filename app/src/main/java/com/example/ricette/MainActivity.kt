package com.example.ricette

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.ricette.fragments.RecipeListFragment

class MainActivity : AppCompatActivity() {

    private val data_ObjectRecipe : ArrayList<DataObjectRecipe> = ArrayList()
    val recipeListFragment = RecipeListFragment()
    val frameLayoutManager = supportFragmentManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        frameLayoutManager.beginTransaction().apply {
            replace(R.id.flMainActivity, recipeListFragment)
            addToBackStack(null)
            commit()
        }


    }
}