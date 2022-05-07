package com.huami.coroutinedemo.coroutine

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlin.coroutines.CoroutineContext

/**
 * @date 2022/5/7
 * @author hezhanghe@zepp.com
 * @desc
 */
class CustomCoroutine : CoroutineScope {

    private val context by lazy {
        SupervisorJob() + Dispatchers.IO
    }

    override val coroutineContext: CoroutineContext
        get() = context
}
