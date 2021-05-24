package com.example.ricette

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.ricette.fragments.RecipeListFragment
import com.example.ricette.R
import com.example.ricette.fragments.TimerFragment
import kotlinx.android.synthetic.main.fragment_timer.*

class MainActivity : AppCompatActivity(),
        TimerFragment.OnCountDownListener {

    private val data_ObjectRecipe : ArrayList<DataObjectRecipe> = ArrayList()

    private val countDownTimer =
            TimerFragment(
                    0,
                    10,
                    this
            )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnResume.isEnabled = false

        btnStartTimer.setOnClickListener {
            countDownTimer.start()

            btnStartTimer.isEnabled = false
        }

        btnResume.setOnClickListener {
            countDownTimer.start(true)
            countDownTimer.runOnBackgroundThread()
        }

        btnPause.setOnClickListener {
            countDownTimer.pause()
            btnResume.isEnabled = true
            countDownTimer.setTimerPattern("s")
        }

        val recipeListFragment = RecipeListFragment()
        val frameLayoutManager = supportFragmentManager

        frameLayoutManager.beginTransaction().apply {
            replace(R.id.flMainActivity, recipeListFragment)
            addToBackStack(null)
            commit()
        }


    }

    override fun onCountDownActive(time: String) {

        runOnUiThread {

            etTimer.text = time

            Toast.makeText(
                this,
                "Seconds = " + countDownTimer.getSecondsTillCountDown() + " Minutes=" + countDownTimer.getMinutesTillCountDown(),
                Toast.LENGTH_SHORT
            ).show()
        }

    }

    override fun onCountDownFinished() {
        runOnUiThread {
            etTimer.text = "Finished"
            btnStartTimer.isEnabled = true
            btnResume.isEnabled = false
        }

    }
}