package com.example.waterintake.storagedata

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

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

}
