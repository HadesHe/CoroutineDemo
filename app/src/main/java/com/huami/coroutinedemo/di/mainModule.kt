package com.huami.coroutinedemo.di

import com.huami.coroutinedemo.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * @date 2021/12/10
 * @author hezhanghe@zepp.com
 * @desc
 */
val mainModule = module {

    viewModel {
        MainViewModel()
    }
}