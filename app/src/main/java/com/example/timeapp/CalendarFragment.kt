package com.example.timeapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Button
import android.widget.CalendarView
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.timeapp.R
import java.text.SimpleDateFormat
import java.util.*

data class Event(val date: String, val event: String)

class CalendarFragment : Fragment(R.layout.fragment_calendar) {
    private lateinit var calendarView: CalendarView
    private lateinit var etEvent: EditText
    private lateinit var btnAddEvent: Button
    private lateinit var rvEvents: RecyclerView
    private val eventList = mutableListOf<Event>()
    private lateinit var eventAdapter: EventAdapter
    private var selectedDate: String = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date())

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        calendarView = view.findViewById(R.id.calendarView)
        etEvent = view.findViewById(R.id.etEvent)
        btnAddEvent = view.findViewById(R.id.btnAddEvent)
        rvEvents = view.findViewById(R.id.rvEvents)

        eventAdapter = EventAdapter(eventList)
        rvEvents.layoutManager = LinearLayoutManager(requireContext())
        rvEvents.adapter = eventAdapter

        calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            selectedDate = String.format("%02d/%02d/%04d", dayOfMonth, month + 1, year)
        }

        btnAddEvent.setOnClickListener {
            val eventText = etEvent.text.toString()
            if (eventText.isNotEmpty()) {
                eventList.add(Event(selectedDate, eventText))
                eventAdapter.notifyDataSetChanged()
                etEvent.text.clear()
            }
        }
    }
}
