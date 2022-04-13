package com.huami.coroutinedemo.lazyinterface

import android.util.Log
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import java.lang.NullPointerException

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

interface ICoroutineScopeDelegate {
    fun registerCoroutineScope(owner: LifecycleOwner, coroutineScope: CoroutineScope)
}

class FlowLifecycleDelegate() : ICoroutineScopeDelegate, DefaultLifecycleObserver {

    private var coroutineScope: CoroutineScope? = null
    private var job: Job? = null

    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        Log.d("CoroutineException", "$throwable")
    }

    override fun registerCoroutineScope(owner: LifecycleOwner, coroutineScope: CoroutineScope) {
        this.coroutineScope =
            CoroutineScope(coroutineScope.coroutineContext + SupervisorJob() + coroutineExceptionHandler)
        owner.lifecycle.addObserver(this)
    }

    override fun onStart(owner: LifecycleOwner) {
        super.onStart(owner)
        job = coroutineScope?.launch {
//
//            flow { emit(Unit) }.collect {}

            throw NullPointerException()
        }
    }

    override fun onStop(owner: LifecycleOwner) {
        super.onStop(owner)
        job?.cancel()
    }

    override fun onDestroy(owner: LifecycleOwner) {
        super.onDestroy(owner)
        owner.lifecycle.removeObserver(this)
    }

}