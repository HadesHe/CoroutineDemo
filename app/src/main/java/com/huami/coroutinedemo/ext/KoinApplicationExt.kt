package com.huami.coroutinedemo.ext

import androidx.work.Configuration
import androidx.work.DelegatingWorkerFactory
import androidx.work.WorkManager
import com.huami.coroutinedemo.factory.KoinWorkerFactory
import org.koin.core.KoinApplication

/**
 * @date 2021/12/10
 * @author hezhanghe@zepp.com
 * @desc
 */
fun KoinApplication.workManagerFactory() {
    createWorkManagerFactory()
}

private fun KoinApplication.createWorkManagerFactory() {

    val factory = DelegatingWorkerFactory()
        .apply {
            addFactory(KoinWorkerFactory())
        }

    val conf = Configuration.Builder()
        .setWorkerFactory(factory)
        .build()
    WorkManager.initialize(koin.get(), conf)

}
