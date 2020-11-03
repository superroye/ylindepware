package com.supylc.ylindepware.base.serialize

import com.supylc.ylindepware.base.utils.GsonUtils.fromJson
import com.supylc.ylindepware.base.utils.GsonUtils.fromJsonAny
import com.supylc.ylindepware.base.utils.GsonUtils.toJson
import java.util.*

/**
 * Created by Supylc on 2020/11/2.
 */
object SerializeConverter {

    /**
     * 方法参数bean序列化成json
     */
    fun methodInvokeToJson(param: SerializeInvokeParam): String {
        val valueJson = toJson(param.value)
        val wrapper = SerializeInvokeParamWrapper(param.clazzName, valueJson)
        return toJson(wrapper);
    }

    /**
     * 方法参数json反序列化成bean
     */
    fun getMethodInvokeList(paramList: List<String>): List<SerializeInvokeParam> {
        var wrapper: SerializeInvokeParamWrapper?
        var clazz: Class<*>?
        val list = ArrayList<SerializeInvokeParam>()
        if (!paramList.isNullOrEmpty()) {
            for (i in paramList.indices) {
                wrapper = fromJson(
                    paramList[i],
                    SerializeInvokeParamWrapper::class.java
                )
                clazz = Class.forName(wrapper!!.clazzName)
                list.add(
                    SerializeInvokeParam(
                        wrapper!!.clazzName, fromJsonAny(
                            wrapper!!.valueJson,
                            clazz!!
                        )
                    )
                )
            }
        }
        return list
    }

    fun getInvokeBeanFromJson(json: String): Any? {
        val wrapper = fromJson(json, SerializeInvokeParamWrapper::class.java)
        val clazz = Class.forName(wrapper!!.clazzName)
        return fromJsonAny(wrapper!!.valueJson, clazz)
    }
}