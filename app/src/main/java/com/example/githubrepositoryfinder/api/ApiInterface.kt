package com.example.githubrepositoryfinder.api

import com.example.githubrepositoryfinder.models.ReposResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {

    @GET("/search/repositories")
    fun getRepositories(
        @Query("q") query: String,
        @Query("sort") sort: String,
        @Query("order") order: String
    ): Call<ReposResponse>

}
