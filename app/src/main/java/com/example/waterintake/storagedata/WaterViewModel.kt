package com.example.waterintake.storagedata

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class WaterViewModel(application: Application) : AndroidViewModel(application) {
    private val db = WaterDatabase.getDatabase(application)
    val entries = db.waterDao().getAllEntries()

    fun insert(amount: Int, date: String, time: String) {
        viewModelScope.launch {
            db.waterDao().insertEntry(WaterEntry(amountMl = amount, date = date, time = time))
        }
    }

    val last7Days: StateFlow<List<WaterEntry>> = db.waterDao().getLastSevenDaysEntries().stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        emptyList()
    )

    // ✅ Add this to fetch all entries for last 4 weeks' dropdown + graph
    val allEntries: StateFlow<List<WaterEntry>> = db.waterDao().getAllEntries().stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        emptyList()
    )

    fun getLast4WeeksConsumption(): List<Float> {
        val entries = allEntries.value
        if (entries.isEmpty()) return List(4) { 0f }

        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val now = LocalDate.now()

        return (0..3).map { weekOffset ->
            val startOfWeek = now.minusWeeks(weekOffset.toLong()).with(DayOfWeek.MONDAY)
            val endOfWeek = startOfWeek.plusDays(6)

            val weekTotal = entries
                .filter {
                    val date = try {
                        LocalDate.parse(it.date, formatter)
                    } catch (e: Exception) {
                        null
                    }
                    date != null && !date.isBefore(startOfWeek) && !date.isAfter(endOfWeek)
                }
                .sumOf { it.amountMl }
                .toFloat()
            weekTotal
        }.reversed() // so Week 1 = oldest, Week 4 = current week
    }

}

