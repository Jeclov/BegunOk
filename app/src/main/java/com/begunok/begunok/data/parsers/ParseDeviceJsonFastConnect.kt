package com.begunok.begunok.data.parsers

import com.begunok.begunok.data.models.BLEItem
import org.json.JSONException
import org.json.JSONObject


fun parseDeviceJson(jsonString: String): BLEItem? {
    return try {
        val jsonObject = JSONObject(jsonString)

        val idGlobal = jsonObject.optString("idGlobal", null)
        val mac = jsonObject.optString("mac", null)
        val model = jsonObject.optDouble("model", -1.0) // Используем -1.0 как значение по умолчанию

        // Проверяем необходимые параметры
        if (idGlobal != null && mac != null) {
            // Создаем BLEItem с учетом наличия model
            if (model >= 0) {
                BLEItem(
                    idGlobal = idGlobal,
                    mac = mac,
                    model = model
                )
            } else {
                BLEItem(
                    idGlobal = idGlobal,
                    mac = mac
                    // model не добавляется, если он не найден
                )
            }
        } else {
            null // Возвращаем null, если idGlobal или mac не найдены
        }
    } catch (e: JSONException) {
        e.printStackTrace()
        null // Возвращаем null в случае исключения
    }
}

