package com.example.whereileftoff.activities.movie

import android.os.Bundle
import android.view.MenuItem
import android.webkit.URLUtil
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.whereileftoff.R
import com.example.whereileftoff.data.MediaApplication
import com.example.whereileftoff.data.MediaViewModel
import com.example.whereileftoff.data.MediaViewModelFactory
import com.example.whereileftoff.databinding.EditMovieBinding
import com.example.whereileftoff.fragments.MoviesFragment
import com.example.whereileftoff.models.Movie
import com.example.whereileftoff.models.MovieState
import kotlinx.coroutines.launch

class MovieEdit : AppCompatActivity() {

    private lateinit var binding: EditMovieBinding
    private val mediaViewModel: MediaViewModel by viewModels {
        MediaViewModelFactory((application as MediaApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = EditMovieBinding.inflate(layoutInflater)
        val b: Bundle? = intent.extras
        val idm = b!!.getLong(MoviesFragment.EXTRA_IDM)

        lifecycleScope.launch {
            var movie: Movie = mediaViewModel.getMovieById(idm)!!

            if (URLUtil.isValidUrl(movie.image)) {
                Glide.with(baseContext).load(movie.image).into(binding.ivImageM)
            } else {
                binding.ivImageM.setImageResource(R.drawable.ic_media)
            }

            binding.etTitleM.setText(movie.title)
            binding.etMinuteM.setText(movie.minute.toString())
            binding.etSecondM.setText(movie.second.toString())
            when (movie.state) {
                MovieState.ToWatch -> {
                    binding.rgSetStateM.check(binding.rbToWatchM.id)
                }
                MovieState.Watching -> {
                    binding.rgSetStateM.check(binding.rbWatchingM.id)
                }
                else -> {
                    binding.rgSetStateM.check(binding.rbWatchedM.id)
                }
            }

            binding.btnSaveM.setOnClickListener {
                val title = binding.etTitleM.text.toString()
                val minute = binding.etMinuteM.text.toString().toInt()
                val second = binding.etSecondM.text.toString().toInt()
                val state = when (binding.rgSetStateM.checkedRadioButtonId) {
                    R.id.rbToWatchM -> MovieState.ToWatch
                    R.id.rbWatchingM -> MovieState.Watching
                    R.id.rbWatchedM -> MovieState.Watched
                    else -> MovieState.ToWatch
                }
                val newMovie =
                    Movie(movie.id, title, minute, second, movie.image, state)
                mediaViewModel.update(newMovie)
                finish()
            }
            binding.btnDeleteM.setOnClickListener {
                mediaViewModel.delete(movie)
                finish()
            }
        }

        binding.btnCancelM.setOnClickListener {
            finish()
        }

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        setContentView(binding.root)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onContextItemSelected(item)
    }
}