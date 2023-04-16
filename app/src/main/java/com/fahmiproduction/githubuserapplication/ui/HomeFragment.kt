package com.fahmiproduction.githubuserapplication.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.fahmiproduction.githubuserapplication.R
import com.fahmiproduction.githubuserapplication.databinding.FragmentHomeBinding
import com.fahmiproduction.githubuserapplication.datamodel.UserLoader
import com.fahmiproduction.githubuserapplication.viewmodel.UserViewModel


class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var viewModel: UserViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvUser.apply {
            layoutManager = LinearLayoutManager(requireContext())
            setItemViewCacheSize(UserLoader.MAX_VIEW_CACHE)
        }

        viewModel = ViewModelProvider(requireActivity())[UserViewModel::class.java]
        UserLoader().let { loader ->
            viewModel.apply {
                userList.observe(viewLifecycleOwner) {
                    loader.setUserListData(requireContext(), it, binding.rvUser, binding.noData)
                }
                isLoading.observe(viewLifecycleOwner) {
                    loader.showLoadingBar(it, binding.progressbar)
                }
            }
        }
    }
}