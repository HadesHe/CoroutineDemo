package com.huami.jetpacksourcemodule.service

import android.content.Context
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.huami.jetpacksourcemodule.UploadWork

/**
 * @date 2022/2/10
 * @author hezhanghe@zepp.com
 * @desc
 */
interface IWorkManagerService {

    fun oneTimeWork()

    fun oneTimeConstraintWork()

    fun repeatTimeWork()

    fun repeatTimeConstraintWork()
}


class WorkManagerServiceImpl(private val context: Context) : IWorkManagerService {

    override fun oneTimeWork() {
        val uploadWork = OneTimeWorkRequestBuilder<UploadWork>().build()
        WorkManager.getInstance(context)
            .enqueue(uploadWork)
    }

    override fun oneTimeConstraintWork() {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()
        val uploadWork = OneTimeWorkRequestBuilder<UploadWork>().setConstraints(constraints).build()
        WorkManager.getInstance(context)
            .enqueue(uploadWork)
    }

    override fun repeatTimeWork() {
    }

    override fun repeatTimeConstraintWork() {
    }
}