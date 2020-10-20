package com.supylc.ylindepware.sub.main

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import com.supylc.ylindepware.internal.IndepWareProcessor

/**
 * Created by Supylc on 2020/10/10.
 */
class MainProcessService : Service() {

    companion object {
        private const val TAG = "MainProcessService"
    }

    private var mStub: Any? = null

    override fun onBind(intent: Intent?): IBinder {
        Log.d(TAG, "onBind")
        if (mStub == null) {
            mStub = IndepWareProcessor.newMainInterfaceStub()
        }
        return mStub as IBinder
    }

    override fun onUnbind(intent: Intent?): Boolean {
        Log.d(TAG, "onUnbind")
        stopSelf()
        if (mStub != null) {
            mStub = null
        }
        IndepWareProcessor.releaseOnUnBind()
        return super.onUnbind(intent)
    }

}