package com.begunok.begunok.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.begunok.begunok.data.models.BLEItem
import com.begunok.begunok.data.parsers.Converters

@Database(
    entities = [BLEItem::class],
    version = 1
)
@TypeConverters(Converters::class)
abstract class MainDb : RoomDatabase() {
    abstract fun dao(): Dao

    companion object {
        @Volatile
        private var INSTANCE: MainDb? = null

        fun getInstance(context: Context): MainDb {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MainDb::class.java,
                    "ble_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}