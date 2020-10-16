# ylindepware
activity进程化，比如可以用在WebViewActivity的子进程运行


本模块支持多进程调用Activity

比如在WebActivity需要子进程打开的场景，
一个url可以随时选择主进程或子进程打开Activity，而对业务方调用是一致的

使用步骤：

移植步骤：
1、重新定义MainInterface（子进程调用的主进程接口）
2、实现MainInterfaceDelegate
3、定义EventBusListenerDelegate要监听的Event
4、修改ConfigProvider
5、主要MainProcessService有没有注册manifest，以及检查action是否正确

调用方法步骤：
activity启动时调用IndepWare.startOnActivityCreated(activity)
activity关闭时调用activity启动时调用IndepWarestopOnActivityDestroyed(activity)
调用外部接口时，用IndepWare.getMainInterface().method1()
发送eventBus时，用IndepWar.sendEvent(event)

备注：
要修改的地方集中在：aidl目录的aidl文件，包custom目录的类，manifest.xml
