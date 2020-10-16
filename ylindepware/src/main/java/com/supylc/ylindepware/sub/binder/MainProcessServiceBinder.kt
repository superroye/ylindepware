package com.supylc.ylindepware.sub.binder

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import android.os.IBinder.DeathRecipient
import android.os.IInterface
import android.os.RemoteException
import android.util.Log
import com.supylc.ylindepware.MainInterface
import com.supylc.ylindepware.internal.IndepWareProcessor
import com.supylc.ylindepware.sub.main.MainProcessService
import java.lang.reflect.InvocationHandler
import java.lang.reflect.Method
import java.lang.reflect.Proxy

/**
 * Created by Supylc on 2020/10/12.
 * 子进程binder，连接主进程
 */
class MainProcessServiceBinder {

    companion object {
        const val TAG = "MainProcessBinder"
    }

    private var mMainInterface: MainInterface? = null
    private var mMainInterfaceDelegate: MainInterface? = null

    fun bind(activity: Context) {
        if (mMainInterface != null && mMainInterface?.asBinder()?.isBinderAlive == true) {
            return
        }
        val intent = Intent(activity, MainProcessService::class.java)
        intent.action = MainProcessService.ACTION_BIND
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
                        "onServiceConnected asBinder=${mMainInterface?.asBinder() == null}, mDeathRecipient is null=${mDeathRecipient == null}",
                    )
                } catch (e: RemoteException) {
                    Log.e(TAG, "onServiceConnected RemoteException")
                }

                /**
                 * 动态代理方式全局捕获异常
                 */
                val serviceClass = MainInterface::class.java
                mMainInterfaceDelegate = Proxy.newProxyInstance(
                    serviceClass.classLoader,
                    arrayOf(serviceClass),
                    ServiceInvokeHandler(mMainInterface!!)
                ) as MainInterface
                IndepWareProcessor.setMainServiceBinder(mMainInterfaceDelegate!!)
                /**
                 * 设置事件回调接口
                 */
                mMainInterfaceDelegate?.registerEventCallback(EventBusCallbackBinder())
            }
        }

        override fun onServiceDisconnected(name: ComponentName) {
            Log.i(
                TAG,
                "onServiceDisconnected name=${name}",
            )
            mMainInterface = null
        }

        override fun onBindingDied(name: ComponentName) {
            Log.i(
                TAG,
                "onBindingDied name=${name}",
            )
        }
    }

    private val mDeathRecipient: DeathRecipient? = object : DeathRecipient {
        override fun binderDied() {
            if (mMainInterface == null) {
                return
            }
            Log.w(
                TAG,
                "DeathRecipient binderDied"
            )
            if (mMainInterface?.asBinder() != null) {
                mMainInterface?.asBinder()?.unlinkToDeath(this, 0)
            }
            mMainInterface = null
        }
    }

    class ServiceInvokeHandler(serviceInterface: IInterface) : InvocationHandler {

        private var mServiceInterface: IInterface? = serviceInterface

        override fun invoke(proxy: Any?, method: Method?, args: Array<out Any>?): Any? {
            var result: Any? = null
            try {
                Log.i(
                    TAG,
                    "method=${method?.name}"
                )
                result = if (args == null) {
                    method?.invoke(mServiceInterface)
                } else {
                    method?.invoke(mServiceInterface, *args)
                }
            } catch (e: RemoteException) {
                Log.w(
                    TAG,
                    "ServiceInvokeHandler error!",
                    e
                )
            }
            return result
        }
    }
}