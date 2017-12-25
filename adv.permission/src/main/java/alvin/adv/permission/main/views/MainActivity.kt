package alvin.adv.permission.main.views

import alvin.adv.permission.R
import alvin.adv.permission.remoteservice.views.RemoteServiceActivityAdapter
import alvin.adv.permission.storage.views.StorageActivity
import android.os.Bundle
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.main_activity.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.startActivity

class MainActivity : DaggerAppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        initialize()
    }

    private fun initialize() {
        arrayOf(
                btn_ask_permission,
                btn_service_permission
        ).forEach {
            it.onClick {
                when (it) {
                    btn_ask_permission -> startActivity<StorageActivity>()
                    btn_service_permission -> startActivity<RemoteServiceActivityAdapter>()
                }
            }
        }
    }
}
