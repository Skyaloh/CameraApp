package com.appruve.cameraapp.helper

import android.view.View
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import com.appruve.cameraapp.R
import com.appruve.cameraapp.vo.Resource
import com.google.android.material.snackbar.Snackbar


fun <T> Fragment.handleNetworkError(response: Resource<T>, @StringRes actionResId: Int? = null, action: () -> Unit = {}){
    this.handleNetworkError(response, view!!, actionResId, action)
}

fun<T> Fragment.handleNetworkError(response: Resource<T>, view: View, @StringRes actionResId: Int? = null, action: () -> Unit = {}){
    activity?.hideKeyboard()

    val snackBar = Snackbar
            .make(view, response.message?.tile ?: getText(R.string.error_general_network), Snackbar.LENGTH_LONG)
    if(actionResId != null) {
        snackBar.setAction(R.string.action_resend) { action() }
    }
    snackBar.show()
}