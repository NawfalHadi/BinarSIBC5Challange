package com.thatnawfal.binarsibc5challange.data.repository

import com.thatnawfal.binarsibc5challange.data.local.database.account.AccountDataSource
import com.thatnawfal.binarsibc5challange.data.local.database.account.AccountEntity
import com.thatnawfal.binarsibc5challange.data.local.preference.AuthPreferenceDataSource
import com.thatnawfal.binarsibc5challange.wrapper.Resource

interface LocalRepository {

    // 1. CheckKalauIdAdalah != 0
    fun checkIfUserLogin(): Boolean
    fun setUserIdInPreference(newId: Int)
    fun getUserIdInPreference(): Int?

    suspend fun getIdFromEmail(email: String): Int
    suspend fun registerAccount(account: AccountEntity): Resource<Number>
    suspend fun isEmailExcist(email: String): Boolean
    suspend fun isPassCorrect(email: String, password: String): Boolean
    suspend fun getDataUser(id: Int): Resource<AccountEntity>
    suspend fun updateAccount(account: AccountEntity): Resource<Number>
}

class LocalRepositoryImpl(
    private val dataSource: AuthPreferenceDataSource,
    private val accountDataSource: AccountDataSource
) : LocalRepository {

    /*** Shared Preferences ***/

    override fun checkIfUserLogin(): Boolean {
        return dataSource.getUserId() != 0
    }


    override fun setUserIdInPreference(newId: Int) {
        dataSource.setUserId(newId)
    }

    override fun getUserIdInPreference(): Int? {
        return dataSource.getUserId()
    }

    /*** Account ***/

    override suspend fun getIdFromEmail(email: String): Int {
        return accountDataSource.getIdFromEmail(email)
    }

    override suspend fun registerAccount(account: AccountEntity): Resource<Number> {
        return proceed {
            accountDataSource.registerAccount(account)
        }
    }

    override suspend fun isEmailExcist(email: String): Boolean {
        return accountDataSource.checkEmailExcist(email)
    }

    override suspend fun isPassCorrect(email: String, password: String): Boolean {
        return accountDataSource.checkPassword(email, password)
    }

    override suspend fun getDataUser(id: Int): Resource<AccountEntity> {
        return try {
            val account = accountDataSource.getDataUser(id)
            if (account.username.isNullOrEmpty()){
                Resource.Empty()
            } else {
                Resource.Success(account)
            }
        } catch (e:Exception) { Resource.Error(e) }
    }

    override suspend fun updateAccount(account: AccountEntity): Resource<Number> {
        return proceed {
            accountDataSource.updateAccount(account)
        }
    }

    private suspend fun <T> proceed(coroutine: suspend () -> T): Resource<T> {
        return try {
            Resource.Success(coroutine.invoke())
        } catch (exception: Exception) {
            Resource.Error(exception)
        }
    }
}