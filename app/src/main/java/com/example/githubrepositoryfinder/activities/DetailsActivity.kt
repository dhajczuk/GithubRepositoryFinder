package com.example.githubrepositoryfinder.activities

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import butterknife.BindView
import butterknife.ButterKnife
import com.example.githubrepositoryfinder.R
import com.example.githubrepositoryfinder.utils.CustomTextView
import com.squareup.picasso.Picasso

class DetailsActivity : AppCompatActivity() {

    @BindView(R.id.git_url)
    internal var gitUrl: CustomTextView? = null
    @BindView(R.id.clone_url)
    internal var cloneUrl: CustomTextView? = null
    @BindView(R.id.forks)
    internal var forks: CustomTextView? = null
    @BindView(R.id.watchers)
    internal var watchers: CustomTextView? = null
    @BindView(R.id.default_branch)
    internal var defaultBranch: CustomTextView? = null
    @BindView(R.id.profile_image)
    internal var profileImage: ImageView? = null


    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        ButterKnife.bind(this)

        gitUrl!!.text = "Git Url: " + intent.getStringExtra("gitUrl")
        cloneUrl!!.text = "Clone Url: " + intent.getStringExtra("cloneUrl")
        forks!!.text = "Forks: " + intent.getStringExtra("forks")
        watchers!!.text = "Watchers: " + intent.getStringExtra("watchers")
        defaultBranch!!.text = "Default Branch: " + intent.getStringExtra("defaultBranch")
        Picasso.get().load(intent.getStringExtra("avatarUrl")).into(profileImage)


    }
}
