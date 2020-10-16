package com.supylc.ylindepware.sub.main

import android.util.Log
import com.google.gson.Gson
import com.supylc.ylindepware.IEventBusCallback
import com.supylc.ylindepware.custom.EventBusListenerDelegate
import com.supylc.ylindepware.base.EventUtils

/**
 * Created by Supylc on 2020/10/10.
 */
class MainEventBusListener : IMainSendEvent {

    private var mEventBusCallback: IEventBusCallback? = null
    private var mGson: Gson? = null
    private var mListenerDelegate: EventBusListenerDelegate? = null

    companion object {
        private const val TAG = "MainEventBusListener"
    }

    init {
        mListenerDelegate = EventBusListenerDelegate(this)
        mGson = Gson()
        EventUtils.register(mListenerDelegate!!)
    }

    fun setEventCallback(eventBusCallback: IEventBusCallback) {
        mEventBusCallback = eventBusCallback
    }

    /**
     * 主进程转发Event到子进程
     */
    override fun sendEvent(event: Any) {
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
}