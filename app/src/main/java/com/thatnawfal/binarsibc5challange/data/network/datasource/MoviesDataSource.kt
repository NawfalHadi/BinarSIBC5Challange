package com.thatnawfal.binarsibc5challange.data.network.datasource

import com.thatnawfal.binarsibc5challange.R
import com.thatnawfal.binarsibc5challange.data.network.response.ListResponse
import com.thatnawfal.binarsibc5challange.data.network.response.MoviesListItemResponse
import com.thatnawfal.binarsibc5challange.data.network.response.detailresponse.MovieDetailResponse
import com.thatnawfal.binarsibc5challange.data.network.service.ApiService

interface MoviesDataSource {
    suspend fun loadNowPlayingMovies(): ListResponse<MoviesListItemResponse>
    suspend fun loadDetailMovie(movieId: Int): MovieDetailResponse
    suspend fun loadLatestMovies(): ListResponse<MoviesListItemResponse>
    suspend fun loadUpcomingMovies(): ListResponse<MoviesListItemResponse>
    suspend fun loadTopRatedMovies(): ListResponse<MoviesListItemResponse>
    suspend fun loadPopularMovies(): ListResponse<MoviesListItemResponse>
}

class MoviesDataSourceImpl(private var apiService: ApiService) : MoviesDataSource{
    override suspend fun loadNowPlayingMovies(): ListResponse<MoviesListItemResponse> {
        return apiService.getMoviePlaying()
    }

    override suspend fun loadDetailMovie(movieId: Int): MovieDetailResponse {
        return apiService.getMovieDetail(movieId = movieId,)
    }

    override suspend fun loadLatestMovies(): ListResponse<MoviesListItemResponse> {
        return apiService.getMovieLatest()
    }

    override suspend fun loadUpcomingMovies(): ListResponse<MoviesListItemResponse> {
        return apiService.getMovieUpcoming()
    }

    override suspend fun loadTopRatedMovies(): ListResponse<MoviesListItemResponse> {
        return apiService.getMovieTopRated()
    }

    override suspend fun loadPopularMovies(): ListResponse<MoviesListItemResponse> {
        return apiService.getPopularMovie()
    }

}