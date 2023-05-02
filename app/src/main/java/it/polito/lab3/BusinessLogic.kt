package it.polito.lab3

import android.database.Observable
import androidx.lifecycle.LiveData
import androidx.room.Room

class BusinessLogic(private  val reserved:ReservationDAO) {






    fun getReservationByUser(userId:Int): List<Reservation> {

        return reserved.loadAllByIds(userId)


    }


    fun getReserveOnDate(userId: Int,reserve_date:String):Reservation{

        var a:Reservation
        try {
           a= reserved.getReservation(userId,reserve_date)
            println(a.id)
        }catch (exception:Exception){
            println("this is the exception" + exception.message)
        }
        return reserved.getReservation(userId,reserve_date)
    }

    fun addNewReservation(reservation: Reservation): Boolean {

        println("from reservation we added " + reservation.reserve_date)
        try {
        reserved.insertAll(reservation)
        println("the return value should be true")
        }catch (exception:Exception){
            println("the false return " + exception.message)
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


