package com.huami.jetpacksourcemodule.di

import com.huami.jetpacksourcemodule.service.IWorkManagerService
import com.huami.jetpacksourcemodule.service.WorkManagerServiceImpl
import org.koin.android.ext.koin.androidContext
import org.koin.core.qualifier.named
import org.koin.dsl.module
import org.koin.java.KoinJavaComponent.getKoin

/**
 * @date 2022/2/10
 * @author hezhanghe@zepp.com
 * @desc
 */
internal const val WORKER_MANAGER_SCOPE = "WORKER_MANAGER_SCOPE"
val workmanagerModule = module {

    scope(named(WORKER_MANAGER_SCOPE)) {
        scoped<IWorkManagerService> {
            WorkManagerServiceImpl(androidContext())
        }
    }
}

fun getWorkerScope() = getKoin().getOrCreateScope(WORKER_MANAGER_SCOPE, named(WORKER_MANAGER_SCOPE))