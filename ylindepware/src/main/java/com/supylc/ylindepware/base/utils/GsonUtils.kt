package com.supylc.ylindepware.base.utils

import com.google.gson.Gson

/**
 * Created by Supylc on 2020/11/2.
 */
object GsonUtils {

    private val gson = Gson()

    fun toJson(value: Any?): String {
        return gson.toJson(value)
    }

    fun <T> fromJson(json: String?, clazz: Class<T>): T? {
        return gson.fromJson(json, clazz)
    }

    fun fromJsonAny(json: String?, clazz: Class<*>): Any? {
        return gson.fromJson(json, clazz)
    }
}