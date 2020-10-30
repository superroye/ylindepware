package com.supylc.ylindepware

import android.app.Activity
import android.app.Application
import android.util.Log
import com.supylc.ylindepware.internal.IndepWareContext
import com.supylc.ylindepware.internal.IndepWareProcessor

/**
 * Created by Supylc on 2020/10/10.
 *
 * 入口管理类, 功能包括：
 * 1、初始化
 * 2、webActivity创建时启动服务
 * 3、调用主进程Service
 * 4、发送Event事件
 */
object IndepWare {

    const val TAG = "IndepWare"

    /**
     * application初始化时调用
     */
    fun init(app: Application, options: IndepWareOptions) {
        Log.i(TAG, "init")
        IndepWareContext.setApp(app, options)
    }

    /**
     * webAvtivity创建时调用
     */
    fun startOnActivityCreated(context: Activity) {
        Log.i(TAG, "startOnActivityCreated")
        IndepWareProcessor.initMainInterface(context)
    }

    /**
     * activity退出回收时调用
     */
    fun stopOnActivityDestroyed(context: Activity) {
        Log.i(TAG, "stopOnActivityDestroyed")
        IndepWareProcessor.tryStopMainServiceBind(context)
    }

    /**
     * webActivity,JsApi调用该服务
     */
    fun <T> getMainInterface(clazz: Class<T>): T {
        return IndepWareProcessor.getMainInterface(clazz)
    }

    /**
     * 发送EventBus事件
     */
    fun sendEvent(event: Any) {
        IndepWareProcessor.sendEvent(event)
    }

}