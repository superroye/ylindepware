package com.supylc.ylindepware.internal

import android.os.IBinder
import android.util.Log
import com.supylc.ylindepware.IEventBusCallback
import com.supylc.ylindepware.base.EventUtils
import com.supylc.ylindepware.sub.main.MainEventBusListener
import com.supylc.ylindepware.sub.main.MainInterfaceStub

/**
 * Created by Supylc on 2020/10/13.
 */
internal class MainProcessorInner {

    companion object {
        private const val TAG = "MainProcessorInner"
    }

    private var mMainEventBusListener: MainEventBusListener? = null

    fun initMainInterface() {
        if (IndepWareContext.isMainProcess()) {
            Log.i(TAG, "initMainInterface")
        }
    }

    fun newMainInterfaceStub(): IBinder {
        return MainInterfaceStub()
    }

    fun <T> getMainInterface(clazz: Class<T>): T {
        return IndepWareContext.getMainInterfaceImpl(clazz)
    }

    /**
     * 打开主进程监听并设置event回调
     */
    fun setMainEventBusCallback(eventBusCallback: IEventBusCallback) {
        Log.i(TAG, "setMainEventBusCallback")
        if (mMainEventBusListener == null) {
            mMainEventBusListener = MainEventBusListener()
        }
        mMainEventBusListener?.setEventCallback(eventBusCallback)
    }

    /**
     * 当前进程发送事件
     */
    fun sendEvent(event: Any) {
        EventUtils.sendEvent(event)
    }

    /**
     * unbind时需要释放主进程监听
     */
    fun releaseOnUnBind() {
        Log.i(TAG, "releaseOnUnBind")
        mMainEventBusListener?.onDestroy()
        mMainEventBusListener = null
    }
}