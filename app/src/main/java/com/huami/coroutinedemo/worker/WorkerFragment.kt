package com.huami.coroutinedemo.worker

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.appcompat.widget.AppCompatButton
import com.huami.coroutinedemo.R
import com.huami.jetpacksourcemodule.di.getWorkerScope
import com.huami.jetpacksourcemodule.service.IWorkManagerService

class WorkerFragment : Fragment(R.layout.worker_fragment) {

    companion object {
        fun newInstance() = WorkerFragment()
    }

    private val workerService by lazy {
        getWorkerScope().getOrNull<IWorkManagerService>()
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val btnOneTimeWork = view.findViewById<AppCompatButton>(R.id.btnOneTimeWorker)
        val btnOneTimeConstraintWorker = view.findViewById<AppCompatButton>(R.id.btnOneTimeConstraintWorker)

        btnOneTimeWork.setOnClickListener {
            workerService?.oneTimeWork()
        }

    }

}