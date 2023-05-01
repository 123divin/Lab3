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
                                        Reservation(3,"Football","test",true,"2023-04-20",2,"15:20",""),
                                        Reservation(4,"Basketball","test",true,"2023-04-27",2,"16:20",""),
                                        Reservation(5,"Handball","test",true,"2023-04-22",2,"10:15","")
                                        )

    private val reservationDatabase = Room.databaseBuilder(
        application,
        AppDatabase::class.java,
        "reservation-db"
    ).allowMainThreadQueries().fallbackToDestructiveMigration().build()

    private val reservationRepository = BusinessLogic(reservationDatabase.reservationDao())


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
            reservationRepository.addNewReservation(item)
        }
      //  reservationRepository.addNewReservation(reservation)
    }




}