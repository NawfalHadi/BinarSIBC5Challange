package com.thatnawfal.binarsibc5challange.ui.home.movie.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.thatnawfal.binarsibc5challange.R
import com.thatnawfal.binarsibc5challange.data.network.response.MoviesListItemResponse
import com.thatnawfal.binarsibc5challange.databinding.ItemHorizontalPosterBinding
import com.thatnawfal.binarsibc5challange.utils.Helper.POSTER_API_ENDPOINT
import com.thatnawfal.binarsibc5challange.utils.Helper.POSTER_SIZE_W780_ENDPOINT

class NowPlayingAdapter(
    private val listener: itemClickListerner
) : RecyclerView.Adapter<NowPlayingAdapter.NowPlayingViewHolder>() {

    private var data: MutableList<MoviesListItemResponse> = mutableListOf()

    fun setItems(movies: List<MoviesListItemResponse>) {
        clearItems()
        this.data.addAll(movies)
        notifyDataSetChanged()
    }
    fun clearItems() {
        this.data.clear()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NowPlayingViewHolder {
        val binding = ItemHorizontalPosterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NowPlayingViewHolder(binding, listener)
    }

    override fun onBindViewHolder(holder: NowPlayingViewHolder, position: Int) {
        val movie = data[position]
        holder.bindingView(movie)
    }

    override fun getItemCount(): Int = data.size

    inner class NowPlayingViewHolder(
        private val binding: ItemHorizontalPosterBinding,
        private val listerner: itemClickListerner
    ): RecyclerView.ViewHolder(binding.root) {
        fun bindingView(movie: MoviesListItemResponse) {
            with(binding) {
                imgItemHorizontalPoster.load(POSTER_API_ENDPOINT + POSTER_SIZE_W780_ENDPOINT + movie.posterPath) {
                    crossfade(true)
                    placeholder(R.drawable.poster_placeholder)
                }
                cvW720Poster.setOnClickListener{
                    listerner.itemClicked(movie.id)
                }
            }
        }

    }
}

interface itemClickListerner {
    fun itemClicked(movieId: Int?)
}
