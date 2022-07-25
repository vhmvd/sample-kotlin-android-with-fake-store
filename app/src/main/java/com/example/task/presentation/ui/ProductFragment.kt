package com.example.task.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.task.R
import com.example.task.data.models.Product
import com.example.task.databinding.FragmentProductBinding
import com.example.task.presentation.adapters.ProductRecyclerViewAdapter
import com.example.task.presentation.viewmodels.ProductViewModel
import com.example.task.utilities.ApiStatus
import com.example.task.utilities.ProductRecycleViewInterface
import com.google.android.material.transition.MaterialSharedAxis
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductFragment : Fragment(), ProductRecycleViewInterface {
    private lateinit var binding: FragmentProductBinding
    private val addToCartListener: ((Product) -> Unit)  = {
        addToCart(it)
    }
    private val adapter = ProductRecyclerViewAdapter(this, addToCartListener)
    private val viewModel: ProductViewModel by lazy {
        ViewModelProvider(this)[ProductViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProductBinding.inflate(inflater)
        animation()
        setAdapter()
        viewModelOperations()
        filterButtonClickListener()
        fabCartClickListener()
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        viewModel.updateCartCount()
    }

    private fun fabCartClickListener(){
        binding.fabCart.setOnClickListener{
            val fm = CartFragment()
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.frag_cont_view, fm)
                .addToBackStack(null)
                .commit()
        }
    }

    private fun setAdapter(){
        binding.rvProductList.adapter = adapter
    }

    private fun viewModelOperations(){
        viewModel.getProducts(arguments?.getString("category")!!)
        viewModel.status.observe(viewLifecycleOwner) {
            when (it) {
                ApiStatus.DONE -> binding.animationRotation.visibility = View.GONE
                ApiStatus.LOCAL -> {
                    binding.animationRotation.visibility = View.GONE
                }
                ApiStatus.ERROR -> Toast.makeText(
                    context,
                    "No Internet!",
                    Toast.LENGTH_LONG
                ).show()
                else -> {
                    Toast.makeText(
                        context,
                        "Some internal error occurred!",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
        viewModel.products.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
        viewModel.productCount.observe(viewLifecycleOwner){ productCount ->
            binding.textCount.text = productCount.toString()
        }
    }

    private fun animation(){
        // Transition animation
        exitTransition = MaterialSharedAxis(MaterialSharedAxis.Z, true)
        reenterTransition = MaterialSharedAxis(MaterialSharedAxis.Z, false)
    }

    private fun filterButtonClickListener(){
        binding.btnFilter.setOnClickListener {
            val bottomSheetFragment = FilterBottomSheetFragment()
            bottomSheetFragment.onSortClickListener {
                when (it) {
                    1 -> viewModel.sortPrice()
                    2 -> viewModel.sortAlphabetically()
                }
            }
            bottomSheetFragment.show(
                requireActivity().supportFragmentManager,
                bottomSheetFragment.tag
            )
        }
    }

    override fun onItemClick(product: Product) {
        val fm = AboutFragment()
        val bundle = Bundle()
        bundle.putParcelable("product", product)
        fm.arguments = bundle
        requireActivity().supportFragmentManager.beginTransaction()
            .addSharedElement(requireView(), "shared_element_container")
            .replace(R.id.frag_cont_view, fm)
            .addToBackStack(null)
            .commit()
    }

    private fun addToCart(product: Product){
        viewModel.addToCart(product)
    }
}