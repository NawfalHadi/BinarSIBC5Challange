package com.thatnawfal.binarsibc5challange.data.repository

import com.thatnawfal.binarsibc5challange.data.network.datasource.MoviesDataSource
import com.thatnawfal.binarsibc5challange.data.network.response.ListResponse
import com.thatnawfal.binarsibc5challange.data.network.response.MoviesListItemResponse
import com.thatnawfal.binarsibc5challange.data.network.response.detailresponse.MovieDetailResponse
import com.thatnawfal.binarsibc5challange.wrapper.Resource

interface MovieRepository {
    suspend fun loadNowPlayingMovies(): Resource<ListResponse<MoviesListItemResponse>>
    suspend fun loadDetailMovie(movieId: Int): Resource<MovieDetailResponse>
    suspend fun loadLatestMovie(): Resource<ListResponse<MoviesListItemResponse>>
    suspend fun loadUpcomingMovies(): Resource<ListResponse<MoviesListItemResponse>>
    suspend fun loadTopRatedMovies(): Resource<ListResponse<MoviesListItemResponse>>
}

class MoviewRepositoryImpl(private var dataSource: MoviesDataSource):MovieRepository{

    override suspend fun loadNowPlayingMovies(): Resource<ListResponse<MoviesListItemResponse>> {
        return loadListData(dataSource.loadNowPlayingMovies())
    }

    override suspend fun loadDetailMovie(movieId: Int): Resource<MovieDetailResponse> {
        return try {
            val movie = dataSource.loadDetailMovie(movieId)
            if (movie.title.isNullOrEmpty()) {
                Resource.Empty()
            } else {
                Resource.Success(movie)
            }
        } catch (e: Exception) {
            Resource.Error(e)
        }
    }

    override suspend fun loadLatestMovie(): Resource<ListResponse<MoviesListItemResponse>> {
        return loadListData(dataSource.loadLatestMovies())
    }

    override suspend fun loadUpcomingMovies(): Resource<ListResponse<MoviesListItemResponse>> {
        return loadListData(dataSource.loadUpcomingMovies())
    }

    override suspend fun loadTopRatedMovies(): Resource<ListResponse<MoviesListItemResponse>> {
        return loadListData(dataSource.loadTopRatedMovies())
    }

    private fun loadListData(list: ListResponse<MoviesListItemResponse>): Resource<ListResponse<MoviesListItemResponse>>{
        return try {
            if (list.result.isNullOrEmpty()){
                Resource.Empty()
            } else {
                Resource.Success(list)
            }
        } catch (e : Exception) {
            Resource.Error(e)
        }
    }

}