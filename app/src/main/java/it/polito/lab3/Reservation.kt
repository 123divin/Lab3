package it.polito.lab3

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.text.DateFormat

@Entity
data class Reservation (

    @PrimaryKey(autoGenerate = true) val id:Int = 0,
    @ColumnInfo(name="name") val courtName:String?,
    @ColumnInfo(name = "city") val lastName: String?,
    @ColumnInfo(name = "reserved") val reserved: Boolean?,
    @ColumnInfo(name="reserve_date") val reserve_date:DateFormat?

)