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
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.navigation.Navigation
import com.mapo.mapoten.config.RetrofitBuilder
import com.mapo.mapoten.data.UpdateBusinessProfileItems
import com.mapo.mapoten.databinding.FragmentBusinessProfile01Binding
import com.mapo.mapoten.service.AccountManageService
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File as File


class BusinessProfile_01 : Fragment() {


    lateinit var binding: FragmentBusinessProfile01Binding
    lateinit var service : AccountManageService
    private var selectedImageUri : Uri? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBusinessProfile01Binding.inflate(inflater, container, false)

        val view = binding.root

        service = RetrofitBuilder.getInstance().create(AccountManageService::class.java)

        binding.backButton.setOnClickListener {
            Navigation.findNavController(view).navigateUp()
        }

        initiateLogoUpload()
        initiateFileUpload()

        binding.businessNameText.setText(arguments?.getString("compNm"))
        binding.businessNumberText.setText(arguments?.getString("compNum"))




        binding.businessSaveButton.setOnClickListener {
            Log.d("profile", "이미지없이 저장하기 눌럼")
            addCompProfile()
            if (selectedImageUri != null) {
                addCompImg(selectedImageUri.toString())
            }
        }
        return view
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when(requestCode){
            2000 -> {
                selectedImageUri = data?.data!!
                if(selectedImageUri != null){
                    //selectedImageUri = data?.data!!
                    binding.businessLogo.setImageURI(selectedImageUri)
                    binding.iconImageUpload.visibility = GONE
                    binding.businessSaveButton.setOnClickListener {
                        Toast.makeText(requireContext(), "클릭리스너 눌림", Toast.LENGTH_SHORT).show()
                        addCompProfile()
                        addCompImg(selectedImageUri.toString())
                        Log.d("profile", "이미지 uri : $selectedImageUri")
                    }
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
        val homepage= binding.businessWebsiteText.text.toString()

        val profile = UpdateBusinessProfileItems(compName,email,category,compName,compNum,ceoName,address,detailAd,category,empNum,homepage,email)
        service.updateBusinessProfile(profile).enqueue(object :Callback<Void>{
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful){
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

    private fun addCompImg(path:String) {
        Toast.makeText(requireContext(), "클릭리스너 이후 addCompimg 까지옴", Toast.LENGTH_SHORT).show()
        Log.d("profile", "클릭리스너 이후 addCompimg 까지옴")
        //creating a file
        val imgFile = File(path)
        //var fileName = imgFile.name.replace("%","").replace(".","")
       /* var fileName = "test123"
        fileName = "$fileName.png"*/
        if (!imgFile.exists()) {       // 원하는 경로에 폴더가 있는지 확인

            imgFile.mkdirs();    // 하위폴더를 포함한 폴더를 전부 생성
            Log.d("profile", "이름 ${imgFile.name}")
        }


        val requestFile = RequestBody.create("image/*".toMediaTypeOrNull(), imgFile)

       // var requestBody : RequestBody = RequestBody.create("image/*".toMediaTypeOrNull(),file)
        var file : MultipartBody.Part = MultipartBody.Part.createFormData("file",imgFile.name,requestFile)
        Log.d("profile", "이미지등록 $file")
        Log.d("profile", "이미지등록  bodu ${file.body}")


        service.updateBusinessLogoImg(file).enqueue(object : Callback<Void>{
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                Log.d("profile", "이미지등록 ${response.code()}")
                Log.d("profile", "이미지 등록 성공!!")
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Log.d("profile", "이미지 등록 실패!!")
                Log.d("profile 수정", "error" + t.message)
            }
        })
    }



}





