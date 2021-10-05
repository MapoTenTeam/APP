package com.mapo.mapoten.ui.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.bumptech.glide.Glide
import com.mapo.mapoten.R
import com.mapo.mapoten.config.RetrofitBuilder
import com.mapo.mapoten.data.employment.CodeName
import com.mapo.mapoten.data.employment.EmploymentResponse
import com.mapo.mapoten.data.employment.GeneralEmpPostingDetailDTO
import com.mapo.mapoten.databinding.FragmentEmploymentDetail01Binding
import com.mapo.mapoten.service.EmploymentService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*
import kotlin.concurrent.thread
import kotlin.properties.Delegates

class Employment_Detail_01 : Fragment() {
    lateinit var binding: FragmentEmploymentDetail01Binding
    lateinit var employmentService: EmploymentService
    var type by Delegates.notNull<Int>()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEmploymentDetail01Binding.inflate(inflater, container, false)
        val view = binding.root

        type = arguments?.getInt("type")!!
        val id = arguments?.getInt("jobId")

        Log.d("generalDetail", "type : " + type)
        Log.d("generalDetail", "id : " + id)


        binding.backButton.setOnClickListener {
            Navigation.findNavController(view).navigateUp()
        }

        if (id != null) {
            loading(true)
            getGeneralJobPostingDetail(id)
        }

        return view
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

    private fun setData(result: GeneralEmpPostingDetailDTO) {
        binding.category.text = if (type === 1) "일반채용" else "공공채용"
        binding.title.text = result.title
        binding.date.text = getDDay(result.endReception)
        if (result.image != null) {
            Glide.with(requireActivity()).load(result.image).into(binding.image)
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
//        binding.companyNameValue.text = result.name
//        binding.ceoValue.text = result.ceo
//        binding.addressValue.text = result.address
//        binding.sectorValue.text = result.sector
        //binding.quaternionValue.text = result.quaternion

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
        binding.endReceptionValue.text = result.endReception.substring(0,10)

        // 채용 담당자 정보
        binding.contactNameValue.text = result.contactName
        binding.contactDepartmentValue.text = result.contactDepartment
        binding.contactPhoneValue.text = result.contactPhone
        binding.contactEmailValue.text = result.contactEmail

        // 근무위치
        binding.placeOfWorkValue.text = result.address

    }

    private fun manufactureData(data: ArrayList<CodeName>): String {
        var tmpText = ""
        data.forEach { it ->
            tmpText += "${it.codeName}, "
        }

        return tmpText.substring(0, tmpText.length - 2)
    }

    private fun getDDay(endDay: String): String {
        val dateFormat = SimpleDateFormat("yyyyMMdd")
        val today = Calendar.getInstance()
        val endDate = dateFormat.parse(endDay)

        Log.d("yyyyMMdd", "today : ${today}")
        Log.d("yyyyMMdd", "today : ${today.time}")
        Log.d("yyyyMMdd", "endDate : ${endDate}")

        return "${
            endDay.substring(
                0,
                10
            )
        } 접수마감    D-${(today.time.time - endDate.time) / (24 * 60 * 60 * 1000)}"

        // 86400000
    }

}