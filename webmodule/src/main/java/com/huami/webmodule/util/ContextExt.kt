package com.huami.webmodule.util

import android.content.Context
import android.content.res.Configuration

/**
 * Created by hezhanghe on 2021/12/26.
 * github: https://github.com/HadesHe
 */
fun Context.injectVConsoleJs(): String? {
    return try {
        val vconsoleJs = resources.assets.open("js/vconsole.min.js").use {
            val buffer = ByteArray(it.available())
            it.read(buffer)
            String(buffer)
        }
        """ $vconsoleJs
                 var vConsole = new VConsole();
            """.trimIndent()
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}

fun Context.isNightMode(): Boolean {
    val config = resources.configuration
    val uiMode = config.uiMode and Configuration.UI_MODE_NIGHT_MASK
    return uiMode == Configuration.UI_MODE_NIGHT_YES
}