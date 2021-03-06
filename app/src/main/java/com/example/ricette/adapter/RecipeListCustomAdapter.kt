package com.example.ricette.adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.ricette.DataObjectRecipe
import com.example.ricette.R
import com.example.ricette.fragments.DetailRecipeFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.database.*

class RecipeListCustomAdapter(
        private val data: ArrayList<DataObjectRecipe>,
        private val fragment: Fragment
    ): RecyclerView.Adapter<RecipeListCustomAdapter.ViewHolder>() {

    private val itemClass: MutableList<CardView>
    private lateinit var database: FirebaseDatabase
    private lateinit var databaseReference: DatabaseReference

    init {
        this.itemClass = ArrayList()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.recipe_card, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvRecipeCard.text = data[position].recipename
        holder.ivRecipeCard.load(data[position].recipepicture)

        itemClass.add(holder.cvRecipeCard)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    inner class ViewHolder (itemView: View): RecyclerView.ViewHolder(itemView) {
        val ivRecipeCard: ImageView
        val tvRecipeCard: TextView
        val cvRecipeCard: CardView

        init {
            ivRecipeCard = itemView.findViewById(R.id.ivRecipeCard)
            tvRecipeCard = itemView.findViewById(R.id.tvRecipeCard)
            cvRecipeCard = itemView.findViewById(R.id.cvRecipeCard)

//            database = FirebaseDatabase.getInstance()
//            databaseReference = database.getReference("recipes").child(data[adapterPosition].recipename.toString())

            itemView.setOnClickListener {
                val recipeName = data[adapterPosition].recipename
                val recipeIngridients = data[adapterPosition].ingridients
                val recipeMethods = data[adapterPosition].method
                val recipePictureUri = data[adapterPosition].recipepicture
                val dataIndex = adapterPosition

                val detailRecipeFragment = DetailRecipeFragment()
                val bundle = Bundle()

                bundle.putString("recipeName", recipeName)
                bundle.putString("recipeIngridients", recipeIngridients)
                bundle.putString("recipeMethods", recipeMethods)
                bundle.putString("recipePictureUri", recipePictureUri)
                bundle.putInt("dataIndex", dataIndex)
                detailRecipeFragment.arguments = bundle

                val fragmentManager = fragment.fragmentManager
                fragmentManager?.beginTransaction()?.apply {
                    replace(R.id.flMainActivity, detailRecipeFragment)
                    addToBackStack(null)
                    commit()
                }

            }
//            ivRecipeDialog.setOnClickListener {
//
//                val items = arrayOf("Delete", "Edit")
//
//                MaterialAlertDialogBuilder(itemView.context)
//                        .setItems(items) { dialog, which ->
//                            when(which) {
//                                0 -> {
//
//                                }
//
//                            }
//                        }
//                        .show()
//
//            }

        }
    }

}