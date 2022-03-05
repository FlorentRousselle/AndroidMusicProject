package com.supdeweb.androidmusicproject.ui.rank.adapter.track

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.supdeweb.androidmusicproject.R
import com.supdeweb.androidmusicproject.data.repository.TrackRepository
import com.supdeweb.androidmusicproject.databinding.FragmentTrackBinding
import com.supdeweb.androidmusicproject.ui.utils.DataStateEnum
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class TrackFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_track, null, false)


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewModel()
        observeViewModel()
        initButton()
    }

    /**
     * the view model
     */
    private lateinit var viewModel: TrackViewModel

    /**
     * the binding
     */
    private lateinit var binding: FragmentTrackBinding

    /**
     *
     */
    private lateinit var adapter: TrackAdapter


    /**
     * init [TrackViewModel] with its factories
     */
    private fun initViewModel() {
        adapter = TrackAdapter()
        binding.fragmentTrackRv.adapter = adapter
        binding.fragmentTrackRv.layoutManager = LinearLayoutManager(
            context,
            LinearLayoutManager.VERTICAL,
            false
        )
        context?.let {
            val vmFactory =
                TrackViewModelFactory(
                    TrackRepository.getInstance(it)
                )
            viewModel = ViewModelProvider(this, vmFactory)[TrackViewModel::class.java]
        }

    }


    /**
     * observe value in [TrackViewModel]
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
        viewModel.trackState().collect {
            when (it.currentStateEnum) {
                DataStateEnum.IDLE -> {
                    binding.fragmentTrackPb.visibility = View.GONE
                    binding.fragmentTrackLlError.visibility = View.GONE
                }
                DataStateEnum.ERROR -> {
                    binding.fragmentTrackPb.visibility = View.GONE
                    binding.fragmentTrackLlError.visibility = View.VISIBLE
                    binding.fragmentTrackTvError.text = it.errorMessage
                }
                DataStateEnum.LOADING -> {
                    binding.fragmentTrackPb.visibility = View.VISIBLE
                    binding.fragmentTrackLlError.visibility = View.GONE
                }
                DataStateEnum.SUCCESS -> {
                    binding.fragmentTrackPb.visibility = View.GONE
                    binding.fragmentTrackLlError.visibility = View.GONE
                    adapter.submitList(it.tracks)
                }
            }
        }
    }

    private fun initButton() {
        binding.fragmentTrackBtError.setOnClickListener {
            viewModel.getTrendingTracks()
        }
    }

}