package com.supylc.ylindepware.custom

import androidx.collection.ArraySet
import com.supylc.ylindepware.custom.test.Sub2MainEvent1
import com.supylc.ylindepware.custom.test.Sub2MainEvent2
import com.supylc.ylindepware.internal.IConfig
import org.greenrobot.eventbus.EventBus

/**
 * Created by Supylc on 2020/10/16.
 */
class ConfigProvider : IConfig {

    companion object {
        //连接的主进程Service的Action，主要必须与manifest配置一致
        private const val SERVICE_ACTION_BIND =
            "com.supylc.webindependent.sub.mainprocess.BIND_MAIN_SERVICE"
    }

    private val mEventClassSet = ArraySet<Class<*>>().also {
        it.add(Sub2MainEvent1::class.java)
        it.add(Sub2MainEvent2::class.java)
    }

    /**
     * 子进程会根据配置的Event类型列表判断是否转发到主进程
     */
    override fun needSendMainEventFromSub(clazz: Class<*>): Boolean {
        return mEventClassSet.contains(clazz)
    }

    override fun registerEventSubscriber(subscriber: Any) {
        EventBus.getDefault().register(subscriber)
    }

    override fun unregisterEventSubscriber(subscriber: Any) {
        EventBus.getDefault().unregister(subscriber)
    }

    override fun sendEvent(event: Any) {
        EventBus.getDefault().post(event)
    }

    override fun getMainServiceBindAction(): String {
        return SERVICE_ACTION_BIND
    }

}