package com.example.whereileftoff.activities.series

import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.MenuItem
import android.webkit.URLUtil
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.whereileftoff.R
import com.example.whereileftoff.adapters.SeriesAPIAdapter
import com.example.whereileftoff.databinding.DetailsSeriesBinding
import com.example.whereileftoff.models.SeriesAPIResponse
import okhttp3.*
import org.json.JSONObject
import java.io.IOException

class SeriesDetails : AppCompatActivity() {

    private val client = OkHttpClient()
    var model = SeriesAPIResponse()
    private lateinit var binding: DetailsSeriesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DetailsSeriesBinding.inflate(layoutInflater)
        val b: Bundle? = intent.extras
        val ids = b!!.getString(SeriesAPIAdapter.EXTRA_API_IDS)
        val urlDetails =
            "https://api.themoviedb.org/3/tv/" + ids + "?api_key=c48bcba9fac65b7a941f6350227e8345"
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
                model.title = jsonContact.getString("name")
                model.season = jsonContact.getString("number_of_seasons")
                model.episode = jsonContact.getString("number_of_episodes")
                model.yearReleased = jsonContact.getString("first_air_date")
                model.rating = jsonContact.getString("vote_average")
                model.overview = jsonContact.getString("overview")
                runOnUiThread {
                    bind(model)
                }
            }
        })
    }

    private fun bind(model: SeriesAPIResponse) {

        if (URLUtil.isValidUrl("https://image.tmdb.org/t/p/w500" + model.image) && model.image != "null") {
            Glide.with(this).load("https://image.tmdb.org/t/p/w500" + model.image)
                .into(binding.ivDetailsImageS)
        } else {
            binding.ivDetailsImageS.setImageResource(R.drawable.ic_media)
        }
        binding.tvDetailsTitleS.text = model.title
        binding.tvDetailsTitleS.isSelected = true
        binding.tvDetailsSeasonS.text = "Seasons: " + model.season
        binding.tvDetailsEpisodeS.text = "Episodes: " + model.episode
        if(model.yearReleased == ""){
            binding.tvDetailsYearReleasedS.text = "Not released"
        }else{
            binding.tvDetailsYearReleasedS.text =
                "Year Released: " + model.yearReleased.substring(0, 4) + "."
        }
        binding.tvDetailsRatingS.text = "Rating: " + model.rating
        binding.tvDetailsOverviewS.text = model.overview
        binding.tvDetailsOverviewS.movementMethod = ScrollingMovementMethod.getInstance()
    }
}