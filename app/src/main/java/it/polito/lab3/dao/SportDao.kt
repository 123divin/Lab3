package it.polito.lab3.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import it.polito.lab3.models.Sport

@Dao
interface SportDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(noteEntity: Sport)

    @Query("SELECT * FROM sports ORDER BY id ASC")
    fun getAll(): LiveData<List<Sport>>
}