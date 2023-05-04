package it.polito.lab3

import android.app.Application
import androidx.lifecycle.*
import androidx.room.Room
import java.time.LocalTime

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.temporal.TemporalUnit
import java.util.Date


class MyViewModel (application:Application): AndroidViewModel(application){

    private val reservationDatabase1 = AppDatabase.getDatabase(application)
    private val reservationRepository = BusinessLogic(reservationDatabase1.reservationDao())


    private val _selectedTime:MutableLiveData<LocalTime> = MutableLiveData()

    private val reservationDates:MutableLiveData<List<Reservation>> = MutableLiveData()

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

    fun onUpdateButtonClicked(reservation: Reservation?) {
        if (reservation == null){
            return
        }
       // TODO (CALL A BUSINESS LOGIC THAT WILL UPDATE(updateReservation) THE RESERVATION WHEN THE UPDATE BUTTON IS CLICKED)
        // Handle update button click here
        else if(reservation.reserve_date != null){
            println("reservation user ID" + reservation.userId)
            println(reservation.reserve_date + "reserved date")

            val entryToDelete = reservationRepository.getReserveOnDate(reservation.userId, reservation.reserve_date)
//            val updatedEntry = entryToDelete.copy(time = selectedTime.value.toString(), requests = suggestion.value.toString())
            val updatedEntry = entryToDelete.copy(time = selectedTime.value?.hour.toString() + ':' + selectedTime.value?.minute.toString() , requests = suggestion.value.toString())

            println("we arrived at the update part")

            //if (reservationRepository.updateReservation(entryToDelete))  {
            if (reservationRepository.updateReservation(updatedEntry))  {
                reservationDates.value=reservationRepository.getReservationByUser(reservation.userId)
            }

            /*
            if(reservationRepository.cancelReservation(entryToDelete)){
                reservationRepository.addNewReservation(reservation)
                reservationDates.value=reservationRepository.getReservationByUser(reservation.userId)
            }*/

        }

    }

    fun onCancelButtonClicked(userId: Int, date: String):Boolean {
        // TODO
        println("we entered in the cancel button")
        val entryToDelete = reservationRepository.getReserveOnDate(userId, date)
        println("entry to delete is $entryToDelete and ${entryToDelete.userId} and ${entryToDelete.reserve_date}")
        if(reservationRepository.cancelReservation(entryToDelete)){
            println("we canceled the reservation from here")
            reservationDates.value=reservationRepository.getReservationByUser(userId)
            return true
        }
        return false
        //_showCancelConfirmation
    }

    fun onCancelConfirmed() {
      //  TODO("Not yet implemented")
    }
}