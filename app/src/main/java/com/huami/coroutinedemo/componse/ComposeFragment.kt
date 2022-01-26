package com.huami.coroutinedemo.componse

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.tooling.preview.Preview
import androidx.fragment.app.Fragment

/**
 * @date 2022/1/20
 * @author hezhanghe@zepp.com
 * @desc
 */
class ComposeFragment: Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return ComposeView(requireContext()).apply {
            setContent {
                Text(text = "Hello world")
            }
        }
    }

    @Composable
    fun simpleComposable(){
        Text(text = "Hello World")
    }

    @Preview
    @Composable
    fun composablePreview(){
        simpleComposable()
    }
}