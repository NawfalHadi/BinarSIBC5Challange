package com.thatnawfal.binarsibc5challange.ui.auth.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thatnawfal.binarsibc5challange.data.repository.LocalRepository
import kotlinx.coroutines.launch

class LoginViewModel(private val repository: LocalRepository): ViewModel() {
    val isPassCorrect = MutableLiveData<Boolean>()
    val idFromEmail = MutableLiveData<Int>()

    fun checkEmailPassword(email: String, pass: String) {
        viewModelScope.launch {
            isPassCorrect.postValue(repository.isPassCorrect(email, pass))
        }
    }

    fun getIdFromEmail(email: String) {
        viewModelScope.launch {
            idFromEmail.postValue(repository.getIdFromEmail(email))
        }
    }

    fun setIdPreference(newId: Int){
        repository.setUserIdInPreference(newId)
    }

    fun getIdPreference(): Int? {
        return repository.getUserIdInPreference()
    }
}