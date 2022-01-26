package com.huami.coroutinedemo.flow

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import androidx.appcompat.widget.AppCompatButton
import androidx.fragment.app.viewModels
import com.huami.coroutinedemo.R

class FlowFragment : Fragment(R.layout.flow_fragment) {

    companion object {
        fun newInstance() = FlowFragment()
    }

    private lateinit var btnFlowClick: AppCompatButton
    private val flowViewModel by viewModels<FlowViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnFlowClick = view.findViewById<AppCompatButton>(R.id.btnFlowClick)

        btnFlowClick.setOnClickListener {

            flowViewModel.onClick(System.currentTimeMillis())
        }

        flowViewModel.resultFlow.observe(this){
            Log.d("flowFragment","reuslt $it")
        }

    }

}