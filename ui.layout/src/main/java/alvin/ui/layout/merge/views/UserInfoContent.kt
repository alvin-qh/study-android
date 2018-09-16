package alvin.ui.layout.merge.views

import alvin.ui.layout.R
import alvin.ui.layout.merge.domain.models.Gender
import alvin.ui.layout.merge.domain.models.UserInfo
import android.app.Activity
import android.view.View
import kotlinx.android.synthetic.main.content_user_info.*
import org.jetbrains.anko.inputMethodManager
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.sdk25.coroutines.onFocusChange

class UserInfoContent(private val parent: Activity) {

    var userInfo: UserInfo
        set(value) = fillUserInfo(value)
        get() = makeUserInfo()

    var onClickListener: View.OnClickListener? = null

    fun initialize(onClickListener: View.OnClickListener?) {
        this.onClickListener = onClickListener
        setupActions()
    }

    private fun setupActions() {
        parent.btn_confirm.onClick {
            onClickListener?.onClick(it)
        }
        parent.edit_name.onFocusChange { v, hasFocus ->
            if (!hasFocus) {
                parent.inputMethodManager.hideSoftInputFromWindow(v.windowToken, 0)
            }
        }
    }

    private fun fillUserInfo(userInfo: UserInfo) {
        parent.edit_name.setText(userInfo.name)
        parent.radio_gender.check(if (userInfo.gender == Gender.MALE) {
            R.id.radio_gender_male
        } else {
            R.id.radio_gender_female
        })
    }

    private fun makeUserInfo(): UserInfo {
        val name = parent.edit_name.text.toString()
        val gender = if (parent.radio_gender.checkedRadioButtonId == R.id.radio_gender_male) {
            Gender.MALE
        } else {
            Gender.FEMALE
        }
        return UserInfo(name, gender)
    }
}
