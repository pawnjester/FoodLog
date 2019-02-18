package com.andela.logfooddiary.viewmodel

import android.arch.lifecycle.ViewModel
import com.andela.logfooddiary.data.Food
import com.andela.logfooddiary.data.Repository


class DiaryViewmodel(val repository: Repository): ViewModel() {

    var foodRecord: String? = null
    var foodDate: String? = null
    var selectedSearchDate: String? = null

    fun addFood(food: Food) = repository.addToDatabase(food)

}