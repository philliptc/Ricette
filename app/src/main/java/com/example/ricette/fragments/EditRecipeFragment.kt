package com.example.ricette.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import com.example.ricette.R


class EditRecipeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit_recipe, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val etFormEditRecipeName = view.findViewById<EditText>(R.id.etFormEditRecipeName)
        val etFormEditIngredients = view.findViewById<EditText>(R.id.etFormEditIngredients)
        val etFormEditMethods = view.findViewById<EditText>(R.id.etFormEditMethods)
        val btnEdit = view.findViewById<Button>(R.id.btnEditFragmentAddRecipe)
        val btnGalleryEditRecipePicture = view.findViewById<Button>(R.id.btnGalleryEditRecipePicture)
        val btnCameraEditRecipePicture = view.findViewById<Button>(R.id.btnCameraEditRecipePicture)
        val btnBackEditRecipeFragment = view.findViewById<Button>(R.id.btnBackEditRecipeFragment)

        val recipeName = arguments?.getString("recipeName")
        val recipeIngridients = arguments?.getString("recipeIngridients")
        val recipeMethods = arguments?.getString("recipeMethods")
        val recipePictureUri = arguments?.getString("recipePictureUri")

        etFormEditRecipeName.setText(recipeName)
        etFormEditIngredients.setText(recipeIngridients)
        etFormEditMethods.setText(recipeMethods)

        btnGalleryEditRecipePicture.setOnClickListener {

        }

        btnCameraEditRecipePicture.setOnClickListener {

        }

        btnEdit.setOnClickListener {

        }
        btnBackEditRecipeFragment.setOnClickListener {
            val fragmentManager = fragmentManager
            val detailRecipeFragment = DetailRecipeFragment()
            val bundle = Bundle()
            bundle.putString("recipeName", recipeName)
            bundle.putString("recipeIngridients", recipeIngridients)
            bundle.putString("recipeMethods", recipeMethods)
            bundle.putString("recipePictureUri", recipePictureUri)
            detailRecipeFragment.arguments = bundle

            fragmentManager?.beginTransaction()?.apply {
                replace(R.id.flMainActivity, detailRecipeFragment)
                remove(this@EditRecipeFragment)
                commit()
            }
        }

    }

    private fun sendEditNotification() {

    }



    companion object {

    }
}