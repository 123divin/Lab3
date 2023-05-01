package it.polito.lab3.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "sports")
data class Sport(
    @ColumnInfo(name = "title") val title: String?,
    @ColumnInfo(name = "note")  val note: String?,
    @PrimaryKey(autoGenerate = true) val id: Int? = 0
    )
