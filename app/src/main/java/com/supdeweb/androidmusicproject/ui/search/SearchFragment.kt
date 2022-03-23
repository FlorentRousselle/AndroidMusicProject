package com.supdeweb.androidmusicproject.ui.search

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnFocusChangeListener
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.supdeweb.androidmusicproject.R
import com.supdeweb.androidmusicproject.data.repository.AlbumRepository
import com.supdeweb.androidmusicproject.data.repository.ArtistRepository
import com.supdeweb.androidmusicproject.databinding.FragmentSearchBinding
import com.supdeweb.androidmusicproject.domain.features.album.FetchAlbumsByArtistNameUseCase
import com.supdeweb.androidmusicproject.domain.features.artist.FetchArtistByNameUseCase
import com.supdeweb.androidmusicproject.ui.search.adapter.SearchResultAdapter
import com.supdeweb.androidmusicproject.ui.utils.DataStateEnum
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


class SearchFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_search, null, false)

        textListener = object : TextWatcher {
            private var searchFor =
                binding.fragmentSearchVSearch.componentEditTextSearchEtFill.text.toString()

            override fun afterTextChanged(s: Editable?) {}

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val searchText = s.toString().trim()
                if (searchText != searchFor) {
                    searchFor = searchText

                    textChangedJob?.cancel()
                    textChangedJob = lifecycleScope.launch(Dispatchers.Main) {
                        delay(500L)
                        if (searchText == searchFor) {
                            viewModel.observeArtistsByName(searchText)
                        }
                    }
                }
            }
        }
        binding.fragmentSearchVSearch.componentEditTextSearchEtFill.onFocusChangeListener =
            OnFocusChangeListener { v, hasFocus ->
                if (hasFocus) {
                    val imm =
                        requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.showSoftInput(v, InputMethodManager.SHOW_IMPLICIT)
                }
            }
        binding.fragmentSearchVSearch.componentEditTextSearchEtFill.requestFocus()


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewModel()
        observeViewModel()
        initRecyclerView()
        initButtons()
    }

    override fun onResume() {
        super.onResume()
        binding.fragmentSearchVSearch.componentEditTextSearchEtFill
            .addTextChangedListener(textListener)
    }

    override fun onPause() {
        val imm =
            requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(requireView().windowToken, 0)
        binding.fragmentSearchVSearch.componentEditTextSearchEtFill
            .removeTextChangedListener(textListener)
        super.onPause()
    }


    override fun onDestroy() {
        textChangedJob?.cancel()
        super.onDestroy()
    }

    /**
     * the view model
     */
    private lateinit var viewModel: SearchViewModel

    /**
     * the binding
     */
    private lateinit var binding: FragmentSearchBinding

    /**
     *
     */
    private lateinit var adapter: SearchResultAdapter

    private var textChangedJob: Job? = null
    private lateinit var textListener: TextWatcher


    /**
     * init [SearchViewModel] with its factories
     */
    private fun initViewModel() {


        // init use cases
        val fetchArtistByNameUseCase =
            FetchArtistByNameUseCase(ArtistRepository.getInstance(requireContext()))
        val fetchAlbumsByArtistNameUseCase =
            FetchAlbumsByArtistNameUseCase(AlbumRepository.getInstance(requireContext()))

        val vmFactory =
            SearchViewModelFactory(
                fetchArtistByNameUseCase,
                fetchAlbumsByArtistNameUseCase,
            )
        viewModel = ViewModelProvider(this, vmFactory)[SearchViewModel::class.java]
    }

    /**
     * observe value in [SearchViewModel]
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
            onArtistsStateChanged()
        }
        lifecycleScope.launch {
            onAlbumsStateChanged()
        }

    }

    private suspend fun onArtistsStateChanged() {
        viewModel.artistState().collect {
            when (it.currentStateEnum) {
                DataStateEnum.IDLE -> {
                    binding.fragmentSearchPb.visibility = View.GONE
                    binding.fragmentSearchLlError.visibility = View.GONE
                    binding.fragmentSearchRv.visibility = View.GONE
                }
                DataStateEnum.ERROR -> {
                    binding.fragmentSearchPb.visibility = View.GONE
                    binding.fragmentSearchLlError.visibility = View.VISIBLE
                    binding.fragmentSearchTvError.text = it.errorMessage
                    binding.fragmentSearchRv.visibility = View.GONE
                }
                DataStateEnum.LOADING -> {
                    binding.fragmentSearchPb.visibility = View.VISIBLE
                    binding.fragmentSearchLlError.visibility = View.GONE
                    binding.fragmentSearchRv.visibility = View.GONE
                }
                DataStateEnum.SUCCESS -> {
                    binding.fragmentSearchPb.visibility = View.GONE
                    binding.fragmentSearchLlError.visibility = View.GONE
                    binding.fragmentSearchRv.visibility = View.VISIBLE
                    if (it.artists.isNullOrEmpty()) {
                        binding.fragmentSearchTvEmptyList.visibility = View.VISIBLE
                    } else {
                        binding.fragmentSearchTvEmptyList.visibility = View.GONE
                        adapter.items += listOf(getString(R.string.fragmentFavorite_tv_label_artists))
                        adapter.items += it.artists
                    }
                }
            }
        }
    }

    private suspend fun onAlbumsStateChanged() {
        viewModel.albumState().collect {
            when (it.currentStateEnum) {
                DataStateEnum.IDLE -> {
                    binding.fragmentSearchPb.visibility = View.GONE
                    binding.fragmentSearchLlError.visibility = View.GONE
                }
                DataStateEnum.ERROR -> {
                    binding.fragmentSearchPb.visibility = View.GONE
                    binding.fragmentSearchLlError.visibility = View.VISIBLE
                    binding.fragmentSearchTvError.text = it.errorMessage
                }
                DataStateEnum.LOADING -> {
                    binding.fragmentSearchPb.visibility = View.VISIBLE
                    binding.fragmentSearchLlError.visibility = View.GONE
                }
                DataStateEnum.SUCCESS -> {
                    binding.fragmentSearchPb.visibility = View.GONE
                    binding.fragmentSearchLlError.visibility = View.GONE
                    if (!it.albums.isNullOrEmpty()) {
                        binding.fragmentSearchTvEmptyList.visibility = View.GONE
                        adapter.items += listOf(getString(R.string.fragmentRank_tabLayout_title_albums))
                        adapter.items += it.albums
                    }
                }
            }
        }
    }

    private fun initRecyclerView() {
        adapter = SearchResultAdapter()
        binding.fragmentSearchRv.adapter = adapter
        binding.fragmentSearchRv.layoutManager = LinearLayoutManager(
            context,
            LinearLayoutManager.VERTICAL,
            false
        )
    }

    private fun initButtons() {
        binding.fragmentSearchBtError.setOnClickListener {
            viewModel.observeArtistsByName(binding.fragmentSearchVSearch.componentEditTextSearchEtFill.text.toString())
        }

        binding.fragmentSearchVSearch.componentEditTextSearchIvClose.setOnClickListener {
            binding.fragmentSearchVSearch.componentEditTextSearchEtFill.setText("")
            adapter.items = emptyList()
        }
    }

}