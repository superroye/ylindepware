package com.supylc.ylindepware.sub.main

import android.util.Log
import com.google.gson.Gson
import com.supylc.ylindepware.MainInterface
import com.supylc.ylindepware.IEventBusCallback
import com.supylc.ylindepware.MethodInvoker
import com.supylc.ylindepware.base.EventUtils
import com.supylc.ylindepware.internal.IndepWareProcessor
import com.supylc.ylindepware.sub.SubInvokeEngine

/**
 * Created by Supylc on 2020/10/13.
 * 子进程调用的Service处理类
 */
class MainInterfaceStub : MainInterface.Stub() {

    companion object {
        private const val TAG = "MainInterfaceStub"
    }

    private val mGson = Gson()

    /**
     * 返回json字符串
     */
    override fun intentMethod(interfaceClassName: String, methodInvoker: MethodInvoker): String? {
        Log.i(TAG, "intentMethod class=$interfaceClassName methodInvoker=$methodInvoker")
        return SubInvokeEngine.invokeSerializableMethod(interfaceClassName, methodInvoker)
    }

    override fun registerEventCallback(callback: IEventBusCallback) {
        IndepWareProcessor.setMainEventBusCallback(callback)
    }

    override fun sendEvent(className: String, gson: String) {
        Log.i(TAG, "sendEvent class=${className} gson=${gson}")
        try {
            val event = mGson.fromJson(gson, Class.forName(className))
            EventUtils.sendEvent(event)
        } catch (ex: Throwable) {
            Log.w(TAG, "sendEvent error, class=${className} gson=$gson", ex)
        }
    }

}