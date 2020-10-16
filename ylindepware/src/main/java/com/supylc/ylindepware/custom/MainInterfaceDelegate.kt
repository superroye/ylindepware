package com.supylc.ylindepware.custom

import android.util.Log
import com.google.gson.Gson
import com.supylc.ylindepware.MainInterface
import com.supylc.ylindepware.base.EventUtils
import com.supylc.ylindepware.base.MainInterfaceDefault

/**
 * Created by Supylc on 2020/10/13.
 *
 * 该类运行在主进程
 * 业务方需要重写该类：
 * 1、定义所需的方法
 * 2、sendEvent方法必须保留
 */
class MainInterfaceDelegate : MainInterfaceDefault() {

    companion object {
        private const val TAG = "MainInterfaceDelegate"
    }

    private val mGson = Gson()

    override fun test(param: String?) {
        Log.i(TAG, "call sc Service test param=${param}")
    }

    /**
     * 子进程通过binder发送事件到主进程
     */
    override fun sendEvent(className: String, gson: String) {
        Log.i(TAG, "sendEvent class=${className}")
        val event = mGson.fromJson(gson, Class.forName(className))
        EventUtils.sendEvent(event)
    }
}