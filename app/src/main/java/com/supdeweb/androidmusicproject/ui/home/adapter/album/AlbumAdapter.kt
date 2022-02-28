package com.supdeweb.androidmusicproject.ui.home.adapter.album

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.supdeweb.androidmusicproject.R
import com.supdeweb.androidmusicproject.data.model.AlbumModel
import com.supdeweb.androidmusicproject.databinding.AdapterAlbumBinding

class AlbumAdapter : ListAdapter<AlbumModel, AlbumAdapter.AlbumViewHolder>(diffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumViewHolder {

        val binding: AdapterAlbumBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.adapter_album, parent,
            false
        )

        return AlbumViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AlbumViewHolder, position: Int) {
        getItem(position)?.let { album ->
            val radius =
                holder.binding.root.context.resources.getDimensionPixelSize(R.dimen.corner_radius)
            val requestOptions = RequestOptions()
                .error(R.drawable.ic_launcher_foreground)
            Glide.with(holder.binding.adapterAlbumIvDisplayImage)
                .setDefaultRequestOptions(requestOptions)
                .load(album.imageUrl)
                .transform(CenterCrop(), RoundedCorners(radius))
                .into(holder.binding.adapterAlbumIvDisplayImage)

            holder.binding.adapterAlbumTvFirstText.text = album.title
            holder.binding.adapterAlbumTvSecondText.text = album.sales.toString()
            holder.binding.adapterAlbumTvNumber.text = ((position.plus(1)).toString())
        }
    }

    class AlbumViewHolder(var binding: AdapterAlbumBinding) :
        RecyclerView.ViewHolder(binding.root)


    companion object {
        private val diffCallback = object :
            DiffUtil.ItemCallback<AlbumModel>() {

            override fun areItemsTheSame(
                oldItem: AlbumModel,
                newItem: AlbumModel,
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: AlbumModel,
                newItem: AlbumModel,
            ): Boolean {
                // TODO  && oldItem.currentDistance == newItem.currentDistance
                // cannot find UserLocationModel
                return oldItem.title == newItem.title
                        && oldItem.isFavorite == newItem.isFavorite
                        && oldItem.imageUrl == newItem.imageUrl
                        && oldItem.sales == newItem.sales
                        && oldItem.style == newItem.style
            }
        }
    }
}
