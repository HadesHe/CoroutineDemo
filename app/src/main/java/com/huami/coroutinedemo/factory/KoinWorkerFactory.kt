package com.huami.coroutinedemo.factory

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import org.koin.core.component.KoinComponent
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.named

/**
 * @date 2021/12/10
 * @author hezhanghe@zepp.com
 * @desc
 */
class KoinWorkerFactory : WorkerFactory(), KoinComponent {
    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        workerParameters: WorkerParameters
    ): ListenableWorker? {
        return getKoin().getOrNull(qualifier = named(workerClassName)) {
            parametersOf(workerParameters)
        }
    }
}