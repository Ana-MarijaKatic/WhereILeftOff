package com.example.whereileftoff.fragments

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.whereileftoff.R
import com.example.whereileftoff.activities.movie.MovieAdd
import com.example.whereileftoff.activities.movie.MovieEdit
import com.example.whereileftoff.adapters.MovieAdapter
import com.example.whereileftoff.data.MediaApplication
import com.example.whereileftoff.data.MediaViewModel
import com.example.whereileftoff.data.MediaViewModelFactory
import com.example.whereileftoff.listeners.OnMediaEventListener
import com.example.whereileftoff.models.Movie
import com.example.whereileftoff.models.MovieState


class MoviesFragment : Fragment(), OnMediaEventListener {

    private lateinit var imageButton: ImageButton
    private lateinit var recyclerView: RecyclerView
    private lateinit var movieAdapter: MovieAdapter
    private val mediaViewModel: MediaViewModel by activityViewModels {
        MediaViewModelFactory((requireActivity().application as MediaApplication).repository)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_movies, container, false)
        imageButton = view.findViewById(R.id.ibAddMovies)
        recyclerView = view.findViewById(R.id.recyclerViewMovies)
        movieAdapter = MovieAdapter()
        movieAdapter.onMovieItemSelectedListener = this
        recyclerView.adapter = movieAdapter
        recyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        var saveResultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->

                if (result.resultCode == Activity.RESULT_OK && result.data != null) {
                    val title = result.data?.getStringExtra(MovieAdd.EXTRA_TITLEM).toString()
                    val minute = returnZeroIfEmpty(
                        result.data?.getStringExtra(MovieAdd.EXTRA_MINUTEM).toString()
                    )
                    val second = returnZeroIfEmpty(
                        result.data?.getStringExtra(MovieAdd.EXTRA_SECONDM).toString()
                    )
                    val image = result.data?.getStringExtra(MovieAdd.EXTRA_IMAGEM).toString()
                    val state =
                        when (result.data?.getStringExtra(MovieAdd.EXTRA_STATEM).toString()) {
                            "ToWatch" -> MovieState.ToWatch
                            "Watching" -> MovieState.Watching
                            "Watched" -> MovieState.Watched
                            else -> MovieState.ToWatch
                        }

                    val movie = Movie(0, title, minute, second, image, state)
                    mediaViewModel.save(movie)
                    Toast.makeText(activity, "Movie $title is saved.", Toast.LENGTH_LONG).show()

                } else {
                    Toast.makeText(activity, "Movie is not saved.", Toast.LENGTH_LONG).show()
                }
            }

        imageButton.setOnClickListener {
            val intent = Intent(context, MovieAdd::class.java)
            saveResultLauncher.launch(intent)
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mediaViewModel.allMovies.observe(viewLifecycleOwner) { movies ->
            movies.let { movieAdapter.submitList(it) }
        }
    }

    private fun returnZeroIfEmpty(item: String): Int {
        if (item == "") return 0
        return item.toInt()
    }

    override fun onMediaSelected(id: Long) {
        val intent = Intent(context, MovieEdit::class.java)
        intent.putExtra(EXTRA_IDM, id)
        startActivity(intent)

    }

    companion object {
        const val EXTRA_IDM = "com.example.android.seriesidsql.REPLY"
    }

}