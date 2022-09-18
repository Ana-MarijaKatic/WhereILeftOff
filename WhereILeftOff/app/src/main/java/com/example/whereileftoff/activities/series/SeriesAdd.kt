package com.example.whereileftoff.activities.series

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.MenuItem
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.whereileftoff.R
import com.example.whereileftoff.adapters.SeriesAPIAdapter
import com.example.whereileftoff.databinding.AddSeriesBinding
import com.example.whereileftoff.models.SeriesState

class SeriesAdd : AppCompatActivity() {

    private lateinit var binding: AddSeriesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = AddSeriesBinding.inflate(layoutInflater)

        var displayResultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK && result.data != null) {
                    val imageAPI = result.data?.getStringExtra(SeriesAPIAdapter.EXTRA_API_IMAGES)
                    val titleAPI = result.data?.getStringExtra(SeriesAPIAdapter.EXTRA_API_TITLES)

                    binding.etEnterTitleS.setText(titleAPI)
                    binding.etEnterSeasonS.setText("1")
                    binding.etEnterEpisodeS.setText("1")
                    binding.etEnterMinuteS.setText("0")
                    binding.etEnterSecondS.setText("0")
                    if (imageAPI != "null") {
                        binding.etEnterImageS.setText("https://image.tmdb.org/t/p/w500" + imageAPI)
                    } else {
                        binding.etEnterImageS.setText("")
                    }

                    binding.rbEnterToWatchS.isChecked
                }
            }

        binding.btnEnterBrowseS.setOnClickListener {
            val intent = Intent(this, SeriesBrowse::class.java)
            displayResultLauncher.launch(intent)
        }

        binding.btnEnterSaveS.setOnClickListener {
            val replyIntent = Intent()
            if (TextUtils.isEmpty(binding.etEnterTitleS.text)) {
                setResult(Activity.RESULT_CANCELED, replyIntent)
            } else {
                val title = binding.etEnterTitleS.text.toString()
                val season = binding.etEnterSeasonS.text.toString()
                val episode = binding.etEnterEpisodeS.text.toString()
                val minute = binding.etEnterMinuteS.text.toString()
                val second = binding.etEnterSecondS.text.toString()
                val image = binding.etEnterImageS.text.toString()
                val state = when (binding.rgEnterSetStateS.checkedRadioButtonId) {
                    R.id.rbEnterToWatchS -> SeriesState.ToWatch.toString()
                    R.id.rbEnterWatchingS -> SeriesState.Watching.toString()
                    R.id.rbEnterWatchedS -> SeriesState.Watched.toString()
                    else -> SeriesState.ToWatch.toString()
                }

                replyIntent.putExtra(EXTRA_TITLES, title)
                replyIntent.putExtra(EXTRA_SEASONS, season)
                replyIntent.putExtra(EXTRA_EPISODES, episode)
                replyIntent.putExtra(EXTRA_MINUTES, minute)
                replyIntent.putExtra(EXTRA_SECONDS, second)
                replyIntent.putExtra(EXTRA_IMAGES, image)
                replyIntent.putExtra(EXTRA_STATES, state)

                setResult(Activity.RESULT_OK, replyIntent)
            }
            finish()
        }

        binding.btnEnterCancelS.setOnClickListener {
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

    companion object {
        const val EXTRA_TITLES = "com.example.android.seriestitlesql.REPLY"
        const val EXTRA_SEASONS = "com.example.android.seriesseasonsql.REPLY"
        const val EXTRA_EPISODES = "com.example.android.seriesepisodesql.REPLY"
        const val EXTRA_MINUTES = "com.example.android.seriesminutesql.REPLY"
        const val EXTRA_SECONDS = "com.example.android.seriessecondsql.REPLY"
        const val EXTRA_IMAGES = "com.example.android.seriesimagesql.REPLY"
        const val EXTRA_STATES = "com.example.android.seriesstatesql.REPLY"
    }
}