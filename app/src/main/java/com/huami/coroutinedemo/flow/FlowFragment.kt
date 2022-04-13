package com.huami.coroutinedemo.flow

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import androidx.appcompat.widget.AppCompatButton
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.huami.coroutinedemo.R
import com.huami.coroutinedemo.lazyinterface.FlowLifecycleDelegate
import com.huami.coroutinedemo.lazyinterface.ICoroutineScopeDelegate

class FlowFragment : Fragment(R.layout.flow_fragment), ICoroutineScopeDelegate by FlowLifecycleDelegate()  {

    companion object {
        fun newInstance() = FlowFragment()
    }

    private lateinit var btnFlowClick: AppCompatButton
    private val flowViewModel by viewModels<FlowViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        registerCoroutineScope(this.viewLifecycleOwner, lifecycleScope)


        btnFlowClick = view.findViewById<AppCompatButton>(R.id.btnFlowClick)

        btnFlowClick.setOnClickListener {

            flowViewModel.onClick(System.currentTimeMillis())
        }

        flowViewModel.resultFlow.observe(viewLifecycleOwner){
            Log.d("flowFragment","reuslt $it")
        }

    }

}