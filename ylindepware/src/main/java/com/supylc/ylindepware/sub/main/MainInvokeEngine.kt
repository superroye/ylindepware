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

    private val mClassMap = ArrayMap<Class<*>, InterfaceClassMethod>()

    fun invokeSerializableMethod(
        interfaceClassName: String,
        methodInvoker: MethodInvoker
    ): String? {
        try {
            val clazz = Class.forName(interfaceClassName)
            val impl = IndepWareContext.getMainInterfaceImpl(clazz)
            val paramClassArray = Array<Class<*>?>(methodInvoker.paramList.size) { null }
            val paramValueArray = Array<Any?>(methodInvoker.paramList.size) { null }
            for (i in paramClassArray.indices) {
                paramClassArray[i] = Class.forName(methodInvoker.paramList[i].clazzName)
                paramValueArray[i] = methodInvoker.paramList[i].value
            }
            val method = mClassMap[clazz]?.getMethod(methodInvoker.methodName, paramClassArray)!!
            val result = if (paramValueArray.isEmpty()) {
                method.invoke(impl)
            } else {
                method.invoke(impl, *paramValueArray)
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
            Log.w(TAG, "invokeSerializableMethod error", ex)
        }
        return null
    }

    fun initMethods(clazz: Class<*>) {
        mClassMap[clazz] = InterfaceClassMethod(clazz)
    }
}