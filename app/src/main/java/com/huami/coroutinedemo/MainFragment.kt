package com.huami.coroutinedemo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.appcompat.widget.AppCompatButton
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.huami.coroutinedemo.ext.commonLog
import com.huami.webmodule.WebFragment
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.retry

class MainFragment : Fragment(R.layout.main_fragment) {


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<AppCompatButton>(R.id.btnWeb).setOnClickListener {

            val args = Bundle()
            args.putString(WebFragment.WEB_FRAGMENR_URL, "https://www.baidu.com/")
            findNavController().navigate(R.id.webFragment, args)
        }


//        val job = kotlin.runCatching {
//            simple().catch {
//                commonLog { "error $it" }
//            }.onEach {
//                commonLog { "onEach $it" }
//            }.launchIn(lifecycleScope)
//        }.onFailure {
//            commonLog {
//                "runcatch $it"
//            }
//        }.getOrNull()

        var job: Job? = null

        kotlin.runCatching {
            job = simple().retry {
                true
            }.onEach { i->
                commonLog { "onEach $i" }
            }.catch { e->
                commonLog { "error $e" }
            }.onCompletion { completion ->
                if (completion != null) {
                    commonLog { "completation error $completion" }
                } else {

                }

            }.launchIn(lifecycleScope)
        }.onFailure { e->
            commonLog { "failure error $e" }
        }


        view.findViewById<AppCompatButton>(R.id.btnCancle).setOnClickListener {
            job?.cancel()
        }

        view.findViewById<AppCompatButton>(R.id.btnCompose).setOnClickListener {
            findNavController().navigate(R.id.composeFragment)
        }

        view.findViewById<AppCompatButton>(R.id.btnFlowPractise).setOnClickListener {
            findNavController().navigate(R.id.flowFragment)
        }
    }

    fun simple(): Flow<Int> = flow {
        for (i in 1..100) {
            delay(500)

            if(i>5){
                throw NullPointerException()
            }
            emit(i)
        }
    }

    private suspend fun delayfun() {
        repeat(5) { i ->
            commonLog {
                "repeast $i"
            }
            delay(500)
        }
    }

}