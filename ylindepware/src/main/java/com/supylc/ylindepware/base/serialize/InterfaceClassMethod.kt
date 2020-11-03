package com.supylc.ylindepware.base.serialize

import androidx.collection.ArrayMap
import com.supylc.ylindepware.base.utils.TypeUtils
import java.lang.reflect.Method

/**
 * Created by Supylc on 2020/11/2.
 */
class InterfaceClassMethod(clazz: Class<*>) {

    private val mMethodListMap = ArrayMap<String, ArrayList<Method>>()

    init {
        clazz.methods.forEach {
            if (mMethodListMap.containsKey(it.name)) {
                mMethodListMap[it.name]!!.add(it)
            } else {
                mMethodListMap[it.name] = ArrayList<Method>().also { list ->
                    list.add(it)
                }
            }
        }
    }

    /**
     * 获取准确的匹配方法
     * 原理：
     * 1、根据方法名，获取匹配的方法列表
     * 2、扫描匹配的方法列表，判断参数类型一致则返回方法对象
     */
    fun getMethod(methodName: String, arrayClass: Array<Class<*>?>): Method? {
        val list = mMethodListMap[methodName]
        list?.forEach {
            val paramSize = it.parameterTypes.size
            if (paramSize == arrayClass.size) {
                if (paramSize == 0) {
                    return it
                } else {
                    var isMatch = true
                    for (i in 0 until paramSize) {
                        isMatch = if (it.parameterTypes[i] == null) {
                            false
                        } else {
                            val isPrimitive = it.parameterTypes[i].isPrimitive
                            if (isPrimitive) {
                                TypeUtils.getJavaObjectClass(it.parameterTypes[i]) == arrayClass[i]
                            } else {
                                it.parameterTypes[i].isAssignableFrom(arrayClass[i]!!)
                            }
                        }

                        //一个参数类型不匹配则马上中止
                        if (!isMatch) {
                            break
                        }
                    }

                    if (isMatch) {
                        return it
                    }
                }
            }
        }

        return null
    }
}