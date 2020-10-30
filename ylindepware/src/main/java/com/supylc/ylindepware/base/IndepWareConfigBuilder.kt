package com.supylc.ylindepware.base

import androidx.collection.ArrayMap
import com.supylc.ylindepware.internal.IConfigCallback
import com.supylc.ylindepware.internal.IndepWareConfig

/**
 * Created by Supylc on 2020/10/28.
 */
class IndepWareConfigBuilder {

    private var mInterfaces: ArrayMap<Class<*>, Any>? = null
    private var mEventBusListenerInterface: Class<*>? = null
    private var mConfigCallback: IConfigCallback? = null

    fun <T> register(mainInterfaceClass: Class<T>, mainInterfaceImpl: T) {
        if (mInterfaces == null) {
            mInterfaces = ArrayMap<Class<*>, Any>()
        }
        mInterfaces!![mainInterfaceClass] = mainInterfaceImpl
    }

    fun setEventBusListenerInterface(clazz: Class<*>) {
        mEventBusListenerInterface = clazz
    }

    fun setConfigCallback(configCallback: IConfigCallback) {
        mConfigCallback = configCallback
    }

    internal fun build(): IndepWareConfig {
        return IndepWareConfig(mInterfaces!!, mEventBusListenerInterface!!, mConfigCallback!!)
    }
}