package com.example.whereileftoff.adapters

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.whereileftoff.R
import com.example.whereileftoff.activities.series.SeriesDetails
import com.example.whereileftoff.data.MediaViewModel
import com.example.whereileftoff.models.MediaArray
import com.example.whereileftoff.models.Series

class SeriesAPIAdapter(
    private var activity: Activity,
    private var context: Context,
    private var data: List<MediaArray>
) :
    RecyclerView.Adapter<SeriesAPIAdapter.SeriesAPIViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SeriesAPIViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.list_series_item, parent, false)
        return SeriesAPIViewHolder(view)
    }

    override fun onBindViewHolder(holder: SeriesAPIViewHolder, position: Int) {
        Glide.with(context).load("https://image.tmdb.org/t/p/w500" + data[position].image)
            .into(holder.image)
        holder.title.text = data[position].title

        holder.addButton.setOnClickListener {
            val replyIntent = Intent()
            replyIntent.putExtra(EXTRA_API_TITLES, data[position].title)
            replyIntent.putExtra(EXTRA_API_IMAGES, data[position].image)
            activity.setResult(Activity.RESULT_OK, replyIntent)
            activity.finish()
        }

        holder.cardView.setOnClickListener {
            val intent = Intent(context, SeriesDetails::class.java)
            intent.putExtra(EXTRA_API_IDS, data[position].id)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_TASK_ON_HOME
            context.startActivity(intent)
        }
    }

    override fun getItemCount() = data.size

    class SeriesAPIViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image: ImageView = itemView.findViewById(R.id.ivBrowseImageS)
        val title: TextView = itemView.findViewById(R.id.tvBrowseTitleS)
        val addButton: ImageButton = itemView.findViewById(R.id.ibBrowseAddS)
        val cardView: CardView = itemView.findViewById(R.id.cvSeries)

        fun save(series: Series, viewModel: MediaViewModel) {
            addButton.setOnClickListener {
                viewModel.save(series)
            }
        }
    }

    companion object {
        const val EXTRA_API_IDS = "com.example.android.seriesapiidssql.REPLY"
        const val EXTRA_API_TITLES = "com.example.android.seriestitlessql.REPLY"
        const val EXTRA_API_IMAGES = "com.example.android.seriesimagessql.REPLY"
    }
}

