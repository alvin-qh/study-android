package alvin.base.kotlin.common.views

import alvin.base.kotlin.R
import alvin.base.kotlin.common.domain.modules.Gender
import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import butterknife.ButterKnife
import butterknife.OnClick
import com.google.common.base.Strings
import kotlinx.android.synthetic.main.dialog_person.*
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class PersonDialog(context: Context?, themeResId: Int) :
        Dialog(context, themeResId) {

    var name: String?
        get() = et_name.text.toString()
        set(value) = et_name.setText(value)

    var gender: Gender?
        get() = when (rg_gender.checkedRadioButtonId) {
            R.id.rb_gender_male -> Gender.MALE
            R.id.rb_gender_female -> Gender.FEMALE
            else -> null
        }
        set(value) = when (value) {
            Gender.FEMALE -> rb_gender_female.isChecked = true
            else -> rb_gender_male.isChecked = true
        }

    var birthday: LocalDate?
        get() {
            val birthday = et_birthday.text.toString()
            return LocalDate.from(DateTimeFormatter.ISO_DATE.parse(birthday))
        }
        set(value) = et_birthday.setText(value?.format(DateTimeFormatter.ISO_DATE))


    var onConfirmClickListener: View.OnClickListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val view = View.inflate(context, R.layout.dialog_person, null)
        setContentView(view)

        ButterKnife.bind(this, view)

        et_birthday.keyListener = null
    }


    @OnClick(R.id.btn_calendar)
    fun onCalendarButtonClick(b: ImageButton) {
        var date = et_birthday.text.toString()

        val dialog = if (Strings.isNullOrEmpty(date)) {
            DatePickerDialog(context)
        } else {
            val birthday = LocalDate.from(DateTimeFormatter.ISO_DATE.parse(date))
            DatePickerDialog(context, null,
                    birthday.year, birthday.monthValue - 1, birthday.dayOfMonth)
        }

        dialog.setOnDateSetListener { _, year, month, dayOfMonth ->
            date = LocalDate.of(year, month + 1, dayOfMonth).format(DateTimeFormatter.ISO_DATE)
            et_birthday.setText(date)
        }
        dialog.show()
    }

    @OnClick(R.id.btn_confirm)
    fun onConfirmClick(b: Button) {
        onConfirmClickListener?.onClick(b)
    }

    @OnClick(R.id.btn_cancel)
    fun onCancelClick(b: Button) {
        dismiss()
    }

    class Builder(val context: Context) {

        fun create(titleResId: Int): PersonDialog {
            val dlg = PersonDialog(context, R.style.AppTheme_Dialog)
            dlg.setTitle(titleResId)
            dlg.create()

            return dlg
        }
    }
}