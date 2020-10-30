package com.supylc.ylindepware.internal

/**
 * Created by Supylc on 2020/10/16.
 */
interface IConfigCallback {

    fun initSubCallback()

    fun getMainServiceBindAction(): String

    fun getActivityProcessName(): String

    /**
     * 子进程会根据配置的Event类型列表判断是否转发到主进程
     */
    fun needSendMainEventFromSub(clazz: Class<*>): Boolean

}