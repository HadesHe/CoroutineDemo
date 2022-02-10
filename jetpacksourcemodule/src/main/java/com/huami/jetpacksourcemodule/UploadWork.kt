package com.huami.jetpacksourcemodule

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

/**
 * @date 2022/2/10
 * @author hezhanghe@zepp.com
 * @desc
 */
class UploadWork(appContext: Context, workerParameters: WorkerParameters) :
    CoroutineWorker(appContext, workerParameters) {
    override suspend fun doWork(): Result {
        withContext(Dispatchers.IO) {
            delay(3000)
            Log.d("UploadWork", "uploadWork")
        }
        return Result.success()
    }
}