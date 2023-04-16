package com.fahmiproduction.githubuserapplication.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.fahmiproduction.githubuserapplication.R
import com.fahmiproduction.githubuserapplication.databinding.FragmentFollowerBinding
import com.fahmiproduction.githubuserapplication.datamodel.UserLoader
import com.fahmiproduction.githubuserapplication.viewmodel.DetailViewModel


class FollowerFragment : Fragment() {

    private lateinit var binding: FragmentFollowerBinding
    private lateinit var viewModel: DetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFollowerBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvUser.apply {
            layoutManager = LinearLayoutManager(context)
            setItemViewCacheSize(UserLoader.MAX_VIEW_CACHE)
        }
        viewModel = ViewModelProvider(requireActivity())[DetailViewModel::class.java]

        setupPage()
    }

    private fun setupPage() {
        val type = arguments?.getString(ARG_FOLLOWER_TYPE)
        val username = arguments?.getString(ARG_USERNAME)
        val loader = UserLoader()

        if (type != null && username != null) {
            when (PageType.valueOf(type)) {
                PageType.FOLLOWER -> {
                    viewModel.getFollowers(username).observe(viewLifecycleOwner) {
                        loader.setUserListData(
                            requireContext(),
                            it, binding.rvUser, binding.noData,
                            replaceWithToast = true
                        )
                    }
                }
                PageType.FOLLOWING -> {
                    viewModel.getFollowings(username).observe(viewLifecycleOwner) {
                        loader.setUserListData(
                            requireContext(),
                            it, binding.rvUser, binding.noData,
                            replaceWithToast = true
                        )
                    }
                }
            }
        }
    }

    companion object {
        const val ARG_FOLLOWER_TYPE = "arg_follower_type"
        const val ARG_USERNAME = "arg_username"

        enum class PageType() {
            FOLLOWER, FOLLOWING
        }
    }
}