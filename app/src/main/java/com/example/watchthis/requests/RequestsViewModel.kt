package com.example.watchthis.requests

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.watchthis.objects.Request
import com.example.watchthis.objects.RequestState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
open class RequestsViewModel @Inject constructor(): ViewModel() {

    var requests = MutableLiveData<List<Request>>()

    init {
        makeRequests()
    }

    private fun makeRequests() {
        val requestsList = mutableListOf<Request>()
        for (i in 0..20) {
            requestsList.add(Request("Request #$i", RequestState.NEW, "Requestor #$i"))
        }
        requests.value = requestsList
    }
}