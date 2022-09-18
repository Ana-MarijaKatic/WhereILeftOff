package com.example.whereileftoff.activities.movie

import android.content.res.Configuration
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.whereileftoff.R
import com.example.whereileftoff.adapters.MovieAPIAdapter
import com.example.whereileftoff.models.MediaArray
import okhttp3.*
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException

class MoviesBrowse : AppCompatActivity() {

    private val urlMovies =
        "https://api.themoviedb.org/3/movie/popular?api_key=c48bcba9fac65b7a941f6350227e8345"
    private val client = OkHttpClient()
    var moviesList = ArrayList<MediaArray>()
    private lateinit var recyclerView: RecyclerView
    private lateinit var searchView: SearchView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.browse_movies)
        searchView = findViewById(R.id.swSearchM)
        recyclerView = findViewById(R.id.recyclerViewBrowseMovies)
        val orientation = resources.configuration.orientation
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            recyclerView.layoutManager = GridLayoutManager(applicationContext, 2)
        } else {
            recyclerView.layoutManager = GridLayoutManager(applicationContext, 4)
        }

        run(urlMovies)

        searchView = findViewById(R.id.swSearchM)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                val searchItem = query?.replace(" ", "+").toString()
                val searchUrl =
                    "https://api.themoviedb.org/3/search/movie?api_key=c48bcba9fac65b7a941f6350227e8345"
                val searchUrlItem = "$searchUrl&query=$searchItem"
                run(searchUrlItem)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }

        })
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
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
                val jsonArrayMovies: JSONArray = jsonContact.getJSONArray("results")
                moviesList = ArrayList()
                for (i in 0 until jsonArrayMovies.length()) {
                    val jsonObjectDetails: JSONObject = jsonArrayMovies.getJSONObject(i)
                    val model = MediaArray()
                    model.id = jsonObjectDetails.getString("id")
                    model.image = jsonObjectDetails.getString("poster_path")
                    model.title = jsonObjectDetails.getString("title")
                    moviesList.add(model)

                }
                runOnUiThread {
                    recyclerView.adapter =
                        MovieAPIAdapter(this@MoviesBrowse, applicationContext, moviesList)
                }
            }
        })
    }
}