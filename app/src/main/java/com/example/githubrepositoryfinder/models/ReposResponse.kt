package com.example.githubrepositoryfinder.models

import com.google.gson.annotations.SerializedName

class ReposResponse {

    @SerializedName("items")
    val itemsList: List<Repo>? = null
}