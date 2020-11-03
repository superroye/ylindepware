package com.supylc.ylindepware.sub.binder

import android.util.Log
import com.google.gson.Gson
import com.supylc.ylindepware.IEventBusCallback
import com.supylc.ylindepware.base.utils.EventUtils

/**
 * Created by Supylc on 2020/10/12.
 */
class EventBusCallbackBinder : IEventBusCallback.Stub() {

    companion object {
        private const val TAG = "EventBusCallbackBinder"
    }

    private val mGson = Gson()

    override fun onEvent(className: String, gson: String) {
        Log.d(TAG, "className=$className , jsonStr=${gson}")
        val event = mGson.fromJson(gson, Class.forName(className))
        EventUtils.sendEvent(event)
    }
}