package com.supylc.ylindepware.base.utils

import org.greenrobot.eventbus.EventBus

/**
 * Created by Supylc on 2020/10/16.
 */
object EventUtils {

    fun register(subscriber: Any) {
        EventBus.getDefault().register(subscriber)
    }

    fun unregister(subscriber: Any) {
        EventBus.getDefault().unregister(subscriber)
    }

    fun sendEvent(event: Any) {
        EventBus.getDefault().post(event)
    }
}