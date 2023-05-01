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
    @ColumnInfo(name="userId") val userId:Int,
    @ColumnInfo(name="reserved_time") val time:String,
    @ColumnInfo(name="requests") val requests:String

)


@Entity
data class User(
    @PrimaryKey(autoGenerate = true) val id:Int = 0,
    @ColumnInfo(name="name") val courtName:String?
    )

@Entity(tableName = "sport_discipline")
data class SportDiscipline(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String
)

@Entity(tableName = "playground")
data class Playground(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val sportDisciplineId: Int,
    val availableDays: List<String> // list of days in "yyyy-MM-dd" format when the playground is available
)
