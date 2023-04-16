package com.fahmiproduction.githubuserapplication.ui

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.fahmiproduction.githubuserapplication.R
import com.fahmiproduction.githubuserapplication.databinding.FragmentSearchBinding
import com.fahmiproduction.githubuserapplication.datamodel.UserLoader
import com.fahmiproduction.githubuserapplication.viewmodel.UserViewModel


class SearchFragment : Fragment() {

    private lateinit var binding: FragmentSearchBinding
    private lateinit var viewModel: UserViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)

        binding.rvUser.apply {
            layoutManager = LinearLayoutManager(requireContext())
            setItemViewCacheSize(UserLoader.MAX_VIEW_CACHE)
        }

        viewModel = ViewModelProvider(requireActivity())[UserViewModel::class.java]
        UserLoader().let { loader ->
            viewModel.apply {
                searchList.observe(viewLifecycleOwner) {
                    binding.tempImage.visibility = View.GONE
                    loader.setUserListData(requireContext(), it, binding.rvUser, binding.noData)
                }
                isLoading.observe(viewLifecycleOwner) {
                    loader.showLoadingBar(it, binding.progressbar)
                }
                numOfFound.observe(viewLifecycleOwner) {
                    loader.setFoundInfo(requireContext(), it, binding.foundInfo)
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.search_menu, menu)

        val searchManager =
            requireActivity().getSystemService(Context.SEARCH_SERVICE) as SearchManager
        (menu.findItem(R.id.search)?.actionView as SearchView)
            .apply {
                queryHint = resources.getString(R.string.search_hint)
                setSearchableInfo(searchManager.getSearchableInfo(requireActivity().componentName))
                setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(query: String?): Boolean {
                        clearFocus()
                        if (query != null && query.trim() != "") {
                            binding.foundInfo.visibility = View.VISIBLE
                            viewModel.searchUser(query)
                        }
                        return true
                    }

                    override fun onQueryTextChange(newText: String?): Boolean = false
                })
            }
    }
}