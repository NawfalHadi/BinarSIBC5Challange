package com.thatnawfal.binarsibc5challange.provider

import android.content.Context
import com.thatnawfal.binarsibc5challange.data.network.datasource.MoviesDataSource
import com.thatnawfal.binarsibc5challange.data.network.datasource.MoviesDataSourceImpl
import com.thatnawfal.binarsibc5challange.data.network.service.ApiClient
import com.thatnawfal.binarsibc5challange.data.network.service.ApiService
import com.thatnawfal.binarsibc5challange.data.repository.MovieRepository
import com.thatnawfal.binarsibc5challange.data.repository.MoviewRepositoryImpl

object ServiceLocator {

    fun provideApiService(): ApiService{
        return ApiClient.instance
    }

    fun provideMovieDataSource(apiService: ApiService): MoviesDataSource{
        return MoviesDataSourceImpl(apiService)
    }

    fun provideRepository(): MovieRepository{
        return MoviewRepositoryImpl(provideMovieDataSource(provideApiService()))
    }
}