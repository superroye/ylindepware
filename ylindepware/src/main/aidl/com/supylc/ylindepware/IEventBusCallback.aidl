package com.supylc.ylindepware;

/**
* 监听主进程事件，转发到子进程
* （注意：该接口固定不能改）
*/
interface IEventBusCallback {
    void onEvent(String className, String gson);
}