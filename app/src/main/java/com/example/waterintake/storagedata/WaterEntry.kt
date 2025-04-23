package com.example.waterintake.storagedata

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "water_entries")
data class WaterEntry(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val amountMl: Int,
    val date: String, // Format: yyyy-MM-dd
    val time: String  // Format: HH:mm
)
