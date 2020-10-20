package com.supylc.ylindepware.demo

import android.app.Activity
import android.app.Application
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.supylc.ylindepware.IndepWare
import com.supylc.ylindepware.base.EventUtils
import com.supylc.ylindepware.custom.test.Sub2MainEvent1
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

/**
 * Created by Supylc on 2020/10/12.
 */
class MyApp : Application() {

    companion object {
        lateinit var context: Application
        const val TAG = "Application"
    }

    var topAct: AppCompatActivity? = null

    override fun onCreate() {
        super.onCreate()

        context = this
        this.registerActivityLifecycleCallbacks(object : ActivityLifecycleCallbacks {
            override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
                Log.d(TAG, "======activity=$activity")

            }

            override fun onActivityStarted(activity: Activity) {}
            override fun onActivityResumed(activity: Activity) {
                Log.d(TAG, "======onActivityResumed=$activity")
                topAct = activity as AppCompatActivity
            }
            override fun onActivityPostResumed(activity: Activity) {}
            override fun onActivityPaused(activity: Activity) {}
            override fun onActivityStopped(activity: Activity) {}
            override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {}
            override fun onActivityDestroyed(activity: Activity) {}
        })
        EventUtils.register(this)

        IndepWare.init(this)
    }

}