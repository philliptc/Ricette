package com.example.ricette.fragments

import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.os.HandlerThread
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.cardview.widget.CardView
import com.example.ricette.R
import io.paperdb.Paper
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit


class TimerFragment (
        private var fromMinutes: Long,
        private var fromSeconds: Long,
        private val onCountDownListener: OnCountDownListener,
        private var delayInSeconds: Long = 1
) {

    private val calendar = Calendar.getInstance()
    private var seconds = 0L
    private var minutes = 0L
    private var finished = false
    private var handler = Handler()
    private var handlerThread: HandlerThread? = null
    private var isBackgroundThreadRunning = false
    private val simpleDateFormat = SimpleDateFormat("mm:ss", Locale.getDefault())
    private val runnable = Runnable { decrementMinutes() }

    init {
        check(!(fromMinutes <= 0 && fromSeconds <= 0)) { javaClass.simpleName + " can't work in state 0:00" }

        if (delayInSeconds <= 0)
            delayInSeconds = 1

        setCountDownValues()
    }


    private fun setCountDownValues(
            fromMinutes: Long = this.fromMinutes,
            fromSeconds: Long = this.fromSeconds
    ) {
        this.fromMinutes = fromMinutes
        this.fromSeconds = fromSeconds
        minutes = this.fromMinutes

        if (fromMinutes > 0 && fromSeconds <= 0) {
            seconds = 0
            return
        }

        if (fromSeconds <= 0 || fromSeconds > 59) {
            seconds = 59
            return
        }
        seconds = this.fromSeconds
    }


    fun getSecondsTillCountDown() = seconds



    fun getMinutesTillCountDown() = minutes



    fun setTimerPattern(pattern: String) {
        if (pattern.equals("mm:ss", ignoreCase = true) || pattern.equals(
                        "m:s",
                        ignoreCase = true
                ) || pattern.equals("mm", ignoreCase = true) ||
                pattern.equals("ss", ignoreCase = true) || pattern.equals(
                        "m",
                        ignoreCase = true
                ) || pattern.equals("s", ignoreCase = true)
        ) simpleDateFormat.applyPattern(pattern)
    }


    fun runOnBackgroundThread() {
        if (isBackgroundThreadRunning) return
        handlerThread = HandlerThread(javaClass.simpleName)
        startBackgroundThreadIfNotRunningAndEnabled()
        handler = Handler(handlerThread!!.looper)
    }

    private fun startBackgroundThreadIfNotRunningAndEnabled() {

        handlerThread!!.run {
            start()
            isBackgroundThreadRunning = true
        }

    }

    private fun getCountDownTime(): String {
        calendar[Calendar.MINUTE] = minutes.toInt()
        calendar[Calendar.SECOND] = seconds.toInt()
        return simpleDateFormat.format(calendar.time)
    }

    private fun decrementMinutes() {
        seconds--

        if (minutes == 0L && seconds == 0L) {
            finish()
        }

        if (seconds < 0L) {
            if (minutes > 0) {
                seconds = 59
                minutes--
            }
        }


        runCountdown()
    }

    private fun finish() {
        onCountDownListener.onCountDownFinished()
        finished = true
        pause()
    }

    private fun decrementSeconds() {
        handler.postDelayed(
                runnable,
                TimeUnit.SECONDS.toMillis(delayInSeconds)
        )
    }


    fun start(resume: Boolean = false) {
        if (!resume) {
            setCountDownValues()
            finished = false
        }
        runCountdown()
    }

    private fun runCountdown() {
        if (!finished) {
            updateUI()
            decrementSeconds()
        }
    }

    private fun updateUI() {
        onCountDownListener.onCountDownActive(getCountDownTime())
    }


    fun pause() {
        handler.removeCallbacks(runnable)
    }


    interface OnCountDownListener {

        fun onCountDownActive(time: String)

        fun onCountDownFinished()
    }
}
