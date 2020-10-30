package com.supylc.ylindepware.sub.main

import android.util.Log
import com.google.gson.Gson
import com.supylc.ylindepware.IEventBusCallback
import com.supylc.ylindepware.base.EventUtils
import com.supylc.ylindepware.internal.IndepWareContext
import java.lang.reflect.InvocationHandler
import java.lang.reflect.Method
import java.lang.reflect.Proxy

/**
 * Created by Supylc on 2020/10/10.
 */
class MainEventBusListener {

    private var mEventBusCallback: IEventBusCallback? = null
    private var mGson: Gson? = null
    private var mListenerDelegate: Any? = null

    companion object {
        private const val TAG = "MainEventBusListener"
    }

    init {
        val interfaceClass = IndepWareContext.getEventBusListenerInterfaceClass()
        mListenerDelegate = Proxy.newProxyInstance(
            interfaceClass.classLoader,
            arrayOf(interfaceClass),
            EventListenerInvokeHandler(this)
        )
        mGson = Gson()
        EventUtils.register(mListenerDelegate!!)
    }

    /**
     * 设置event回调
     */
    fun setEventCallback(eventBusCallback: IEventBusCallback) {
        mEventBusCallback = eventBusCallback
    }

    /**
     * 主进程转发Event到子进程
     */
    fun sendEvent(event: Any) {
        try {
            if (mEventBusCallback == null) {
                return
            }
            if (mEventBusCallback?.asBinder()?.isBinderAlive != true) {
                return
            }

            Log.i(TAG, "sendEvent event=${event}")
            val jsonStr = mGson?.toJson(event)
            mEventBusCallback?.onEvent(event::class.java.name, jsonStr)
        } catch (ex: Exception) {
            Log.e(TAG, "sendEvent error", ex)
        }
    }

    fun onDestroy() {
        EventUtils.unregister(mListenerDelegate!!)
    }

    class EventListenerInvokeHandler(private val listenerProxy: MainEventBusListener) :
        InvocationHandler {

        override fun invoke(proxy: Any?, method: Method?, args: Array<out Any>?): Any? {
            var result: Any? = null
            try {
                result = if (args == null) {
                    Log.w(TAG, "eventListener invoke error, no args")
                } else {
                    listenerProxy.sendEvent(args[0])
                }
            } catch (e: Throwable) {
                Log.w(TAG, "eventListener invoke error!", e)
            }
            return result
        }
    }
}