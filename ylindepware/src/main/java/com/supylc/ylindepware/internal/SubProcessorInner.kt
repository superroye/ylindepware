package com.supylc.ylindepware.internal

import android.app.Activity
import android.util.Log
import androidx.collection.ArraySet
import com.google.gson.Gson
import com.supylc.ylindepware.MainInterface
import com.supylc.ylindepware.base.EventUtils
import com.supylc.ylindepware.sub.binder.MainProcessServiceBinder

/**
 * Created by Supylc on 2020/10/13.
 */
internal class SubProcessorInner {

    companion object {
        private const val TAG = "SubProcessorInner"
    }

    private val mGson = Gson()
    private var mService: MainInterface? = null
    private var mMainProcessServiceBinder: MainProcessServiceBinder? = null
    private val mSubActivityKeySet = ArraySet<String>() //记录当前打开的子进程WebActivity

    fun startMainServiceBind(activity: Activity) {
        Log.i(TAG, "startMainServiceBind hashCode=${activity.hashCode()}")
        if (mMainProcessServiceBinder == null) {
            mMainProcessServiceBinder = MainProcessServiceBinder()
        }
        mMainProcessServiceBinder?.bind(activity)
        mSubActivityKeySet.add("${activity.hashCode()}")
    }

    fun tryStopMainServiceBind(activity: Activity) {
        Log.i(TAG, "tryStopMainServiceBind hashCode=${activity.hashCode()}")
        mSubActivityKeySet.remove("${activity.hashCode()}")
        if (mSubActivityKeySet.isEmpty()) {
            mMainProcessServiceBinder?.exitProcess()
        }
    }

    fun setMainServiceBinder(serviceBinder: MainInterface) {
        Log.i(TAG, "setMainServiceBinder")
        if (!IndepWareConfigs.isMainProcess()) {
            mService = serviceBinder
        }
    }

    fun getMainServiceBinder(): MainInterface? {
        return mService
    }

    fun sendEvent(event: Any) {
        Log.i(TAG, "sendEvent class=${event::class.java.name}")
        EventUtils.sendEvent(event)
        //是否需要通知主进程
        if (IndepWareConfigs.needSendMainEventFromSub(event::class.java)) {
            val jsonStr = mGson?.toJson(event)
            mService?.sendEvent(event::class.java.name, jsonStr)
        }
    }

}