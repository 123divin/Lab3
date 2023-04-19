package it.polito.lab3

import androidx.room.Database

@Database(entities = [Reservation::class], version = 1)
abstract class AppDatabase {
    abstract fun reservationDao(): ReservationDAO
}