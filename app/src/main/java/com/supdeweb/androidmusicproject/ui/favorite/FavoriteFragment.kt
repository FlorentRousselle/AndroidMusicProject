package com.supdeweb.androidmusicproject.ui.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.supdeweb.androidmusicproject.R
import com.supdeweb.androidmusicproject.data.model.AlbumModel
import com.supdeweb.androidmusicproject.data.repository.AlbumRepository
import com.supdeweb.androidmusicproject.databinding.FragmentFavoriteBinding
import com.supdeweb.androidmusicproject.design.RadiusButton
import com.supdeweb.androidmusicproject.domain.features.album.ObserveFavoriteAlbumsUseCase
import com.supdeweb.androidmusicproject.ui.details.album.AlbumDetailFragment
import com.supdeweb.androidmusicproject.ui.rank.RankViewModel
import com.supdeweb.androidmusicproject.ui.rank.adapter.album.AlbumViewModel
import com.supdeweb.androidmusicproject.ui.utils.DataStateEnum
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class FavoriteFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_favorite, null, false)

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
    private lateinit var viewModel: FavoriteViewModel

    /**
     * the binding
     */
    private lateinit var binding: FragmentFavoriteBinding


    /**
     * init [RankViewModel] with its factories
     */
    private fun initViewModel() {
        // init use cases
        val observeFavoriteAlbumsUseCase =
            ObserveFavoriteAlbumsUseCase(AlbumRepository.getInstance(requireContext()))
        val vmFactory =
            FavoriteViewModelFactory(
                observeFavoriteAlbumsUseCase
            )
        viewModel = ViewModelProvider(this, vmFactory)[FavoriteViewModel::class.java]
    }

    /**
     * observe value in [AlbumViewModel]
     */
    private fun observeViewModel() {
        lifecycleScope.launchWhenStarted {
            collectStates()
        }
    }

    /**
     * collect states
     */
    private suspend fun collectStates() {
        lifecycleScope.launch {
            onAlbumStateChanged()
        }
    }

    private suspend fun onAlbumStateChanged() {
        viewModel.albumState().collect {
            when (it.currentStateEnum) {
                DataStateEnum.IDLE -> {
                    binding.fragmentFavoriteClRoot.visibility = View.GONE
                }
                DataStateEnum.ERROR -> {
                    binding.fragmentFavoriteClRoot.visibility = View.GONE
                }
                DataStateEnum.LOADING -> {
                    binding.fragmentFavoriteClRoot.visibility = View.GONE
                }
                DataStateEnum.SUCCESS -> {
                    binding.fragmentFavoriteClRoot.visibility = View.VISIBLE
                    it.albums?.let { albums -> initAlbumComponent(albums) }
                }
            }
        }
    }

    private fun initAlbumComponent(albums: List<AlbumModel>) {
        albums.forEach { album ->
            val radiusButton = RadiusButton(requireContext())
            radiusButton.customizeButton(
                textLabel = album.albumName,
                firstLabel = album.year,
                imageUrl = album.imageUrl,
            )
            radiusButton.setListener(object : RadiusButton.RadiusButtonListener {
                override fun onUserClickOnItem() {
                    val bundle = bundleOf(AlbumDetailFragment.ARG_ALBUM_DETAIL_ID to album.id)
                    findNavController()
                        .navigate(R.id.albumDetailFragment, bundle)
                }

            })
            binding.fragmentFavoriteLlAlbums.addView(
                radiusButton
            )
        }
    }
}