@file:Suppress("UNUSED_PARAMETER")

package alvin.base.kotlin.butterknife.views

import alvin.base.kotlin.R
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import butterknife.ButterKnife
import butterknife.OnClick
import kotlinx.android.synthetic.main.activity_butterknife.*

class ButterKnifeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_butterknife)

        ButterKnife.bind(this)
    }

    @OnClick(R.id.button_ok)
    fun onButtonOkClick(b: Button) {
        text_result.text = getString(R.string.string_hello, edit_input.text)
    }
}
