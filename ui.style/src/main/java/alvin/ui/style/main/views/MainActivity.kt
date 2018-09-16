package alvin.ui.style.main.views

import alvin.ui.style.R
import alvin.ui.style.shape.views.ShapeActivity
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.sdk25.coroutines.onClick

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        setupActions()
    }

    private fun setupActions() {
        arrayOf(btn_shape,
                btn_selector,
                btn_layer_list,
                btn_animation,
                btn_style).forEach {
            it.onClick { btn ->
                startActivity(when (btn) {
                    btn_shape -> intentFor<ShapeActivity>()
                    else -> throw IllegalArgumentException()
                })
            }
        }
    }
}
