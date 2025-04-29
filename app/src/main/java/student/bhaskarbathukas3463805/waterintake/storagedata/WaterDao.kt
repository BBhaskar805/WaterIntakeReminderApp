package student.bhaskarbathukas3463805.waterintake.storagedata

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface WaterDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEntry(entry: WaterEntry)

    @Query("SELECT * FROM water_entries ORDER BY date DESC, time DESC")
    fun getAllEntriesOld(): Flow<List<WaterEntry>>

    @Query("""
        SELECT * FROM water_entries
        WHERE date >= date('now', '-6 days') 
        ORDER BY date ASC
    """)
    fun getLastSevenDaysEntries(): Flow<List<WaterEntry>>

    @Query("SELECT * FROM water_entries ORDER BY date")
    fun getAllEntries(): Flow<List<WaterEntry>>

}
