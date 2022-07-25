package com.example.task.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.RoundedCornersTransformation
import com.example.task.R
import com.example.task.data.models.Product
import com.example.task.databinding.ProductItemBinding
import com.example.task.utilities.ProductRecycleViewInterface

class ProductRecyclerViewAdapter(
    private val callback: ProductRecycleViewInterface,
    private val addToCartCallback: ((Product) -> Unit)?
) :
    RecyclerView.Adapter<ProductRecyclerViewAdapter.ProductRecyclerViewHolder>() {
    private lateinit var binding: ProductItemBinding

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Product>() {
            override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
                return oldItem == newItem
            }
        }
    }

    private val mDiffer = AsyncListDiffer(this, DIFF_CALLBACK)


    inner class ProductRecyclerViewHolder :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(product: Product, callback: ProductRecycleViewInterface) {
            binding.product = product
            binding.imProductView.load(product.image) {
                placeholder(R.drawable.ic_baseline_image_not_supported_24)
                transformations(RoundedCornersTransformation(25f))
                crossfade(true)
            }
            binding.cvParent.setOnClickListener {
                callback.onItemClick(product)
            }
            binding.btnAddToCart.setOnClickListener{
                addToCartCallback!!(product)
            }
        }
    }

    fun submitList(list: List<Product>) = mDiffer.submitList(list)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductRecyclerViewHolder {
        binding = ProductItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductRecyclerViewHolder()
    }

    override fun onBindViewHolder(holder: ProductRecyclerViewHolder, position: Int) {
        holder.bind(mDiffer.currentList[position], callback)
    }

    override fun getItemCount() = mDiffer.currentList.size
}