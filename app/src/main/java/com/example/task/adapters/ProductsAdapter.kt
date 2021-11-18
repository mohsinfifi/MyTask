package com.example.task.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.task.databinding.ListItemProductBinding
import com.example.task.models.Product

class ProductsAdapter(private val listener: OnItemClickListener) :
    ListAdapter<Product, ProductsAdapter.ProductsViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductsViewHolder {
        val binding =
            ListItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductsViewHolder, position: Int) {
        val item = getItem(position)
        item.let { holder.bind(it) }
    }

    inner class ProductsViewHolder(private val binding: ListItemProductBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(
            product: Product
        ) {
            binding.tvProductName.text = product.title
            binding.tvProductPrice.text = product.price.toString()
            Glide.with(binding.ivProductImage).load(product.image).into(binding.ivProductImage)
            binding.ivProductImage.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val selectedProduct = getItem(position)
                    listener.onItemClick(selectedProduct)
                }
            }

            binding.btnAddToCart.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val selectedProduct = getItem(position)
                    listener.addToCart(selectedProduct)
                }
            }

        }

    }


    interface OnItemClickListener {
        fun onItemClick(product: Product)
        fun addToCart(product: Product)
    }

    class DiffCallback : DiffUtil.ItemCallback<Product>() {
        override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem == newItem
        }

    }


}