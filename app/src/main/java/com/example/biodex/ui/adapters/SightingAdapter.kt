package com.example.biodex.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.RoundedCornersTransformation
import com.example.biodex.R
import com.example.biodex.databinding.ItemSightBinding
import com.example.biodex.domain.model.Sighting

class SightingAdapter(
    private val onSightingClick: (Sighting) -> Unit
): ListAdapter<Sighting, SightingAdapter.SightingViewHolder>(SightingDiffCallback()) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SightingAdapter.SightingViewHolder {
        val binding = ItemSightBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        return SightingViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: SightingAdapter.SightingViewHolder,
        position: Int
    ) {
        holder.bind(getItem(position))
    }

    class SightingViewHolder(private val binding: ItemSightBinding): RecyclerView.ViewHolder(binding.root){

        fun bind(sighting: Sighting) {
            with(binding){
                tvTitle.text = sighting.title
                tvDescription.text = sighting.description
                ivSightImage.load(sighting.photoURL) {
                    crossfade(true)
                    placeholder(R.drawable.ic_launcher_foreground)
                    error(R.drawable.ic_launcher_foreground)
                    transformations(RoundedCornersTransformation(12f))
                }
                root.setOnClickListener { }
            }
        }
    }

    class SightingDiffCallback : DiffUtil.ItemCallback<Sighting>() {
        override fun areItemsTheSame(
            oldItem: Sighting,
            newItem: Sighting
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: Sighting,
            newItem: Sighting
        ): Boolean {
            return oldItem == newItem
        }

    }
}