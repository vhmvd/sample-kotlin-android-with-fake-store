package com.example.task.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.task.databinding.FragmentFilterBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class FilterBottomSheetFragment : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentFilterBottomSheetBinding
    lateinit var clickListner: (Int) -> Unit
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFilterBottomSheetBinding.inflate(inflater)
        binding.btnSort.setOnClickListener {
            when (binding.radioGroup.checkedRadioButtonId) {
                binding.radioButton1.id -> {
                    clickListner(1)
                    dismiss()
                }
                binding.radioButton2.id -> {
                    clickListner(2)
                    dismiss()
                }
                else -> {
                    Toast.makeText(
                        context,
                        "Filter not selected!",
                        Toast.LENGTH_SHORT
                    ).show()
                    dismiss()
                }
            }
        }
        return binding.root
    }

    fun onSortClickListener(callback: (Int) -> Unit) {
        clickListner = callback
    }
}