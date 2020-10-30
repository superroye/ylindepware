package com.supylc.ylindepware.sub.binder

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import android.os.IBinder.DeathRecipient
import android.os.RemoteException
import android.util.Log
import com.supylc.ylindepware.MainInterface
import com.supylc.ylindepware.internal.IndepWareContext
import com.supylc.ylindepware.internal.IndepWareProcessor
import com.supylc.ylindepware.sub.main.MainProcessService

/**
 * Created by Supylc on 2020/10/12.
 * 子进程binder，连接主进程
 */
class MainProcessServiceBinder {

    companion object {
        const val TAG = "MainProcessBinder"
    }

    private var mMainInterface: MainInterface? = null

    fun bind(activity: Context) {
        if (mMainInterface != null && mMainInterface?.asBinder()?.isBinderAlive == true) {
            return
        }
        val intent = Intent(activity, MainProcessService::class.java)
        intent.action = IndepWareContext.getConfigCallback().getMainServiceBindAction()
        activity.bindService(intent, mServiceConnection, Context.BIND_AUTO_CREATE)
    }

    fun exitProcess() {
        Log.i(TAG, "exitProcess")
        kotlin.system.exitProcess(1)
    }

    private val mServiceConnection: ServiceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName, service: IBinder) {
            Log.i(TAG, "onServiceConnected")

            mMainInterface = MainInterface.Stub.asInterface(service)
            if (mMainInterface != null) {
                try {
                    if (mMainInterface?.asBinder() != null && mDeathRecipient != null) {
                        mMainInterface?.asBinder()?.linkToDeath(mDeathRecipient, 0)
                    }
                    Log.i(
                        TAG,
                        "onServiceConnected asBinder=${mMainInterface?.asBinder() == null}, mDeathRecipient is null=${mDeathRecipient == null}"
                    )
                } catch (e: RemoteException) {
                    Log.e(TAG, "onServiceConnected RemoteException")
                }

                IndepWareProcessor.setMainServiceBinder(mMainInterface!!)
                /**
                 * 设置事件回调接口
                 */
                try {
                    mMainInterface?.registerEventCallback(EventBusCallbackBinder())
                } catch (ex: Throwable) {
                    Log.e(TAG, "registerEventCallback error", ex)
                }
            }
        }

        override fun onServiceDisconnected(name: ComponentName) {
            Log.i(TAG, "onServiceDisconnected name=${name}")
            mMainInterface = null
        }

        override fun onBindingDied(name: ComponentName) {
            Log.i(TAG, "onBindingDied name=${name}")
        }
    }

    private val mDeathRecipient: DeathRecipient? = object : DeathRecipient {
        override fun binderDied() {
            if (mMainInterface == null) {
                return
            }
            Log.w(TAG, "DeathRecipient binderDied")
            if (mMainInterface?.asBinder() != null) {
                mMainInterface?.asBinder()?.unlinkToDeath(this, 0)
            }
            mMainInterface = null
        }
    }
}