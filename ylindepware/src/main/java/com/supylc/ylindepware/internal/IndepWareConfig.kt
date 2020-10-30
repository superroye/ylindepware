package com.supylc.ylindepware.internal

import androidx.collection.ArrayMap

class IndepWareConfig(
    val interfaces: ArrayMap<Class<*>, Any>,
    val eventBusListenerInterface: Class<*>,
    val configCallback: IConfigCallback
)