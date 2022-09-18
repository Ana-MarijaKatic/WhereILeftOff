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
import com.example.whereileftoff.activities.series.SeriesAdd
import com.example.whereileftoff.activities.series.SeriesEdit
import com.example.whereileftoff.adapters.SeriesAdapter
import com.example.whereileftoff.data.MediaApplication
import com.example.whereileftoff.data.MediaViewModel
import com.example.whereileftoff.data.MediaViewModelFactory
import com.example.whereileftoff.listeners.OnMediaEventListener
import com.example.whereileftoff.models.Series
import com.example.whereileftoff.models.SeriesState


class SeriesFragment : Fragment(), OnMediaEventListener {

    private lateinit var imageButton: ImageButton
    private lateinit var recyclerView: RecyclerView
    private lateinit var seriesAdapter: SeriesAdapter
    private val mediaViewModel: MediaViewModel by activityViewModels {
        MediaViewModelFactory((requireActivity().application as MediaApplication).repository)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_series, container, false)
        imageButton = view.findViewById(R.id.ibAddSeries)
        recyclerView = view.findViewById(R.id.recyclerViewSeries)
        seriesAdapter = SeriesAdapter()
        seriesAdapter.onSeriesItemSelectedListener = this
        recyclerView.adapter = seriesAdapter
        recyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)


        var saveResultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->

                if (result.resultCode == Activity.RESULT_OK && result.data != null) {
                    val title = result.data?.getStringExtra(SeriesAdd.EXTRA_TITLES).toString()
                    val season = returnOneIfEmpty(
                        result.data?.getStringExtra(SeriesAdd.EXTRA_SEASONS).toString()
                    )
                    val episode = returnOneIfEmpty(
                        result.data?.getStringExtra(SeriesAdd.EXTRA_EPISODES).toString()
                    )
                    val minute = returnZeroIfEmpty(
                        result.data?.getStringExtra(SeriesAdd.EXTRA_MINUTES).toString()
                    )
                    val second = returnZeroIfEmpty(
                        result.data?.getStringExtra(SeriesAdd.EXTRA_SECONDS).toString()
                    )
                    val image = result.data?.getStringExtra(SeriesAdd.EXTRA_IMAGES).toString()
                    val state =
                        when (result.data?.getStringExtra(SeriesAdd.EXTRA_STATES).toString()) {
                            "ToWatch" -> SeriesState.ToWatch
                            "Watching" -> SeriesState.Watching
                            "Watched" -> SeriesState.Watched
                            else -> SeriesState.ToWatch
                        }

                    val series = Series(0, title, season, episode, minute, second, image, state)
                    mediaViewModel.save(series)
                    Toast.makeText(activity, "Series $title is saved.", Toast.LENGTH_LONG).show()

                } else {
                    Toast.makeText(activity, "Series is not saved.", Toast.LENGTH_LONG).show()
                }
            }

        imageButton.setOnClickListener {
            val intent = Intent(context, SeriesAdd::class.java)
            saveResultLauncher.launch(intent)
        }
        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mediaViewModel.allSeries.observe(viewLifecycleOwner) { series ->
            series.let { seriesAdapter.submitList(it) }
        }
    }

    private fun returnZeroIfEmpty(item: String): Int {
        if (item == "") return 0
        return item.toInt()
    }

    private fun returnOneIfEmpty(item: String): Int {
        if (item == "") return 1
        return item.toInt()
    }

    override fun onMediaSelected(id: Long) {
        val intent = Intent(context, SeriesEdit::class.java)
        intent.putExtra(EXTRA_IDS, id)
        startActivity(intent)

    }

    companion object {
        const val EXTRA_IDS = "com.example.android.seriesidsql.REPLY"
    }
}