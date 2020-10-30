package com.supylc.ylindepware

import com.supylc.ylindepware.base.IndepWareConfigBuilder

/**
 * Created by Supylc on 2020/10/21.
 */
interface IndepWareOptions {

    fun apply(builder: IndepWareConfigBuilder)
}