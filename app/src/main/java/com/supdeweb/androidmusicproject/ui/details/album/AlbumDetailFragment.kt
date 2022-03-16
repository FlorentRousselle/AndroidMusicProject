package com.supdeweb.androidmusicproject.ui.details.album

import android.os.Bundle
import android.view.*
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.supdeweb.androidmusicproject.R
import com.supdeweb.androidmusicproject.data.repository.AlbumRepository
import com.supdeweb.androidmusicproject.data.repository.TrackRepository
import com.supdeweb.androidmusicproject.databinding.FragmentAlbumDetailBinding
import com.supdeweb.androidmusicproject.domain.features.album.FetchAlbumDetailUseCase
import com.supdeweb.androidmusicproject.domain.features.album.UpdateFavoriteAlbumUseCase
import com.supdeweb.androidmusicproject.domain.features.track.FetchTracksByAlbumUseCase
import com.supdeweb.androidmusicproject.extension.showSnackbar
import com.supdeweb.androidmusicproject.ui.rank.adapter.album.AlbumViewModel
import com.supdeweb.androidmusicproject.ui.utils.DataStateEnum
import com.supdeweb.androidmusicproject.ui.utils.SnackBarStatusEnum
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class AlbumDetailFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_album_detail, null, false)

        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val albumId = arguments?.getString(ARG_ALBUM_DETAIL_ID)
            ?: throw IllegalAccessException("No albumId value pass")

        initViewModel(albumId)
        observeViewModel()
        initButtons()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_detail, menu)

        binding.fragmentAlbumDetailTb.setOnMenuItemClickListener {
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
    private lateinit var viewModel: AlbumDetailViewModel

    /**
     * the binding
     */
    private lateinit var binding: FragmentAlbumDetailBinding

    /**
     * the track album adapter
     */
    private lateinit var adapter: TracksDetailAdapter

    /**
     * init [AlbumDetailViewModel] with its factories
     */
    private fun initViewModel(albumId: String) {
        adapter = TracksDetailAdapter()
        binding.fragmentAlbumDetailRvTracks.adapter = adapter
        binding.fragmentAlbumDetailRvTracks.layoutManager = LinearLayoutManager(
            context,
            LinearLayoutManager.VERTICAL,
            false
        )
        //init use case
        val fetchTracksByAlbumUseCase =
            FetchTracksByAlbumUseCase(TrackRepository.getInstance(requireContext()))
        val fetchAlbumDetailUseCase =
            FetchAlbumDetailUseCase(AlbumRepository.getInstance(requireContext()))
        val updateFavoriteAlbumUseCase =
            UpdateFavoriteAlbumUseCase(AlbumRepository.getInstance(requireContext()))

        val vmFactory =
            AlbumDetailViewModelFactory(
                albumId,
                fetchTracksByAlbumUseCase,
                fetchAlbumDetailUseCase,
                updateFavoriteAlbumUseCase,
            )
        viewModel = ViewModelProvider(this, vmFactory)[AlbumDetailViewModel::class.java]
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
            onTrackStateChanged()
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

    /**
     *
     */
    private suspend fun onAlbumStateChanged() {
        viewModel.albumState().collect {
            when (it.currentStateEnum) {
                DataStateEnum.IDLE -> {
                    binding.fragmentAlbumDetailPb.visibility = View.GONE
                    binding.fragmentAlbumDetailLlError.visibility = View.GONE
                    binding.fragmentAlbumDetailClHeader.visibility = View.GONE
                    binding.fragmentAlbumDetailTvToolbarTitle.visibility = View.GONE
                }
                DataStateEnum.ERROR -> {
                    binding.fragmentAlbumDetailPb.visibility = View.GONE
                    binding.fragmentAlbumDetailClHeader.visibility = View.GONE
                    binding.fragmentAlbumDetailTvToolbarTitle.visibility = View.GONE
                    binding.fragmentAlbumDetailLlError.visibility = View.VISIBLE
                    binding.fragmentAlbumDetailTvError.text = it.errorMessage
                }
                DataStateEnum.LOADING -> {
                    binding.fragmentAlbumDetailClHeader.visibility = View.GONE
                    binding.fragmentAlbumDetailLlError.visibility = View.GONE
                    binding.fragmentAlbumDetailTvToolbarTitle.visibility = View.GONE
                    binding.fragmentAlbumDetailPb.visibility = View.VISIBLE
                }
                DataStateEnum.SUCCESS -> {
                    binding.fragmentAlbumDetailPb.visibility = View.GONE
                    binding.fragmentAlbumDetailLlError.visibility = View.GONE
                    binding.fragmentAlbumDetailTvToolbarTitle.visibility = View.VISIBLE
                    binding.fragmentAlbumDetailClHeader.visibility = View.VISIBLE
                    binding.fragmentAlbumDetailLlMark.visibility =
                        if (it.album?.score != null || it.album?.scoreVotes != null) View.VISIBLE
                        else View.GONE
                    binding.fragmentAlbumDetailTvAlbumDesc.visibility =
                        if (it.album?.description != null) View.VISIBLE
                        else View.GONE

                    binding.fragmentAlbumDetailTvTitle.text = it.album?.albumName
                    binding.fragmentAlbumDetailTvMark.text = it.album?.score.toString()
                    binding.fragmentAlbumDetailTvVote.text =
                        getString(R.string.fragmentAlbumDetail_tv_label_albumVotes,
                            it.album?.scoreVotes)
                    binding.fragmentAlbumDetailTvAlbumDesc.text = it.album?.description
                    binding.fragmentAlbumDetailTvToolbarTitle.text = it.album?.artistName
                    it.album?.imageUrl?.let { url ->
                        initHeaderBackground(url)
                        initHeaderImage(url)
                    }
                    it.album?.isFavorite?.let { isFav -> initFavoriteIcon(isFav) }
                }
            }
        }
    }

    /**
     *
     */
    private suspend fun onTrackStateChanged() {
        viewModel.trackListState().collect {
            when (it.currentStateEnum) {
                DataStateEnum.IDLE -> {
                    binding.fragmentAlbumDetailClContent.visibility = View.GONE
                    binding.fragmentAlbumDetailLlError.visibility = View.GONE
                    binding.fragmentAlbumDetailPb.visibility = View.GONE
                }
                DataStateEnum.ERROR -> {
                    binding.fragmentAlbumDetailClContent.visibility = View.GONE
                    binding.fragmentAlbumDetailPb.visibility = View.GONE
                    binding.fragmentAlbumDetailLlError.visibility = View.VISIBLE
                    binding.fragmentAlbumDetailTvError.text = it.errorMessage
                }
                DataStateEnum.LOADING -> {
                    binding.fragmentAlbumDetailClContent.visibility = View.GONE
                    binding.fragmentAlbumDetailLlError.visibility = View.GONE
                    binding.fragmentAlbumDetailPb.visibility = View.VISIBLE
                }
                DataStateEnum.SUCCESS -> {
                    binding.fragmentAlbumDetailPb.visibility = View.GONE
                    binding.fragmentAlbumDetailLlError.visibility = View.GONE
                    binding.fragmentAlbumDetailClContent.visibility = View.VISIBLE
                    binding.fragmentAlbumDetailTvTrackNumber.text = getString(
                        R.string.fragmentAlbumDetail_tv_label_tracksNumber,
                        it.tracks?.size
                    )
                    adapter.submitList(it.tracks)
                }
            }
        }
    }

    private fun initButtons() {
        binding.fragmentAlbumDetailTb.setNavigationOnClickListener {
            NavHostFragment.findNavController(this).navigateUp()
        }
        binding.fragmentAlbumDetailBtError.setOnClickListener {
            viewModel.refreshData()
        }
    }

    private fun initHeaderBackground(imageUrl: String) {
        val requestOptions = RequestOptions()
            .error(R.drawable.ic_launcher_foreground)
        Glide.with(binding.fragmentAlbumDetailIvBackground)
            .setDefaultRequestOptions(requestOptions)
            .load(imageUrl)
            .into(binding.fragmentAlbumDetailIvBackground)
    }

    private fun initHeaderImage(imageUrl: String) {
        val radius = binding.root.context.resources.getDimensionPixelSize(R.dimen.corner_radius_m)
        val requestOptions = RequestOptions()
            .error(R.drawable.ic_launcher_foreground)
        Glide.with(binding.fragmentAlbumDetailIvThumb)
            .setDefaultRequestOptions(requestOptions)
            .load(imageUrl)
            .transform(RoundedCorners(radius))
            .into(binding.fragmentAlbumDetailIvThumb)
    }

    private fun setFavoriteIcon(id: Int) {
        binding.fragmentAlbumDetailTb.menu.findItem(R.id.menuDetail_item_favorite)?.icon =
            ContextCompat.getDrawable(requireContext(), id)
    }

    private fun initFavoriteIcon(isFavorite: Boolean) {
        if (isFavorite) {
            setFavoriteIcon(R.drawable.ic_baseline_favorite_24)
        } else setFavoriteIcon(R.drawable.ic_baseline_favorite_border_24)
    }

    private fun onUserClickOnFavorite() {
        lifecycleScope.launch {
            val isFav = viewModel.albumState().first().album?.isFavorite ?: false
            viewModel.updateFavoriteAlbum(!isFav)
            initFavoriteIcon(isFav)
        }
    }

    companion object {
        const val ARG_ALBUM_DETAIL_ID = "ARG_ALBUM_DETAIL_ID"
    }
}