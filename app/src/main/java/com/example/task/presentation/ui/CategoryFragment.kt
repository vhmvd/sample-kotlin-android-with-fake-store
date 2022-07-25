package com.example.task.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.task.R
import com.example.task.categories.CategoryViewModel
import com.example.task.databinding.FragmentCategoryBinding
import com.example.task.presentation.adapters.CategoryRecyclerViewAdapter
import com.example.task.utilities.ApiStatus
import com.example.task.utilities.CategoryRecycleViewInterface
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class CategoryFragment : Fragment(), CategoryRecycleViewInterface {
    private lateinit var binding: FragmentCategoryBinding
    private val adapter = CategoryRecyclerViewAdapter(listOf(), this)
    private val viewModel: CategoryViewModel by lazy {
        ViewModelProvider(this)[CategoryViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCategoryBinding.inflate(inflater)
        binding.rvCategoryList.adapter = adapter
        viewModel.status.observe(viewLifecycleOwner) { status ->
            when (status) {
                ApiStatus.DONE -> binding.animationRotation.visibility = View.GONE
                ApiStatus.ERROR -> Toast.makeText(
                    context,
                    "No Internet!",
                    Toast.LENGTH_LONG
                ).show()
                ApiStatus.LOCAL -> {
                    binding.animationRotation.visibility = View.GONE
                }
                else -> Toast.makeText(
                    context,
                    "Error!",
                    Toast.LENGTH_LONG
                ).show()
            }
            observeCategories()
        }
        return binding.root
    }

    private fun observeCategories() {
        adapter.updateData(viewModel.categories.data)
        adapter.notifyDataSetChanged()
    }

    override fun onItemClick(category: String) {
        val fm = ProductFragment()
        val bundle = Bundle()
        bundle.putString("category", category)
        fm.arguments = bundle
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.frag_cont_view, fm)
            .addToBackStack(null)
            .commit()
    }
}