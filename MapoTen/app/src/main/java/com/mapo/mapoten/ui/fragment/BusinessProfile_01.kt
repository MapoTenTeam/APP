package com.mapo.mapoten.ui.fragment

import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.navigation.Navigation
import com.mapo.mapoten.R
import com.mapo.mapoten.databinding.FragmentBusinessProfile01Binding
import com.mapo.mapoten.ui.activity.MainActivity
import java.io.InputStream
import android.content.Context as Context
import java.io.File as File


class BusinessProfile_01 : Fragment() {


    lateinit var binding: FragmentBusinessProfile01Binding
    val file = File("경로")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBusinessProfile01Binding.inflate(inflater, container, false)

        val view = binding.root

        binding.backBtn.setOnClickListener {
            Navigation.findNavController(view).navigateUp()
        }

        initiateLogoUpload()
        initiateFileUpload()

        return view
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when(requestCode){
            2000 -> {
                val selectedImageUri: Uri? = data?.data
                if(selectedImageUri != null){
                    binding.businessLogo.setImageURI(selectedImageUri)
                    binding.iconImageUpload.visibility = GONE
                }else{
                    Toast.makeText(requireContext(), "사진을 가져오지 못했습니다", Toast.LENGTH_SHORT).show()
                }

            }

            3000 -> {
                val selectedFile : Uri? = data?.data
                if(selectedFile != null){
                    val file =selectedFile!!


                    binding.fileView.text = file.encodedPath
                    binding.iconFileUpload.visibility = GONE
                    binding.fileView.visibility= VISIBLE
                }
            }

        }
    }

    fun initiateLogoUpload(){
        binding.iconImageUpload.setOnClickListener {
            when {
                ContextCompat.checkSelfPermission(
                    requireContext(),
                    android.Manifest.permission.READ_EXTERNAL_STORAGE
                ) == PackageManager.PERMISSION_GRANTED -> {
                    Log.d("logo", "1 번 진입")
                    //갤러리에서 사진 선택
                    navigatePhotos()

                }
                shouldShowRequestPermissionRationale(android.Manifest.permission.READ_EXTERNAL_STORAGE)->{
                    //동의하라는 팝업
                    showPermissionContextPopup()
                    Log.d("logo", "2 번 진입")

                }
                else -> {
                    requestPermissions(arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),1000)
                    Log.d("logo", "3 번 진입")
                }
            }
        }
    }

    private fun showPermissionContextPopup(){
        AlertDialog.Builder(requireContext())
            .setTitle("권한이 필요합니다")
            .setMessage("사진을 불러오기 위해 권한 필요")
            .setPositiveButton("동의하기",{dialog, which ->
                requestPermissions(arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),1000)
            })
            .setNegativeButton("취소하기",{_, _ ->})
            .create()
            .show()
    }

    private fun navigatePhotos(){
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        startActivityForResult(intent, 2000)


    }

   private fun initiateFileUpload() {
       binding.iconFileUpload.setOnClickListener {
           when {
               ContextCompat.checkSelfPermission(
                   requireContext(),
                   android.Manifest.permission.READ_EXTERNAL_STORAGE
               ) == PackageManager.PERMISSION_GRANTED -> {
                   Log.d("file", "파일 1 번 진입")
                   //파일 선택
                   navigateFiles()

               }
               shouldShowRequestPermissionRationale(android.Manifest.permission.READ_EXTERNAL_STORAGE)->{
                   //동의하라는 팝업
                   showPermissionContextPopup()
                   Log.d("logo", "2 번 진입")

               }
               else -> {
                   requestPermissions(arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),1000)
                   Log.d("logo", "3 번 진입")
               }
           }
       }
   }

    private fun navigateFiles(){
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "*/*"
        startActivityForResult(intent, 3000)


    }





}





