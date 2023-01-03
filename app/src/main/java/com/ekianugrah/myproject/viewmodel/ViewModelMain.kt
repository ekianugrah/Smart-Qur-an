package com.ekianugrah.myproject.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ekianugrah.myproject.model.ResponseMain
import com.ekianugrah.myproject.repo.RepositoryMain

class ViewModelMain: ViewModel() {

    val repository = RepositoryMain()

    var responGetData = MutableLiveData<List<ResponseMain>>()
    var isError = MutableLiveData<Throwable>()
    var isLoading = MutableLiveData<Boolean>()

    fun getDataView() {
        isLoading.value = true

        repository.getData({
            isLoading.value = false
            responGetData.value = it
        }, {
            isLoading.value = false
            isError.value = it
        })
    }
}