package com.begunok.begunok.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "bleItems")
data class BLEItem(
    @PrimaryKey
    val id: Int? = null, // DataBase generated
    val idGlobal: String, // From device login
    var localName: String = "", // Ask from user
    val mac: String, // From device login
    val model: Double? = 3.1, // From device login
    var timeDistanceList: List<InfoPackage> = emptyList(), // history of packages from devices
    var changerHistoryList: List<CommandItem> = emptyList(), // history of commands
    var password: String = "", // Ask from user
    var main: Boolean = false // Ask from user
    )
