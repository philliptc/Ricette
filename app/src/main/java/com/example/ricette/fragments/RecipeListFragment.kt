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
import com.example.ricette.DataObjectRecipe
import com.example.ricette.R
import com.example.ricette.adapter.RecipeListCustomAdapter

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [RecipeListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class RecipeListFragment() : Fragment() {


    private val data_ObjectRecipe : ArrayList<DataObjectRecipe> = ArrayList()


    init {
        data_ObjectRecipe.add(DataObjectRecipe("asd","asd","asdasdsda\nasdsadsda"))
        data_ObjectRecipe.add(DataObjectRecipe("asd","asd","asdasdsda\nasdsadsda"))
        data_ObjectRecipe.add(DataObjectRecipe("asd","asd","asdasdsda\nasdsadsda"))
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_recipe_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val btnAddRecipe = view.findViewById<Button>(R.id.btnAddRecipe)
        val btnTimer = view.findViewById<Button>(R.id.btnTimer)

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

        Log.d("INFO DATA", data_ObjectRecipe.size.toString())


        val layouManager = GridLayoutManager(view.context, 2)
        val adapter = RecipeListCustomAdapter(data_ObjectRecipe, this)
        val rvMain = view.findViewById<RecyclerView>(R.id.rvMain)

        rvMain.layoutManager = layouManager
        rvMain.setHasFixedSize(true)
        rvMain.adapter = adapter
    }

    companion object {

    }
}