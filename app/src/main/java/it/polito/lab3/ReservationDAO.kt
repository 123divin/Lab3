package it.polito.lab3

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ReservationDAO{

    @Query("SELECT * FROM reservation")
    fun getAll(): List<Reservation>

    @Query("SELECT * FROM reservation WHERE uid IN (:reservationId)")
    fun loadAllByIds(userIds: IntArray): List<Reservation>

    @Insert
    fun insertAll(vararg reservation: Reservation)

    @Delete
    fun delete(reservation: Reservation)

}