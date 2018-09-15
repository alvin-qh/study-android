package alvin.ui.ime.main.views

import alvin.ui.ime.R
import alvin.ui.ime.hide.views.HideImeActivity
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.sdk25.coroutines.onClick

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        setupButtons()
    }

    private fun setupButtons() {
        arrayOf(btn_hide_ime).forEach {
            it.onClick { btn ->
                startActivity(when (btn) {
                    btn_hide_ime -> intentFor<HideImeActivity>()
                    else -> throw IllegalArgumentException()
                })
            }
        }
    }
}
