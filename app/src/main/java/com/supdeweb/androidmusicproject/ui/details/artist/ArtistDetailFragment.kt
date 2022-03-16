package com.supdeweb.androidmusicproject.ui.details.artist

import android.os.Bundle
import android.view.*
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.supdeweb.androidmusicproject.R
import com.supdeweb.androidmusicproject.data.model.AlbumModel
import com.supdeweb.androidmusicproject.data.repository.AlbumRepository
import com.supdeweb.androidmusicproject.data.repository.ArtistRepository
import com.supdeweb.androidmusicproject.databinding.FragmentArtistDetailBinding
import com.supdeweb.androidmusicproject.design.RadiusButton
import com.supdeweb.androidmusicproject.domain.features.album.FetchAlbumsByArtistUseCase
import com.supdeweb.androidmusicproject.domain.features.artist.FetchArtistDetailUseCase
import com.supdeweb.androidmusicproject.domain.features.artist.UpdateFavoriteArtistUseCase
import com.supdeweb.androidmusicproject.extension.showSnackbar
import com.supdeweb.androidmusicproject.ui.details.album.AlbumDetailFragment.Companion.ARG_ALBUM_DETAIL_ID
import com.supdeweb.androidmusicproject.ui.utils.DataStateEnum
import com.supdeweb.androidmusicproject.ui.utils.SnackBarStatusEnum
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class ArtistDetailFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_artist_detail, null, false)

        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val artistId = arguments?.getString(ARG_ARTIST_DETAIL_ID)
            ?: throw IllegalAccessException("No artistId value pass")

        initViewModel(artistId)
        observeViewModel()
        initButtons()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_detail, menu)

        binding.fragmentArtistDetailTb.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.menuDetail_item_favorite -> {
                    onUserClickOnFavorite()
                    true
                }
                else -> false
            }
        }
    }

    /**
     * the view model
     */
    private lateinit var viewModel: ArtistDetailViewModel

    /**
     * the binding
     */
    private lateinit var binding: FragmentArtistDetailBinding


    /**
     * init [ArtistDetailViewModel] with its factories
     */
    private fun initViewModel(artistId: String) {
        // init use cases
        val fetchArtistDetailUseCase =
            FetchArtistDetailUseCase(ArtistRepository.getInstance(requireContext()))
        val fetchAlbumsByArtistUseCase =
            FetchAlbumsByArtistUseCase(AlbumRepository.getInstance(requireContext()))
        val updateFavoriteArtistUseCase =
            UpdateFavoriteArtistUseCase(ArtistRepository.getInstance(requireContext()))
        val vmFactory =
            ArtistDetailViewModelFactory(
                artistId,
                fetchArtistDetailUseCase,
                fetchAlbumsByArtistUseCase,
                updateFavoriteArtistUseCase
            )
        viewModel = ViewModelProvider(this, vmFactory)[ArtistDetailViewModel::class.java]

    }


    /**
     * observe value in [ArtistDetailViewModel]
     */
    private fun observeViewModel() {
        lifecycleScope.launchWhenStarted {
            collectStates()
        }
    }

    /**
     * collect states
     */
    private fun collectStates() {
        lifecycleScope.launch {
            onArtistStateChanged()
        }
        lifecycleScope.launch {
            onAlbumStateChanged()
        }
        lifecycleScope.launch {
            viewModel.errorMessageState().collect { errorMessage ->
                errorMessage?.let {
                    showSnackbar(
                        message = it,
                        messageType = SnackBarStatusEnum.ERROR
                    )
                }
            }
        }
    }

    private suspend fun onArtistStateChanged() {
        viewModel.artistState().collect {
            when (it.currentStateEnum) {
                DataStateEnum.IDLE -> {
                    binding.fragmentArtistDetailPb.visibility = View.GONE
                    binding.fragmentArtistDetailLlError.visibility = View.GONE
                    binding.fragmentArtistDetailClHeader.visibility = View.GONE
                    binding.fragmentArtistDetailClContent.visibility = View.GONE
                }
                DataStateEnum.ERROR -> {
                    binding.fragmentArtistDetailPb.visibility = View.GONE
                    binding.fragmentArtistDetailClHeader.visibility = View.GONE
                    binding.fragmentArtistDetailClContent.visibility = View.GONE
                    binding.fragmentArtistDetailLlError.visibility = View.VISIBLE
                    binding.fragmentArtistDetailTvError.text = it.errorMessage
                }
                DataStateEnum.LOADING -> {
                    binding.fragmentArtistDetailClHeader.visibility = View.GONE
                    binding.fragmentArtistDetailClContent.visibility = View.GONE
                    binding.fragmentArtistDetailLlError.visibility = View.GONE
                    binding.fragmentArtistDetailPb.visibility = View.VISIBLE
                }
                DataStateEnum.SUCCESS -> {
                    binding.fragmentArtistDetailPb.visibility = View.GONE
                    binding.fragmentArtistDetailLlError.visibility = View.GONE
                    binding.fragmentArtistDetailClHeader.visibility = View.VISIBLE
                    binding.fragmentArtistDetailClContent.visibility = View.VISIBLE

                    binding.fragmentArtistDetailTvArtistDesc.text = it.artist?.description
                    binding.fragmentArtistDetailTvArtistName.text = it.artist?.name
                    binding.fragmentArtistDetailTvArtistCountry.text = it.artist?.country
                    binding.fragmentArtistDetailTvArtistCountry.text =
                        getString(
                            R.string.fragmentArtistDetail_tv_label_country,
                            it.artist?.country,
                            it.artist?.genre
                        )
                    it.artist?.imageUrl?.let { url -> initHeaderBackground(url) }
                    it.artist?.isFavorite?.let { isFav -> initFavoriteIcon(isFav) }
                }
            }
        }
    }

    private suspend fun onAlbumStateChanged() {
        viewModel.albumsState().collect {
            when (it.currentStateEnum) {
                DataStateEnum.IDLE -> {
                    binding.fragmentArtistDetailPb.visibility = View.GONE
                    binding.fragmentArtistDetailLlError.visibility = View.GONE
                    binding.fragmentArtistDetailClContent.visibility = View.GONE
                }
                DataStateEnum.ERROR -> {
                    binding.fragmentArtistDetailPb.visibility = View.GONE
                    binding.fragmentArtistDetailClContent.visibility = View.GONE
                    binding.fragmentArtistDetailLlError.visibility = View.VISIBLE
                    binding.fragmentArtistDetailTvError.text = it.errorMessage
                }
                DataStateEnum.LOADING -> {
                    binding.fragmentArtistDetailClContent.visibility = View.GONE
                    binding.fragmentArtistDetailLlError.visibility = View.GONE
                    binding.fragmentArtistDetailPb.visibility = View.VISIBLE
                }
                DataStateEnum.SUCCESS -> {
                    binding.fragmentArtistDetailPb.visibility = View.GONE
                    binding.fragmentArtistDetailLlError.visibility = View.GONE
                    binding.fragmentArtistDetailClContent.visibility = View.VISIBLE

                    it.albums?.let { albums -> initAlbumComponent(albums) }
                }
            }
        }
    }

    private fun initButtons() {
        binding.fragmentArtistDetailTb.setNavigationOnClickListener {
            NavHostFragment.findNavController(this).navigateUp()
        }
        binding.fragmentArtistDetailBtError.setOnClickListener {
            viewModel.refreshData()
        }
    }

    private fun initHeaderBackground(imageUrl: String) {
        val requestOptions = RequestOptions()
            .error(R.drawable.ic_launcher_foreground)
        Glide.with(binding.fragmentArtistDetailIvBackground)
            .setDefaultRequestOptions(requestOptions)
            .load(imageUrl)
            .into(binding.fragmentArtistDetailIvBackground)
    }

    private fun setFavoriteIcon(id: Int) {
        binding.fragmentArtistDetailTb.menu.findItem(R.id.menuDetail_item_favorite)?.icon =
            ContextCompat.getDrawable(requireContext(), id)
    }

    private fun initFavoriteIcon(isFavorite: Boolean) {
        if (isFavorite) {
            setFavoriteIcon(R.drawable.ic_baseline_favorite_24)
        } else setFavoriteIcon(R.drawable.ic_baseline_favorite_border_24)
    }


    private fun onUserClickOnFavorite() {
        lifecycleScope.launch {
            val isFav = viewModel.artistState().first().artist?.isFavorite ?: false
            viewModel.updateFavoriteArtist(!isFav)
            initFavoriteIcon(isFav)
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
                    val bundle = bundleOf(ARG_ALBUM_DETAIL_ID to album.id)
                    findNavController()
                        .navigate(R.id.albumDetailFragment, bundle)
                }

            })
            binding.fragmentArtistDetailLlAlbums.addView(
                radiusButton
            )
        }
    }

    companion object {
        const val ARG_ARTIST_DETAIL_ID = "ARG_ARTIST_DETAIL_ID"
    }
}