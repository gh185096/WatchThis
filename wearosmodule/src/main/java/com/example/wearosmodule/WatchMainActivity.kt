package com.example.wearosmodule

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.wearosmodule.objects.Request
import com.example.wearosmodule.objects.RequestState
import com.example.wearosmodule.databinding.ActivityWatchMainBinding
import com.example.wearosmodule.databinding.RequestsListItemBinding
import com.example.wearosmodule.objects.SecurityFeature
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class WatchMainActivity : FragmentActivity() {

    private lateinit var binding: ActivityWatchMainBinding
    private lateinit var watchRequestAdapter: WatchRequestsAdapter
    private val database = Firebase.firestore.collection("Merchants")
        .document("Merchant 1")

    private val query = database
        .collection("Requests")
        .whereEqualTo("requestState", RequestState.WAITING)

    private var requestsQueryOptions: FirestoreRecyclerOptions<Request> = FirestoreRecyclerOptions.Builder<Request>()
        .setQuery(query, Request::class.java)
        .build()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityWatchMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        watchRequestAdapter = WatchRequestsAdapter(requestsQueryOptions)
        binding.rvRequestsList.layoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.HORIZONTAL, false)
        binding.rvRequestsList.adapter = watchRequestAdapter
        PagerSnapHelper().attachToRecyclerView(binding.rvRequestsList)
    }


    override fun onStart() {
        super.onStart()
        watchRequestAdapter.startListening()
    }

    override fun onStop() {
        super.onStop()
        watchRequestAdapter.stopListening()
    }
}

class WatchRequestsAdapter(options: FirestoreRecyclerOptions<Request>) : FirestoreRecyclerAdapter<Request, WatchRequestsAdapter.RequestViewHolder>(options) {

    private val database = Firebase.firestore.collection("Merchants")
        .document("Merchant 1").collection("Requests")

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RequestViewHolder {
        val binding = RequestsListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RequestViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RequestViewHolder, position: Int, model: Request) {
        with(holder) {
            with (model) {
                with (binding) {
                    tvRequest.text = securityFeatures[0].toString()
                    //tvRequester.text =
                    btAccept.setOnClickListener {
                        database.document(model.documentId).update("requestState", RequestState.ACCEPTED)
                    }
                    btReject.setOnClickListener {
                        database.document(model.documentId).update("requestState", RequestState.CANCELLED)
                    }
                /*
                    btIgnore.setOnClickListener {
                        database.document(model.documentId).update("requestState", RequestState.IGNORED)
                    }*/
                }
            }
        }
    }

    inner class RequestViewHolder(val binding: RequestsListItemBinding) : RecyclerView.ViewHolder(binding.root)
}
