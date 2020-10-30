package com.supylc.ylindepware;
import com.supylc.ylindepware.IEventBusCallback;
import com.supylc.ylindepware.MethodInvoker;
/**
* （注意：该接口名固定不能改）
*/
interface MainInterface {

    /**
    * 万能的方法
    */
    String intentMethod(String interfaceClassName, in MethodInvoker methodInvoker);

    /**
    * 设置回调接口----（注意：该函数固定不能改）
    */
    void registerEventCallback(IEventBusCallback callback);

    /**
    * 当前进程（主/子进程）发送事件，转发到主进程----（注意：该函数固定不能改）
    */
    void sendEvent(String className, String gson);
}