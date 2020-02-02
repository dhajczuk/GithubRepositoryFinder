package com.example.githubrepositoryfinder.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class ApiClient {

    private var apiInterface: ApiInterface? = null

    fun createApiInterface() {
        this.apiInterface = ApiClient.apiClient?.create(ApiInterface::class.java)
    }

    fun getApiInterface(): ApiInterface? {
        if (apiInterface == null) createApiInterface()

        return apiInterface
    }

    companion object {

        private val BASE_URL = "https://api.github.com"

        private var retrofit: Retrofit? = null

        val apiClient: Retrofit?
            get() {

                if (retrofit == null) {
                    retrofit = Retrofit.Builder().baseUrl(BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create()).build()
                }

                return retrofit
            }
    }

}