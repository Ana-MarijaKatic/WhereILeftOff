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
import com.example.whereileftoff.activities.movie.MovieDetails
import com.example.whereileftoff.data.MediaViewModel
import com.example.whereileftoff.models.MediaArray
import com.example.whereileftoff.models.Movie

class MovieAPIAdapter(
    private var activity: Activity,
    private var context: Context,
    private var data: List<MediaArray>
) : RecyclerView.Adapter<MovieAPIAdapter.MovieAPIViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieAPIViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.list_movie_item, parent, false)
        return MovieAPIViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieAPIAdapter.MovieAPIViewHolder, position: Int) {
        Glide.with(context).load("https://image.tmdb.org/t/p/w500" + data[position].image)
            .into(holder.image)
        holder.title.text = data[position].title

        holder.addButton.setOnClickListener {
            val replyIntent = Intent()
            replyIntent.putExtra(EXTRA_API_TITLEM, data[position].title)
            replyIntent.putExtra(EXTRA_API_IMAGEM, data[position].image)
            activity.setResult(Activity.RESULT_OK, replyIntent)
            activity.finish()
        }

        holder.cardView.setOnClickListener {
            val intent = Intent(context, MovieDetails::class.java)
            intent.putExtra(EXTRA_API_IDM, data[position].id)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_TASK_ON_HOME
            context.startActivity(intent)
        }
    }

    override fun getItemCount() = data.size

    class MovieAPIViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image: ImageView = itemView.findViewById(R.id.ivBrowseImageM)
        val title: TextView = itemView.findViewById(R.id.tvBrowseTitleM)
        val addButton: ImageButton = itemView.findViewById(R.id.ibBrowseAddM)
        val cardView: CardView = itemView.findViewById(R.id.cvMovies)

        fun save(movie: Movie, viewModel: MediaViewModel) {
            addButton.setOnClickListener {
                viewModel.save(movie)
            }
        }
    }

    companion object {
        const val EXTRA_API_IDM = "com.example.android.movieapiidsql.REPLY"
        const val EXTRA_API_TITLEM = "com.example.android.movietitlesql.REPLY"
        const val EXTRA_API_IMAGEM = "com.example.android.movieimagesql.REPLY"
    }
}