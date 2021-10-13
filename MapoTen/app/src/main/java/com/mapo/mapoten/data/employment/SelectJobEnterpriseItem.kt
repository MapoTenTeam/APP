package com.mapo.mapoten.data.employment

import com.google.gson.annotations.SerializedName

data class SelectJobEnterpriseItem(

    @SerializedName("JOBID")
    val jobId: Int,

    @SerializedName("TITLE")
    val title: String,

    @SerializedName("CREATE_AT")
    val createAt: String,

    @SerializedName("REQUEST_DATE")
    val requestDate: String,

    @SerializedName("COMENTS")
    val comments: String,

    @SerializedName("JOB_STAT_NAME")
    val jobStat: String,

    )
