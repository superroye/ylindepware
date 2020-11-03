package com.supylc.ylindepware.base.serialize

import androidx.collection.ArrayMap
import java.lang.reflect.Method

/**
 * Created by Supylc on 2020/11/2.
 */
class InterfaceClassMethod(clazz: Class<*>) {

    private val mMethodList = ArrayMap<String, ArrayList<Method>>()

    init {
        clazz.methods.forEach {
            if (mMethodList.containsKey(it.name)) {
                mMethodList[it.name]!!.add(it)
            } else {
                mMethodList[it.name] = ArrayList<Method>().also { list ->
                    list.add(it)
                }
            }
        }
    }

    fun getMethod(methodName: String, arrayClass: Array<Class<*>>) {

    }
}