package com.thatnawfal.binarsibc5challange.provider

import android.content.Context
import com.thatnawfal.binarsibc5challange.data.local.database.AppDatabase
import com.thatnawfal.binarsibc5challange.data.local.database.account.AccountDao
import com.thatnawfal.binarsibc5challange.data.local.database.account.AccountDataSource
import com.thatnawfal.binarsibc5challange.data.local.database.account.AccountDataSourceImpl
import com.thatnawfal.binarsibc5challange.data.local.preference.AuthPreference
import com.thatnawfal.binarsibc5challange.data.local.preference.AuthPreferenceDataSource
import com.thatnawfal.binarsibc5challange.data.local.preference.AuthPreferenceDataSourceImpl
import com.thatnawfal.binarsibc5challange.data.network.datasource.MoviesDataSource
import com.thatnawfal.binarsibc5challange.data.network.datasource.MoviesDataSourceImpl
import com.thatnawfal.binarsibc5challange.data.network.service.ApiClient
import com.thatnawfal.binarsibc5challange.data.network.service.ApiService
import com.thatnawfal.binarsibc5challange.data.repository.LocalRepository
import com.thatnawfal.binarsibc5challange.data.repository.LocalRepositoryImpl
import com.thatnawfal.binarsibc5challange.data.repository.MovieRepository
import com.thatnawfal.binarsibc5challange.data.repository.MoviewRepositoryImpl

object ServiceLocator {

    fun provideAuthPreference(context: Context) : AuthPreference {
        return AuthPreference(context)
    }

    fun provideAuthPreferenceDataSource(context: Context) : AuthPreferenceDataSource {
        return AuthPreferenceDataSourceImpl(provideAuthPreference(context))
    }

    fun provideAppDatabase(context: Context): AppDatabase{
        return AppDatabase.getInstance(context)
    }

    fun provideAccountDao(context: Context): AccountDao {
        return provideAppDatabase(context).accountDao()
    }

    fun provideAccountDataSource(context: Context): AccountDataSource {
        return AccountDataSourceImpl(provideAccountDao(context))
    }

    fun provideLocalRepository(context: Context): LocalRepository{
        return LocalRepositoryImpl(
            provideAuthPreferenceDataSource(context),
            provideAccountDataSource(context))
    }

    fun provideApiService(): ApiService{
        return ApiClient.instance
    }

    fun provideMovieDataSource(apiService: ApiService): MoviesDataSource{
        return MoviesDataSourceImpl(apiService)
    }

    fun provideMovieRepository(): MovieRepository{
        return MoviewRepositoryImpl(
            provideMovieDataSource(provideApiService()))
    }

}