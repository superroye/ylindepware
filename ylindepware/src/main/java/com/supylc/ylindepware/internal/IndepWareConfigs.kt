package com.supylc.ylindepware.internal

import android.app.Application
import com.supylc.ylindepware.base.Utils
import com.supylc.ylindepware.custom.ConfigProvider

/**
 * Created by Supylc on 2020/10/10.
 */
object IndepWareConfigs : IConfig by ConfigProvider() {

    private var mApp: Application? = null

    /**
     * 是否主进程
     */
    private var mIsMainProcess = false
    /**
     * 是否Activity子进程
     */
    private var mIsSubActivityProcess = false

    fun setApp(app: Application) {
        if (mApp == null) {
            mApp = app
            val processName = Utils.getProcessName()
            mIsMainProcess = app.packageName == processName
            mIsSubActivityProcess = "${app.packageName}:${getActivityProcessName()}" == processName
        }
    }

    fun getApp(): Application {
        return mApp!!
    }

    fun isMainProcess(): Boolean {
        return mIsMainProcess
    }

    fun isSubActivityProcess(): Boolean {
        return mIsSubActivityProcess
    }

}