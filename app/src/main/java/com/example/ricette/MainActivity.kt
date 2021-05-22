package com.example.ricette

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.FrameLayout
import com.example.ricette.fragments.RecipeListFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recipeListFragment = RecipeListFragment()
        val frameLayoutManager = supportFragmentManager

        frameLayoutManager.beginTransaction().apply {
            replace(R.id.flMainActivity, recipeListFragment)
            addToBackStack(null)
            commit()
        }


    }
}