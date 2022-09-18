package com.example.whereileftoff.activities.movie

import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.MenuItem
import android.webkit.URLUtil
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.whereileftoff.R
import com.example.whereileftoff.adapters.MovieAPIAdapter
import com.example.whereileftoff.databinding.DetailsMovieBinding
import com.example.whereileftoff.models.MovieAPIResponse
import okhttp3.*
import org.json.JSONObject
import java.io.IOException

class MovieDetails : AppCompatActivity() {

    private val client = OkHttpClient()
    var model = MovieAPIResponse()
    private lateinit var binding: DetailsMovieBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DetailsMovieBinding.inflate(layoutInflater)
        val b: Bundle? = intent.extras
        val idm = b!!.getString(MovieAPIAdapter.EXTRA_API_IDM)
        val urlDetails =
            "https://api.themoviedb.org/3/movie/" + idm + "?api_key=c48bcba9fac65b7a941f6350227e8345"
        run(urlDetails)

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

    private fun run(url: String) {

        val request = Request.Builder().url(url).build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {}

            override fun onResponse(call: Call, response: Response) {
                val jsonContact = JSONObject(response.body!!.string())
                model.id = jsonContact.getString("id")
                model.image = jsonContact.getString("poster_path")
                model.title = jsonContact.getString("title")
                model.runtime = jsonContact.getString("runtime")
                model.yearReleased = jsonContact.getString("release_date")
                model.rating = jsonContact.getString("vote_average")
                model.overview = jsonContact.getString("overview")
                runOnUiThread {
                    bind(model)
                }
            }
        })
    }

    private fun bind(model: MovieAPIResponse) {

        if (URLUtil.isValidUrl("https://image.tmdb.org/t/p/w500" + model.image) && model.image != "null") {
            Glide.with(this).load("https://image.tmdb.org/t/p/w500" + model.image)
                .into(binding.ivDetailsImageM)
        } else {
            binding.ivDetailsImageM.setImageResource(R.drawable.ic_media)
        }
        binding.tvDetailsTitleM.text = model.title
        binding.tvDetailsTitleM.isSelected = true
        binding.tvDetailsRuntimeM.text = "Runtime: " + model.runtime + " min"
        if(model.yearReleased == ""){
            binding.tvDetailsYearReleasedM.text = "Not released"
        }else{
            binding.tvDetailsYearReleasedM.text =
                "Year Released: " + model.yearReleased.substring(0, 4) + "."
        }
        binding.tvDetailsRatingM.text = "Rating: " + model.rating
        binding.tvDetailsOverviewM.text = model.overview
        binding.tvDetailsOverviewM.movementMethod = ScrollingMovementMethod.getInstance()

    }
}