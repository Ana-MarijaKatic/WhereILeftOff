package com.example.whereileftoff.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.whereileftoff.R
import com.example.whereileftoff.SeriesAdd


class SeriesFragment : Fragment() {

    private lateinit var imageButton:ImageButton
    private lateinit var recyclerView:RecyclerView

    companion object{
        fun newInstance(): SeriesFragment{
            return SeriesFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view:View = inflater.inflate(R.layout.fragment_series, container, false)
        imageButton = view.findViewById(R.id.ibAddSeries)
        recyclerView = view.findViewById(R.id.recyclerViewSeries)

        return view
    }

}