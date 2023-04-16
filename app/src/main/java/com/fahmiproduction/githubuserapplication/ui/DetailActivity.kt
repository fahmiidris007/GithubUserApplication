package com.fahmiproduction.githubuserapplication.ui

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.fahmiproduction.githubuserapplication.R
import com.fahmiproduction.githubuserapplication.adapter.FollowerAdapter
import com.fahmiproduction.githubuserapplication.databinding.ActivityDetailBinding
import com.fahmiproduction.githubuserapplication.datamodel.FavoriteUser
import com.fahmiproduction.githubuserapplication.datamodel.UserDetailsResponse
import com.fahmiproduction.githubuserapplication.datamodel.UserLoader
import com.fahmiproduction.githubuserapplication.viewmodel.DetailViewModel
import com.fahmiproduction.githubuserapplication.viewmodel.FavoriteViewModel
import com.fahmiproduction.githubuserapplication.viewmodel.RepositoryViewModel
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayoutMediator

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private val viewModel: DetailViewModel by viewModels()
    private lateinit var favoriteViewModel: FavoriteViewModel

    private lateinit var tUsername: String
    private var tInvokePos: Int = -1
    private var tIsFollowing = false
    private var tIsFavorite = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.apply {
            title = resources.getString(R.string.temp_subtitle)
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
            elevation = 0f
        }

        tUsername = intent.getStringExtra(UserLoader.EXTRA_USERNAME).toString()
        tInvokePos = intent.getIntExtra(UserLoader.EXTRA_LIST_POS, -1)

        setupFavoriteFeature()
        viewModel.apply {
            getUserDetails(tUsername).observe(this@DetailActivity) { setUserDetails(it) }
            isLoading.observe(this@DetailActivity) {
                UserLoader().showLoadingBar(it, binding.progressbar)
            }
            isFailed.observe(this@DetailActivity) {
                it.getContentIfNotHandled()?.let { fail ->
                    if (fail) {
                        Snackbar.make(
                            window.decorView.rootView,
                            resources.getString(R.string.failed_to_load),
                            Snackbar.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
        setupFollowButton()
        setupFollowerPages()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setUserDetails(user: UserDetailsResponse) {
        supportActionBar?.title = user.getAtUsername()

        binding.apply {
            username.text = user.getAtUsername()
            Glide.with(this@DetailActivity)
                .load(user.avatarUrl)
                .circleCrop()
                .into(binding.avatarImg)

            name.text = user.name ?: resources.getString(R.string.temp_title)
            company.text = user.company ?: resources.getString(R.string.temp_company)
            location.text = user.location ?: resources.getString(R.string.temp_location)
            followers.text = user.followers.toString()
            followings.text = user.following.toString()
            numOfRepo.text = user.publicRepos.toString()
            followBtn.setOnClickListener { toggleFollow() }
            githubBtn.setOnClickListener {
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(user.getRepoURL())))
            }
            favoriteBtn.setOnClickListener {
                toggleFavorite(
                    FavoriteUser(user.login, user.type, user.avatarUrl)
                )
            }
        }
    }

    private fun setupFollowerPages() {
        val pagerAdapter = FollowerAdapter(
            this@DetailActivity
        ).apply { pageUser = tUsername }

        binding.apply {
            followerPager.adapter = pagerAdapter
            TabLayoutMediator(followerTabs, followerPager) { tab, pageNum ->
                tab.text = resources.getString(PAGE_TITLES[pageNum])
            }.attach()
        }
    }

    private fun toggleFollow() {
        Toast.makeText(
            this@DetailActivity,
            "${getToggledFollowText()} ${"@$tUsername"}",
            Toast.LENGTH_SHORT
        ).show()
        tIsFollowing = !tIsFollowing
        setupFollowButton()
    }

    private fun setupFollowButton() {
        binding.followBtn.text = getToggledFollowText()
    }

    private fun getToggledFollowText(): String {
        return if (tIsFollowing) resources.getString(R.string.unfollow)
        else resources.getString(R.string.follow)
    }

    private fun setupFavoriteFeature() {
        favoriteViewModel = ViewModelProvider(
            this, RepositoryViewModel(application)
        )[FavoriteViewModel::class.java]

        favoriteViewModel.isAFavUser(tUsername).observe(this) {
            setupFavoriteButton(it > 0)
        }
    }

    private fun toggleFavorite(user: FavoriteUser) {
        if (tIsFavorite) favoriteViewModel.deleteUser(user)
        else favoriteViewModel.insertUser(user)
    }

    private fun setupFavoriteButton(isFavorite: Boolean) {
        tIsFavorite = isFavorite
        binding.favoriteBtn.setImageResource(
            if (isFavorite) R.drawable.ic_favorite_inside
            else R.drawable.ic_favorite
        )
    }

    override fun onBackPressed() {
        setResult(UserLoader.RESULT_CODE, Intent().apply {
            putExtra(UserLoader.EXTRA_LIST_MODIFIED, tIsFavorite)
            putExtra(UserLoader.EXTRA_LIST_POS, tInvokePos)
            putExtra(UserLoader.EXTRA_USERNAME, tUsername)
        })
        super.onBackPressed()
    }

    companion object {
        @StringRes
        private val PAGE_TITLES = intArrayOf(R.string.followers, R.string.followings)
    }
}