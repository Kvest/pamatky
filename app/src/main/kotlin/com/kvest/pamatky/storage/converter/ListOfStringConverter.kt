package com.kvest.pamatky.storage.converter

import androidx.room.TypeConverter
import org.json.JSONArray

class ListOfStringConverter {
    @TypeConverter
    fun fromString(value: String?): List<String>? {
        if (value == null) {
            return null
        }

        val array = JSONArray(value)

        return (0 until array.length()).map {
            array.getString(it)
        }
    }

    @TypeConverter
    fun toString(pictures: List<String>?): String? {
        if (pictures == null) {
            return null
        }

        val result = JSONArray()
        pictures.forEach {
            result.put(it)
        }

        return result.toString()
    }
}