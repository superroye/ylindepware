package com.supylc.ylindepware.demo.custom

/**
 * Created by Supylc on 2020/10/26.
 */
interface MainServiceInterface {

    fun test(id: Int, name: String?)

    fun testReturn(id: Int, name: String?): String

    fun testMap(id: Int, map: Map<String, String>?): String

    fun testList(id: Int, list: List<String>?): String
}