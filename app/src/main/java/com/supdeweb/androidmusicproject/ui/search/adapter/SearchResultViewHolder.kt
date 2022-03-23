package com.supdeweb.androidmusicproject.ui.search.adapter

import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.supdeweb.androidmusicproject.R
import com.supdeweb.androidmusicproject.data.model.AlbumModel
import com.supdeweb.androidmusicproject.data.model.ArtistModel
import com.supdeweb.androidmusicproject.databinding.AdapterItemAlbumBinding
import com.supdeweb.androidmusicproject.databinding.AdapterItemArtistBinding
import com.supdeweb.androidmusicproject.databinding.AdapterItemTitleBinding
import com.supdeweb.androidmusicproject.design.RadiusButton
import com.supdeweb.androidmusicproject.ui.details.album.AlbumDetailFragment.Companion.ARG_ALBUM_DETAIL_ID
import com.supdeweb.androidmusicproject.ui.details.artist.ArtistDetailFragment.Companion.ARG_ARTIST_DETAIL_ID

sealed class SearchResultViewHolder(binding: ViewBinding) : RecyclerView.ViewHolder(binding.root) {

    class TitleViewHolder(
        private val binding: AdapterItemTitleBinding,
    ) : SearchResultViewHolder(binding) {
        fun bind(title: String) {
            binding.adapterItemTitleTvTitle.text = title
        }
    }

    class SearchArtistViewHolder(
        private val binding: AdapterItemArtistBinding,
    ) : SearchResultViewHolder(binding) {
        fun bind(artist: ArtistModel) {
            val radiusButton = binding.adapterItemArtistRb
            radiusButton.customizeButton(
                textLabel = artist.name,
                imageUrl = artist.imageUrl,
                isArtist = true
            )
            radiusButton.setListener(object : RadiusButton.RadiusButtonListener {
                override fun onUserClickOnItem() {
                    val bundle = bundleOf(ARG_ARTIST_DETAIL_ID to artist.id)
                    radiusButton.findNavController()
                        .navigate(R.id.artistDetailFragment, bundle)
                }

            })
        }
    }

    class SearchAlbumViewHolder(
        private val binding: AdapterItemAlbumBinding,
    ) : SearchResultViewHolder(binding) {
        fun bind(album: AlbumModel) {
            val radiusButton = binding.adapterItemAlbumRb
            radiusButton.customizeButton(
                textLabel = album.albumName,
                firstLabel = album.artistName,
                imageUrl = album.imageUrl,
            )
            radiusButton.setListener(object : RadiusButton.RadiusButtonListener {
                override fun onUserClickOnItem() {
                    val bundle = bundleOf(ARG_ALBUM_DETAIL_ID to album.id)
                    radiusButton.findNavController()
                        .navigate(R.id.albumDetailFragment, bundle)
                }

            })
        }
    }
}
