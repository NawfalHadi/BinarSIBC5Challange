package com.thatnawfal.binarsibc5challange.ui.home.movie.detail

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

class DetailMovieViewModel(private var repository: MovieRepository): ViewModel() {

    val resultListRecommended = MutableLiveData<Resource<ListResponse<MoviesListItemResponse>>>()
    val resultDetailMovie = MutableLiveData<Resource<MovieDetailResponse>>()


    fun loadDetailMovie(movieId: Int, language:String){
        resultDetailMovie.postValue(Resource.Loading())
        viewModelScope.launch(Dispatchers.IO) {
            val movie = repository.loadDetailMovie(movieId, language)
            viewModelScope.launch(Dispatchers.Main) {
                resultDetailMovie.postValue(movie)
            }
        }
    }

    fun loadRecommendedMovies(movieId: Int){
        resultListRecommended.postValue(Resource.Loading())
        viewModelScope.launch(Dispatchers.IO) {
            val movies = repository.loadRecommendedMovies(movieId)
            viewModelScope.launch(Dispatchers.Main) {
                resultListRecommended.postValue(movies)
            }
        }
    }



}