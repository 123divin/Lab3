package it.polito.lab3

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(entities = [Reservation::class], version = 1)
abstract class AppDatabase:RoomDatabase() {
    abstract fun reservationDao(): ReservationDAO
}