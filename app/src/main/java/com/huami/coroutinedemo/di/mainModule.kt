package com.huami.coroutinedemo.di

import com.huami.coroutinedemo.MainViewModel
import com.huami.coroutinedemo.coroutine.CustomCoroutine
import com.huami.coroutinedemo.flow.FlowViewModel
import kotlinx.coroutines.CoroutineScope
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import org.koin.java.KoinJavaComponent

/**
 * @date 2021/12/10
 * @author hezhanghe@zepp.com
 * @desc
 */

internal const val MAIN_SCOPE = "MAIN_SCOPE"
val mainModule = module {

    viewModel {
        MainViewModel()
    }

    viewModel {
        FlowViewModel()
    }

    scope(named(MAIN_SCOPE)) {
        scoped<CoroutineScope> {
            CustomCoroutine()
        }
    }
}

fun getMainScope() = KoinJavaComponent.getKoin()
    .getOrCreateScope(MAIN_SCOPE, named(MAIN_SCOPE))