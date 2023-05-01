package it.polito.lab3

import android.database.Observable
import androidx.lifecycle.LiveData
import androidx.room.Room

class BusinessLogic(private  val reserved:ReservationDAO) {




    fun getReservationByUser(userId:Int): List<Reservation> {

        return reserved.loadAllByIds(userId)


    }

    fun addNewReservation(reservation: Reservation): Boolean {

        try {
        reserved.insertAll(reservation)

        }catch (exception:Exception){
            return false
        }
        return true
    }

    fun cancelReservation(reservation: Reservation): Boolean {

        try {
            reserved.delete(reservation)

        }catch (exception:Exception){
            return false
        }
        return true
    }

    fun updateReservation(reservation: Reservation): Boolean {
        try {
            reserved.updateReservation(reservation)

        }catch (exception:Exception){
            return false
        }
        return true
    }


}


