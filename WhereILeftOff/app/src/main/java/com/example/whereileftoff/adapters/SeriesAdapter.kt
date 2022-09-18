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
import com.example.whereileftoff.databinding.SeriesItemBinding
import com.example.whereileftoff.listeners.OnMediaEventListener
import com.example.whereileftoff.models.Series
import com.example.whereileftoff.utils.getSeriesImageResource

class SeriesAdapter : ListAdapter<Series, SeriesAdapter.SeriesViewHolder>(SeriesComparator()) {
    var onSeriesItemSelectedListener: OnMediaEventListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SeriesViewHolder {
        return SeriesViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: SeriesViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current)
        onSeriesItemSelectedListener?.let { listener ->
            holder.itemView.setOnClickListener { listener.onMediaSelected(current.id) }
        }
    }

    class SeriesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(series: Series) {
            val binding = SeriesItemBinding.bind(itemView)
            if (URLUtil.isValidUrl(series.image)) {
                Glide.with(itemView).load(series.image).into(binding.ivSeriesImage)
            } else {
                binding.ivSeriesImage.setImageResource(R.drawable.ic_media)
            }

            binding.tvSeriesTitle.text = series.title
            binding.tvSeriesSeason.text = "Se: " + series.season.toString()
            binding.tvSeriesEpisode.text = "Ep: " + series.episode.toString()
            binding.ivSeriesState.setImageResource(getSeriesImageResource(series.state))
        }

        companion object {
            fun create(parent: ViewGroup): SeriesViewHolder {
                val view: View =
                    LayoutInflater.from(parent.context).inflate(R.layout.series_item, parent, false)
                return SeriesViewHolder(view)
            }
        }
    }
}

class SeriesComparator : DiffUtil.ItemCallback<Series>() {
    override fun areItemsTheSame(oldItem: Series, newItem: Series): Boolean {
        return oldItem === newItem
    }

    override fun areContentsTheSame(oldItem: Series, newItem: Series): Boolean {
        return oldItem.image == newItem.image &&
                oldItem.title == newItem.title &&
                oldItem.season == newItem.season &&
                oldItem.episode == newItem.episode &&
                oldItem.minute == newItem.minute &&
                oldItem.second == newItem.second &&
                oldItem.state == newItem.state
    }
}