package it.polito.lab3

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ReservationDAO{

    @Query("SELECT * FROM reservation")
    fun getAll(): List<Reservation>

    @Query("SELECT reserve_date FROM reservation WHERE userId IN (:userId)")
    fun loadAllByIds(userId: Int): List<String>

    @Insert
    fun insertAll(vararg reservation: Reservation)

    @Delete
    fun delete(reservation: Reservation)

}