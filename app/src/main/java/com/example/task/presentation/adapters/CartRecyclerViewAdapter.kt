package com.example.task.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.RoundedCornersTransformation
import com.example.task.R
import com.example.task.data.models.Cart
import com.example.task.databinding.CartItemBinding

class CartRecyclerViewAdapter(
    private val callback: ((Cart) -> Unit)
): RecyclerView.Adapter<CartRecyclerViewAdapter.CartRecycleViewHolder>() {
    private lateinit var binding: CartItemBinding

    inner class CartRecycleViewHolder: RecyclerView.ViewHolder(binding.root) {
        fun bind(cart: Cart){
            binding.cart = cart
            binding.imCartProduct.load(cart.image){
                placeholder(R.drawable.ic_baseline_image_not_supported_24)
                transformations(RoundedCornersTransformation(25f))
                crossfade(true)
            }
            binding.btnDelete.setOnClickListener {
                callback(cart)
            }
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Cart>() {
            override fun areItemsTheSame(oldItem: Cart, newItem: Cart): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Cart, newItem: Cart): Boolean {
                return oldItem == newItem
            }
        }
    }

    fun submitList(list: List<Cart>) = mDiffer.submitList(list)

    private val mDiffer = AsyncListDiffer(this, DIFF_CALLBACK)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartRecycleViewHolder {
        binding = CartItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CartRecycleViewHolder()
    }

    override fun onBindViewHolder(holder: CartRecycleViewHolder, position: Int) {
        holder.bind(mDiffer.currentList[position])
    }

    override fun getItemCount() = mDiffer.currentList.size
}