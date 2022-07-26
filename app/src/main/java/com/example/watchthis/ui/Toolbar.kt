package com.example.watchthis.ui

import android.content.Context
import android.view.LayoutInflater
import android.widget.ImageButton
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.findNavController
import com.example.watchthis.R
import com.example.watchthis.databinding.ToolbarBinding

class Toolbar(context: Context): ConstraintLayout(context) {
    var binding: ToolbarBinding

    init {
        inflate(context, R.layout.toolbar, this)
        binding = ToolbarBinding.inflate(LayoutInflater.from(context))

        binding.ibSettings.setOnClickListener {
            findNavController().navigate(R.id.action_requestsFragment_to_settingsFragment)
        }
    }
}