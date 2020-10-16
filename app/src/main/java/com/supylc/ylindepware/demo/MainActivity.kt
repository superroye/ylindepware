package com.supylc.ylindepware.demo

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


    }

    fun test1(view: View) {
        val intent = Intent(this, RouteActivity::class.java)
        intent.data = Uri.parse("http://www.baidu.com/sub")
        startActivity(intent)
    }

    fun test2(view: View) {
        val intent = Intent(this, RouteActivity::class.java)
        intent.data = Uri.parse("http://www.baidu.com/main")
        startActivity(intent)
    }
}