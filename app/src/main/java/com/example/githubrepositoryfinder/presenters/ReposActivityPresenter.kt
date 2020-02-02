package com.example.githubrepositoryfinder.presenters

import com.example.githubrepositoryfinder.api.ApiClient
import com.example.githubrepositoryfinder.models.ReposResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ReposActivityPresenter(private val repoListener: RepoListener) {

    private var reposCall: Call<ReposResponse>? = null

    interface RepoListener {
        fun reposOnResponseListener(
            reposResponse: ReposResponse?,
            query: String,
            isNewQuery: Boolean
        )

        fun reposOnFailureListener()
    }

    fun getRepositories(query: String, isNewQuery: Boolean) {

        val apiClient = ApiClient()
        if (reposCall != null) reposCall!!.cancel()

        reposCall = apiClient.getApiInterface()?.getRepositories(query, "stars", "desc")
        reposCall!!.enqueue(object : Callback<ReposResponse> {

            override fun onResponse(call: Call<ReposResponse>, response: Response<ReposResponse>) {
                if (response.code() == 200) {
                    val reposResponse = response.body()
                    if (reposResponse != null)
                        repoListener.reposOnResponseListener(reposResponse, query, isNewQuery)
                }

            }

            override fun onFailure(call: Call<ReposResponse>, t: Throwable) {
                repoListener.reposOnFailureListener()
            }
        })

    }

}