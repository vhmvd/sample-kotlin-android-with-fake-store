package com.example.task.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.task.databinding.CategoryItemBinding
import com.example.task.utilities.CategoryRecycleViewInterface


/**
 * [RecyclerView.Adapter] that can display a Category.
 */
class CategoryRecyclerViewAdapter(
    private var values: List<String>,
    private val callback: CategoryRecycleViewInterface
) : RecyclerView.Adapter<CategoryRecyclerViewAdapter.CategoryRecyclerViewHolder>() {
    private lateinit var binding: CategoryItemBinding

    inner class CategoryRecyclerViewHolder :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(category: String, callback: CategoryRecycleViewInterface) {
            binding.category = category
            binding.btnCategoryName.setOnClickListener {
                callback.onItemClick(category)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CategoryRecyclerViewHolder {
        binding = CategoryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CategoryRecyclerViewHolder()
    }

    override fun onBindViewHolder(holder: CategoryRecyclerViewHolder, position: Int) {
        holder.bind(values[position], callback)
    }


    fun updateData(values: List<String>) {
        this.values = values
    }

    override fun getItemCount() = values.size

}