package com.supylc.ylindepware.demo

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.supylc.ylindepware.custom.test.Main2SubEvent1
import org.greenrobot.eventbus.EventBus

/**
 * Created by Supylc on 2020/10/10.
 */
class RouteActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "RouteActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val uri = intent.data

        Log.d(TAG, "uri====${uri.toString()}")
        if (uri?.host == "abc.cn") {
            startActivity(Intent(this, TestJumpActivity::class.java))
            return
        }

        if (uri.toString().endsWith("sub")) {
            val intent = Intent(this, WebSubProcessActivity::class.java)
            intent.putExtra(WebBaseProcessActivity.KEY_URL, uri.toString())
            startActivity(intent)

            Handler().postDelayed({
                EventBus.getDefault().post(Main2SubEvent1(111))
            }, 3000)

            finish()
        } else {
            val intent = Intent(this, WebMainProcessActivity::class.java)
            intent.putExtra(WebBaseProcessActivity.KEY_URL, uri.toString())
            startActivity(intent)


            finish()
        }

    }
}