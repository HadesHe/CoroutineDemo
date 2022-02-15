package com.huami.jetpacksourcemodule.service

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkInfo
import androidx.work.WorkManager
import com.huami.jetpacksourcemodule.UploadWork
import java.util.concurrent.TimeUnit

/**
 * @date 2022/2/10
 * @author hezhanghe@zepp.com
 * @desc
 */
interface IWorkManagerService {

    //    1. 一次性任务
    fun oneTimeWork()

    //   2. 周期性任务
    fun repeatTimeWork()

    //   3. 约束条件任务
    fun oneTimeConstraintWork()

    //    4. 延迟任务
    fun delayWork()

    fun getWorkInfoById(): LiveData<WorkInfo>
}


class WorkManagerServiceImpl(private val context: Context) : IWorkManagerService {

    val oneTimeWork = OneTimeWorkRequestBuilder<UploadWork>().build()
    override fun oneTimeWork() {
        WorkManager.getInstance(context)
            .enqueue(oneTimeWork)
    }

    override fun oneTimeConstraintWork() {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()
        val uploadWork = OneTimeWorkRequestBuilder<UploadWork>().setConstraints(constraints).build()
        WorkManager.getInstance(context)
            .enqueue(uploadWork)
    }

    override fun delayWork() {
        val uploadWorkRequest = OneTimeWorkRequestBuilder<UploadWork>()
            .setInitialDelay(10,TimeUnit.SECONDS)
            .build()
        WorkManager.getInstance(context).enqueue(uploadWorkRequest)
    }

    override fun getWorkInfoById(): LiveData<WorkInfo> {
        return WorkManager.getInstance(context).getWorkInfoByIdLiveData(
            oneTimeWork.id
        )
    }

    override fun repeatTimeWork() {
        val uploadWork = PeriodicWorkRequestBuilder<UploadWork>(12, TimeUnit.HOURS).build()
        WorkManager.getInstance(context).enqueue(uploadWork)
    }
}