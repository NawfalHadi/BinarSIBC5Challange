package com.thatnawfal.binarsibc5challange.ui.home.movie

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.thatnawfal.binarsibc5challange.databinding.FragmentMovieListBinding
import com.thatnawfal.binarsibc5challange.provider.ServiceLocator
import com.thatnawfal.binarsibc5challange.ui.home.movie.adapter.NowPlayingAdapter
import com.thatnawfal.binarsibc5challange.ui.home.movie.adapter.itemClickListerner
import com.thatnawfal.binarsibc5challange.ui.home.movie.viewmodel.MovieListViewModel
import com.thatnawfal.binarsibc5challange.utils.viewModelFactory
import com.thatnawfal.binarsibc5challange.wrapper.Resource

class MovieListFragment : Fragment() {
    private lateinit var binding: FragmentMovieListBinding

    private val viewModel: MovieListViewModel by viewModelFactory {
        MovieListViewModel(ServiceLocator.provideRepository())
    }

    private val adapter : NowPlayingAdapter by lazy {
        NowPlayingAdapter(object : itemClickListerner{
            override fun itemClicked(movieId: Int?) {
                movieId?.let { viewModel.loadDetailMovie(it) }
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMovieListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.loadNowPlayingMovies()
        observeData()

    }

    private fun initlist() {
        binding.rvMovieNowPlaying.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        binding.rvMovieNowPlaying.adapter = adapter
    }

    private fun observeData() {
        viewModel.nowPlayingMovies.observe(requireActivity()){
            when(it){
                is Resource.Empty ->{
                    adapter.clearItems()
                    Toast.makeText(requireActivity(), "EmptyList", Toast.LENGTH_SHORT).show()
                }
                is Resource.Error ->
                    Toast.makeText(requireActivity(), "Error", Toast.LENGTH_SHORT).show()
                is Resource.Loading ->
                    Toast.makeText(requireActivity(), "Loading", Toast.LENGTH_SHORT).show()
                is Resource.Success -> {
                    it.payload?.result?.let { data -> adapter.setItems(data) }
                    initlist()
                    Toast.makeText(requireActivity(), "Success", Toast.LENGTH_SHORT).show()
                }
            }
        }

        viewModel.resultDetailMovie.observe(requireActivity()){
            when (it) {
                is Resource.Success -> it.payload?.let {
                    Toast.makeText(requireActivity(), it.title, Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}