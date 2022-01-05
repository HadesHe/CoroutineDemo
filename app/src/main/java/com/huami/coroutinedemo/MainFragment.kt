package com.huami.coroutinedemo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.appcompat.widget.AppCompatButton
import androidx.navigation.fragment.findNavController
import com.huami.webmodule.WebFragment

class MainFragment : Fragment(R.layout.main_fragment) {


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<AppCompatButton>(R.id.btnWeb).setOnClickListener {

            val args = Bundle()
            args.putString(WebFragment.WEB_FRAGMENR_URL,"https://www.baidu.com/")
            findNavController().navigate(R.id.webFragment,args)
        }
    }

}