package com.supdeweb.androidmusicproject.ui.rank.adapter.track

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.supdeweb.androidmusicproject.R
import com.supdeweb.androidmusicproject.data.model.TrackModel
import com.supdeweb.androidmusicproject.databinding.AdapterRankItemBinding
import com.supdeweb.androidmusicproject.ui.details.artist.ArtistDetailFragment.Companion.ARG_ARTIST_DETAIL_ID

class TrackAdapter : ListAdapter<TrackModel, TrackAdapter.TrackViewHolder>(diffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrackViewHolder {

        val binding: AdapterRankItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.adapter_rank_item, parent,
            false
        )

        return TrackViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TrackViewHolder, position: Int) {
        getItem(position)?.let { track ->
            val radius =
                holder.binding.root.context.resources.getDimensionPixelSize(R.dimen.corner_radius_m)
            val requestOptions = RequestOptions()
                .error(R.drawable.ic_launcher_foreground)
            Glide.with(holder.binding.adapterRankItemIvDisplayImage)
                .setDefaultRequestOptions(requestOptions)
                .load(track.imageUrl)
                .transform(CenterCrop(), RoundedCorners(radius))
                .into(holder.binding.adapterRankItemIvDisplayImage)

            holder.binding.adapterRankItemTvFirstText.text = track.trackName
            holder.binding.adapterRankItemTvSecondText.text = track.artistName
            holder.binding.adapterRankItemTvNumber.text = ((position.plus(1)).toString())

            // CLICK ACTION
            holder.itemView.setOnClickListener {
                val bundle = bundleOf(ARG_ARTIST_DETAIL_ID to track.artistId)
                holder.itemView.findNavController().navigate(R.id.artistDetailFragment, bundle)
            }
        }
    }

    class TrackViewHolder(var binding: AdapterRankItemBinding) :
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
                        && oldItem.imageUrl == newItem.imageUrl
                        && oldItem.score == newItem.score
                        && oldItem.style == newItem.style
            }
        }
    }
}
