package com.example.whereileftoff.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.URLUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.whereileftoff.R
import com.example.whereileftoff.databinding.MovieItemBinding
import com.example.whereileftoff.listeners.OnMediaEventListener
import com.example.whereileftoff.models.Movie
import com.example.whereileftoff.utils.getMovieImageResource

class MovieAdapter : ListAdapter<Movie, MovieAdapter.MovieViewHolder>(MoviesComparator()) {
    var onMovieItemSelectedListener: OnMediaEventListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        return MovieViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current)
        onMovieItemSelectedListener?.let { listener ->
            holder.itemView.setOnClickListener { listener.onMediaSelected(current.id) }
        }
    }

    class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(movie: Movie) {
            val binding = MovieItemBinding.bind(itemView)
            if (URLUtil.isValidUrl(movie.image)) {
                Glide.with(itemView).load(movie.image).into(binding.ivMovieImage)
            } else {
                binding.ivMovieImage.setImageResource(R.drawable.ic_media)
            }
            binding.tvMovieTitle.text = movie.title
            binding.ivMovieState.setImageResource(getMovieImageResource(movie.state))
        }

        companion object {
            fun create(parent: ViewGroup): MovieViewHolder {
                val view: View =
                    LayoutInflater.from(parent.context).inflate(R.layout.movie_item, parent, false)
                return MovieViewHolder(view)
            }
        }
    }
}

class MoviesComparator : DiffUtil.ItemCallback<Movie>() {
    override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem === newItem
    }

    override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem.image == newItem.image &&
                oldItem.title == newItem.title &&
                oldItem.minute == newItem.minute &&
                oldItem.second == newItem.second &&
                oldItem.state == newItem.state
    }
}