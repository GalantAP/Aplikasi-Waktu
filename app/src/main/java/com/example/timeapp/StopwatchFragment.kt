package com.example.timeapp

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.example.timeapp.R

class StopwatchFragment : Fragment(R.layout.fragment_stopwatch) {
    private lateinit var tvStopwatch: TextView
    private lateinit var btnStartStop: Button
    private lateinit var btnReset: Button

    private var running = false
    private var elapsedSeconds = 0
    private val handler = Handler(Looper.getMainLooper())

    private val updateStopwatchRunnable = object : Runnable {
        override fun run() {
            if (running) {
                elapsedSeconds++
                val hours = elapsedSeconds / 3600
                val minutes = (elapsedSeconds % 3600) / 60
                val seconds = elapsedSeconds % 60
                tvStopwatch.text = String.format("%02d:%02d:%02d", hours, minutes, seconds)
                handler.postDelayed(this, 1000)
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tvStopwatch = view.findViewById(R.id.tvStopwatch)
        btnStartStop = view.findViewById(R.id.btnStartStop)
        btnReset = view.findViewById(R.id.btnReset)

        btnStartStop.setOnClickListener {
            running = !running
            btnStartStop.text = if (running) "Stop" else "Start"
            if (running) handler.post(updateStopwatchRunnable)
        }

        btnReset.setOnClickListener {
            running = false
            elapsedSeconds = 0
            tvStopwatch.text = "00:00:00"
            btnStartStop.text = "Start"
            handler.removeCallbacks(updateStopwatchRunnable)
        }
    }
}
