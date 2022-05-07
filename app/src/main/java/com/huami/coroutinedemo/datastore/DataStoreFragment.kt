package com.huami.coroutinedemo.datastore

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.huami.coroutinedemo.R
import com.huami.coroutinedemo.databinding.FragmentDataStoreBinding
import com.huami.viewbinding.viewBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class DataStoreFragment : Fragment(R.layout.fragment_data_store) {

    private val TAG = DataStoreFragment::class.java.simpleName

    private val binding by viewBinding<FragmentDataStoreBinding>()

    private val model by viewModel<DataStoreViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.btnDataUpdate.setOnClickListener {
            model.updateDataStore()
        }

        model.dataStoreResult.observe(viewLifecycleOwner){
            binding.tvDataResult.text = "$it"
        }

    }
}