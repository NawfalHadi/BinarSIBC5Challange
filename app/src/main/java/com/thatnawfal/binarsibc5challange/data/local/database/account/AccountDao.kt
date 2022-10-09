package com.thatnawfal.binarsibc5challange.data.local.database.account

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface AccountDao {

    @Query("SELECT COUNT() FROM ACCOUNT WHERE email = :email")
    suspend fun emailExcistCheck(email: String): Int

    @Query("SELECT COUNT() FROM ACCOUNT WHERE email = :email AND password = :password")
    suspend fun passwordIsCorrect(email: String, password: String): Int

    @Query("SELECT id FROM ACCOUNT WHERE email = :email")
    suspend fun getIdFromEmail(email: String): Int

    @Insert
    suspend fun registerAccount(account: AccountEntity) : Long

}