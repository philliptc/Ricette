package com.example.ricette

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.ricette.fragments.RecipeListFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val RecipeListFragment = RecipeListFragment()

    }
}