package it.polito.lab3

import androidx.room.Dao
import androidx.room.Query

@Dao
interface SportDisciplineDao {
    @Query("SELECT * FROM sport_discipline")
    fun getAll(): List<SportDiscipline>
}
