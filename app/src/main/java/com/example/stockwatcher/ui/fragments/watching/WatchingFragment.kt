package com.example.stockwatcher.ui.fragments.watching

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.stockwatcher.R

class WatchingFragment : Fragment() {

//    lateinit var mockApiService: MockAPIService

    var recyclerView: RecyclerView? = null;


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_watching, container, false)
        recyclerView = view.findViewById(R.id.stock_recycler_view)
        recyclerView!!.adapter =
            WatchingAdapter()
        recyclerView!!.layoutManager = LinearLayoutManager(view.context)
        return view;
    }

}