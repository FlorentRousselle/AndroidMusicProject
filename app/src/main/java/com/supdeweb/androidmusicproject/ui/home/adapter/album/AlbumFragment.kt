package com.supdeweb.androidmusicproject.ui.home.adapter.album

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
import com.supdeweb.androidmusicproject.data.repository.AlbumRepository
import com.supdeweb.androidmusicproject.databinding.FragmentAlbumBinding
import com.supdeweb.androidmusicproject.ui.utils.DataStateEnum
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class AlbumFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_album, null, false)


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewModel()
        observeViewModel()
    }

    /**
     * the view model
     */
    private lateinit var viewModel: AlbumViewModel

    /**
     * the binding
     */
    private lateinit var binding: FragmentAlbumBinding

    /**
     *
     */
    private lateinit var adapter: AlbumAdapter


    /**
     * init [AlbumViewModel] with its factories
     */
    private fun initViewModel() {
        adapter = AlbumAdapter()
        binding.fragmentAlbumRv.adapter = adapter
        binding.fragmentAlbumRv.layoutManager = LinearLayoutManager(
            context,
            LinearLayoutManager.VERTICAL,
            false
        )
        context?.let {
            val vmFactory =
                AlbumViewModelFactory(
                    AlbumRepository.getInstance(it)
                )
            viewModel = ViewModelProvider(this, vmFactory)[AlbumViewModel::class.java]
        }

    }


    /**
     * observe value in [AlbumViewModel]
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
        viewModel.albumState().collect {
            when (it.currentStateEnum) {
                DataStateEnum.IDLE -> {
                    binding.fragmentAlbumClRoot.visibility = View.GONE
                    binding.fragmentAlbumPb.visibility = View.GONE
                    binding.fragmentAlbumTvError.visibility = View.GONE
                }
                DataStateEnum.ERROR -> {
                    binding.fragmentAlbumClRoot.visibility = View.VISIBLE
                    binding.fragmentAlbumPb.visibility = View.GONE
                    binding.fragmentAlbumTvError.visibility = View.VISIBLE
                    binding.fragmentAlbumTvError.text = "Error" //TODO: put message
                }
                DataStateEnum.LOADING -> {
                    binding.fragmentAlbumClRoot.visibility = View.VISIBLE
                    binding.fragmentAlbumPb.visibility = View.VISIBLE
                    binding.fragmentAlbumTvError.visibility = View.GONE
                }
                DataStateEnum.SUCCESS -> {
                    binding.fragmentAlbumClRoot.visibility = View.VISIBLE
                    binding.fragmentAlbumPb.visibility = View.GONE
                    binding.fragmentAlbumTvError.visibility = View.GONE
                    adapter.submitList(it.albums)
                }
            }
        }
    }

}