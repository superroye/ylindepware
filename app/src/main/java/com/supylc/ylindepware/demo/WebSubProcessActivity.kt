package com.supylc.ylindepware.demo

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import com.supylc.ylindepware.IndepWare
import com.supylc.ylindepware.base.Utils
import com.supylc.ylindepware.custom.test.Main2SubEvent1
import com.supylc.ylindepware.custom.test.Sub2MainEvent1
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

/**
 * Created by Supylc on 2020/10/10.
 */
class WebSubProcessActivity : WebBaseProcessActivity() {

    companion object {
        const val TAG = "WebSubProcessActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_web_sub)
        EventBus.getDefault().register(this)
        Log.d(TAG, "onCreate")
        IndepWare.startOnActivityCreated(this)
    }

    fun test1(view: View) {
        Log.d(TAG, "test1 process=${ Utils.getProcessName()}")
        IndepWare.sendEvent(Main2SubEvent1(2223))
        IndepWare.sendEvent(Sub2MainEvent1(1222))
    }

    fun testJump(view: View) {
        val intent = Intent(this, RouteActivity::class.java)
        intent.data = Uri.parse("http://www.baidu.com/sub")
        startActivity(intent)
    }

    @Subscribe
    fun test(event: Main2SubEvent1) {
        Log.d(TAG, "sub event=${event}")
    }

    override fun onDestroy() {
        super.onDestroy()

        IndepWare.stopOnActivityDestroyed(this)
        EventBus.getDefault().unregister(this)
    }
}