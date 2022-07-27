package com.example.wearosmodule.objects

data class Request (var documentId: String,
                    var requestMessage: String,
                    var requestState: RequestState,
                    var securityFeatures: List<SecurityFeature>) {

    constructor() : this("", "", RequestState.CANCELLED, mutableListOf(SecurityFeature.VoidTicket))

}