package com.example.ricette.fragments

import android.Manifest
import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.core.content.ContextCompat
import coil.load
import com.example.ricette.DataObjectRecipe
import com.example.ricette.R
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

private lateinit var storage : FirebaseStorage
private lateinit var storageReference : StorageReference
private lateinit var etRecipeName : EditText
private lateinit var ivRecipePicture : ImageView
private lateinit var database : FirebaseDatabase
private lateinit var databaseReference : DatabaseReference
private val PERMISSION_CODE = 1000;
private val IMAGE_PICTURE_CODE = 1001
var image_uri : Uri? = null
var imageurl : Uri? = null


class EditRecipeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit_recipe, container, false)
    }

    private fun openCamera(){
        val values = ContentValues()
        values.put(MediaStore.Images.Media.TITLE, "New Picture")
        values.put(MediaStore.Images.Media.DESCRIPTION, "From the camera")

        image_uri = requireActivity().contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,values)
        //camera intent
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        cameraIntent.putExtra (MediaStore.EXTRA_OUTPUT, image_uri)
        startActivityForResult(cameraIntent, IMAGE_PICTURE_CODE)
    }

    private fun openGallery(){
        val gallery = Intent()
        gallery.type = "image/*"
        gallery.action = Intent.ACTION_GET_CONTENT
        gallery.putExtra(MediaStore.EXTRA_OUTPUT, image_uri)

        startActivityForResult(gallery, 100)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        // dipanggil ketika user menekan ALLOW atau DENY
        when (requestCode){
            PERMISSION_CODE -> {
                if (grantResults.size > 0 && grantResults [0] ==
                        PackageManager.PERMISSION_GRANTED){
                    //permission from popup was granted
                    openCamera()
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        //called when image was captured from camera intent
        if (resultCode == Activity.RESULT_OK && requestCode == IMAGE_PICTURE_CODE) {
            storage = FirebaseStorage.getInstance()
            storageReference = storage.getReference("recipes/" + etRecipeName.text.toString())
            val uploadTask = storageReference.putFile(image_uri!!)
            uploadTask.addOnSuccessListener {
                storageReference.downloadUrl.addOnCompleteListener {
                    imageurl = it.result
                }
            }
            // set image capture to image view
            ivRecipePicture.load(image_uri)
        }
        if (resultCode == Activity.RESULT_OK && requestCode == 100){
            image_uri = data?.data
            storage = FirebaseStorage.getInstance()
            storageReference = storage.getReference("recipes/" + etRecipeName.text.toString())
            val uploadTask = storageReference.putFile(image_uri!!)
            uploadTask.addOnSuccessListener {
                storageReference.downloadUrl.addOnCompleteListener {
                    imageurl = it.result
                }
            }
            // set gallery image to image view
            ivRecipePicture.load(image_uri)
        }
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
            openGallery()
        }

        btnCameraEditRecipePicture.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA)
                        == PackageManager.PERMISSION_DENIED ||
                        ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        == PackageManager.PERMISSION_DENIED) {
                    // permission ditolak
                    val permission = arrayOf(android.Manifest.permission.CAMERA, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    // nampilin popup request permission
                    requestPermissions(permission, PERMISSION_CODE)
                }
                else{
                    openCamera()
                }
            }
            else{
                openCamera()
            }
        }

        btnEdit.setOnClickListener {
            val recipeName = etFormEditRecipeName.text.toString()
            val ingridients = etFormEditIngredients.text.toString()
            val methods = etFormEditMethods.text.toString()
            val recipe = DataObjectRecipe(recipeName, ingridients, methods, imageurl.toString())

            database = FirebaseDatabase.getInstance()
            databaseReference = database.getReference("recipes").child(recipeName)
            databaseReference.setValue(recipe)

            val fragmentManager = fragmentManager
            val recipeListFragment = RecipeListFragment()

            fragmentManager?.beginTransaction()?.apply {
                replace(R.id.flMainActivity, recipeListFragment)
                remove(this@EditRecipeFragment)
                commit()
            }
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