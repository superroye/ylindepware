package com.supylc.ylindepware.demo.custom

import com.supylc.ylindepware.demo.custom.test.Main2SubEvent1
import org.greenrobot.eventbus.Subscribe

/**
 * Created by Supylc on 2020/10/14.
 * 该类运行在主进程
 * 主进程需要转发到子进程的Event
 * 需要做两件事：
 * 1、定制监听（Subscribe）的Event类型
 * 2、调用eventListener.sendEvent(event)转发到子进程
 */
interface EventBusListenerDelegate {

    @Subscribe
    fun test(event: Main2SubEvent1)
}