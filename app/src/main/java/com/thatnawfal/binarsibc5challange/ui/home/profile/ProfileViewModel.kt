package com.thatnawfal.binarsibc5challange.ui.home.profile

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thatnawfal.binarsibc5challange.data.local.database.account.AccountEntity
import com.thatnawfal.binarsibc5challange.data.repository.LocalRepository
import com.thatnawfal.binarsibc5challange.wrapper.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProfileViewModel(private val repository: LocalRepository): ViewModel() {
    val userDetailResult = MutableLiveData<Resource<AccountEntity>>()
    val updateAccountResult = MutableLiveData<Resource<Number>>()

    fun getDataUser(id: Int){
        userDetailResult.postValue(Resource.Loading())
        viewModelScope.launch(Dispatchers.IO){
            val account = repository.getDataUser(id)
            viewModelScope.launch(Dispatchers.Main) {
                userDetailResult.postValue(account)
            }
        }
    }

    fun updateAccount(accountEntity: AccountEntity){
        viewModelScope.launch {
            updateAccountResult.postValue(repository.updateAccount(accountEntity))
        }

    }

    fun getIdPreference(): Int? {
        return repository.getUserIdInPreference()
    }

    fun setIdPreference(i: Int) {
        return repository.setUserIdInPreference(i)
    }
}