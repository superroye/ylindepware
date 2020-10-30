package com.supylc.ylindepware.demo.custom

import android.util.Log

/**
 * Created by Supylc on 2020/10/13.
 *
 * 该类运行在主进程
 * 业务方需要重写该类：
 * 1、定义所需的方法
 * 2、sendEvent方法必须保留
 */
class MainServiceInterfaceImpl : MainServiceInterface {

    companion object {
        const val TAG = "MainInterfaceDelegate"
    }

    override fun test(id: Int, name: String?) {
        Log.d(TAG, "test id=$id name=$name")
    }

    override fun testReturn(id: Int, name: String?): String {
        Log.d(TAG, "testReturn id=$id name=$name")
        return "good"
    }

    override fun testMap(id: Int, map: Map<String, String>?): String {
        Log.d(TAG, "testReturn id=$id map=$map")
        return "good"
    }

    override fun testList(id: Int, list: List<String>?): String {
        Log.d(TAG, "testReturn id=$id list=$list")
        return "good"
    }
}