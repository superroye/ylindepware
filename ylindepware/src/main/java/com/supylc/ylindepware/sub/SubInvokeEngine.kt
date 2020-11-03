package com.supylc.ylindepware.sub

import android.util.Log
import androidx.collection.ArrayMap
import com.supylc.ylindepware.MethodInvoker
import com.supylc.ylindepware.base.serialize.SerializeConverter
import com.supylc.ylindepware.base.serialize.SerializeInvokeParam
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
        private const val TAG = "SubInvokeEngine"
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
                        methodInvoker.addMethodParam(SerializeInvokeParam(it.javaClass.name, it))
                    }
                }
                val jsonStr = serviceBinder.intentMethod(clazzName, methodInvoker) ?: return null
                result = SerializeConverter.getInvokeBeanFromJson(jsonStr)
            } catch (e: Throwable) {
                Log.w(TAG, "ServiceInvokeHandler error!", e)
            }
            return result
        }
    }
}