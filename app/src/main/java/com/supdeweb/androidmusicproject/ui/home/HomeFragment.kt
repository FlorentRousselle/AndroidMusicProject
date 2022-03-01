package com.supdeweb.androidmusicproject.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.google.android.material.tabs.TabLayoutMediator
import com.supdeweb.androidmusicproject.R
import com.supdeweb.androidmusicproject.data.repository.AlbumRepository
import com.supdeweb.androidmusicproject.data.repository.TrackRepository
import com.supdeweb.androidmusicproject.databinding.FragmentHomeBinding
import com.supdeweb.androidmusicproject.ui.home.adapter.ClassementAdapter
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, null, false)


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewPager()
        initViewModel()
        observeViewModel()
    }

    /**
     * the view model
     */
    private lateinit var viewModel: HomeViewModel

    /**
     * the binding
     */
    private lateinit var binding: FragmentHomeBinding


    /**
     * init [HomeViewModel] with its factories
     */
    private fun initViewModel() {
        context?.let {
            val vmFactory =
                HomeViewModelFactory(
                    AlbumRepository.getInstance(it),
                    TrackRepository.getInstance(it)
                )
            viewModel = ViewModelProvider(this, vmFactory)[HomeViewModel::class.java]
        }

    }


    /**
     * observe value in [HomeViewModel]
     */
    private fun observeViewModel() {
        lifecycleScope.launch {
            collectStates()
        }
    }

    /**
     *
     */
    private suspend fun collectStates() {
        viewModel.toast().collect {
            if (it.isNullOrEmpty().not()) {
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
            }
        }
    }

    /**
     *
     */
    private fun initViewPager() {
        binding.fragmentHomeVp.adapter =
            ClassementAdapter(childFragmentManager, this.lifecycle)

        TabLayoutMediator(
            binding.fragmentHomeTl,
            binding.fragmentHomeVp
        ) { tab, position ->
            when (position) {
                0 -> {
                    tab.text = "Titres"
                }
                1 -> {
                    tab.text = "Albums"
                }
            }
        }.attach()
    }

}