package com.appruve.cameraapp.repository

import androidx.lifecycle.LiveData
import com.appruve.cameraapp.AppExecutors
import com.appruve.cameraapp.api.ApiResponse
import com.appruve.cameraapp.api.Webservice
import com.appruve.cameraapp.api.models.Response
import com.appruve.cameraapp.vo.NetworkOnlyResource
import com.appruve.cameraapp.vo.Resource
import okhttp3.MultipartBody
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ServiceRepository @Inject constructor( val webservice: Webservice, val appExecutors: AppExecutors)  {

    fun uploadServiceImage(userId: String, photo: MultipartBody.Part): LiveData<Resource<Unit>> {
        return object : NetworkOnlyResource<Unit, Response<Unit>>(appExecutors) {
            override fun processResult(item: Response<Unit>?): Unit? = item?.data

            override fun createCall(): LiveData<ApiResponse<Response<Unit>>>
                    = webservice.uploadServiceImage(
                MultipartBody.Part.createFormData("user_id", userId), photo)
        }.asLiveData()
    }

}