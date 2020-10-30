package com.supylc.ylindepware.sub

import android.util.Log
import androidx.collection.ArrayMap
import com.google.gson.Gson
import com.supylc.ylindepware.MethodInvoker
import com.supylc.ylindepware.internal.IndepWareContext
import com.supylc.ylindepware.internal.IndepWareProcessor
import java.lang.reflect.InvocationHandler
import java.lang.reflect.Method
import java.lang.reflect.Proxy

/**
 * Created by Supylc on 2020/10/27.
 * 公共接口代理处理类
 */
class SubInvokeEngine {

    companion object {
        private const val TAG = "IntentMethodEngine"
        private var mGson = Gson()

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
                    paramClassArray[i] = Class.forName(methodInvoker.paramList[i].name)
                    if (Int::class.javaObjectType == paramClassArray[i]) {
                        paramClassArray[i] = Int::class.javaPrimitiveType
                    }
                    paramValueArray[i] = methodInvoker.paramList[i].
                }
                val method = if (paramClassArray.isEmpty()) {
                    impl.javaClass.getDeclaredMethod(methodInvoker.methodName)
                } else {
                    impl.javaClass.getDeclaredMethod(methodInvoker.methodName, *paramClassArray)
                }
                val result = if (paramValueArray.isEmpty()) {
                    method.invoke(impl)
                } else {
                    method.invoke(impl, *paramValueArray)
                }
                if (result != null) {
                    return mGson.toJson(Pair(result.javaClass.name, result.toString()))
                }
            } catch (ex: Throwable) {
                Log.w(TAG, "invokeSerializableMethod error", ex)
            }
            return null
        }
    }

    private val mMainInterfaceMap = ArrayMap<Class<*>, Any>()

    fun <T> getMainInterfaceProxy(clazz: Class<T>): T {
        var proxy = mMainInterfaceMap[clazz]
        if (proxy == null) {
            proxy = Proxy.newProxyInstance(
                clazz.classLoader,
                arrayOf(clazz),
                ServiceInvokeHandler(clazz.name)
            )
            mMainInterfaceMap[clazz] = proxy
        }
        return proxy as T
    }

    class ServiceInvokeHandler(private val clazzName: String) : InvocationHandler {

        override fun invoke(proxy: Any?, method: Method?, args: Array<out Any>?): Any? {
            var result: Any? = null
            try {
                val serviceBinder = IndepWareProcessor.getMainServiceBinder()
                Log.i(TAG, "invoke ${clazzName}.${method?.name}()")
                if (serviceBinder == null) {
                    Log.i(TAG, "serviceBinder == null")
                    return null
                }

                val methodInvoker = MethodInvoker()
                methodInvoker.methodName = method?.name!!
                if (!args.isNullOrEmpty()) {
                    args.forEach {
                        methodInvoker.addMethodParam(MethodInvokeParam(it.javaClass.name, it))
                    }
                }
                val jsonStr = serviceBinder.intentMethod(clazzName, methodInvoker)
                val pair: Pair<String, String> =
                    mGson.fromJson(jsonStr, Pair::class.java) as Pair<String, String>
                val clazz: Class<*> = Class.forName(pair.first)
                result = mGson.fromJson(pair.second, clazz)
            } catch (e: Throwable) {
                Log.w(TAG, "ServiceInvokeHandler error!", e)
            }
            return result
        }
    }
}