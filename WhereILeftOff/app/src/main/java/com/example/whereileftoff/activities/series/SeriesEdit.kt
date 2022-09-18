package com.example.whereileftoff.activities.series

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
import com.example.whereileftoff.databinding.EditSeriesBinding
import com.example.whereileftoff.fragments.SeriesFragment
import com.example.whereileftoff.models.Series
import com.example.whereileftoff.models.SeriesState
import kotlinx.coroutines.launch

class SeriesEdit : AppCompatActivity() {

    private lateinit var binding: EditSeriesBinding
    private val mediaViewModel: MediaViewModel by viewModels {
        MediaViewModelFactory((application as MediaApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = EditSeriesBinding.inflate(layoutInflater)
        val b: Bundle? = intent.extras
        val ids = b!!.getLong(SeriesFragment.EXTRA_IDS)

        lifecycleScope.launch {
            var series: Series = mediaViewModel.getSeriesById(ids)!!

            if (URLUtil.isValidUrl(series.image)) {
                Glide.with(baseContext).load(series.image).into(binding.ivImageS)
            } else {
                binding.ivImageS.setImageResource(R.drawable.ic_media)
            }

            binding.etTitleS.setText(series.title)
            binding.etSeasonS.setText(series.season.toString())
            binding.etEpisodeS.setText(series.episode.toString())
            binding.etMinuteS.setText(series.minute.toString())
            binding.etSecondS.setText(series.second.toString())
            when (series.state) {
                SeriesState.ToWatch -> {
                    binding.rgSetStateS.check(binding.rbToWatchS.id)
                }
                SeriesState.Watching -> {
                    binding.rgSetStateS.check(binding.rbWatchingS.id)
                }
                else -> {
                    binding.rgSetStateS.check(binding.rbWatchedS.id)
                }
            }

            binding.btnSaveS.setOnClickListener {
                val title = binding.etTitleS.text.toString()
                val season = binding.etSeasonS.text.toString().toInt()
                val episode = binding.etEpisodeS.text.toString().toInt()
                val minute = binding.etMinuteS.text.toString().toInt()
                val second = binding.etSecondS.text.toString().toInt()
                val state = when (binding.rgSetStateS.checkedRadioButtonId) {
                    R.id.rbToWatchS -> SeriesState.ToWatch
                    R.id.rbWatchingS -> SeriesState.Watching
                    R.id.rbWatchedS -> SeriesState.Watched
                    else -> SeriesState.ToWatch
                }
                val newSeries =
                    Series(series.id, title, season, episode, minute, second, series.image, state)
                mediaViewModel.update(newSeries)
                finish()
            }
            binding.btnDeleteS.setOnClickListener {
                mediaViewModel.delete(series)
                finish()
            }
        }

        binding.btnCancelS.setOnClickListener {
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
