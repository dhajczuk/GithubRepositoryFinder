package com.example.githubrepositoryfinder.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import butterknife.ButterKnife
import com.example.githubrepositoryfinder.R
import com.example.githubrepositoryfinder.activities.DetailsActivity
import com.example.githubrepositoryfinder.models.Repo
import com.example.githubrepositoryfinder.utils.CustomTextView
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class ReposAdapter(val repositories: List<Repo>, private val context: Context) :
    RecyclerView.Adapter<ReposAdapter.MyViewHolder>() {

    override fun getItemCount(): Int {
        return repositories.size
    }

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        @BindView(R.id.avatar_url)
        internal var avatarURL: ImageView? = null
        @BindView(R.id.full_name)
        internal var fullName: CustomTextView? = null
        @BindView(R.id.description)
        internal var description: CustomTextView? = null
        @BindView(R.id.updated_at)
        internal var updatedAt: CustomTextView? = null
        @BindView(R.id.stargazers_count)
        internal var stargazersCount: CustomTextView? = null
        @BindView(R.id.language)
        internal var language: CustomTextView? = null

        init {

            ButterKnife.bind(this, view)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.repository_layout, parent, false)

        return MyViewHolder(itemView)
    }

    override  fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.fullName!!.text = repositories[position].fullName
        holder.description!!.text = repositories[position].description
        holder.updatedAt!!.text = changeDate(repositories[position].updatedAt)
        holder.stargazersCount!!.text = repositories[position].stargazers_count
        holder.language!!.text = repositories[position].language
//        Picasso.get().load(repositories[position].itemOwner)
//            .into(holder.avatarURL)

        holder.itemView.setOnClickListener { view ->
            val intent = Intent(view.context, DetailsActivity::class.java)
            intent.putExtra("gitUrl", repositories[position].gitUrl)
            intent.putExtra("cloneUrl", repositories[position].cloneUrl)
            intent.putExtra("forks", repositories[position].forks)
            intent.putExtra("watchers", repositories[position].watchers)
            intent.putExtra("defaultBranch", repositories[position].defaultBranch)
//            intent.putExtra("avatarUrl", repositories[position].itemOwner)
            view.context.startActivity(intent)
        }
    }

    fun changeDate(date: String?): String {
        val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
        val output = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        var d: Date? = null
        try {
            d = sdf.parse(date)
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        return output.format(d)
    }

}

