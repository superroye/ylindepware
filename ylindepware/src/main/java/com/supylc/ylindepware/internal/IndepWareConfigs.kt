package com.supylc.ylindepware.internal

import android.app.Application
import com.supylc.ylindepware.base.Utils
import com.supylc.ylindepware.custom.ConfigProvider

/**
 * Created by Supylc on 2020/10/10.
 */
object IndepWareConfigs : IConfig by ConfigProvider() {

    private var mApp: Application? = null
    private var mIsMainProcess = false

    fun setApp(app: Application) {
        if (mApp == null) {
            mApp = app
            mIsMainProcess = Utils.isMainProcess()
        }
    }

    fun getApp(): Application {
        return mApp!!
    }

    fun isMainProcess(): Boolean {
        return mIsMainProcess
    }

}