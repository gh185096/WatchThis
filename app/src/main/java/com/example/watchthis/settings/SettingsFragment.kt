package com.example.watchthis.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.watchthis.databinding.FragmentSettingsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingsFragment : Fragment() {
    private lateinit var binding: FragmentSettingsBinding
    private val settingsViewModel: SettingsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        settingsViewModel.currentPIN.observe(viewLifecycleOwner) { currentPIN ->
            binding.tvCurrentPin.text = "Current PIN: $currentPIN"
        }

        with(binding) {
            btSave.setOnClickListener {
                val newPIN = etManagerPin.editText?.text.toString()
                if (newPIN.isNotBlank() && newPIN.length >= 4) {
                    settingsViewModel.updatePIN(newPIN)
                }
            }
        }

    }

}