package com.example.watchthis.requests

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.watchthis.databinding.FragmentRequestsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RequestsFragment : Fragment() {
    private lateinit var binding: FragmentRequestsBinding
    private val requestsViewModel: RequestsViewModel by viewModels()
    private lateinit var requestsAdapter: RequestsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRequestsBinding.inflate(inflater, container, false)
        requestsAdapter = RequestsAdapter()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requestsViewModel.requests.observe(viewLifecycleOwner) { requests ->
            requests?.let { requestsAdapter.updateRequests(it) }
        }

        binding.rvRequestsList.run {
            layoutManager = LinearLayoutManager(context)
            adapter = requestsAdapter
        }
    }

}
