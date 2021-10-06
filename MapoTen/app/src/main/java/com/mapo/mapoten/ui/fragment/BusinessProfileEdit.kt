package com.mapo.mapoten.ui.fragment

import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.bumptech.glide.Glide
import com.mapo.mapoten.config.RetrofitBuilder
import com.mapo.mapoten.data.ImageResponse
import com.mapo.mapoten.data.UpdateBusinessProfileItems
import com.mapo.mapoten.databinding.FragmentBusinessProfile01Binding
import com.mapo.mapoten.databinding.FragmentBusinessProfileEditBinding
import com.mapo.mapoten.service.AccountManageService
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.MultipartBody.Part.Companion.createFormData
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File


class BusinessProfileEdit : Fragment() {


    lateinit var binding: FragmentBusinessProfileEditBinding
    lateinit var service: AccountManageService
    private var selectedImageUri: Uri? = null
    private var filePath: String = ""
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBusinessProfileEditBinding.inflate(inflater, container, false)

        val view = binding.root

        service = RetrofitBuilder.getInstance().create(AccountManageService::class.java)

        binding.backButton.setOnClickListener {
            Navigation.findNavController(view).navigateUp()
        }

        initiateLogoUpload()
        initiateFileUpload()

        binding.businessNameText.setText(arguments?.getString("cmpny_nm"))
        binding.businessNumberText.setText(arguments?.getString("bizrno"))
        binding.ownerNameText.setText(arguments?.getString("ceo"))
        binding.businessEmailText.setText(arguments?.getString("cmpny_email"))
        binding.businessAddressText.setText(arguments?.getString("address"))
        binding.businessCategoryText.setText(arguments?.getString("category"))
        binding.businessEmployeeNumberText.setText(arguments?.getString("empNum"))
        binding.businessWebsiteText.setText(arguments?.getString("webSite"))

       // Glide.with(binding.imgBusinessLogoValue).load(img).into(binding.imgBusinessLogoValue)




        binding.businessSaveButton.setOnClickListener {
            Log.d("profile", "이미지없이 저장하기 눌럼")
            addCompProfile()
            if (selectedImageUri != null) {
                addCompImg()
            }
        }
        return view
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (requestCode) {
            2000 -> {
                selectedImageUri = data?.data!!
                if (selectedImageUri != null) {
                    binding.businessLogo.setImageURI(selectedImageUri)
                    binding.iconImageUpload.visibility = GONE
                    filePath = getImageFilePath(selectedImageUri!!)
                    Log.d("profile", "절대주소: $filePath")
                    binding.businessSaveButton.setOnClickListener {
                        Toast.makeText(requireContext(), "클릭리스너 눌림", Toast.LENGTH_SHORT).show()
                        addCompProfile()
                        addCompImg()
                        Log.d("profile", "이미지 uri : $selectedImageUri")
                    }
                } else {
                    Toast.makeText(requireContext(), "사진을 가져오지 못했습니다", Toast.LENGTH_SHORT).show()
                }

            }

            3000 -> {
                val selectedFile: Uri? = data?.data
                if (selectedFile != null) {
                    val file = selectedFile!!
                    binding.fileView.text = file.encodedPath
                    binding.iconFileUpload.visibility = GONE
                    binding.fileView.visibility = VISIBLE
                }
            }

        }
    }


    fun initiateLogoUpload() {
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
                shouldShowRequestPermissionRationale(android.Manifest.permission.READ_EXTERNAL_STORAGE) -> {
                    //동의하라는 팝업
                    showPermissionContextPopup()
                    Log.d("logo", "2 번 진입")

                }
                else -> {
                    requestPermissions(
                        arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),
                        1000
                    )
                    Log.d("logo", "3 번 진입")
                }
            }
        }
    }

    private fun showPermissionContextPopup() {
        AlertDialog.Builder(requireContext())
            .setTitle("권한이 필요합니다")
            .setMessage("사진을 불러오기 위해 권한 필요")
            .setPositiveButton("동의하기", { dialog, which ->
                requestPermissions(arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE), 1000)
            })
            .setNegativeButton("취소하기", { _, _ -> })
            .create()
            .show()
    }

    private fun navigatePhotos() {
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
                shouldShowRequestPermissionRationale(android.Manifest.permission.READ_EXTERNAL_STORAGE) -> {
                    //동의하라는 팝업
                    showPermissionContextPopup()
                    Log.d("logo", "2 번 진입")

                }
                else -> {
                    requestPermissions(
                        arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),
                        1000
                    )
                    Log.d("logo", "3 번 진입")
                }
            }
        }
    }

    private fun navigateFiles() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "*/*"
        startActivityForResult(intent, 3000)

    }

    private fun addCompProfile() {
        Log.d("profile", "이미지없이 저장하기 눌럼2222222")
        val compName = binding.businessNameText.text.toString()
        val compNum = binding.businessNumberText.text.toString()
        val ceoName = binding.ownerNameText.text.toString()
        val email = binding.businessEmailText.text.toString()
        val address = binding.businessAddressText.text.toString()
        val detailAd = binding.businessAddressDetailText.text.toString()
        val category = binding.businessCategoryText.text.toString()
        val empNum = binding.businessEmployeeNumberText.text.toString()
        val homepage = binding.businessWebsiteText.text.toString()

        //모든 항목에 값이 있어야 통신되니까, 값이 없는 항목 처리해줘야함.
        val profile = UpdateBusinessProfileItems(
            compName,
            email,
            category,
            compName,
            compNum,
            ceoName,
            address,
            detailAd,
            category,
            empNum,
            homepage,
            email
        )
        service.updateBusinessProfile(profile).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    val msg = response.message()
                    Log.d("profile 수정", "msg : $msg")
                    Toast.makeText(requireContext(), "수정 완료 되었습니다.", Toast.LENGTH_SHORT).show()

                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Log.d("profile 수정", "error" + t.message)
            }
        })


    }

    fun getImageFilePath(contentUri: Uri): String {
        Log.d("profile", "들어오는 uri : $contentUri")
        var columnIndex = 0
        val column = "_data";
        val projection = arrayOf(column)
        // val projection = arrayOf(MediaStore.Images.Media.DATA)
        var cursor = requireContext().contentResolver.query(contentUri, null, null, null, null)
        if (cursor != null) {
            cursor.moveToFirst()
            var document_id: String = cursor.getString(0);
            if (document_id == null) {
                for (i in 1..cursor.getColumnCount()) {
                    if (column.equals(cursor.getColumnName(i))) {
                        filePath = cursor.getString(i);
                        break;
                    }
                }
            } else {
                document_id = document_id.substring(document_id.lastIndexOf(":") + 1);
                cursor.close()



                try {
                    cursor = requireContext().getContentResolver().query(
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        projection,
                        MediaStore.Images.Media._ID + " = ? ",
                        arrayOf(document_id),
                        null
                    );
                    if (cursor != null) {
                        cursor.moveToFirst();
                        filePath = cursor.getString(cursor.getColumnIndexOrThrow(column));
                    }
                } finally {
                    if (cursor != null) cursor.close();
                }
            }
        }

            /*if (cursor!!.moveToFirst()) {
                columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
            } cursor.getString(columnIndex)*/

            return filePath
        }


        private fun addCompImg() {
            Toast.makeText(requireContext(), "클릭리스너 이후 addCompimg 까지옴", Toast.LENGTH_SHORT).show()
            Log.d("profile", "클릭리스너 이후 addCompimg 까지옴")
            //creating a file
            if(filePath ==""){
                Log.d("profile", "file path가 없음-----")
            }
            Thread {
                val uploadFile = File(filePath)

                val requestBody: RequestBody = RequestBody.create(selectedImageUri?.let {
                    requireContext().contentResolver.getType(it)?.toMediaTypeOrNull()
                }, uploadFile)
                val fileToUpload: MultipartBody.Part =
                    createFormData("file", uploadFile.name, requestBody)


                Log.d("profile", "이미지이름 ${uploadFile.name}")
                Log.d("profile", "이미지등록  body $fileToUpload")

                service.updateBusinessLogoImg(fileToUpload).enqueue(object : Callback<ImageResponse> {
                    override fun onResponse(call: Call<ImageResponse>,response: Response<ImageResponse>) {
                        if (response.isSuccessful){
                            Log.d("profile", "이미지 등록 성공!!")
                        }
                        Log.d("profile", "이미지 등록 ${response.code()}")
                        Log.d("profile", "이미지 등록 ${response.message()}")
                        Log.d("profile", "이미지 오류 ${response.errorBody()?.string()}")

                    }

                    override fun onFailure(call: Call<ImageResponse>, t: Throwable) {
                        Log.d("profile", "이미지 등록 실패!!")
                        Log.d("profile 수정", "error" + t.message)
                    }


                })
            }.start()

        }



}





