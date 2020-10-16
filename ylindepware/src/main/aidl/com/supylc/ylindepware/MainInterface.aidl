// MainInterface.aidl
package com.supylc.ylindepware;
import com.supylc.ylindepware.IEventBusCallback;
// Declare any non-default types here with import statements
/**
* （注意：该接口名固定不能改）
*/
interface MainInterface {

    /**
    * 自定义方法, 自由定制
    */
    void test(String param);

    /**
    * 设置回调接口----（注意：该函数固定不能改）
    */
    void registerEventCallback(IEventBusCallback callback);

    /**
    * 当前进程（主/子进程）发送事件，转发到主进程----（注意：该函数固定不能改）
    */
    void sendEvent(String className, String gson);
}