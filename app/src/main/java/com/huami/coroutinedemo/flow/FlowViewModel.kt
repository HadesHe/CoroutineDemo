package com.huami.coroutinedemo.flow

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.huami.coroutinedemo.ext.ControlledRunner
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class FlowViewModel : ViewModel() {

    private val runner = ControlledRunner<String>()

    private val resultChannel = Channel<String>()
    val resultFlow = resultChannel.receiveAsFlow().asLiveData(viewModelScope.coroutineContext)

    fun onClick(currentTimeMillis: Long) {
        viewModelScope.launch {

            resultChannel.send(runner.joinPreviousOrReturn {
                timeComsumer(currentTimeMillis = currentTimeMillis)
            })
        }
    }

    suspend fun timeComsumer(currentTimeMillis: Long): String {
        delay(1000)
        return "currentTimeMillis $currentTimeMillis"
    }
}