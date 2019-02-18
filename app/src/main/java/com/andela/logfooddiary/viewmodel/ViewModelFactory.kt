package com.andela.logfooddiary.viewmodel

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.andela.logfooddiary.data.Repository
import com.andela.logfooddiary.viewmodel.DiaryViewmodel


@Suppress("UNCHECKED_CAST")
class ViewModelFactory(val repository: Repository): ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DiaryViewmodel::class.java)) {
            return DiaryViewmodel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}