package com.appruve.cameraapp.api.models

import com.appruve.cameraapp.api.models.data.Message
import com.google.gson.annotations.SerializedName

data class Response<T>(
    @SerializedName("response_code") var responseCode: String = "",
    var module: String = "",
    @SerializedName("status_code") var statusCode: String = "",
    @SerializedName("dev_message") var developerMessage: Boolean = false,
    var data: T? = null,
    var message: Message = Message()
)
