package it.polito.lab3

import android.database.Observable
import androidx.lifecycle.LiveData
import androidx.room.Room
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class BusinessLogic(private  val reserved:ReservationDAO) {






    fun getReservationByUser(userId:Int): List<Reservation> {

        return reserved.loadAllByIds(userId)


    }


    fun getReserveOnDate(userId: Int,reserve_date:String):Reservation{



        val a:Reservation
        println(reserved.getAll() + "all of the reservations please")
        println(reserved.getReservation(2,reserve_date))
        try {
           a= reserved.getReservation(userId,reserve_date)
            println(a.id)
        }catch (exception:Exception){
            println("this is the exception with  $userId and $reserve_date"  + exception.message)
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

        println("we entered in the business logic of reservation")
        try {
            reserved.delete(reservation)

        }catch (exception:Exception){
            println("an error occurred  $exception")

            return false
        }
        return true
    }

    fun updateReservation(reservation: Reservation): Boolean {
        try {
            println("we entered in the business logic of reservation update")

            reserved.updateReservation(reservation)

        }catch (exception:Exception){
            println("an error occurred  $exception")

            return false
        }
        return true
    }


}


