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

    fun getMainInterface(): MainInterface {
        if (IndepWareConfigs.isMainProcess()) {
            return mMainProcessorInner.getMainInterface()
        }
        return mSubProcessorInner.getMainServiceBinder()
    }

    fun initMainInterface(context: Activity) {
        if (!IndepWareConfigs.isMainProcess()) {
            mSubProcessorInner.startMainServiceBind(context)
        } else {
            mMainProcessorInner.initMainInterface()
        }
    }

    /**
     * 设置主进程监听eventBus
     */
    fun setMainEventBusCallback(eventBusCallback: IEventBusCallback) {
        mMainProcessorInner.setMainEventBusCallback(eventBusCallback)
    }

    /**
     * 创建MainInterfaceStub
     */
    fun newMainInterfaceStub(): IBinder {
        return mMainProcessorInner.newMainInterfaceStub()
    }

    /**
     * activity退出调用
     */
    fun tryStopMainServiceBind(activity: Activity) {
        mSubProcessorInner.tryStopMainServiceBind(activity)
    }

    /**
     * 连接主进程服务成功后，设置本地interface
     */
    fun setMainServiceBinder(serviceBinder: MainInterface) {
        mSubProcessorInner.setMainServiceBinder(serviceBinder)
    }

    /**
     * 当前进程发送事件
     */
    fun sendEvent(event: Any) {
        if (IndepWareConfigs.isMainProcess()) {
            return mMainProcessorInner.sendEvent(event)
        }
        return mSubProcessorInner.sendEvent(event)
    }

    /**
     * 子进程已连接主进程服务
     */
    fun isServiceReady(): Boolean {
        return mSubProcessorInner.isServiceReady()
    }

    /**
     * 子进程退出时，释放主进程的EventBus监听
     */
    fun releaseOnUnBind() {
        mMainProcessorInner.releaseOnUnBind()
    }

}