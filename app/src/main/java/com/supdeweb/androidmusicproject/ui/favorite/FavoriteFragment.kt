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
import com.supdeweb.androidmusicproject.data.model.ArtistModel
import com.supdeweb.androidmusicproject.data.repository.AlbumRepository
import com.supdeweb.androidmusicproject.data.repository.ArtistRepository
import com.supdeweb.androidmusicproject.databinding.FragmentFavoriteBinding
import com.supdeweb.androidmusicproject.design.RadiusButton
import com.supdeweb.androidmusicproject.domain.features.album.ObserveFavoriteAlbumsUseCase
import com.supdeweb.androidmusicproject.domain.features.artist.ObserveFavoriteArtistsUseCase
import com.supdeweb.androidmusicproject.ui.details.album.AlbumDetailFragment
import com.supdeweb.androidmusicproject.ui.details.artist.ArtistDetailFragment.Companion.ARG_ARTIST_DETAIL_ID
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
        val observeFavoriteArtistsUseCase =
            ObserveFavoriteArtistsUseCase(ArtistRepository.getInstance(requireContext()))
        val vmFactory =
            FavoriteViewModelFactory(
                observeFavoriteAlbumsUseCase,
                observeFavoriteArtistsUseCase
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
        lifecycleScope.launch {
            onArtistStateChanged()
        }
    }

    private suspend fun onAlbumStateChanged() {
        viewModel.albumState().collect {
            when (it.currentStateEnum) {
                DataStateEnum.IDLE -> {
                    binding.fragmentFavoritePbAlbums.visibility = View.GONE
                    binding.fragmentFavoriteTvAlbumEmptyList.visibility = View.GONE
                }
                DataStateEnum.ERROR -> {
                    binding.fragmentFavoritePbAlbums.visibility = View.GONE
                    binding.fragmentFavoriteTvAlbumEmptyList.visibility = View.GONE
                }
                DataStateEnum.LOADING -> {
                    binding.fragmentFavoritePbAlbums.visibility = View.VISIBLE
                    binding.fragmentFavoriteTvAlbumEmptyList.visibility = View.GONE
                }
                DataStateEnum.SUCCESS -> {
                    binding.fragmentFavoritePbAlbums.visibility = View.GONE
                    binding.fragmentFavoriteTvAlbumEmptyList.visibility = View.GONE
                    if (it.albums.isNullOrEmpty()) {
                        binding.fragmentFavoriteTvAlbumEmptyList.visibility = View.VISIBLE
                    } else {
                        binding.fragmentFavoriteTvAlbumEmptyList.visibility = View.GONE
                        initAlbumComponent(it.albums)
                    }
                }
            }
        }
    }

    private suspend fun onArtistStateChanged() {
        viewModel.artistState().collect {
            when (it.currentStateEnum) {
                DataStateEnum.IDLE -> {
                    binding.fragmentFavoritePbArtists.visibility = View.GONE
                    binding.fragmentFavoriteTvArtistEmptyList.visibility = View.GONE
                }
                DataStateEnum.ERROR -> {
                    binding.fragmentFavoritePbArtists.visibility = View.GONE
                    binding.fragmentFavoriteTvArtistEmptyList.visibility = View.GONE
                }
                DataStateEnum.LOADING -> {
                    binding.fragmentFavoritePbArtists.visibility = View.VISIBLE
                    binding.fragmentFavoriteTvArtistEmptyList.visibility = View.GONE
                }
                DataStateEnum.SUCCESS -> {
                    binding.fragmentFavoritePbArtists.visibility = View.GONE
                    binding.fragmentFavoriteTvArtistEmptyList.visibility = View.GONE
                    if (it.artists.isNullOrEmpty()) {
                        binding.fragmentFavoriteTvArtistEmptyList.visibility = View.VISIBLE
                    } else {
                        binding.fragmentFavoriteTvArtistEmptyList.visibility = View.GONE
                        initArtistComponent(it.artists)
                    }
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

    private fun initArtistComponent(artists: List<ArtistModel>) {
        artists.forEach { artist ->
            val radiusButton = RadiusButton(requireContext())
            radiusButton.customizeButton(
                textLabel = artist.name,
                imageUrl = artist.imageUrl,
                isArtist = true
            )
            radiusButton.setListener(object : RadiusButton.RadiusButtonListener {
                override fun onUserClickOnItem() {
                    val bundle = bundleOf(ARG_ARTIST_DETAIL_ID to artist.id)
                    findNavController()
                        .navigate(R.id.artistDetailFragment, bundle)
                }

            })
            binding.fragmentFavoriteLlArtists.addView(
                radiusButton
            )
        }
    }
}