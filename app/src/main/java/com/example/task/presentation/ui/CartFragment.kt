package com.example.task.presentation.ui

import android.animation.Animator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.task.data.models.Cart
import com.example.task.databinding.FragmentCartBinding
import com.example.task.presentation.adapters.CartRecyclerViewAdapter
import com.example.task.presentation.viewmodels.CartViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CartFragment : Fragment() {

    private val viewModel: CartViewModel by lazy {
        ViewModelProvider(this)[CartViewModel::class.java]
    }
    private lateinit var binding: FragmentCartBinding
    private val adapter = CartRecyclerViewAdapter { deleteClickListener(it) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCartBinding.inflate(inflater)
        binding.animationView.addAnimatorListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator?) {
            }
            override fun onAnimationEnd(animation: Animator?) {
                binding.animationView.visibility = View.INVISIBLE
            }

            override fun onAnimationCancel(animation: Animator?) {
            }

            override fun onAnimationRepeat(animation: Animator?) {
            }

        })
        binding.btnCheckout.setOnClickListener {
            binding.animationView.visibility = View.VISIBLE
            binding.animationView.playAnimation()
        }
        setAdapter()
        viewModelOperations()
        return binding.root
    }

    private fun deleteClickListener(cart: Cart) {
        viewModel.delete(cart)
        adapter.submitList(viewModel.cartList.value!!)
    }

    private fun setAdapter() {
        binding.rvCart.adapter = adapter
    }

    private fun viewModelOperations() {
        viewModel.cartList.observe(viewLifecycleOwner) {
            adapter.submitList(it)
            binding.totalPrice.text = String.format("$%.2f", viewModel.totalPrice())
        }
    }
}