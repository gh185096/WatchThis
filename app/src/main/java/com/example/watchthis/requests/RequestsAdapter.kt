package com.example.watchthis.requests

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.watchthis.databinding.RequestsListItemBinding
import com.example.watchthis.objects.Request

class RequestsAdapter : RecyclerView.Adapter<RequestsAdapter.RequestViewHolder>() {

    private var requests = mutableListOf<Request>()

    fun updateRequests(requestsIn: List<Request>) {
        requests = requestsIn.toMutableList()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RequestViewHolder {
        val binding = RequestsListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RequestViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return requests.size
    }

    override fun onBindViewHolder(holder: RequestViewHolder, position: Int) {
        if (requests.isNotEmpty() && requests.size > position) {
            val request = requests[position]
            with(holder) {
                with (request) {
                    with (binding) {
                        tvRequest.text = requestMessage
                        tvRequester.text = requester
                    }
                }
            }
        }
    }

    inner class RequestViewHolder(val binding: RequestsListItemBinding) : RecyclerView.ViewHolder(binding.root)
}