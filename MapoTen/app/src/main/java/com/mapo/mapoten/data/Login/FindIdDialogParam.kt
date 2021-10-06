package com.mapo.mapoten.data.Login

import android.os.Parcelable
import android.view.View
import android.widget.TextView
import com.mapo.mapoten.R
import kotlinx.parcelize.Parcelize

abstract class FindIdDialogParam : Parcelable {

    open val code: String = ""
    open val info: String = ""
    open val userId: String = ""

    fun setup(view: View) {
        view.apply {

            findViewById<TextView>(R.id.tv_information).text = info
            findViewById<TextView>(R.id.tv_findId).text = userId
        }
    }


}

@Parcelize
class DialogInfo(
    override val code: String,
    override val info: String
) : FindIdDialogParam() {


//    override fun setup(view: View) {
//        super.setup(view: View)
//        // 필요 시 override
//    }
}

@Parcelize
class Warn(
    override val code: String,
    override val info: String
) : FindIdDialogParam()

@Parcelize
class Error(
    override val code: String,
    override val info: String
) : FindIdDialogParam()


