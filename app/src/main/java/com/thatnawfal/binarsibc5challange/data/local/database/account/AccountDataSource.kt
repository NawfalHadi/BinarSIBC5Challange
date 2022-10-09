package com.thatnawfal.binarsibc5challange.data.local.database.account


    interface AccountDataSource {
        suspend fun getIdFromEmail(email: String): Int
        suspend fun checkEmailExcist(email: String): Boolean
        suspend fun registerAccount(account: AccountEntity): Long
        suspend fun checkPassword(email: String, password: String): Boolean
        suspend fun getDataUser(id: Int): AccountEntity

    }

    class AccountDataSourceImpl(private var accountDao: AccountDao): AccountDataSource {
        override suspend fun getIdFromEmail(email: String): Int {
            return accountDao.getIdFromEmail(email)
        }

        override suspend fun checkEmailExcist(email: String): Boolean {
            return accountDao.emailExcistCheck(email) > 0
        }

        override suspend fun registerAccount(account: AccountEntity): Long {
            return accountDao.registerAccount(account)
        }

        override suspend fun checkPassword(email: String, password: String): Boolean {
            return accountDao.passwordIsCorrect(email, password) > 0
        }

        override suspend fun getDataUser(id: Int): AccountEntity {
            return accountDao.getDataUser(id)
        }
    }
