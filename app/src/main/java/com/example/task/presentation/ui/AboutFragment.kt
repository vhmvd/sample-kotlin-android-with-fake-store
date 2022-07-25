package com.example.task.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import coil.load
import com.example.task.R
import com.example.task.data.models.Product
import com.example.task.databinding.FragmentAboutBinding
import com.example.task.presentation.viewmodels.AboutViewModel
import com.google.android.material.transition.MaterialSharedAxis

class AboutFragment : Fragment() {
    private lateinit var binding: FragmentAboutBinding
    private val viewModel: AboutViewModel by lazy {
        ViewModelProvider(this)[AboutViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAboutBinding.inflate(inflater)
        viewModel.update(arguments?.get("product") as Product)
        binding.product = viewModel.currentProduct
        binding.imProduct.load(viewModel.currentProduct.image) {
            placeholder(R.drawable.ic_baseline_image_not_supported_24)
//            transformations(RoundedCornersTransformation(25f))
            crossfade(true)
        }

        // Transition animation
        enterTransition = MaterialSharedAxis(MaterialSharedAxis.Z, true)
        returnTransition = MaterialSharedAxis(MaterialSharedAxis.Z, false)

        return binding.root
    }

}