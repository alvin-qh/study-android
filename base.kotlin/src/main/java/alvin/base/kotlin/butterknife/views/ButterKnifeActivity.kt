package alvin.base.kotlin.butterknife.views

import alvin.base.kotlin.R
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import butterknife.ButterKnife
import butterknife.OnClick
import kotlinx.android.synthetic.main.butterknife_activity.*

class ButterKnifeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.butterknife_activity)

        ButterKnife.bind(this)
    }

    @OnClick(R.id.button_ok)
    fun onButtonOkClick(b: Button) {
        text_result.text = getString(R.string.string_hello, edit_input.text)
    }
}