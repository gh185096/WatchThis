package com.example.watchthis.requests

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.watchthis.objects.Request
import com.example.watchthis.objects.RequestState
import com.example.watchthis.objects.SecurityFeature
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
open class RequestsViewModel @Inject constructor(): ViewModel() {

    private val database = Firebase.firestore.collection("Merchants").document("Merchant 1")
    val requests = MutableLiveData(mutableListOf<Request>())

    init {
        //makeRequests()
    }

    fun addRequest(newRequest: Request) {
        requests.value?.add(newRequest)
    }

    private fun makeRequests() {
        val requestsList = mutableListOf<Request>()
        for (i in 0..20) {

            val newRequest = Request("Request $i","Request $i", RequestState.WAITING, mutableListOf(SecurityFeature.VoidTicket))
            database.collection("Requests")
                .document("Request $i")
                .set(newRequest)
            //requestsList.add(newRequest)
        }
        //requests.value = requestsList
    }


}