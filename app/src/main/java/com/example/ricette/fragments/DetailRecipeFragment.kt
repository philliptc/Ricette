package com.example.ricette.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.ricette.R


class DetailRecipeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail_recipe, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val tvNamaDetailRecipeFragment = view.findViewById<TextView>(R.id.tvNamaDetailRecipeFragment)
        val etRecipeNameDetailRecipe = view.findViewById<TextView>(R.id.etRecipeNameDetailRecipe)
        val etMethodDetailRecipe = view.findViewById<TextView>(R.id.etMethodsDetailRecipe)

        val recipeName = arguments?.getString("recipeName")
        val recipeIngridients = arguments?.getString("recipeIngridients")
        val recipeMethods = arguments?.getString("recipeMethods")

        tvNamaDetailRecipeFragment.setText(recipeName)
        etRecipeNameDetailRecipe.setText(recipeIngridients)
        etMethodDetailRecipe.setText(recipeMethods)


    }

    companion object {

    }
}