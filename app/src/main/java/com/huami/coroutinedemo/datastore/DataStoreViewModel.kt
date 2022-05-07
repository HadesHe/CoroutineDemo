package com.huami.coroutinedemo.datastore

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

/**
 * @date 2022/5/7
 * @author hezhanghe@zepp.com
 * @desc
 */
class DataStoreViewModel(private val dataStoreRepo: DataStoreRepo) : ViewModel() {

    val dataStoreResult = dataStoreRepo.counterPreferencesFlow.catch {
    }.asLiveData(viewModelScope.coroutineContext + Dispatchers.Main)

    fun updateDataStore(){
        viewModelScope.launch {
            dataStoreRepo.incrementCounter()
        }
    }

}