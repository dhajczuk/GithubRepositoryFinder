package com.example.githubrepositoryfinder.activities

import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.example.githubrepositoryfinder.R
import com.example.githubrepositoryfinder.adapters.ReposAdapter
import com.example.githubrepositoryfinder.models.ReposResponse
import com.example.githubrepositoryfinder.presenters.ReposActivityPresenter

class ReposActivity : AppCompatActivity(), ReposActivityPresenter.RepoListener {
    override fun reposOnResponseListener(
        reposResponse: ReposResponse?,
        query: String,
        isNewQuery: Boolean
    ) {
        currentQuery = query

        if (isNewQuery) {
            if (reposResponse != null) {
                reposAdapter = reposResponse.itemsList?.let { ReposAdapter(it, this) }
            }
            repositoriesRecyclerView!!.adapter = reposAdapter
        } else
            Toast.makeText(
                applicationContext,
                applicationContext.resources.getString(R.string.old_query),
                Toast.LENGTH_SHORT
            ).show()


        loadingFlag = false

    }

    internal var reposActivityPresenter: ReposActivityPresenter? = null

    @BindView(R.id.search_edit_text)
    internal var searchEditText: EditText? = null
    @BindView(R.id.search_button)
    internal var searchImageButton: Button? = null
    @BindView(R.id.rvRepos)
    internal var repositoriesRecyclerView: RecyclerView? = null

    private var currentQuery: String? = ""
    private val defaultQuery = "Android"
    private var loadingFlag = false

    private var reposAdapter: ReposAdapter? = null

    private var toast: Toast? = null

    val isOnline: Boolean
        get() {
            val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val netInfo = cm.activeNetworkInfo
            return netInfo != null && netInfo.isConnectedOrConnecting
        }

     override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_repositories)

        ButterKnife.bind(this)

        reposActivityPresenter = ReposActivityPresenter(this)
        repositoriesRecyclerView!!.setHasFixedSize(true)
        val linearLayoutManager = LinearLayoutManager(applicationContext)
         repositoriesRecyclerView!!.layoutManager = linearLayoutManager


        val mScrollListener = object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (!loadingFlag) {
                    val lastVisibleItemPosition =
                        (recyclerView.layoutManager as LinearLayoutManager).findLastCompletelyVisibleItemPosition()
                    if (lastVisibleItemPosition != RecyclerView.NO_POSITION && lastVisibleItemPosition == (recyclerView.adapter?.itemCount)?.minus(
                            1
                        ) ?: Int
                    )
                        getRepositories(currentQuery, false, true)
                }
            }
        }

        repositoriesRecyclerView!!.addOnScrollListener(mScrollListener)

        if (savedInstanceState != null) {
            currentQuery = savedInstanceState.getString("currentQuery")
        }

        getRepositories(defaultQuery, true, false)
    }


    @OnClick(R.id.search_button)
    internal fun searchButtonAction() {
        var inputMethodManager =
            getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.showSoftInput(searchEditText, InputMethodManager.SHOW_IMPLICIT)

        if (searchEditText!!.text.toString().trim { it <= ' ' }.isNotEmpty()) {
            getRepositories(searchEditText!!.text.toString().trim { it <= ' ' }, true, true)
            searchEditText!!.clearFocus()
            val view = this.currentFocus
            if (view != null) {
                inputMethodManager =
                    getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)

            }
        }
    }

    private fun getRepositories(query: String?, isNewQuery: Boolean, showLoadingToast: Boolean) {

        if (showLoadingToast) {
            val toastMessage: String

            if (isNewQuery) {
                toastMessage = applicationContext.resources.getString(R.string.loading)
            } else {
                toastMessage =
                    applicationContext.resources.getString(R.string.loading_more)
            }

            toast = Toast.makeText(applicationContext, toastMessage, Toast.LENGTH_SHORT)
            toast!!.show()
        }

        loadingFlag = true

        if (reposActivityPresenter != null)
            query?.let { reposActivityPresenter!!.getRepositories(it, isNewQuery) }
    }


    override fun reposOnFailureListener() {
        Toast.makeText(
            applicationContext,
            applicationContext.resources.getString(R.string.error_msg),
            Toast.LENGTH_SHORT
        ).show()
        loadingFlag = false
    }


    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putCharSequence("currentQuery", currentQuery)
    }

    override fun onDestroy() {
        super.onDestroy()
        repositoriesRecyclerView!!.adapter = null
    }
}