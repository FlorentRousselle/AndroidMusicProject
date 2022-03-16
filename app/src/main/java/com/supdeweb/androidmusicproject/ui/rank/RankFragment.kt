package com.supdeweb.androidmusicproject.ui.rank

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import com.supdeweb.androidmusicproject.R
import com.supdeweb.androidmusicproject.databinding.FragmentRankBinding
import com.supdeweb.androidmusicproject.ui.rank.adapter.RankAdapter

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
    private fun initViewModel() {}


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
                    tab.text = getString(R.string.fragmentRank_tabLayout_title_tracks)
                }
                1 -> {
                    tab.text = getString(R.string.fragmentRank_tabLayout_title_albums)
                }
            }
        }.attach()
    }

}