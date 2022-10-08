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
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MovieListViewModel(private var repository: MovieRepository): ViewModel() {

    val nowPlayingMovies = MutableLiveData<Resource<ListResponse<MoviesListItemResponse>>>()
    val resultDetailMovie = MutableLiveData<Resource<MovieDetailResponse>>()

    val topRatedListMovies = MutableLiveData<Resource<ListResponse<MoviesListItemResponse>>>()
    val latestListMovies = MutableLiveData<Resource<ListResponse<MoviesListItemResponse>>>()
    val upcomingListMovies = MutableLiveData<Resource<ListResponse<MoviesListItemResponse>>>()
    val popularListMovies = MutableLiveData<Resource<ListResponse<MoviesListItemResponse>>>()

    fun loadNowPlayingMovies(){
        nowPlayingMovies.postValue(Resource.Loading())
        viewModelScope.launch(Dispatchers.IO) {
            val list =  repository.loadNowPlayingMovies()
            delay(200)
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

    // Kalo ada error disini coba jangan ditaruh di io thread barengan

    fun loadTopRatedMovies(){
        topRatedListMovies.postValue(Resource.Loading())
        viewModelScope.launch(Dispatchers.IO) {
            val list = repository.loadTopRatedMovies()
            viewModelScope.launch(Dispatchers.Main) {
                topRatedListMovies.postValue(list)
            }
        }
    }

    fun loadPopularMovies(){
        popularListMovies.postValue(Resource.Loading())
        viewModelScope.launch(Dispatchers.IO) {
            val list = repository.loadPopularMovies()
            viewModelScope.launch(Dispatchers.Main) {
                popularListMovies.postValue(list)
            }
        }
    }

    fun loadLatestMovie(){
        latestListMovies.postValue(Resource.Loading())
        viewModelScope.launch(Dispatchers.IO) {
            val list = repository.loadLatestMovie()
            viewModelScope.launch(Dispatchers.Main){
                latestListMovies.postValue(list)
            }
        }
    }

    fun loadUpcomingMovies(){
        upcomingListMovies.postValue(Resource.Loading())
        viewModelScope.launch(Dispatchers.IO) {
            val list = repository.loadUpcomingMovies()
            viewModelScope.launch(Dispatchers.Main){
                upcomingListMovies.postValue(list)
            }
        }
    }

}