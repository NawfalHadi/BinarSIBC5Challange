package com.thatnawfal.binarsibc5challange.ui.home.movie.detail

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import com.thatnawfal.binarsibc5challange.R
import com.thatnawfal.binarsibc5challange.data.network.response.detailresponse.MovieDetailResponse
import com.thatnawfal.binarsibc5challange.databinding.FragmentDetailMovieBinding
import com.thatnawfal.binarsibc5challange.provider.ServiceLocator
import com.thatnawfal.binarsibc5challange.ui.home.movie.adapter.ChildAdapter
import com.thatnawfal.binarsibc5challange.ui.home.movie.adapter.itemClickListerner
import com.thatnawfal.binarsibc5challange.utils.Helper
import com.thatnawfal.binarsibc5challange.wrapper.Resource

class DetailMovieFragment : Fragment() {

    private lateinit var binding : FragmentDetailMovieBinding

    private val adapterGenre : GenreAdapter by lazy {
        GenreAdapter()
    }

    private val adapterRecomended : ChildAdapter by lazy {
        ChildAdapter(object : itemClickListerner{
            override fun itemClicked(movieId: Int?) {
                findNavController().popBackStack()
                findNavController().navigate(R.id.action_movieListFragment_to_detailMovieFragment)
            }
        })
    }

    private val viewModel: DetailMovieViewModel by lazy {
        DetailMovieViewModel(ServiceLocator.provideMovieRepository())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailMovieBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val movieId = arguments?.getInt("movie_id",0)

        binding.backButton.setOnClickListener { findNavController().popBackStack() }
        movieId?.let { viewModel.loadDetailMovie(927341, getString(R.string.countryCode)) }
        observeDetail()
    }

    private fun observeDetail() {
        viewModel.resultDetailMovie.observe(requireActivity()){
            when (it) {
                is Resource.Loading -> binding.pbDetail.isEnabled = true
                is Resource.Error -> {
                    print(it.exception)
                    binding.pbDetail.isEnabled = false
                    findNavController().popBackStack()
                }
                is Resource.Empty -> {
                    Toast.makeText(requireContext(), "ba", Toast.LENGTH_SHORT).show()
                    binding.pbDetail.isEnabled = false
                    findNavController().popBackStack()
                }
                is Resource.Success -> {
                    binding.pbDetail.isEnabled = false
                    bindingView(it.payload)
                }
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun bindingView(movie: MovieDetailResponse?) {
        movie?.genres?.let { adapterGenre.setItems(it) }

        binding.ivDetailBackdropImg.load(Helper.POSTER_API_ENDPOINT + Helper.POSTER_SIZE_W780_ENDPOINT + movie?.backdropPath) {
            crossfade(true)
            placeholder(R.drawable.poster_placeholder)
        }
        binding.ivDetailPoster.load(Helper.POSTER_API_ENDPOINT + Helper.POSTER_SIZE_W780_ENDPOINT + movie?.posterPath) {
            crossfade(true)
            placeholder(R.drawable.poster_placeholder)
        }

        movie?.let {
            binding.tvDetailTitle.text = " : ${it.title}"
            binding.tvDetailOverview.text = " : ${it.overview}"
            binding.tvDetailRuntime.text = " : ${it.runtime.toString()} ${getString(R.string.minute)}"
            binding.tvDetailStatus.text = " : ${it.status}"
            binding.tvDetailReleaseDate.text = " : ${it.releaseDate}"
        }

        binding.rvDetailGenreList.layoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
        binding.rvDetailGenreList.adapter = adapterGenre

    }


}