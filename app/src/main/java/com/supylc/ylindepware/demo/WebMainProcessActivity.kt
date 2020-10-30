package com.supylc.ylindepware.demo

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.collection.ArrayMap
import com.supylc.ylindepware.IndepWare
import com.supylc.ylindepware.base.EventUtils
import com.supylc.ylindepware.base.Utils
import com.supylc.ylindepware.demo.custom.MainServiceInterface
import com.supylc.ylindepware.demo.custom.test.Sub2MainEvent1

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

        var result:String? = ""
        IndepWare.getMainInterface(MainServiceInterface::class.java).test(1, "test")
        result = IndepWare.getMainInterface(MainServiceInterface::class.java).testList(2, ArrayList<String>().also {
            it.add("11")
            it.add("22")
        })
        Log.d(WebSubProcessActivity.TAG, "testList result=$result")
        result = IndepWare.getMainInterface(MainServiceInterface::class.java).testMap(3, ArrayMap<String, String>().also {
            it["name"] = "cyl"
        })
        Log.d(WebSubProcessActivity.TAG, "testMap result=$result")
        result = IndepWare.getMainInterface(MainServiceInterface::class.java).testReturn(4, "return")
        Log.d(WebSubProcessActivity.TAG, "testReturn result=$result")
    }

}