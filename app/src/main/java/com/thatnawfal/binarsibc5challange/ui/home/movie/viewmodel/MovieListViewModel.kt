package com.thatnawfal.binarsibc5challange.ui.home.movie.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thatnawfal.binarsibc5challange.data.network.response.ListResponse
import com.thatnawfal.binarsibc5challange.data.network.response.MoviesListItemResponse
import com.thatnawfal.binarsibc5challange.data.network.response.detailresponse.MovieDetailResponse
import com.thatnawfal.binarsibc5challange.data.repository.MovieRepository
import com.thatnawfal.binarsibc5challange.wrapper.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MovieListViewModel(private var repository: MovieRepository): ViewModel() {

    val nowPlayingMovies = MutableLiveData<Resource<ListResponse<MoviesListItemResponse>>>()
    val resultDetailMovie = MutableLiveData<Resource<MovieDetailResponse>>()

    fun loadNowPlayingMovies(){
        nowPlayingMovies.postValue(Resource.Loading())
        viewModelScope.launch(Dispatchers.IO) {
            val list =  repository.loadNowPlayingMovies()
            viewModelScope.launch(Dispatchers.Main) {
                nowPlayingMovies.postValue(list)
            }
        }
    }

    fun loadDetailMovie(movieId: Int){
        resultDetailMovie.postValue(Resource.Loading())
        viewModelScope.launch(Dispatchers.IO) {
            val movie = repository.loadDetailMovie(movieId)
            viewModelScope.launch(Dispatchers.Main) {
                resultDetailMovie.postValue(movie)
            }
        }
    }

    fun loadLatestMovie(){
        TODO()
    }

    fun loadUpcomingMovies(){
        TODO()
    }

    fun loadTopRatedMovies(){
        TODO()
    }

}