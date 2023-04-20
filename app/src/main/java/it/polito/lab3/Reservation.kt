package it.polito.lab3

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Reservation (

    @PrimaryKey(autoGenerate = true) val id:Int = 0,
    @ColumnInfo(name="name") val courtName:String?,
    @ColumnInfo(name = "city") val lastName: String?,
    @ColumnInfo(name = "reserved") val reserved: Boolean?,
    @ColumnInfo(name="reserve_date") val reserve_date:String?,
    @ColumnInfo(name="userId") val userId:Int

)


@Entity
data class User(
    @PrimaryKey(autoGenerate = true) val id:Int = 0,
    @ColumnInfo(name="name") val courtName:String?
    )