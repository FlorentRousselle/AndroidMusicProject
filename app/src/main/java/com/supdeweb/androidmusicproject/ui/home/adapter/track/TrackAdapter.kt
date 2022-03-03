package com.supdeweb.androidmusicproject.ui.home.adapter.track

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
import com.supdeweb.androidmusicproject.data.model.TrackModel
import com.supdeweb.androidmusicproject.databinding.AdapterClassementBinding

class TrackAdapter : ListAdapter<TrackModel, TrackAdapter.TrackViewHolder>(diffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrackViewHolder {

        val binding: AdapterClassementBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.adapter_classement, parent,
            false
        )

        return TrackViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TrackViewHolder, position: Int) {
        getItem(position)?.let { track ->
            val radius =
                holder.binding.root.context.resources.getDimensionPixelSize(R.dimen.corner_radius)
            val requestOptions = RequestOptions()
                .error(R.drawable.ic_launcher_foreground)
            Glide.with(holder.binding.adapterClassementIvDisplayImage)
                .setDefaultRequestOptions(requestOptions)
                .load(track.imageUrl)
                .transform(CenterCrop(), RoundedCorners(radius))
                .into(holder.binding.adapterClassementIvDisplayImage)

            holder.binding.adapterClassementTvFirstText.text = track.trackName
            holder.binding.adapterClassementTvSecondText.text = track.artistName
            holder.binding.adapterClassementTvNumber.text = ((position.plus(1)).toString())
        }
    }

    class TrackViewHolder(var binding: AdapterClassementBinding) :
        RecyclerView.ViewHolder(binding.root)


    companion object {
        private val diffCallback = object :
            DiffUtil.ItemCallback<TrackModel>() {

            override fun areItemsTheSame(
                oldItem: TrackModel,
                newItem: TrackModel,
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: TrackModel,
                newItem: TrackModel,
            ): Boolean {
                return oldItem.trackName == newItem.trackName
                        && oldItem.isFavorite == newItem.isFavorite
                        && oldItem.imageUrl == newItem.imageUrl
                        && oldItem.score == newItem.score
                        && oldItem.style == newItem.style
            }
        }
    }
}
