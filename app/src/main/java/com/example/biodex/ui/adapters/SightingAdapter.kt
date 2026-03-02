package com.example.biodex.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
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

                root.setOnClickListener { }
            }
        }
    }

    class SightingDiffCallback : DiffUtil.ItemCallback<Sighting>() {
        override fun areItemsTheSame(
            oldItem: Sighting,
            newItem: Sighting
        ): Boolean {
            TODO("Not yet implemented")
        }

        override fun areContentsTheSame(
            oldItem: Sighting,
            newItem: Sighting
        ): Boolean {
            TODO("Not yet implemented")
        }

    }
}