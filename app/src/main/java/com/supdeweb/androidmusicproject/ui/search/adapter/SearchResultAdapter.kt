package com.supdeweb.androidmusicproject.ui.search.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.supdeweb.androidmusicproject.R
import com.supdeweb.androidmusicproject.data.model.AlbumModel
import com.supdeweb.androidmusicproject.data.model.ArtistModel
import com.supdeweb.androidmusicproject.databinding.AdapterItemAlbumBinding
import com.supdeweb.androidmusicproject.databinding.AdapterItemArtistBinding
import com.supdeweb.androidmusicproject.databinding.AdapterItemTitleBinding

class SearchResultAdapter :
    RecyclerView.Adapter<SearchResultViewHolder>() {

    var items = listOf<Any>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchResultViewHolder {
        return when (viewType) {
            R.layout.adapter_item_title -> SearchResultViewHolder.TitleViewHolder(
                AdapterItemTitleBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
            R.layout.adapter_item_album -> SearchResultViewHolder.SearchAlbumViewHolder(
                AdapterItemAlbumBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
            R.layout.adapter_item_artist -> SearchResultViewHolder.SearchArtistViewHolder(
                AdapterItemArtistBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
            else -> throw IllegalAccessException("Invalid ViewHolder")
        }
    }

    override fun onBindViewHolder(holder: SearchResultViewHolder, position: Int) {
        return when (holder) {
            is SearchResultViewHolder.TitleViewHolder -> holder.bind(items[position] as String)
            is SearchResultViewHolder.SearchArtistViewHolder -> holder.bind(items[position] as ArtistModel)
            is SearchResultViewHolder.SearchAlbumViewHolder -> holder.bind(items[position] as AlbumModel)
        }
    }

    override fun getItemCount() = items.size

    override fun getItemViewType(position: Int): Int {
        return when (items[position]) {
            is String -> R.layout.adapter_item_title
            is AlbumModel -> R.layout.adapter_item_album
            is ArtistModel -> R.layout.adapter_item_artist
            else -> throw IllegalArgumentException("Invalid ViewType Provided")
        }
    }
}