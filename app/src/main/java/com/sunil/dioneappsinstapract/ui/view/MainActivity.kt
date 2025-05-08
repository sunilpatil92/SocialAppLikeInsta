package com.sunil.dioneappsinstapract.ui.view

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sunil.dioneappsinstapract.MyApp
import com.sunil.dioneappsinstapract.R
import com.sunil.dioneappsinstapract.adapter.MediaAdapter
import com.sunil.dioneappsinstapract.adapter.PostAdapter
import com.sunil.dioneappsinstapract.constants.ConnectionStatus
import com.sunil.dioneappsinstapract.constants.constant
import com.sunil.dioneappsinstapract.constants.constant.Companion.getCurrentViewHolder
import com.sunil.dioneappsinstapract.data.Resource
import com.sunil.dioneappsinstapract.databinding.ActivityMainBinding
import com.sunil.dioneappsinstapract.domain.model.Data
import com.sunil.dioneappsinstapract.domain.model.postRequest
import com.sunil.dioneappsinstapract.ui.viewModel.PostViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: PostViewModel by viewModels()
    val postAdapter = PostAdapter()
    var page = 1
    var isLoading = false
    var postCount = 0
    val request = postRequest()

    private val networkObserve by lazy {
        (application as MyApp).networkObserver
    }

    private var currentPlayingViewHolder: MediaAdapter.VideoVH? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding.rvPostList.layoutManager = LinearLayoutManager(this)
        binding.rvPostList.adapter = postAdapter


        callApi()

        viewModel.resource.observe(this) { response ->
            when (response) {
                is Resource.Loading -> {
                    loading()
                }

                is Resource.Success -> {
                    response.data?.let {
                        Log.d("Dione : Response : ", response.toString())
                        try {
                            try {
                                postCount = postCount + (response.count!!)
                                binding.tvPostCount.text = "${postCount} Posts"
                            } catch (e: Exception) {
                            }


                            val data = response.data
                            page = page + 1
                            postAdapter.addPost(data.takeLast(data.size - postAdapter.itemCount))
                            isLoading = false

                        } catch (e: Exception) {
                            Toast.makeText(this, "something went wrong", Toast.LENGTH_SHORT).show()
                        }
                    }
                    stopLoading()
                }

                is Resource.Error -> {
                    Toast.makeText(this, "Error:" + response.message, Toast.LENGTH_SHORT).show()
                    stopLoading()
                }

                else -> {
                    stopLoading()
                }
            }
        }

        binding.rvPostList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(rv: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(rv, dx, dy)

                val layoutManager = rv.layoutManager as LinearLayoutManager
                val totalItemCount = layoutManager.itemCount
                val lastVisibleItem = layoutManager.findLastVisibleItemPosition()

                if (totalItemCount <= lastVisibleItem + 2) {
                    if (!isLoading) {
                        Log.d("Dione : Page : ", page.toString())
                        val net = constant.isInternetAvailable(applicationContext)
                        if (net) {
                            viewModel.getPost(page, request)
                        }
                    }
                }


                //---------
                val firstVisible = layoutManager.findFirstVisibleItemPosition()
                val lastVisible = layoutManager.findLastVisibleItemPosition()

                var newVideoFound = false

                for (i in firstVisible..lastVisible) {
                    val view = layoutManager.findViewByPosition(i) ?: continue

                    val viewHeight = view.height
                    val visibleHeight = kotlin.math.min(
                        view.bottom, rv.height
                    ) - kotlin.math.max(view.top, 0)

                    val visibilityPercent = visibleHeight.toFloat() / viewHeight
                    if (visibilityPercent >= 0.5f) {
                        val holder = binding.rvPostList.findViewHolderForAdapterPosition(i)
                        if (holder is PostAdapter.ViewHolderMedia) {
                            val mediaViewPager = holder.binding.pagerMedia
                            val currentItem = mediaViewPager.currentItem
                            val mediaAdapter = mediaViewPager.adapter as? MediaAdapter
                            val innerHolder = mediaViewPager.getCurrentViewHolder()

                            if (innerHolder is MediaAdapter.VideoVH) {
                                if (currentPlayingViewHolder != innerHolder) {
                                    currentPlayingViewHolder?.pause()
                                    innerHolder.play()
                                    currentPlayingViewHolder = innerHolder
                                }
                                newVideoFound = true
                                break
                            }
                        }
                    }
                }

                if (!newVideoFound) {
                    currentPlayingViewHolder?.pause()
                    currentPlayingViewHolder = null
                }

            }
        })
    }

    private fun callApi() {
        lifecycleScope.launch {
            networkObserve.status
                .collect { status ->
                    if (status == ConnectionStatus.Available) {
                        viewModel.getPost(page, request)
                    }
                }
        }
    }

    private fun loading() {
        isLoading = true
        binding.shimmerLayout.showShimmer(true)
        binding.shimmerLayout.visibility = View.VISIBLE
        binding.rvPostList.visibility = View.GONE
    }

    private fun stopLoading() {
        binding.shimmerLayout.visibility = View.GONE
        binding.rvPostList.visibility = View.VISIBLE
        binding.shimmerLayout.stopShimmer()
        isLoading = false
    }

    override fun onDestroy() {
        currentPlayingViewHolder?.releasePlayer()
        super.onDestroy()
    }
}
