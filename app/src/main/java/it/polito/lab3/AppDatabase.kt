package it.polito.lab3

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import it.polito.lab3.dao.SportDao
import it.polito.lab3.models.Sport


@Database(entities = [Reservation::class, Sport::class], version = 1)
abstract class AppDatabase:RoomDatabase() {
    abstract fun reservationDao(): ReservationDAO

    abstract fun sportDao(): SportDao

    companion object{

        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "nodir"
                )
                    .createFromAsset("database/nodir.db")
                .build()
                INSTANCE = instance
                instance
            }
        }

    }
}