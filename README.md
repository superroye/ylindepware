
### 组件子进程独立运行适配方案

### 改造背景
- 目前公司应用使用腾讯X5浏览器，但会按条件判断，使用不同策略打开url（原生内核 or X5内核）
- 在android5x,6x有较多的崩溃现象，虽然升级x5浏览器得到了改善
- 内存泄漏问题或native报错，导致的整个应用闪退，体验不好
希望通过WebView子进程化改造，达到减少崩溃和OOM问题发生。

### 支持功能
- activity进程化，比如可以用在WebViewActivity的子进程运行，也可以同时在主进程运行
- 本模块支持多进程调用Activity，不限于WebViewActivity
- 支持调用外部的主进程接口
- 支持发送到主进程，或接收主进程的EventBus

比如在WebActivity需要子进程打开的场景，一个url可以随时选择主进程或子进程打开Activity，而对业务方调用是一致的

### 使用步骤

##### 移植步骤

- 拷贝模块到应用目录
- 重新定义MainInterface的aidl文件（子进程调用的主进程接口）
- 实现MainInterfaceDelegate
- 定义EventBusListenerDelegate要监听的Event
- 定义ConfigProvider
- MainProcessService有没有注册manifest, 以及检查action与ConfigProvider配置是否一致
- 子进程化的Activity需要在manifest注册, 并设置process, 注意process与ConfigProvider配置是否一致

**备注**：要修改的地方集中在：aidl目录的aidl文件，包custom目录的类，manifest.xml


##### 调用方法步骤
- application初始化时调用IndepWare.init(app)
- activity启动时调用IndepWare.startOnActivityCreated(activity)
- activity关闭时调用activity启动时调用IndepWarestopOnActivityDestroyed(activity)
- 调用外部接口时，用IndepWare.getMainInterface().method1()
- 发送eventBus时，用IndepWar.sendEvent(event)

### 注意点
- 调用IndepWare.getMainInterface().method1()时，java编译器提示必须捕获异常，可以转成kotlin，kotlin无须捕获
- 注意事件需要有无参构造方法，否则会到只事件转发失败
