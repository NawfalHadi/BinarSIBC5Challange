package com.thatnawfal.binarsibc5challange.ui.home.movie.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.thatnawfal.binarsibc5challange.data.network.response.ListResponse
import com.thatnawfal.binarsibc5challange.data.network.response.MoviesListItemResponse
import com.thatnawfal.binarsibc5challange.databinding.ItemListRecyclerBinding

class ParentAdapter : RecyclerView.Adapter<ParentAdapter.ParentViewHolder>() {

    private var data: MutableList<ListRecycler> = mutableListOf()

    fun addItems(list: ListRecycler) {
        this.data.add(list)
        notifyDataSetChanged()
    }

    fun clearItems(){
        this.data.clear()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ParentViewHolder {
        val binding = ItemListRecyclerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ParentViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ParentViewHolder, position: Int) {
        val list = data[position]
        holder.bindingView(list)
    }

    override fun getItemCount(): Int = data.size

    class ParentViewHolder(
        private val binding: ItemListRecyclerBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bindingView(list: ListRecycler) {
            with(binding) {
                itemTvListMovieTitle.text = list.title

                val adapter: ChildAdapter by lazy {
                    ChildAdapter(object : itemClickListerner {
                        override fun itemClicked(movieId: Int?) {
                            TODO()
                        }
                    })
                }

                adapter.setItems(list.listResponse)
                //adapter
                 itemRvListMovie.layoutManager = LinearLayoutManager(itemView.context, LinearLayoutManager.HORIZONTAL, false)
                 itemRvListMovie.adapter = adapter
            }
        }

    }

}

data class ListRecycler(
    val title: String?,
    val listResponse: List<MoviesListItemResponse>,
    )