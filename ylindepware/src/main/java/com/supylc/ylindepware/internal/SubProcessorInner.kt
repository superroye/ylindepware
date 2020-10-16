package com.supylc.ylindepware.internal

import android.app.Activity
import android.util.Log
import androidx.collection.ArraySet
import com.google.gson.Gson
import com.supylc.ylindepware.MainInterface
import com.supylc.ylindepware.base.EventUtils
import com.supylc.ylindepware.base.MainInterfaceDefault
import com.supylc.ylindepware.sub.binder.MainProcessServiceBinder

/**
 * Created by Supylc on 2020/10/13.
 */
internal class SubProcessorInner {

    companion object {
        private const val TAG = "SubProcessorInner"
    }

    private val mGson = Gson()

    /**
     * 由于子进程的mService异步设置，此处设置默认空实现，保证mService调用不会为空，不然业务方可能会出现大面积崩溃
     */
    private var mService: MainInterface? = MainInterfaceDefault()
    private var mMainProcessServiceBinder: MainProcessServiceBinder? = null

    /**
     * 记录当前打开的子进程WebActivity
     */
    private val mSubActivityKeySet = ArraySet<String>()

    /**
     * 绑定子进程服务，打开连接
     */
    fun startMainServiceBind(activity: Activity) {
        Log.i(TAG, "startMainServiceBind hashCode=${activity.hashCode()}")
        if (mMainProcessServiceBinder == null) {
            mMainProcessServiceBinder = MainProcessServiceBinder()
        }
        mSubActivityKeySet.add("${activity.hashCode()}")
        mMainProcessServiceBinder?.bind(activity)
    }

    /**
     * activity退出调用，当所有子进程activity都退出时，退出子进程
     */
    fun tryStopMainServiceBind(activity: Activity) {
        Log.i(TAG, "tryStopMainServiceBind hashCode=${activity.hashCode()}")
        mSubActivityKeySet.remove("${activity.hashCode()}")
        if (mSubActivityKeySet.isEmpty()) {
            mMainProcessServiceBinder?.exitProcess()
        }
    }

    /**
     * 连接主进程服务成功后，设置本地interface
     */
    fun setMainServiceBinder(serviceBinder: MainInterface) {
        Log.i(TAG, "setMainServiceBinder")
        if (!IndepWareConfigs.isMainProcess()) {
            mService = serviceBinder
        }
    }

    fun getMainServiceBinder(): MainInterface? {
        return mService
    }

    /**
     * 当前进程发送事件
     */
    fun sendEvent(event: Any) {
        Log.i(TAG, "sendEvent class=${event::class.java.name}")
        EventUtils.sendEvent(event)
        //是否需要通知主进程
        if (IndepWareConfigs.needSendMainEventFromSub(event::class.java)) {
            val jsonStr = mGson?.toJson(event)
            mService?.sendEvent(event::class.java.name, jsonStr)
        }
    }

    /**
     * service是否初始化成功
     */
    fun isServiceReady(): Boolean {
        return mService?.asBinder()?.isBinderAlive == true
    }
}