package com.example.stockwatcher.api.services

import com.example.stockwatcher.data.models.Post
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET
import retrofit2.http.Path

interface MockAPIService {

    @GET("/everything?q={query}")
    fun getPosts(): Observable<List<Post>>

    @GET("/posts/{id}")
    fun getPostById(@Path("id") id: String): Observable<Post>
}