package it.polito.lab3

import androidx.room.Dao
import androidx.room.Query

@Dao
interface PlaygroundDao {
    @Query("SELECT * FROM playground WHERE sportDisciplineId = :sportDisciplineId")
    fun getBySportDisciplineId(sportDisciplineId: Int): List<Playground>
}
