package com.example.ricette.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.ricette.DataObjectRecipe
import com.example.ricette.R

class RecipeListCustomAdapter(
        private val data: List<DataObjectRecipe>
    ): RecyclerView.Adapter<RecipeListCustomAdapter.ViewHolder>() {

    private val itemClass: MutableList<CardView>

    init {
        this.itemClass = ArrayList()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.recipe_card, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvRecipeCard.text = data[position].recipename
        holder.ivRecipeCard.setImageURI(data[position].recipepicture)
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

            itemView.setOnClickListener {

            }
        }
    }

}