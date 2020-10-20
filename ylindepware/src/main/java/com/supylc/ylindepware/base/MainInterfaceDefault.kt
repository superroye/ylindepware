package com.supylc.ylindepware.base

import android.util.Log
import com.google.gson.Gson
import com.supylc.ylindepware.MainInterface
import com.supylc.ylindepware.custom.MainInterfaceDelegate

/**
 * Created by Supylc on 2020/10/16.
 */
open class MainInterfaceDefault(delegate: MainInterface) : MainInterface by delegate {

    constructor() : this(EmptyInvokeHandler.newIntfInstance(MainInterface::class.java))

    private val mGson = Gson()

    /**
     * 子进程通过binder发送事件到主进程
     */
    override fun sendEvent(className: String, gson: String) {
        Log.i(MainInterfaceDelegate.TAG, "sendEvent class=${className}")
        val event = mGson.fromJson(gson, Class.forName(className))
        EventUtils.sendEvent(event)
    }
}