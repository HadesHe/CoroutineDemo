package com.huami.coroutinedemo.lazyinterface

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner

/**
 * @date 2022/4/12
 * @author hezhanghe@zepp.com
 * @desc
 */
interface ILifecycleDelegate {
    fun registerDelegate(owner: LifecycleOwner)
}

class LogLifecycleDelegate : ILifecycleDelegate, DefaultLifecycleObserver {
    override fun registerDelegate(owner: LifecycleOwner) {
        owner.lifecycle.addObserver(this)
    }

    override fun onDestroy(owner: LifecycleOwner) {
        super.onDestroy(owner)
        owner.lifecycle.removeObserver(this)
    }

    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)
    }
}