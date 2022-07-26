package com.example.watchthis.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.watchthis.databinding.FragmentSettingsBinding
import com.example.watchthis.requests.RequestsAdapter
import com.example.watchthis.requests.RequestsViewModel

class SettingsFragment : Fragment() {
    private lateinit var binding: FragmentSettingsBinding
    //private val requestsViewModel: RequestsViewModel by viewModels()

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

    }

}