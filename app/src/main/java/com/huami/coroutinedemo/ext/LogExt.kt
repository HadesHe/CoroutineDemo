package com.huami.coroutinedemo.ext

import android.util.Log

/**
 * @date 2022/1/13
 * @author hezhanghe@zepp.com
 * @desc
 */
private val TAG = "commonLog"
fun commonLog(tag: String = TAG, block: (Unit) -> String) {
    Log.d(tag, block.invoke(Unit))
}