package com.supdeweb.androidmusicproject.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.supdeweb.androidmusicproject.R
import com.supdeweb.androidmusicproject.data.repository.AlbumRepository
import com.supdeweb.androidmusicproject.databinding.FragmentHomeBinding
import com.supdeweb.androidmusicproject.utils.DataStateEnum
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
                    AlbumRepository.getInstance(it)
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
        viewModel.albumState().collect {
            when (it.currentStateEnum) {
                DataStateEnum.IDLE,
                DataStateEnum.ERROR,
                -> {
                    binding.fragmentHomeTv.visibility = View.GONE
                    binding.fragmentHomePb.visibility = View.GONE
                    binding.fragmentHomeTvError.visibility = View.VISIBLE
                    binding.fragmentHomeTvError.text = it.errorMessage
                }
                DataStateEnum.LOADING -> {
                    binding.fragmentHomeTvError.visibility = View.GONE
                    binding.fragmentHomeTv.visibility = View.GONE
                    binding.fragmentHomePb.visibility = View.VISIBLE
                }
                DataStateEnum.SUCCESS -> {
                    binding.fragmentHomeTvError.visibility = View.GONE
                    binding.fragmentHomePb.visibility = View.GONE
                    binding.fragmentHomeTv.visibility = View.VISIBLE
                    binding.fragmentHomeTv.text = it.albums?.component1()?.description
                }
            }
        }
    }

}