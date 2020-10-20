package com.supylc.ylindepware.demo

import android.os.Bundle
import android.util.Log
import android.view.View
import com.supylc.ylindepware.IndepWare
import com.supylc.ylindepware.base.EventUtils
import com.supylc.ylindepware.base.Utils
import com.supylc.ylindepware.custom.test.Sub2MainEvent1

/**
 * Created by Supylc on 2020/10/10.
 */
class WebMainProcessActivity : WebBaseProcessActivity() {

    companion object {
        const val TAG = "WebMainProcessActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_web_main)
        Log.d(TAG, "onCreate")
        IndepWare.startOnActivityCreated(this)
    }

    fun test1(view: View) {
        Log.d(TAG, "test1 process=${ Utils.getProcessName()}")
        //IndepWare.getMainInterface()?.test("WebMain")
        EventUtils.sendEvent(Sub2MainEvent1())
    }

}