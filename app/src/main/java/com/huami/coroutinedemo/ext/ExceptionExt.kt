package com.huami.coroutinedemo.ext

import java.lang.Exception

/**
 * @date 2021/12/16
 * @author hezhanghe@zepp.com
 * @desc
 */

object ExceptionExt {

    @JvmStatic
    fun printMethodTrace(tag:String){
        val trace= Exception(tag)
        trace.printStackTrace()
    }

}
