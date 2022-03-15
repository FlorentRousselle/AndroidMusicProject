package com.supdeweb.androidmusicproject.ui.details.album

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.supdeweb.androidmusicproject.R
import com.supdeweb.androidmusicproject.data.model.TrackModel
import com.supdeweb.androidmusicproject.databinding.AdapterTrackDetailItemBinding

class TracksDetailAdapter : ListAdapter<TrackModel, TracksDetailAdapter.TrackViewHolder>(
    diffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrackViewHolder {

        val binding: AdapterTrackDetailItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.adapter_track_detail_item, parent,
            false
        )

        return TrackViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TrackViewHolder, position: Int) {
        getItem(position)?.let { track ->
            holder.binding.adapterTrackDetailTvTitleTrack.text = track.trackName
            holder.binding.adapterTrackDetailTvNumber.text = ((position.plus(1)).toString())
        }
    }

    class TrackViewHolder(var binding: AdapterTrackDetailItemBinding) :
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
            }
        }
    }
}
