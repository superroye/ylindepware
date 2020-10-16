package com.supylc.ylindepware.base

import com.supylc.ylindepware.internal.IndepWareConfigs

/**
 * Created by Supylc on 2020/10/16.
 */
object EventUtils {

    fun register(subscriber: Any) {
        IndepWareConfigs.registerEventSubscriber(subscriber)
    }

    fun unregister(subscriber: Any) {
        IndepWareConfigs.unregisterEventSubscriber(subscriber)
    }

    fun sendEvent(event: Any) {
        IndepWareConfigs.sendEvent(event)
    }
}