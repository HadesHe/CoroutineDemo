package com.huami.webmodule

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

/**
 * Created by hezhanghe on 2021/12/27.
 * github: https://github.com/HadesHe
 */
class WebFragment : Fragment(R.layout.fragment_web) {

    companion object {
        @JvmStatic
        fun newInstance(): WebFragment {
            return WebFragment()
        }
    }

}