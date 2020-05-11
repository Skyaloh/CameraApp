package com.appruve.cameraapp.ui.upload

import android.content.Context
import android.content.Intent
import android.media.MediaScannerConnection
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.MimeTypeMap
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import androidx.viewpager.widget.ViewPager
import com.appruve.cameraapp.R
import com.appruve.cameraapp.helper.handleNetworkError
import com.appruve.cameraapp.ui.utils.padWithDisplayCutout
import com.appruve.cameraapp.utils.FileUploadUtil
import com.appruve.cameraapp.viewmodel.ServiceViewModel
import com.appruve.cameraapp.vo.Status
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.upload_file_fragment.*
import java.io.File
import java.util.*
import javax.inject.Inject

val EXTENSION_WHITELIST = arrayOf("JPG")

class UploadImageFragment internal constructor() : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    protected lateinit var viewModel: ServiceViewModel

    private val args: UploadImageFragmentArgs by navArgs()

    private lateinit var mediaList: MutableList<File>

    private var loading: Boolean = false
        set(value) {
            progressBar.visibility = when(loading){
                true -> View.VISIBLE
                false -> View.GONE
            }
            field = value
        }

    /** Adapter class used to present a fragment containing one photo or video as a page */
    inner class MediaPagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
        override fun getCount(): Int = mediaList.size
        override fun getItem(position: Int): Fragment = PhotoFragment.create(mediaList[position])
        override fun getItemPosition(obj: Any): Int = POSITION_NONE
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this,viewModelFactory).get(ServiceViewModel::class.java)

        retainInstance = true

        val rootDirectory = File(args.imagePath)

        mediaList = rootDirectory.listFiles { file ->
            EXTENSION_WHITELIST.contains(file.extension.toUpperCase(Locale.ROOT))
        }?.sortedDescending()?.toMutableList() ?: mutableListOf()

        val savedUri =  Uri.fromFile(rootDirectory)
        when(savedUri != null){
            true -> uploadImage(savedUri)
            false -> Toast.makeText(context, R.string.error_pick_image, Toast.LENGTH_LONG).show()
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.upload_file_fragment, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {

            view.findViewById<ConstraintLayout>(R.id.cutout_safe_area).padWithDisplayCutout()
        }

        // Handle back button press
        view.findViewById<ImageButton>(R.id.back_button).setOnClickListener {
            Navigation.findNavController(requireActivity(), R.id.fragment_container).navigateUp()
        }

    }

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    private fun uploadImage(uri: Uri) {

        viewModel.uploadServiceImage(generateRandomUsername(),
            FileUploadUtil.prepareFilePart("document", File(uri.path), requireContext()))
            .observe(this, androidx.lifecycle.Observer {
                loading = false
                when(it.status){
                    Status.LOADING -> loading = true
                    Status.SUCCESS -> Toast.makeText(requireContext(),"Uploaded Successfully!",Toast.LENGTH_LONG).show()
                    Status.FAILED -> handleNetworkError(it)
                }
            })
    }

    private fun generateRandomUsername(): String {
        val chars = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"
        var passWord = ""
        for (i in 0..31) {
            passWord += chars[Math.floor(Math.random() * chars.length).toInt()]
        }
        return passWord
    }
}
