package com.begunok.begunok.data.parsers

import androidx.room.TypeConverter
import com.begunok.begunok.data.models.CommandItem
import com.begunok.begunok.data.models.InfoPackage
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.Date

class Converters {
    private val gson = Gson()

    // Converter List<String>
    @TypeConverter
    fun fromStringList(value: String?): List<String>? {
        return value?.split(",") ?: emptyList()
    }

    @TypeConverter
    fun fromListString(value: List<String>?): String? {
        return value?.joinToString(",")
    }

    // Converter for List<InfoPackage>
    @TypeConverter
    fun fromInfoPackageList(value: List<InfoPackage>?): String? {
        return gson.toJson(value)
    }

    @TypeConverter
    fun toInfoPackageList(value: String?): List<InfoPackage>? {
        val listType = object : TypeToken<List<InfoPackage>>() {}.type
        return gson.fromJson(value, listType)
    }

    // Converter for List<CommandItem>
    @TypeConverter
    fun fromCommandItemList(value: List<CommandItem>?): String? {
        return gson.toJson(value)
    }

    @TypeConverter
    fun toCommandItemList(value: String?): List<CommandItem>? {
        val listType = object : TypeToken<List<CommandItem>>() {}.type
        return gson.fromJson(value, listType)
    }

    // Converter for Date
    @TypeConverter
    fun fromDate(value: Date?): Long? {
        return value?.time
    }

    @TypeConverter
    fun toDate(value: Long?): Date? {
        return value?.let { Date(it) }
    }
}