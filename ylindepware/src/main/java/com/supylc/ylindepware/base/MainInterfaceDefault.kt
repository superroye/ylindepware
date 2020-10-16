package com.supylc.ylindepware.base

import com.supylc.ylindepware.MainInterface

/**
 * Created by Supylc on 2020/10/16.
 */
open class MainInterfaceDefault(delegate: MainInterface) : MainInterface by delegate {

    constructor() : this(EmptyInvokeHandler.newIntfInstance(MainInterface::class.java))
}