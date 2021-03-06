package com.example.stockwatcher.ui.fragments.news

import android.util.Log
import com.example.stockwatcher.api.RetrofitClientInstance
import com.example.stockwatcher.api.services.NewsApiService
import com.example.stockwatcher.ui.base.BaseViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers


class NewsViewModel : BaseViewModel<NewsNavigator>(){
    lateinit var newsApiService: NewsApiService

    init{
       start()
    }

    private fun start(){
        createService()
    }

    fun createService(){
        newsApiService = RetrofitClientInstance().instance()!!.create(NewsApiService::class.java)
    }

    fun getNews() {
        var getNews = newsApiService.getNews("bitcoin")
        var disposable = getNews.subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    Log.d("Retrofit", "Success")
                    getNavigator()!!.processResponse(it)
                },{
                    Log.d("Retrofit Error", "${it}")
                    getNavigator()!!.handleError(it)
                })

        addToDisposable(disposable)
    }
}