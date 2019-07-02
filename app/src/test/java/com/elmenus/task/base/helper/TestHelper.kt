package com.elmenus.task.base.helper

import com.google.gson.Gson

import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.lang.reflect.Type

/**
 * this helper contains all mock data paths and method which convert string into objects
 */
object TestHelper {

    const val MOCK_DATA_PATH_TAGS = "tag/tags.json"
    const val MOCK_DATA_PATH_TAGS_ROOM = "tag/tags_room.json"

    const val MOCK_DATA_PATH_ITEMS = "item/items.json"
    const val MOCK_DATA_PATH_ITEMS_ROOM = "item/items_room.json"


    fun <T> loadJson(path: String, type: Type): T {
        val json = loadJson(path)
        return Gson().fromJson<Any>(json, type) as T
    }

    fun <T> loadJson(path: String, clazz: Class<T>): T {
        val json = loadJson(path)
        return Gson().fromJson(json, clazz)
    }

    fun loadJson(path: String): String {
        try {
            val sb = StringBuilder()
            val reader = BufferedReader(
                InputStreamReader(
                    TestHelper::class.java.classLoader!!.getResourceAsStream("mock/$path")
                )
            )

            reader.lines().forEach { sb.append(it) }
            return sb.toString()
        } catch (e: IOException) {
            throw IllegalArgumentException("Could not read from resource at: $path")
        }

    }
}
