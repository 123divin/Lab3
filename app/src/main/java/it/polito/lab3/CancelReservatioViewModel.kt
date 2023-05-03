package it.polito.lab3

import android.app.Application
import androidx.lifecycle.*
import androidx.room.Room
import java.time.LocalTime

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date


class MyViewModel (application:Application): AndroidViewModel(application){

    private val reservationDatabase1 = AppDatabase.getDatabase(application)
    private val reservationRepository = BusinessLogic(reservationDatabase1.reservationDao())


    private val _selectedTime:MutableLiveData<LocalTime> = MutableLiveData()

    val reservationDates:MutableLiveData<List<Reservation>> = MutableLiveData()

    val selectedTime: LiveData<LocalTime>
        get() = _selectedTime

   private  val _suggestion = MutableLiveData<String>()
    val suggestion: LiveData<String>
        get() = _suggestion

   private val _showCancelConfirmationDialog = MutableLiveData<Boolean>()
    val showCancelConfirmationDialog: LiveData<Boolean>
        get() = _showCancelConfirmationDialog


    init {
        _selectedTime.value = LocalTime.now()
        _suggestion.value = ""
        _showCancelConfirmationDialog.value = false

    }


    fun onTimeChanged(hourOfDay: Int, minute: Int) {
        _selectedTime.value = LocalTime.of(hourOfDay, minute)

    }

    fun onSuggestionTextChanged(suggestion: String) {
        _suggestion.value = suggestion
    }

    fun reservationOnDate(userId: Int, date: String):Reservation{

        println(userId)
        println(date + "to change or not")
        //println(reservationRepository.getReserveOnDate(userId,date).reserve_date)
       return reservationRepository.getReserveOnDate(userId,date)
    }

    fun onUpdateButtonClicked(reservation: Reservation) {

       // TODO (CALL A BUSINESS LOGIC THAT WILL UPDATE(updateReservation) THE RESERVATION WHEN THE UPDATE BUTTON IS CLICKED)
        // Handle update button click here
        if(reservation.reserve_date != null){
            val entryToDelete = reservationRepository.getReserveOnDate(reservation.userId, reservation.reserve_date)
            if(reservationRepository.cancelReservation(entryToDelete)){
                reservationRepository.addNewReservation(reservation)
            }

        }

    }

    fun onCancelButtonClicked(userId: Int, date: String):Boolean {
        // TODO
        val entryToDelete = reservationRepository.getReserveOnDate(userId, date)
        return reservationRepository.cancelReservation(entryToDelete)

        //_showCancelConfirmation
    }

    fun onCancelConfirmed() {
      //  TODO("Not yet implemented")
    }
}