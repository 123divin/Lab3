package it.polito.lab3

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserReservationViewModel(application: Application):AndroidViewModel(application) {

     val reservationDates:MutableLiveData<List<Reservation>> = MutableLiveData()

    var list:List<Reservation> =listOf(
                                        Reservation(1,"Football","test",true,"2023-05-20",2,"15:20",""),
                                        Reservation(2,"Basketball","test",true,"2023-05-28",2,"16:20",""),
                                        //Reservation(0,"Handball","test",true,"2023-05-23",2,"10:15","")
                                        )
    private val reservationDatabase1 = AppDatabase.getDatabase(application.applicationContext)
    private val reservationRepository = BusinessLogic(reservationDatabase1.reservationDao())

    fun loadReservation(userId:String){
        var user:Int=userId.toInt()
        val reservationForUser = reservationRepository.getReservationByUser(user)
        reservationDates.value=reservationForUser
    }


    fun deleteReservation(reservation:Reservation)=viewModelScope.launch ( Dispatchers.IO ){
        reservationRepository.cancelReservation(reservation)
    }


    fun addReservation(reservation: Reservation?)=viewModelScope.launch ( Dispatchers.IO ){

        for (item in list){
            println("from the abyss " + item.reserve_date)

            reservationRepository.addNewReservation(item)
        }
    }




}