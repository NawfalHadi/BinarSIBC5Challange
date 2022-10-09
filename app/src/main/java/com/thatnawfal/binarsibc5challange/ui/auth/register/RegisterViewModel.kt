package com.thatnawfal.binarsibc5challange.ui.auth.register

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thatnawfal.binarsibc5challange.data.local.database.account.AccountEntity
import com.thatnawfal.binarsibc5challange.data.repository.LocalRepository
import com.thatnawfal.binarsibc5challange.wrapper.Resource
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class RegisterViewModel(private var repository: LocalRepository): ViewModel() {

    val isEmailExcist = MutableLiveData<Boolean>()
    val newAccount = MutableLiveData<Resource<Number>>()

    fun checkEmailExcist(email: String){
        viewModelScope.launch {
            isEmailExcist.postValue(repository.isEmailExcist(email))
        }
    }

    fun registerNewAccount(account: AccountEntity) {
        viewModelScope.launch {
            newAccount.postValue(Resource.Loading())
            delay(1000)
            newAccount.postValue(repository.registerAccount(account))
        }
    }
}