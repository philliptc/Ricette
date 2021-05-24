package com.example.ricette.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.ricette.DataObjectRecipe
import com.example.ricette.R
import com.example.ricette.adapter.RecipeListCustomAdapter
import com.google.firebase.database.*

class RecipeListFragment() : Fragment() {

    private val data_ObjectRecipe : ArrayList<DataObjectRecipe> = ArrayList()
    private lateinit var database: FirebaseDatabase
    private lateinit var databaseReference: DatabaseReference
//    val adapter = RecipeListCustomAdapter(data_ObjectRecipe, this)


//    init {
//        getData()
//    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_recipe_list, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val btnAddRecipe = view.findViewById<Button>(R.id.btnAddRecipe)
        val btnTimer = view.findViewById<Button>(R.id.btnTimer)
        val btnSetting = view.findViewById<Button>(R.id.btnSetting)

        getData()

        btnAddRecipe.setOnClickListener {
            val fragmentManager = fragmentManager
            val addRecipeFragment = AddRecipeFragment()

            fragmentManager?.beginTransaction()?.apply {
                replace(R.id.flMainActivity, addRecipeFragment)
                addToBackStack(null)
                commit()
            }
        }

        btnTimer.setOnClickListener {
            val fragmentManager = fragmentManager
            val timerFragment = TimerFragment()

            fragmentManager?.beginTransaction()?.apply {
                replace(R.id.flMainActivity, timerFragment)
                addToBackStack(null)
                commit()
            }
        }

        btnSetting.setOnClickListener {
            val fragmentManager = fragmentManager
            val setting = SettingDarkMode()

            fragmentManager?.beginTransaction()?.apply {
                replace(R.id.flMainActivity, setting)
                addToBackStack(null)
                commit()
            }
        }

        Log.d("INFO DATA", data_ObjectRecipe.size.toString())

    }

    private fun getData() {
        database = FirebaseDatabase.getInstance()
        databaseReference = database.getReference("recipes")
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    data_ObjectRecipe.clear()
                    for (data in snapshot.children) {
                        val name = data.child("recipename").value.toString()
                        val ingredients = data.child("ingridients").value.toString()
                        val methods = data.child("method").value.toString()
                        val image = data.child("recipepicture").value.toString()

                        data_ObjectRecipe.add(DataObjectRecipe(name, ingredients, methods, image))

                        val layouManager = GridLayoutManager(view?.context, 2)
                        val adapter = RecipeListCustomAdapter(data_ObjectRecipe, this@RecipeListFragment)
                        val rvMain = view?.findViewById<RecyclerView>(R.id.rvMain)

                        rvMain?.layoutManager = layouManager
                        rvMain?.setHasFixedSize(true)
                        rvMain?.adapter = adapter

                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("DATA BASE", "Can't load database")
            }

        })
    }

    companion object {

    }
}