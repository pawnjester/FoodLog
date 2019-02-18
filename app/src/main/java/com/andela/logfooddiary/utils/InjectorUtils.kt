package com.andela.logfooddiary.utils

import com.andela.logfooddiary.data.Repository
import com.andela.logfooddiary.viewmodel.ViewModelFactory


object InjectorUtils {

    private fun getRepository() : Repository {
        return Repository()
    }

    fun provideViewModelFactory() : ViewModelFactory {
        return ViewModelFactory(getRepository())
    }
}