package com.example.whereileftoff.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.recyclerview.widget.RecyclerView
import com.example.whereileftoff.R


class MoviesFragment : Fragment() {

    private lateinit var imageButton: ImageButton
    private lateinit var recyclerView: RecyclerView
    companion object{
        fun newInstance():MoviesFragment{
            return MoviesFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view:View = inflater.inflate(R.layout.fragment_movies, container, false)
        imageButton = view.findViewById(R.id.ibAddMovies)
        recyclerView = view.findViewById(R.id.recyclerViewMovies)

        return view
    }


}