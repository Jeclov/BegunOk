package com.begunok.begunok.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.begunok.begunok.data.models.BLEItem
import kotlinx.coroutines.flow.Flow

@Dao
interface Dao {
    @Insert
    suspend fun insertBLEItem(bleItem: BLEItem)

    @Update
    suspend fun updateBLEItem(bleItem: BLEItem)

    @Delete
    suspend fun deleteBLEItem(bleItem: BLEItem)

    @Query("SELECT * FROM bleItems")
    fun getAllBLEItems() : Flow<List<BLEItem>>

    @Query("SELECT * FROM bleItems WHERE mac = :bleItemMac")
    fun getBLEItemByMac(bleItemMac: String) : BLEItem?

    @Query("SELECT * FROM bleItems WHERE id = :itemId LIMIT 1")
    suspend fun getBLEItemById(itemId: Int): BLEItem?
}