package com.example.githubrepositoryfinder.models

import java.security.acl.Owner
import com.google.gson.annotations.SerializedName

class Repo {

    @SerializedName("full_name")
    val fullName: String? = null

    @SerializedName("description")
    val description: String? = null

    @SerializedName("updated_at")
    val updatedAt: String? = null

    @SerializedName("stargazers_count")
    val stargazers_count: String? = null

    @SerializedName("language")
    val language: String? = null

    @SerializedName("owner")
    val itemOwner: Owner? = null

    @SerializedName("git_url")
    val gitUrl: String? = null

    @SerializedName("clone_url")
    val cloneUrl: String? = null

    @SerializedName("forks")
    val forks: String? = null

    @SerializedName("watchers")
    val watchers: String? = null

    @SerializedName("default_branch")
    val defaultBranch: String? = null
}
