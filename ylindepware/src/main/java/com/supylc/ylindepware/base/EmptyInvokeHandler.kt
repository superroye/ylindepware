package com.supylc.ylindepware.base

import android.util.Log
import java.lang.reflect.InvocationHandler
import java.lang.reflect.Method
import java.lang.reflect.Proxy

class EmptyInvokeHandler : InvocationHandler {

    companion object {
        private const val TAG = "EmptyInvokeHandler"
        fun <T> newIntfInstance(intfClass: Class<T>): T {
            var handler = EmptyInvokeHandler()
            return Proxy.newProxyInstance(
                intfClass.classLoader,
                arrayOf(intfClass),
                handler
            ) as T
        }
    }

    override fun invoke(proxy: Any?, method: Method?, args: Array<out Any>?): Any? {
        //Log.d("tag", "----object=" + method?.returnType.toString() + " ")
        method?.let {
            when {
                it.returnType.isArray -> {
                    return java.lang.reflect.Array.newInstance(it.returnType.componentType)
                }
                it.returnType.isInterface -> {
                    Log.d(TAG, "invoke isInterface returnType:${it.returnType}")
                    return when (it.returnType) {
                        List::class.java -> {
                            ArrayList<Any>()
                        }
                        Map::class.java -> {
                            HashMap<Any, Any>()
                        }
                        Set::class.java -> {
                            HashSet<Any>()
                        }
                        else -> newIntfInstance(it.returnType)
                    }
                }
                else -> {
                    when (it.returnType) {
                        Short::class.java -> {
                            return 0
                        }
                        Int::class.java -> {
                            return 0
                        }
                        String::class.java -> {
                            return ""
                        }
                        Boolean::class.java -> {
                            return false
                        }
                        Long::class.java -> {
                            return 0
                        }
                        Void::class.java -> {
                            return null
                        }
                        else -> {
                            try {
                                Log.d(
                                    TAG,
                                    "invoke proxy:$proxy method:${method.name} returnType:${it.returnType}"
                                )
                                return it.returnType.newInstance()
                            } catch (ex: Throwable) {
                                Log.d(
                                    TAG,
                                    "${it.returnType} error, constructors:${it.returnType.constructors}",
                                    ex
                                )
                            }
                            return null
                        }
                    }
                }
            }
        }
        return null
    }
}