package com.example.ricette.fragments

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatDelegate
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Switch
import com.example.ricette.R

class SettingDarkMode : Fragment() {
    private lateinit var switchDark: Button
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.settings, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val switchToDark = view.findViewById<Switch>(R.id.switchDark)
        val appSettingPrefs = requireActivity().getSharedPreferences("AppSettingPrefs", Context.MODE_PRIVATE)
        val sharedPrefsEdit: SharedPreferences.Editor = appSettingPrefs.edit()
        val isNightModeOn: Boolean = appSettingPrefs.getBoolean("NightMode", false)

        switchToDark.isChecked = isNightModeOn
        switchToDark.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                sharedPrefsEdit.putBoolean("NightMode", true)

            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                sharedPrefsEdit.putBoolean("NightMode", false)

            }
            sharedPrefsEdit.apply()
        }


        val btnBackSettingDarkMode = view.findViewById<Button>(R.id.btnBackSettings)

        btnBackSettingDarkMode.setOnClickListener {
            val fragmentManager = fragmentManager

            fragmentManager?.beginTransaction()?.apply {
                val setting = SettingDarkMode()
                val recipeListFragment = RecipeListFragment()
                replace(R.id.flMainActivity, recipeListFragment)
                remove(setting)
                commit()
            }
        }
    }
}