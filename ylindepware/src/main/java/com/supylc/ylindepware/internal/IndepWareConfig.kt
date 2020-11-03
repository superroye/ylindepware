package com.supylc.ylindepware.internal

import androidx.collection.ArrayMap

class IndepWareConfig(
    val apiImplMap: ArrayMap<Class<*>, Any>,
    val eventBusListenerInterface: Class<*>,
    val configCallback: IConfigCallback
)