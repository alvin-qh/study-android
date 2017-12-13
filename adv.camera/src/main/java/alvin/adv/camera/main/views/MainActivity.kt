package alvin.adv.camera.main.views

import alvin.adv.camera.R
import alvin.adv.camera.cameraapp.views.CameraAppActivity
import alvin.adv.camera.main.MainContracts
import alvin.lib.mvp.views.AppCompatActivityView
import android.os.Bundle
import kotlinx.android.synthetic.main.main_activity.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.startActivity

class MainActivity : AppCompatActivityView<MainContracts.Presenter>(), MainContracts.View {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        initialize()
    }

    private fun initialize() {
        val buttons = arrayOf(
                btn_use_camera_app,
                btn_use_camera2,
                btn_use_camera_with_service)

        buttons.forEach {
            it.onClick {
                when (it) {
                    btn_use_camera_app -> startActivity<CameraAppActivity>()
                }
            }
        }
    }
}
