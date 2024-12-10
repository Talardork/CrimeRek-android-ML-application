package com.example.crimerek

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment

class HomeFragment : Fragment() {

    private val CAMERA_PERMISSION_CODE = 101
    private val CAMERA_REQUEST_CODE = 102
    private lateinit var imageView: ImageView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        // Initialize the ImageView and Button
        imageView = view.findViewById(R.id.imageView)  // Assuming you have an ImageView in your fragment layout
        val btnCamera: Button = view.findViewById(R.id.cameraButton)

        // Set onClickListener for the Camera Button
        btnCamera.setOnClickListener {
            checkPermission(Manifest.permission.CAMERA, CAMERA_PERMISSION_CODE)
        }

        return view
    }

    private fun checkPermission(permission: String, requestCode: Int) {
        if (ContextCompat.checkSelfPermission(requireContext(), permission) == PackageManager.PERMISSION_DENIED) {
            // Requesting the permission
            requestPermissions(arrayOf(permission), requestCode)
        } else {
            // Permission already granted, open the camera
            openCamera()
        }
    }

    private fun openCamera() {
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(cameraIntent, CAMERA_REQUEST_CODE)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<out String>, grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == CAMERA_PERMISSION_CODE) {
            if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                openCamera()
            } else {
                Toast.makeText(requireContext(), "Camera Permission Denied", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == CAMERA_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            val photo: Bitmap = data?.extras?.get("data") as Bitmap
            imageView.setImageBitmap(photo)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = HomeFragment()
    }
}
