package com.supylc.ylindepware.internal

import android.app.Activity
import android.os.IBinder
import com.supylc.ylindepware.IEventBusCallback
import com.supylc.ylindepware.MainInterface

/**
 * Created by Supylc on 2020/10/10.
 */
object IndepWareProcessor {

    private val mSubProcessorInner = SubProcessorInner()
    private val mMainProcessorInner = MainProcessorInner()

    fun getMainInterface(): MainInterface? {
        if (IndepWareConfigs.isMainProcess()) {
            return mMainProcessorInner.getMainInterface()
        }
        return mSubProcessorInner.getMainServiceBinder()
    }

    fun init(context: Activity) {
        IndepWareConfigs.setApp(context.application)

        if (!IndepWareConfigs.isMainProcess()) {
            mSubProcessorInner.startMainServiceBind(context)
        } else {
            mMainProcessorInner.initMainInterface()
        }
    }

    fun setMainEventBusCallback(eventBusCallback: IEventBusCallback) {
        mMainProcessorInner.setMainEventBusCallback(eventBusCallback)
    }

    fun newMainInterfaceStub(): IBinder {
        return mMainProcessorInner.newMainInterfaceStub()
    }

    fun tryStopMainServiceBind(activity: Activity) {
        mSubProcessorInner.tryStopMainServiceBind(activity)
    }

    fun setMainServiceBinder(serviceBinder: MainInterface) {
        mSubProcessorInner.setMainServiceBinder(serviceBinder)
    }

    fun sendEvent(event: Any) {
        if (IndepWareConfigs.isMainProcess()) {
            return mMainProcessorInner.sendEvent(event)
        }
        return mSubProcessorInner.sendEvent(event)
    }

    /**
     * 子进程退出时，释放主进程的EventBus监听
     */
    fun releaseOnUnBind() {
        mMainProcessorInner.releaseOnUnBind()
    }

}