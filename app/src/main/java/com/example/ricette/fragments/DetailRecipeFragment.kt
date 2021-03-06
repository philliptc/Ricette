package com.example.ricette.fragments

import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import coil.load
import com.example.ricette.DataObjectRecipe
import com.example.ricette.R
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference


class DetailRecipeFragment() : Fragment() {

    private lateinit var databaseReference: DatabaseReference
    private lateinit var database: FirebaseDatabase
    private lateinit var storage: FirebaseStorage
    private lateinit var storageReference: StorageReference

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
        val btnBackDetailRecipeFragment = view.findViewById<Button>(R.id.btnBackDetailRecipeFragment)
        val ivRecipeDetailRecipe = view.findViewById<ImageView>(R.id.ivRecipePictureDetail)
        val btnEditRecipeFragment = view.findViewById<Button>(R.id.btnEditDetailRecipeFragment)
        val btnDeleteDetailRecipeFragment = view.findViewById<Button>(R.id.btnDeleteDetailRecipeFragment)

        val recipeName = arguments?.getString("recipeName")
        val recipeIngridients = arguments?.getString("recipeIngridients")
        val recipeMethods = arguments?.getString("recipeMethods")
        val recipePictureUri = arguments?.getString("recipePictureUri")

        val dataIndex = arguments?.getInt("dataIndex")

        tvNamaDetailRecipeFragment.setText(recipeName)
        etRecipeNameDetailRecipe.setText(recipeIngridients)
        etMethodDetailRecipe.setText(recipeMethods)
        ivRecipeDetailRecipe.load(recipePictureUri)

        btnBackDetailRecipeFragment.setOnClickListener {
            val fragmentManager = fragmentManager
            val recipeListFragment = RecipeListFragment()

            fragmentManager?.beginTransaction()?.apply {
                replace(R.id.flMainActivity, recipeListFragment)
                remove(this@DetailRecipeFragment)
                commit()
            }
        }

        btnDeleteDetailRecipeFragment.setOnClickListener {

            database = FirebaseDatabase.getInstance()
            databaseReference = database.getReference("recipes").child(recipeName.toString())
            storage = FirebaseStorage.getInstance()
            storageReference = storage.getReference("recipes/$recipeName")

            storageReference.delete()
            databaseReference.removeValue()

            val fragmentManager = fragmentManager
            val recipeListFragment = RecipeListFragment()

            fragmentManager?.beginTransaction()?.apply {
                replace(R.id.flMainActivity, recipeListFragment)
                remove(this@DetailRecipeFragment)
                commit()
            }
        }

        btnEditRecipeFragment.setOnClickListener {
            val fragmentManager = fragmentManager
            val editRecipeFragment = EditRecipeFragment()
            val bundle = Bundle()
            bundle.putString("recipeName", recipeName)
            bundle.putString("recipeIngridients", recipeIngridients)
            bundle.putString("recipeMethods", recipeMethods)
            bundle.putString("recipePictureUri", recipePictureUri)
            editRecipeFragment.arguments = bundle

            fragmentManager?.beginTransaction()?.apply {
                replace(R.id.flMainActivity, editRecipeFragment)
                remove(this@DetailRecipeFragment)
                commit()
            }
        }

    }

    companion object {

    }
}