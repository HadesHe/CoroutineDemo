package com.huami.coroutinedemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.huami.coroutinedemo.lazyinterface.ILifecycleDelegate
import com.huami.coroutinedemo.lazyinterface.LogLifecycleDelegate

class MainActivity : AppCompatActivity(), ILifecycleDelegate by LogLifecycleDelegate() {
//    val client by lazy {
//        OkHttpClient.Builder().addInterceptor(LogIntercept()).build()
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        registerDelegate(this)

//        lifecycleScope.launch(Dispatchers.IO) {
//
//            val request = Request.Builder()
//                .url("https://www.baidu.com")
//                .build()
//            // 同步请求
//            val result = client.newCall(request).execute()
//
//            //异步请求
//            client.newCall(request).enqueue(object : Callback {
//                override fun onFailure(call: Call, e: IOException) {
//                    TODO("Not yet implemented")
//                }
//
//                override fun onResponse(call: Call, response: Response) {
//                    TODO("Not yet implemented")
//                }
//
//            })
//
//        }

    }
}