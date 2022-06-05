package com.digikrafi.mybikes.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.digikrafi.mybikes.databinding.ItemBikeInfoBinding
import com.digikrafi.mybikes.model.Features

/**
 * Created by rahul,p
 * BikeInfoAdapter class that recycles views
 */
class BikeInfoAdapter(private val imageClickedListener: (features: Features) -> Unit) :
    ListAdapter<Features, BikeInfoAdapter.ImageResultHolder>(ImageDataDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageResultHolder {
        return ImageResultHolder(
            ItemBikeInfoBinding.inflate(
                LayoutInflater.from(
                    parent.context
                ), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ImageResultHolder, position: Int) {
        val item = getItem(position)
        holder.itemView.setOnClickListener {
            imageClickedListener.invoke(item)
        }

        holder.bind(item)
    }

    inner class ImageResultHolder(private val binding: ItemBikeInfoBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(imageItem: Features) {
            binding.feature = imageItem
        }

    }
}

class ImageDataDiffCallback : DiffUtil.ItemCallback<Features>() {
    override fun areItemsTheSame(oldItem: Features, newItem: Features): Boolean {
        return oldItem.id == newItem.id
    }

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: Features, newItem: Features): Boolean {
        return oldItem == newItem
    }
}