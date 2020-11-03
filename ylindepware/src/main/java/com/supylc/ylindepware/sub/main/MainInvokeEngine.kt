package com.supylc.ylindepware.sub.main

import android.util.Log
import androidx.collection.ArrayMap
import com.supylc.ylindepware.MethodInvoker
import com.supylc.ylindepware.base.serialize.InterfaceClassMethod
import com.supylc.ylindepware.base.serialize.SerializeConverter
import com.supylc.ylindepware.base.serialize.SerializeInvokeParam
import com.supylc.ylindepware.internal.IndepWareContext

/**
 * Created by Supylc on 2020/11/3.
 */
object MainInvokeEngine {

    private const val TAG = "MainInvokeEngine"

    private val mClassMethodListMap = ArrayMap<Class<*>, InterfaceClassMethod>()

    fun invokeSerializableMethod(
        interfaceClassName: String,
        methodInvoker: MethodInvoker
    ): String? {
        try {
            val clazz = Class.forName(interfaceClassName)
            if (clazz == null) {
                Log.w(TAG, "invokeSerializableMethod error, clazz $interfaceClassName not register")
                return null
            }

            val clazzMethodList = mClassMethodListMap[clazz]
            if (clazzMethodList == null) {
                Log.w(TAG, "invokeSerializableMethod error, classMethodList $interfaceClassName not found")
                return null
            }

            val impl = IndepWareContext.getMainInterfaceImpl(clazz)
            val paramClassArray = Array<Class<*>?>(methodInvoker.paramList.size) { null }
            val paramValueArray = Array<Any?>(methodInvoker.paramList.size) { null }
            for (i in paramClassArray.indices) {
                paramClassArray[i] = Class.forName(methodInvoker.paramList[i].clazzName)
                paramValueArray[i] = methodInvoker.paramList[i].value
            }

            val method = clazzMethodList!!.getMethod(methodInvoker.methodName, paramClassArray)
            if (method == null) {
                Log.w(TAG, "invokeSerializableMethod error, method ${methodInvoker.methodName}() not found")
                return null
            }

            val result = if (paramValueArray.isEmpty()) {
                method!!.invoke(impl)
            } else {
                method!!.invoke(impl, *paramValueArray)
            }
            if (result != null) {
                return SerializeConverter.methodInvokeToJson(
                    SerializeInvokeParam(
                        result.javaClass.name,
                        result
                    )
                )
            }
        } catch (ex: Throwable) {
            Log.e(TAG, "invokeSerializableMethod error", ex)
        }
        return null
    }

    fun initMethods(clazz: Class<*>) {
        mClassMethodListMap[clazz] = InterfaceClassMethod(clazz)
    }
}