package com.appruve.cameraapp.viewmodel

import androidx.lifecycle.ViewModel
import com.appruve.cameraapp.repository.ServiceRepository
import okhttp3.MultipartBody
import javax.inject.Inject

class ServiceViewModel @Inject constructor(var serviceRepository: ServiceRepository) : ViewModel() {

    fun uploadServiceImage(userId: String, photo: MultipartBody.Part) = serviceRepository.uploadServiceImage(userId, photo)
}