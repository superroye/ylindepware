package com.supylc.ylindepware.sub.main

import android.os.IBinder
import com.supylc.ylindepware.MainInterface
import com.supylc.ylindepware.IEventBusCallback
import com.supylc.ylindepware.custom.MainInterfaceDelegate
import com.supylc.ylindepware.internal.IndepWareProcessor

/**
 * Created by Supylc on 2020/10/13.
 * 子进程调用的Service处理类
 * 业务方需要重写该类：
 * 1、需要业务方手动调用mDelegate
 * 2、registerEventCallback
 */
class MainInterfaceStub : MainInterface.Stub(), MainInterface by MainInterfaceDelegate() {

    companion object {
        private const val TAG = "MainInterfaceStub"
    }

    /**
     * 注意，此处不能删
     */
    override fun asBinder(): IBinder {
        return super.asBinder()
    }

    override fun registerEventCallback(callback: IEventBusCallback) {
        IndepWareProcessor.setMainEventBusCallback(callback)
    }
}