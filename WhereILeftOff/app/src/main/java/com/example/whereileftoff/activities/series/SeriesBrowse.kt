package com.example.whereileftoff.activities.series

import android.content.res.Configuration
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.whereileftoff.R
import com.example.whereileftoff.adapters.SeriesAPIAdapter
import com.example.whereileftoff.models.MediaArray
import okhttp3.*
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException

class SeriesBrowse : AppCompatActivity() {

    private val urlSeries =
        "https://api.themoviedb.org/3/tv/popular?api_key=c48bcba9fac65b7a941f6350227e8345"
    private val client = OkHttpClient()
    var seriesList = ArrayList<MediaArray>()
    private lateinit var recyclerView: RecyclerView
    private lateinit var searchView: SearchView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.browse_series)
        searchView = findViewById(R.id.swSearchS)
        recyclerView = findViewById(R.id.recyclerViewBrowseSeries)
        val orientation = resources.configuration.orientation
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            recyclerView.layoutManager = GridLayoutManager(applicationContext, 2)
        } else {
            recyclerView.layoutManager = GridLayoutManager(applicationContext, 4)
        }

        run(urlSeries)

        searchView = findViewById(R.id.swSearchS)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                val searchItem = query?.replace(" ", "+").toString()
                val searchUrl =
                    "https://api.themoviedb.org/3/search/tv?api_key=c48bcba9fac65b7a941f6350227e8345"
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
                val jsonArraySeries: JSONArray = jsonContact.getJSONArray("results")
                seriesList = ArrayList()
                for (i in 0 until jsonArraySeries.length()) {
                    val jsonObjectDetails: JSONObject = jsonArraySeries.getJSONObject(i)
                    val model = MediaArray()
                    model.id = jsonObjectDetails.getString("id")
                    model.image = jsonObjectDetails.getString("poster_path")
                    model.title = jsonObjectDetails.getString("name")
                    seriesList.add(model)

                }
                runOnUiThread {
                    recyclerView.adapter =
                        SeriesAPIAdapter(this@SeriesBrowse, applicationContext, seriesList)
                }
            }
        })
    }
}