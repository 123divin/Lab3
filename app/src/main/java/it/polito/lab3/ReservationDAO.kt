package it.polito.lab3

import android.database.Observable
import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ReservationDAO{

    @Query("SELECT * FROM reservation")
    fun getAll(): List<Reservation>

    @Query("SELECT * FROM reservation WHERE userId IN (:userId)")
    fun loadAllByIds(userId: Int): List<Reservation>

    @Insert
    fun insertAll(vararg reservation: Reservation)

    @Delete
    fun delete(reservation: Reservation)

    @Update()
    fun updateReservation(reservation: Reservation)

}