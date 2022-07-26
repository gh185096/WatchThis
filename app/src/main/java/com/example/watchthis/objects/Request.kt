package com.example.watchthis.objects

data class Request (var requestMessage: String,
                    var requestState: RequestState,
                    var requester: String)