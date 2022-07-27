package com.example.watchthis.requests

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.watchthis.databinding.RequestsListItemBinding
import com.example.watchthis.objects.Request
import com.example.watchthis.objects.RequestState
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class RequestsAdapter(options: FirestoreRecyclerOptions<Request>) : FirestoreRecyclerAdapter<Request, RequestsAdapter.RequestViewHolder>(options) {

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
                        btAccept.setOnClickListener {
                            Toast.makeText(holder.binding.root.context, model.documentId, Toast.LENGTH_SHORT).show()
                            database.document(model.documentId).update("requestState", RequestState.ACCEPTED)
                        }
                        btReject.setOnClickListener {
                            database.document(model.documentId).update("requestState", RequestState.CANCELLED)
                        }
                        /*btIgnore.setOnClickListener {
                            database.document(model.documentId).update("requestState", RequestState.IGNORED)
                        }*/
                    }
                }
            }
    }

    inner class RequestViewHolder(val binding: RequestsListItemBinding) : RecyclerView.ViewHolder(binding.root)
}