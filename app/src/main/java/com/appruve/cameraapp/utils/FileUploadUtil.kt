package com.appruve.cameraapp.utils

import android.content.Context
import android.net.Uri
import android.provider.MediaStore
import com.google.gson.Gson
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import timber.log.Timber
import java.io.File


object FileUploadUtil {

    fun prepareFilePart(partName: String, fileUri: Uri, context: Context): MultipartBody.Part {
        Timber.e("File URI: $fileUri")

        // use the FileUtils to get the actual file by uri
        val file = FileUtils(context).getFile(fileUri)

        // create RequestBody instance from file
        val requestFile = RequestBody.create(
            MediaType.parse(context.contentResolver.getType(fileUri)), file)

        // MultipartBody.Part is used to send also the actual file name
        return MultipartBody.Part.createFormData(partName, file?.name, requestFile)
    }

    fun prepareFilePart(partName: String, file: File, context: Context): MultipartBody.Part {
        Timber.e("File URI: $file")
        Timber.e("Uri from file ${Uri.fromFile(file)}")
        Timber.e("Uri from path ${Uri.parse(file.absolutePath)}")
        Timber.e("File Type: ${context.contentResolver.getType(Uri.parse(file.absolutePath))}")
        Timber.e("External Uri ${MediaStore.Files.getContentUri("external")}")

        // create RequestBody instance from file
        val requestFile = RequestBody.create(
            MediaType.parse(droidninja.filepicker.utils.FileUtils.getFileType(file.absolutePath).toString()), file)

        // MultipartBody.Part is used to send also the actual file name
        return MultipartBody.Part.createFormData(partName, file.name, requestFile)
    }

    fun createPartFromString(descriptionString: String): RequestBody {
        return RequestBody.create(
            okhttp3.MultipartBody.FORM, descriptionString)
    }


}