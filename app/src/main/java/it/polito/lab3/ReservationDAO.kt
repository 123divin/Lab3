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

    @Query("SELECT * FROM reservation WHERE userId IN (:userId) and reserve_date IN (:reserve_date)")
    fun getReservation(userId: Int,reserve_date:String):Reservation

    @Insert
    fun insertAll(vararg reservation: Reservation)

    @Delete
    fun delete(vararg reservation: Reservation)

    //@Query("UPDATE reservation SET column1 = :newVal1, column2 = :newVal2 WHERE id = :entryId")
    //fun updateReservation(reservation: Reservation)


}