package com.example.stockwatcher.ui.fragments.news

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.stockwatcher.R
import com.example.stockwatcher.api.models.News
import com.example.stockwatcher.api.models.NewsApiResponse
import com.example.stockwatcher.databinding.FragmentNewsBinding
import com.example.stockwatcher.di.component.DaggerFragmentComponent
import com.example.stockwatcher.di.component.FragmentComponent
import com.example.stockwatcher.di.module.ViewModelModule
import javax.inject.Inject

class NewsFragment : Fragment(), NewsNavigator{

    lateinit var binding: FragmentNewsBinding
    var adapter: NewsAdapter = NewsAdapter()

    @Inject
    lateinit var viewModel: NewsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        buildComponent().inject(this)
        viewModel.setNavigator(this)
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_news, container, false)

        val recyclerView = binding.newsRecyclerView
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(binding.root.context)

        viewModel.getNews()

        return binding.root
    }

    override fun processResponse(response: NewsApiResponse) {
        adapter.updateNewsDatastore(response.articles as ArrayList<News>)
        adapter.notifyDataSetChanged()
    }

    override fun handleError(error: Throwable) {
        Toast.makeText(activity, "Error retriving news", Toast.LENGTH_SHORT).show()
    }

    fun buildComponent(): FragmentComponent {
        return DaggerFragmentComponent.builder()
                .viewModelModule(ViewModelModule())
                .build()
    }
}
