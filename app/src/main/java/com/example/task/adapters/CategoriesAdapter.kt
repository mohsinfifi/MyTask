package com.example.task.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.task.databinding.ListItemCategoriesBinding
import com.example.task.databinding.ListItemProductBinding
import com.example.task.models.Product

class CategoriesAdapter(private val listener: OnItemClickListener) :
    ListAdapter<String, CategoriesAdapter.CategoriesViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoriesViewHolder {
        val binding =
            ListItemCategoriesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CategoriesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CategoriesViewHolder, position: Int) {
        val item = getItem(position)
        item.let { holder.bind(it) }
    }

    inner class CategoriesViewHolder(private val binding: ListItemCategoriesBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(
            name: String
        ) {
            binding.tvCategoryTitle.text = name

            binding.tvCategoryTitle.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val selectedCategory = getItem(position)
                    listener.onItemClick(selectedCategory)
                }
            }


        }

    }


    interface OnItemClickListener {
        fun onItemClick(category: String)
    }

    class DiffCallback : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem.length == newItem.length
        }

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

    }


}