package it.polito.lab3

import androidx.lifecycle.LiveData
import androidx.room.Room

class BusinessLogic {

    lateinit var reservationDates:ArrayList<String>
    lateinit var reserved:ReservationDAO



    fun getReservationByUser(userId:Int): ArrayList<String>? {

        reservationDates.addAll(reserved.loadAllByIds(userId))
        return reservationDates
    }

    fun addNewReservation(reservation: Reservation): LiveData<Boolean>? {

        try {

        }catch (exception:Exception){

        }
        return null
    }

    fun cancelReservation(reservation: Reservation): LiveData<Boolean>? {

        return null
    }


}


