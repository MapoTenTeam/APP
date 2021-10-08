package com.mapo.mapoten.ui.fragment

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import androidx.navigation.Navigation
import com.bumptech.glide.Glide
import com.mapo.mapoten.R
import com.mapo.mapoten.config.RetrofitBuilder
import com.mapo.mapoten.data.employment.CodeName
import com.mapo.mapoten.data.employment.EmploymentResponse
import com.mapo.mapoten.data.employment.GeneralEmpPostingDetailDTO
import com.mapo.mapoten.databinding.FragmentEmploymentDetail01Binding
import com.mapo.mapoten.service.EmploymentService
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraUpdate
import com.naver.maps.map.MapView
import com.naver.maps.map.NaverMap
import com.naver.maps.map.OnMapReadyCallback
import com.naver.maps.map.overlay.Marker
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.concurrent.thread
import kotlin.properties.Delegates

class Employment_Detail_01 : Fragment(), OnMapReadyCallback {
    lateinit var binding: FragmentEmploymentDetail01Binding
    lateinit var employmentService: EmploymentService
    var type by Delegates.notNull<Int>()
    private lateinit var dialog: Dialog

    private lateinit var mapView : MapView
    private lateinit var geocoder: Geocoder
    private lateinit var geoLatLng : LatLng

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentEmploymentDetail01Binding.inflate(inflater, container, false)
        val view = binding.root


        geocoder = Geocoder(requireContext())
        type = arguments?.getInt("type")!!
        val id = arguments?.getInt("jobId")


        mapView = binding.mapView
        mapView.onCreate(savedInstanceState)

        binding.backButton.setOnClickListener {
            Navigation.findNavController(view).navigateUp()
        }

        if (id != null) {
            loading(true)
            getGeneralJobPostingDetail(id)
        }

        binding.refreshLayout.setOnRefreshListener {
            if (id != null) {
                getGeneralJobPostingDetail(id)
            } else {
                Toast.makeText(requireContext(), "다시 실행해주세요..!", Toast.LENGTH_SHORT).show()
            }
            binding.refreshLayout.isRefreshing = false
        }

        if (arguments?.getString("dDay") === "closed") {
            binding.submitBtn.isEnabled = false
            binding.submitBtn.setBackgroundColor(Color.parseColor("#C4C4C4"))
        }

        binding.submitBtn.setOnClickListener {
            dialog = Dialog(requireContext())
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setContentView(R.layout.popup_emp_posting_detail_submit)
            showDialog()
        }

        return view
    }


    private fun getLatLng(address: String?) {

        var list : ArrayList<Address>? = null

        val test = "서울특별시 은평구 응암동 응암로22길 9-4"
        try {
            list = geocoder.getFromLocationName(address, 10) as ArrayList<Address>?

        }catch (e : IOException) {
            e.printStackTrace()
            Log.d("map", "error")
        }

        if(list!=null) {
            if(list.size == 0) {
                Log.d("map", "list size : 0")

            }else {
                val lat = list[0].latitude
                val long = list[0].longitude

                Log.d("map", "list : ${list[0].latitude}")
                Log.d("map", "long : ${list[0].longitude}")


                geoLatLng = LatLng(lat, long)
                Log.d("map", "list : ${geoLatLng}")

                mapView.getMapAsync(this)

            }
        }
    }

    private fun loading(isLoading: Boolean) {
        if (isLoading) binding.loading.visibility = View.VISIBLE
        else {
            binding.loading.visibility = View.GONE
            binding.detail.visibility = View.VISIBLE
        }
    }

    private fun getGeneralJobPostingDetail(id: Int) {
        employmentService = RetrofitBuilder.getInstance().create(EmploymentService::class.java)
        val generalJobList = employmentService.inquireGeneralDetailPosting(id)

        generalJobList.enqueue(object : Callback<EmploymentResponse> {
            override fun onResponse(
                call: Call<EmploymentResponse>,
                response: Response<EmploymentResponse>
            ) {

                Log.d("generalDetail", "code : " + response.code())
                Log.d("generalDetail", "message : " + response.message())

                if (response.isSuccessful) {

                    Log.d("generalDetail", "resultDataList : ${response.body()?.data}")

                    thread(start = true) {
                        Thread.sleep(200)

                        requireActivity().runOnUiThread {
                            loading(false)
                            response.body()?.data?.let { setData(it) }
                            getLatLng(response.body()?.data?.address)
                        }
                    }

                } else {
                    Log.d("generalDetail", "code : " + response.code())
                }
            }

            override fun onFailure(call: Call<EmploymentResponse>, t: Throwable) {
                Log.e("generalDetail", "통신 실패" + t.localizedMessage)

            }

        })
    }

    private fun registerBookmark() {

    }

    private fun cancelBookmark() {

    }

    private fun setData(result: GeneralEmpPostingDetailDTO) {
        binding.category.text = if (type === 1) "일반채용" else "공공채용"
        binding.title.text = result.title
        binding.date.text = setDDay(result.endReception)
        if (result.jobImage != null) {
            Glide.with(requireActivity()).load(result.jobImage).into(binding.image)
        } else {
            binding.image.setImageResource(R.drawable.banner_image1)
        }
        // 채용사항
        binding.jobTypeDescValue.text = result.jobTypeDesc
        binding.requireCountValue.text = result.requireCount
        binding.jobDescValue.text = result.jobDesc
        binding.educationValue.text = result.education
        binding.careerValue.text = result.career
        binding.employTypeValue.text = manufactureData(result.employTypeDet)



        Log.d("detail dept", "dept : ${result.employTypeDet}")
        Log.d("detail dept", "size : ${result.employTypeDet.size}")


        // 업체현황
        Glide.with(requireActivity()).load(result.companyImage).into(binding.companyImage)
        binding.companyNameValue.text = result.name
        binding.ceoValue.text = result.ceo
        binding.addressValue.text = result.address
        binding.sectorValue.text = result.sector
        binding.quaternionValue.text = result.quaternion

        // 근로조건
        binding.paycdValue.text = result.paycd
        binding.payAmountValue.text = result.payAmount
        binding.workTimeTypeValue.text = result.workTimeType
        binding.mealCodValue.text = result.mealCod
        binding.workingHoursValue.text = result.workingHours
        binding.severancePayTypeValue.text = result.severancePayType
        binding.socialInsuranceValue.text = manufactureData(result.socialInsurance)

        // 전형사항
        binding.applyMethodValue.text = result.applyMethod
        binding.testMethodValue.text = result.testMethod
        binding.applyDocumentValue.text = manufactureData(result.applyDocument)
        binding.endReceptionValue.text = result.endReception.substring(0, 10)

        // 채용 담당자 정보
        binding.contactNameValue.text = result.contactName
        binding.contactDepartmentValue.text = result.contactDepartment
        binding.contactPhoneValue.text = result.contactPhone
        binding.contactEmailValue.text = result.contactEmail

        // 근무위치
        binding.placeOfWorkValue.text = result.workAddress

    }

    private fun manufactureData(data: ArrayList<CodeName>): String {
        var tmpText = ""
        data.forEach { it ->
            tmpText += "${it.codeName}, "
        }

        return tmpText.substring(0, tmpText.length - 2)
    }

    private fun setDDay(endDay: String): String {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd")
        val today = Calendar.getInstance()
        val endDate = dateFormat.parse(endDay.substring(0, 10))

        val day = (endDate.time - today.time.time) / (24 * 60 * 60 * 1000)

        return if (day.toString() == "0") {
            "D-day"
        } else if (day < 0) {
            "지원 모집이 마감된 공고입니다."
        } else {
            "${endDay.substring(0, 4)}년 ${
                endDay.substring(
                    5,
                    7
                )
            }월 ${endDay.substring(8, 10)}일 모집마감  D-${day}"

        }
    }

    private fun showDialog() {
        dialog.show()
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val deleteBtn: AppCompatButton = dialog.findViewById(R.id.deleteBtn)
        deleteBtn.setOnClickListener {
            dialog.dismiss()
        }


        val closeBtn: ImageView = dialog.findViewById(R.id.closeBtn)
        closeBtn.setOnClickListener {
            dialog.dismiss()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mapView = view.findViewById(R.id.mapView)
        mapView.onCreate(savedInstanceState)
    }

    override fun onStart() {
        super.onStart()
        mapView.onStart()
    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mapView.onSaveInstanceState(outState)
    }

    override fun onStop() {
        super.onStop()
        mapView.onStop()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mapView.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }

    override fun onMapReady(naverMap: NaverMap) {

        Log.d("map", "onMapReady()...$geoLatLng")

        val marker = Marker()
        marker.position = geoLatLng
        marker.map = naverMap

        val cameraUpdate = CameraUpdate.scrollTo(geoLatLng)
        naverMap.moveCamera(cameraUpdate)

    }

}