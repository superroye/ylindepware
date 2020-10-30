package com.supylc.ylindepware.internal

import android.app.Application
import com.supylc.ylindepware.IndepWareOptions
import com.supylc.ylindepware.base.IndepWareConfigBuilder
import com.supylc.ylindepware.base.Utils

/**
 * Created by Supylc on 2020/10/10.
 */
object IndepWareContext {

    private var mApp: Application? = null
    private var mIndepWareConfig: IndepWareConfig? = null

    /**
     * 是否主进程
     */
    private var mIsMainProcess = false

    /**
     * 是否Activity子进程
     */
    private var mIsSubActivityProcess = false

    fun setApp(app: Application, options: IndepWareOptions) {
        if (mApp == null) {
            mApp = app

            val builder = IndepWareConfigBuilder()
            options.apply(builder)
            val config = builder.build()
            mIndepWareConfig = config

            val processName = Utils.getProcessName()
            mIsMainProcess = app.packageName == processName
            mIsSubActivityProcess = "${app.packageName}:${
                config.configCallback.getActivityProcessName()
            }" == processName

            if (isSubActivityProcess()) {
                config.configCallback.initSubCallback()
            }
        }
    }

    fun getApp(): Application {
        return mApp!!
    }

    fun isMainProcess(): Boolean {
        return mIsMainProcess
    }

    private fun isSubActivityProcess(): Boolean {
        return mIsSubActivityProcess
    }

    fun <T> getMainInterfaceImpl(clazz: Class<T>): T {
        return mIndepWareConfig!!.interfaces[clazz] as T
    }

    fun getEventBusListenerInterfaceClass(): Class<*> {
        return mIndepWareConfig!!.eventBusListenerInterface
    }

    fun getConfigCallback(): IConfigCallback {
        return mIndepWareConfig!!.configCallback
    }

}