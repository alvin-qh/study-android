package alvin.kotlin

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import butterknife.BindString
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick

class MainActivity : AppCompatActivity() {

    @BindView(R.id.edit_input)
    lateinit var editInput: EditText

    @BindView(R.id.text_result)
    lateinit var textResult: TextView

    @BindString(R.string.string_hello)
    lateinit var stringHello: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ButterKnife.bind(this)
    }

    @SuppressLint("SetTextI18n")
    @OnClick(R.id.button_ok)
    fun onButtonOkClick(button: Button) {
        val name = editInput.text.toString()
        textResult.text = stringHello + " " + name
    }
}
