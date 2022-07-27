package com.example.watchthis.requests

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.watchthis.R
import com.example.watchthis.databinding.FragmentRequestsBinding
import com.example.watchthis.objects.Request
import com.example.watchthis.objects.RequestState
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RequestsFragment : Fragment() {
    private lateinit var binding: FragmentRequestsBinding
    private val requestsViewModel: RequestsViewModel by viewModels()
    private lateinit var requestsAdapter: RequestsAdapter

    private val database = Firebase.firestore.collection("Merchants")
        .document("Merchant 1")

    private val query = database
        .collection("Requests")
        .whereEqualTo("requestState", RequestState.WAITING)

    var requestsQueryOptions: FirestoreRecyclerOptions<Request> = FirestoreRecyclerOptions.Builder<Request>()
            .setQuery(query, Request::class.java)
            .build()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRequestsBinding.inflate(inflater, container, false)
        requestsAdapter = RequestsAdapter(requestsQueryOptions)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requestsViewModel.requests

        with(binding) {
            rvRequestsList.run {
                layoutManager = LinearLayoutManager(context)
                adapter = requestsAdapter
            }

            ibSettings.setOnClickListener { findNavController().navigate(R.id.action_requestsFragment_to_settingsFragment) }
        }
    }

    override fun onStart() {
        super.onStart()
        requestsAdapter.startListening()
    }

    override fun onStop() {
        super.onStop()
        requestsAdapter.stopListening()
    }

}
