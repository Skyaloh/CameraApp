package com.appruve.cameraapp.api

import androidx.lifecycle.LiveData
import com.appruve.cameraapp.api.models.Response
import okhttp3.MultipartBody
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface Webservice{
    @Multipart
    @POST("v1/verifications/test/file_upload")
    fun uploadServiceImage(@Part user_id: MultipartBody.Part, @Part document: MultipartBody.Part): LiveData<ApiResponse<Response<Unit>>>
}