package com.huami.coroutinedemo

import android.app.Application
import com.huami.coroutinedemo.di.mainModule
import com.huami.coroutinedemo.ext.workManagerFactory
import com.huami.jetpacksourcemodule.di.workmanagerModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.component.KoinComponent
import org.koin.core.context.startKoin

/**
 * @date 2021/12/10
 * @author hezhanghe@zepp.com
 * @desc
 */
class MainApp : Application(), KoinComponent {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@MainApp)

//            workManagerFactory()
            modules(mainModule, workmanagerModule)
        }


    }
}