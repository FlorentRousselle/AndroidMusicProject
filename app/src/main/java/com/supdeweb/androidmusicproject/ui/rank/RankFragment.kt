package com.supdeweb.androidmusicproject.ui.rank

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
import com.supdeweb.androidmusicproject.databinding.FragmentRankBinding
import com.supdeweb.androidmusicproject.ui.rank.adapter.RankAdapter
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class RankFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_rank, null, false)


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
    private lateinit var viewModel: RankViewModel

    /**
     * the binding
     */
    private lateinit var binding: FragmentRankBinding


    /**
     * init [RankViewModel] with its factories
     */
    private fun initViewModel() {
        context?.let {
            val vmFactory =
                RankViewModelFactory(
                    AlbumRepository.getInstance(it),
                    TrackRepository.getInstance(it)
                )
            viewModel = ViewModelProvider(this, vmFactory)[RankViewModel::class.java]
        }

    }


    /**
     * observe value in [RankViewModel]
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
        binding.fragmentRankVp.adapter =
            RankAdapter(childFragmentManager, this.lifecycle)

        TabLayoutMediator(
            binding.fragmentRankTl,
            binding.fragmentRankVp
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