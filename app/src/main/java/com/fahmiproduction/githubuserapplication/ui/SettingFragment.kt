package com.fahmiproduction.githubuserapplication.ui

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import com.fahmiproduction.githubuserapplication.R
import com.fahmiproduction.githubuserapplication.databinding.FragmentSettingBinding


class SettingFragment : Fragment() {
    private lateinit var binding: FragmentSettingBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSettingBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            MainActivity.getSettingViewModel().let {
                it?.getIsDarkMode()?.observe(viewLifecycleOwner) { isDarkMode ->
                    themeSwitch.isChecked = isDarkMode
                    themeIcon.setImageResource(
                        if (isDarkMode) R.drawable.ic_nights
                        else R.drawable.ic_sunny
                    )
                }
                themeSwitch.setOnCheckedChangeListener { _, isChecked ->
                    MainActivity.getSettingViewModel()?.saveThemeMode(isChecked)
                }
            }
        }
    }
}