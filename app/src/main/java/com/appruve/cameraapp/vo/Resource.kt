package com.appruve.cameraapp.vo

import com.appruve.cameraapp.api.models.data.Message

class Resource<T>(var status: Status, var data: T?, var message: Message?) {

    companion object {
        fun <T> success(data: T?): Resource<T> = Resource(Status.SUCCESS, data, null)

        fun <T> error(message: Message, data: T?): Resource<T> = Resource(Status.FAILED, data, message)

        fun <T> loading(data: T?): Resource<T> = Resource(Status.LOADING, data, null)
    }

    override fun toString(): String = "Status: $status Data: $data Message: $message"
}

