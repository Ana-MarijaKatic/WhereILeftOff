package com.example.whereileftoff.activities.movie

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.MenuItem
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.whereileftoff.R
import com.example.whereileftoff.adapters.MovieAPIAdapter
import com.example.whereileftoff.databinding.AddMovieBinding
import com.example.whereileftoff.models.MovieState

class MovieAdd : AppCompatActivity() {

    private lateinit var binding: AddMovieBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = AddMovieBinding.inflate(layoutInflater)

        var displayResultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK && result.data != null) {
                    val imageAPI = result.data?.getStringExtra(MovieAPIAdapter.EXTRA_API_IMAGEM)
                    val titleAPI = result.data?.getStringExtra(MovieAPIAdapter.EXTRA_API_TITLEM)

                    binding.etEnterTitleM.setText(titleAPI)
                    binding.etEnterMinuteM.setText("0")
                    binding.etEnterSecondM.setText("0")
                    if (imageAPI != "null") {
                        binding.etEnterImageM.setText("https://image.tmdb.org/t/p/w500" + imageAPI)
                    } else {
                        binding.etEnterImageM.setText("")
                    }

                    binding.rbEnterToWatchS.isChecked
                }
            }

        binding.btnEnterBrowseM.setOnClickListener {
            val intent = Intent(this, MoviesBrowse::class.java)
            displayResultLauncher.launch(intent)
        }

        binding.btnEnterSaveM.setOnClickListener {
            val replyIntent = Intent()
            if (TextUtils.isEmpty(binding.etEnterTitleM.text)) {
                setResult(Activity.RESULT_CANCELED, replyIntent)
            } else {
                val title = binding.etEnterTitleM.text.toString()
                val minute = binding.etEnterMinuteM.text.toString()
                val second = binding.etEnterSecondM.text.toString()
                val image = binding.etEnterImageM.text.toString()
                val state = when (binding.rgEnterSetStateM.checkedRadioButtonId) {
                    R.id.rbEnterToWatchS -> MovieState.ToWatch.toString()
                    R.id.rbEnterWatchingS -> MovieState.Watching.toString()
                    R.id.rbEnterWatchedS -> MovieState.Watched.toString()
                    else -> MovieState.ToWatch.toString()
                }

                replyIntent.putExtra(EXTRA_TITLEM, title)
                replyIntent.putExtra(EXTRA_MINUTEM, minute)
                replyIntent.putExtra(EXTRA_SECONDM, second)
                replyIntent.putExtra(EXTRA_IMAGEM, image)
                replyIntent.putExtra(EXTRA_STATEM, state)

                setResult(Activity.RESULT_OK, replyIntent)
            }
            finish()
        }

        binding.btnEnterCancelM.setOnClickListener {
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
        const val EXTRA_TITLEM = "com.example.android.movietitlesql.REPLY"
        const val EXTRA_MINUTEM = "com.example.android.movieminutesql.REPLY"
        const val EXTRA_SECONDM = "com.example.android.moviesecondsql.REPLY"
        const val EXTRA_IMAGEM = "com.example.android.movieimagesql.REPLY"
        const val EXTRA_STATEM = "com.example.android.moviestatesql.REPLY"
    }
}